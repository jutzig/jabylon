/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchResultPanel<T> extends GenericPanel<T> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient QueryService queryService;
	
	public SearchResultPanel(String id, PageParameters parameters) {
		super(id);
		StringValue searchString = parameters.get("search");
		if(searchString.isEmpty())
			info("No results");
		else
		{
			final SearchResult result = queryService.search(searchString.toString(), 50);
			List<ScoreDoc> list = Arrays.asList(result.getTopDocs().scoreDocs);
			ListView<ScoreDoc> repeater = new ListView<ScoreDoc>("results",list){

				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem<ScoreDoc> item) {
					ScoreDoc doc = item.getModelObject();
					Document document;
					try {
						document = result.getSearcher().doc(doc.doc);
						item.add(new Label("text", document.get(QueryService.FIELD_VALUE)));
						item.add(new Label("key", document.get(QueryService.FIELD_KEY)));
						
					} catch (CorruptIndexException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			};
			add(repeater);

		}
	}

	
	public QueryService getQueryService() {
		return queryService;
	}
	
}
