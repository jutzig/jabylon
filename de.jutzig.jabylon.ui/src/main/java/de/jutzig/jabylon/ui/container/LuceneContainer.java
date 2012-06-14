/**
 * 
 */
package de.jutzig.jabylon.ui.container;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class LuceneContainer extends AbstractInMemoryContainer<ScoreDoc, String, DocumentItem> {

	private IndexSearcher searcher;
	private ScoreDoc[] scoreDocs;
	
	public LuceneContainer(TopDocs topDocs, IndexSearcher searcher) {
		scoreDocs = topDocs.scoreDocs;
		this.searcher = searcher;
		
	}
	
	@Override
	public Collection<?> getContainerPropertyIds() {
		if(scoreDocs!=null && scoreDocs.length>0)
		{
			try {
				Document doc = searcher.doc(scoreDocs[0].doc);
				List<Fieldable> fields = doc.getFields();
				Set<String> fieldNames = new HashSet<String>(fields.size());
				for (Fieldable field : fields) {
					fieldNames.add(field.name());
				}
				return fieldNames;
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return String.class;
	}

	
	
	@Override
	protected DocumentItem getUnfilteredItem(Object itemId) {
		ScoreDoc docs = (ScoreDoc)itemId;
		try {
			return new DocumentItem(getDoc(docs.doc));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected Document getDoc(int doc) throws CorruptIndexException, IOException {
		return searcher.doc(doc);
	}
	
	@Override
	protected List<ScoreDoc> getAllItemIds() {
		return Arrays.asList(scoreDocs);
	}
	
}

class DocumentItem implements Item
{
	
	private Document doc;
	
	
	public DocumentItem(Document doc) {
		super();
		this.doc = doc;
	}

	@Override
	public Property getItemProperty(Object id) {
		return new GenericProperty<String>(String.class, doc.get(id.toString())); 
	}
	
	@Override
	public Collection<?> getItemPropertyIds() {
		return doc.getFields();
	}
	
	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
}