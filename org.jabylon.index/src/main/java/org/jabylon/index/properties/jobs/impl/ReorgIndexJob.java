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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.index.properties.IndexActivator;
import org.jabylon.index.properties.impl.PropertyFileAnalyzer;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Workspace;
import org.jabylon.scheduler.JobContextUtil;
import org.jabylon.scheduler.JobExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component(enabled=true,immediate=true)
@Service
public class ReorgIndexJob implements JobExecution {


    private static final Logger logger = LoggerFactory.getLogger(ReorgIndexJob.class);
    
    
    @org.apache.felix.scr.annotations.Property(value="true", name=JobExecution.PROP_JOB_ACTIVE)
    private String ACTIVE = JobExecution.PROP_JOB_ACTIVE;
    
    /** at 2 am every day*/
    @org.apache.felix.scr.annotations.Property(value="0 2 0 * * ?",name=JobExecution.PROP_JOB_SCHEDULE)
    private String DEFAULT_SCHEDULE = JobExecution.PROP_JOB_SCHEDULE;
    
    @org.apache.felix.scr.annotations.Property(value="Rebuild Index", name=JobExecution.PROP_JOB_NAME)
    private String NAME = JobExecution.PROP_JOB_NAME;
    
    /** at 2 am every day*/
    @org.apache.felix.scr.annotations.Property(value="Rebuilds the search index completely", name=JobExecution.PROP_JOB_DESCRIPTION)
    private String DESCRIPTION = JobExecution.PROP_JOB_DESCRIPTION;     

    /**
     *
     */
    public ReorgIndexJob() {
    }

    protected static IndexWriter createIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
        Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
        return new IndexWriter(directory, config);
    }

    @Override
    public void run(Map<String, Object> jobContext) throws Exception {
            RepositoryConnector connector = JobContextUtil.getRepositoryConnector(jobContext);
            indexWorkspace(connector, new NullProgressMonitor());
    }

    @Override
    public boolean retryOnError() {
        // retry if the index is currently locked
        return true;
    }
    
    private static void indexWorkspace(Workspace workspace, IndexWriter writer, IProgressMonitor monitor) throws CorruptIndexException, IOException {    	
        EList<Project> projects = workspace.getChildren();
        SubMonitor submon = SubMonitor.convert(monitor, projects.size()*10);
        PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
        try{
        	for (Project project : projects) {
        		SubMonitor mon = submon.newChild(10, 0);
        		mon.beginTask("", 100); //TODO that is not exactly accurate, but at least it moves :-)
        		String message = "Indexing {0}";
        		submon.setTaskName(MessageFormat.format(message, project.getName()));
        		TreeIterator<EObject> contents = project.eAllContents();
        		while (contents.hasNext()) {
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
    
    public static void indexWorkspace(RepositoryConnector connector, IProgressMonitor monitor) throws CorruptIndexException, IOException {
    	 long time = System.currentTimeMillis();
         logger.info("Reorg of search index started");
         IndexWriter writer = null;
         CDONet4jSession session = null;
         boolean closed = false;
         try {
             writer = createIndexWriter();
             writer.deleteAll();
             session = connector.createSession();
             CDOView view = session.openView();
             CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
             Workspace workspace = (Workspace) resource.getContents().get(0);
             indexWorkspace(workspace, writer, monitor);
             writer.commit();
             writer.close();
             closed = true;
         } catch (OutOfMemoryError error) {
             logger.error("Out of memory during index reorg",error);
             //As suggested by lucene documentation
             writer.close();
             closed = true;
         } catch (Exception e) {
             logger.error("Exception during index reorg. Rolling back",e);
             if (writer != null)
                 writer.rollback();
             throw new IllegalStateException("Failed to write index",e);
         } finally {
             if(session!=null)
             {
                 session.close();
             }
             if (!closed && IndexWriter.isLocked(writer.getDirectory())) {
                 IndexWriter.unlock(writer.getDirectory());
             }
         }
         long duration = (System.currentTimeMillis() - time) / 1000;
         logger.info("Search Index Reorg finished. Took {} seconds",duration);
    }    
}
