package de.jutzig.jabylon.index.properties.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.common.notify.Notification;

import de.jutzig.jabylon.index.properties.IndexActivator;
import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;

public class PropertyIndex extends Job implements PropertiesListener {

	BlockingQueue<DocumentTuple> writes;

	public PropertyIndex() {
		super("Index Job");
		writes = new ArrayBlockingQueue<DocumentTuple>(50);
	}

	private IndexWriter createIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();

		return new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_29), MaxFieldLength.UNLIMITED);
	}

	@Override
	public void propertyFileAdded(PropertyFileDescriptor descriptor, boolean autoSync) {

		PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
		List<Document> documents = analyzer.createDocuments(descriptor);
		try {
			writes.put(new DocumentTuple(documents));
			schedule();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void propertyFileDeleted(PropertyFileDescriptor descriptor, boolean autoSync) {
		try {
			writes.put(new DocumentTuple(descriptor));
			schedule();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes, boolean autoSync) {
		PropertyFileAnalyzer analyzer = new PropertyFileAnalyzer();
		List<Document> documents = analyzer.createDocuments(descriptor);
		try {
			writes.put(new DocumentTuple(descriptor, documents));
			schedule();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IndexWriter writer = null;
		try {
			writer = createIndexWriter();
			while (true) {
				DocumentTuple documentTuple = writes.poll();
				if (documentTuple == null)
					break;
				List<Document> documents = documentTuple.getDocuments();
				switch (documentTuple.getAction()) {
				case CREATE:
					for (Document document : documents) {
						writer.addDocument(document);
					}
					break;
				case DELETE:
					StringBuilder builder = new StringBuilder();
					CDOIDUtil.write(builder, documentTuple.getDescriptor().cdoID());
					writer.deleteDocuments(new Term(QueryService.FIELD_CDO_ID, builder.toString()));
					break;
				case REPLACE:
					writer.deleteDocuments(new Term(QueryService.FIELD_URI, documentTuple.getDescriptor().fullPath().toString()));
					for (Document document : documents) {
						writer.addDocument(document);
					}
					break;

				default:
					break;
				}

			}
			writer.commit();

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return Status.OK_STATUS;
	}

	@Override
	public boolean belongsTo(Object family) {
		return IndexWriter.class == family;
	}

}

class DocumentTuple {
	private List<Document> docs;
	private DocumentAction action;
	private PropertyFileDescriptor descriptor;

	public DocumentTuple(List<Document> docs) {
		super();
		this.docs = docs;
		this.action = DocumentAction.CREATE;
	}

	public DocumentTuple(PropertyFileDescriptor descriptor) {
		super();
		this.descriptor = descriptor;
		this.action = DocumentAction.DELETE;
	}

	public DocumentTuple(PropertyFileDescriptor descriptor, List<Document> docs) {
		super();
		this.descriptor = descriptor;
		this.docs = docs;
		this.action = DocumentAction.REPLACE;
	}

	public DocumentAction getAction() {
		return action;
	}

	public List<Document> getDocuments() {
		return docs;
	}

	public PropertyFileDescriptor getDescriptor() {
		return descriptor;
	}

}

enum DocumentAction {
	CREATE, DELETE, REPLACE;
}
