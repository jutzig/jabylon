/**
 * 
 */
package de.jutzig.jabylon.index.properties.jobs.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.index.properties.IndexActivator;
import de.jutzig.jabylon.index.properties.impl.PropertyFileAnalyzer;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.scheduler.JobContextUtil;
import de.jutzig.jabylon.scheduler.JobExecution;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class ReorgIndexJob implements JobExecution {

	
	private static final Logger logger = LoggerFactory.getLogger(ReorgIndexJob.class);
	
	/**
	 * 
	 */
	public ReorgIndexJob() {
	}

	protected IndexWriter createIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
		return new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_29), MaxFieldLength.UNLIMITED);
	}

	@Override
	public void run(Map<String, Object> jobContext) throws Exception {
		long time = System.currentTimeMillis();
		logger.info("Reorg of search index started");
		IndexWriter writer = null;
		CDOSession session = null;
		boolean closed = false;
		try {
			writer = createIndexWriter();
			writer.deleteAll();
			RepositoryConnector connector = JobContextUtil.getRepositoryConnector(jobContext);
			session = connector.createSession();
			CDOView view = session.openView();
			CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
			Workspace workspace = (Workspace) resource.getContents().get(0);
			indexWorkspace(workspace, writer);
			writer.optimize();
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
			throw e;
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

	private void indexWorkspace(Workspace workspace, IndexWriter writer) throws CorruptIndexException, IOException {
		TreeIterator<EObject> contents = workspace.eAllContents();
		PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
		while (contents.hasNext()) {
			EObject next = contents.next();
			if (next instanceof PropertyFileDescriptor) {
				PropertyFileDescriptor descriptor = (PropertyFileDescriptor) next;
				List<Document> documents = analyzer.createDocuments(descriptor);
				for (Document document : documents) {
					writer.addDocument(document);
				}
			}
		}

	}

	@Override
	public boolean retryOnError() {
		// retry if the index is currently locked
		return true;
	}
}
