/**
 * 
 */
package de.jutzig.jabylon.ui.search;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.GenericProperty;
import de.jutzig.jabylon.ui.container.LuceneContainer;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchResultPage implements CrumbTrail{

	
	public static final String SEARCH_ADDRESS ="?search"; //$NON-NLS-1$
	private Object scope;
	private String searchString;
	private Table table;
	private SearchResult result;
	
	public SearchResultPage(String search, Object scope)
	{
		this.scope = scope;
		this.searchString = search;
	}
	
	@Override
	public CrumbTrail walkTo(String path) {
		return null;
	}

	@Override
	public String getTrailCaption() {
		return Messages.getString("SearchResultPage_SEARCH_RESULT_TRAIL_CAPTION"); //$NON-NLS-1$
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public CDOObject getDomainObject() {
		return null;
	}

	@Override
	public Component createContents() {
		table = new Table() {
			@Override
			public void detach() {
				super.detach();
				if(result!=null)
				{
					try {
						result.getSearcher().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			protected Object getPropertyValue(Object rowId, Object colId, Property property) {
				if(property instanceof com.vaadin.ui.Field)
					return property;
				return super.getPropertyValue(rowId, colId, property);
			}
		};
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		table.setSelectable(true);
		
		search(searchString);
		return table;
	}
	
	public void search(String newSearch)
	{
		searchString = newSearch;
		if(result!=null)
		{
			try {
				result.getSearcher().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		QueryService queryService = MainDashboard.getCurrent().getQueryService();
		result = queryService.search(searchString,scope);
		if(result.getTopDocs().totalHits==0)
		{
			MainDashboard.getCurrent().getMainWindow().showNotification(Messages.getString("SearchResultPage_NO_HITS_NOTIFICATION")); //$NON-NLS-1$
		}
		else
		{
			table.setContainerDataSource(new ResultLuceneContainer(result.getTopDocs(), result.getSearcher()));
			table.setVisibleColumns(new Object[]{QueryService.FIELD_URI,QueryService.FIELD_KEY,QueryService.FIELD_VALUE});
			
			table.setColumnWidth(QueryService.FIELD_KEY, 150);
			table.setSizeFull();
			
		}
	}

	
	
}


class ResultLuceneContainer extends LuceneContainer
{

	public ResultLuceneContainer(TopDocs topDocs, IndexSearcher searcher) {
		super(topDocs, searcher);
	}
	
	
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if(propertyId.equals(QueryService.FIELD_URI))
		{
			ScoreDoc doc = (ScoreDoc)itemId;
			try {
				Document document = getDoc(doc.doc);
				Field field = document.getField(QueryService.FIELD_URI);
				String cdoID = document.getField(QueryService.FIELD_CDO_ID).stringValue();
				
				URI uri = URI.createURI(field.stringValue());
				Button button = new Button(uri.lastSegment());
				button.setDescription(uri.toString());
				button.setCaption(uri.lastSegment());
				button.setStyleName(Reindeer.BUTTON_LINK);
				CDOView view = MainDashboard.getCurrent().getWorkspace().cdoView();
				
				CDOID id = CDOIDUtil.read(cdoID);
				
				PropertyFileDescriptor descriptor = null;
				try {
					descriptor = (PropertyFileDescriptor) view.getObject(id);
				} catch (ObjectNotFoundException e) {
					//TODO: logging and handling if the object cannot be found
					System.out.println("SearchResultPage: "+e.getMessage()); //$NON-NLS-1$
					return null;
				}
				button.setData(descriptor);
				button.addListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						//TODO: walk to
						PropertyFileDescriptor descriptor = (PropertyFileDescriptor) event.getButton().getData();
						ProjectLocale locale = descriptor.getProjectLocale();
						String version = "?"+locale.getParent().getName(); //$NON-NLS-1$
						String project = locale.getParent().getParent().getName();
						if(locale.getLocale()!=null)
						{
							MainDashboard.getCurrent().getBreadcrumbs().setPath(project,version,locale.getLocale().toString(),descriptor.relativePath().toString());							
						}
						else
							MainDashboard.getCurrent().getBreadcrumbs().setPath(project,version,descriptor.relativePath().toString());
					}
				});
				GenericProperty<Button> property = new GenericProperty<Button>(Button.class, button);
				return property;
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.getContainerProperty(itemId, propertyId);
	}
	
	@Override
	public Class<?> getType(Object propertyId) {
		if(propertyId.equals(QueryService.FIELD_URI))
			return Button.class;
		return super.getType(propertyId);
	}
	
}