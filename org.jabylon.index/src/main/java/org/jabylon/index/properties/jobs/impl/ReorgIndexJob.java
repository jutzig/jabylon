/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.index.properties.jobs.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.index.properties.IndexActivator;
import org.jabylon.index.properties.impl.PropertyFileAnalyzer;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.types.impl.TMXConverter;
import org.jabylon.scheduler.JobExecution;
import org.jabylon.scheduler.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component(enabled=true,immediate=true)
@Service
public class ReorgIndexJob implements JobExecution {


    public static final String JOB_ID = "job.reorg.index";


	private static final Logger logger = LoggerFactory.getLogger(ReorgIndexJob.class);


    @org.apache.felix.scr.annotations.Property(value="true", name=JobExecution.PROP_JOB_ACTIVE)
    public static final String DEFAULT_ACTIVE = "true";

    /** at 2 am every day*/
    @org.apache.felix.scr.annotations.Property(value="0 0 2 * * ?",name=JobExecution.PROP_JOB_SCHEDULE)
    public static final String DEFAULT_SCHEDULE = "0 0 2 * * ?";

    @org.apache.felix.scr.annotations.Property(value="%reorg.job.name", name=JobExecution.PROP_JOB_NAME)
    private String NAME = JobExecution.PROP_JOB_NAME;

    /** at 2 am every day*/
    @org.apache.felix.scr.annotations.Property(value="%reorg.job.description", name=JobExecution.PROP_JOB_DESCRIPTION)
    private String DESCRIPTION = JobExecution.PROP_JOB_DESCRIPTION;

    /**
     *
     */
    public ReorgIndexJob() {
    }

    @Override
    public void run(IProgressMonitor monitor, Map<String, Object> jobContext) throws Exception {
            RepositoryConnector connector = JobUtil.getRepositoryConnector(jobContext);
            indexWorkspace(connector, monitor);
    }

    @Override
    public boolean retryOnError() {
        // retry if the index is currently locked
        return true;
    }

    private static void indexWorkspace(Workspace workspace, IndexWriter writer, IProgressMonitor monitor) throws CorruptIndexException, IOException {
        EList<Project> projects = workspace.getChildren();
        SubMonitor submon = SubMonitor.convert(monitor,"Rebuilding Index", projects.size()*10);
        PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
        try{
        	for (Project project : projects) {
        		SubMonitor mon = submon.newChild(10, 0);
        		mon.beginTask("", 100); //TODO that is not exactly accurate, but at least it moves :-)
        		String message = "Indexing {0}";
        		submon.setTaskName(MessageFormat.format(message, project.getName()));
        		TreeIterator<EObject> contents = project.eAllContents();
        		while (contents.hasNext()) {
        			checkCanceled(monitor);
        			EObject next = contents.next();
        			if (next instanceof PropertyFileDescriptor) {
        				PropertyFileDescriptor descriptor = (PropertyFileDescriptor) next;
        				mon.subTask(descriptor.getLocation().toString());
        				List<Document> documents = analyzer.createDocuments(descriptor);
        				for (Document document : documents) {
        					writer.addDocument(document);
        				}
        				mon.worked(1);
        			}
        		}
				mon.done();
			}
        }
        finally {
        	monitor.done();
        }
    }
    
    /**
     * analyzes the TMX directory
     * @param writer
     * @param monitor
     */
	private static void indexTMX(IndexWriter writer, IProgressMonitor monitor) {
		PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
		File workingDir = new File(ServerConstants.WORKING_DIR);
		File tmx = new File(workingDir,"tmx");
		File[] files = tmx.listFiles();
		for (File file : files) {

			TMXConverter converter = new TMXConverter();
			try {
				PropertyFile propertyFile = converter.load(new FileInputStream(file), "UTF-8");
				List<Document> documents = analyzer.createTMXDocuments(propertyFile, URI.createFileURI("tmx/"+file.getName()));
				for (Document document : documents) {
					writer.addDocument(document);
				}
				
			} catch (FileNotFoundException e) {
				logger.error("Could not load TMX file", e);
			} catch (IOException e) {
				logger.error("Could not load TMX file", e);
			}
		}
		
	}

    private static void checkCanceled(IProgressMonitor monitor) {
    	if(monitor.isCanceled())
    		throw new OperationCanceledException();

	}

	public static void indexWorkspace(RepositoryConnector connector, IProgressMonitor monitor) throws CorruptIndexException, IOException {
    	 long time = System.currentTimeMillis();
         logger.info("Reorg of search index started");
         IndexWriter writer = null;
         CDONet4jSession session = null;
         SubMonitor submon = SubMonitor.convert(monitor, 100);
         try {
             writer = IndexActivator.getDefault().obtainIndexWriter();
             writer.deleteAll();
             session = connector.createSession();
             CDOView view = connector.openView(session);
             CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
             Workspace workspace = (Workspace) resource.getContents().get(0);
             indexWorkspace(workspace, writer, submon.newChild(95));
             indexTMX(writer,submon.newChild(5));
             writer.commit();
         } catch (OutOfMemoryError error) {
             logger.error("Out of memory during index reorg",error);
             //As suggested by lucene documentation
             writer.close();
         } catch (Exception e) {
             logger.error("Exception during index reorg. Rolling back",e);
             if (writer != null)
                 writer.rollback();
             throw new IllegalStateException("Failed to write index",e);
         } finally {
        	 if(monitor!=null)
        		 monitor.done();
             if(session!=null)
             {
                 session.close();
             }
             IndexActivator.getDefault().returnIndexWriter(writer);
         }
         long duration = (System.currentTimeMillis() - time) / 1000;
         logger.info("Search Index Reorg finished. Took {} seconds",duration);
    }

	@Override
	public String getID() {
		return JOB_ID;
	}
}
