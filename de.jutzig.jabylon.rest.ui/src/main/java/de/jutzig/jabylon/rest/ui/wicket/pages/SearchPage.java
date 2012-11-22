/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.pages;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.rest.ui.wicket.panels.SearchResultPanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchPage extends GenericPage<String> {

	public static final String MAX_HITS = "m";

	public static final String SEARCH_TERM = "t";

	public static final String SCOPE = "s";

	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient QueryService queryService;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchPage.class);
	
	public SearchPage(PageParameters parameters) {
		super(parameters);
		setStatelessHint(true);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void construct() {
		super.construct();
		String term = getSearchTerm(getPageParameters());
		String scope = getSearchScope(getPageParameters());
		if(term==null)
			add(new Label("content","no search term entered"));
		else
		{
			SearchResult result = search(term,scope, getPageParameters().get(MAX_HITS).toInt(50));
			add(new SearchResultPanel("content",result,getPageParameters()));
			
		}
	}

	private SearchResult search(String term, String scope, int maxHits) {
		return queryService.search(term, scope);
	}

	private String getSearchTerm(PageParameters params) {
		StringValue value = params.get(SEARCH_TERM);
		if(value.isEmpty())
			return null;
		return value.toString();
	}
	
	private String getSearchScope(PageParameters params) {
		StringValue value = params.get(SCOPE);
		if(value.isEmpty())
			return null;
		return value.toString();
	}

	@Override
	protected IModel<String> createModel(PageParameters params) {
		StringValue value = params.get("uri");
		if(value.isEmpty())
			return null;
		return Model.of(value.toString());
	}
	
	@Override
	public boolean isBookmarkable() {
		return true;
	}

}
