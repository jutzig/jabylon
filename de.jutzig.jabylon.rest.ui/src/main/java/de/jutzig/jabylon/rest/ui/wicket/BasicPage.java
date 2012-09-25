/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class BasicPage extends WebPage {

	
	public BasicPage() {
		super();
	}

	public BasicPage(IModel<?> model) {
		super(model);
	}

	public BasicPage(PageParameters parameters) {
		super(parameters);
	}
	
	

	@Override
	public void renderHead(IHeaderResponse response) {

//		Bootstrap.renderHead(response);
		
		response.render(CssHeaderItem.forUrl("css/main.css"));
//		response.render(JavaScriptHeaderItem.forUrl("/js/main.js"));
	}

}
