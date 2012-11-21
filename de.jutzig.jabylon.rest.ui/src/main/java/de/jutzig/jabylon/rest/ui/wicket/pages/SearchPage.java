/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.pages;

import org.apache.felix.scr.annotations.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.wicket.panels.SearchResultPanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchPage extends GenericPage<Object> {

	private static final long serialVersionUID = 1L;
	
	public SearchPage(PageParameters parameters) {
		super(parameters);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void construct() {
		super.construct();
		add(new SearchResultPanel("content",getPageParameters()));
	}

	@Override
	protected IModel<Object> createModel(PageParameters params) {
		return null;
	}

	

}
