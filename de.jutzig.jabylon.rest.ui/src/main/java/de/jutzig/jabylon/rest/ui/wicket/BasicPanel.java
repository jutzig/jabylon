/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class BasicPanel<T>extends GenericPanel<T> {

	
	private transient PageParameters pageParameters;

	public BasicPanel(String id, IModel<T> model, PageParameters parameters) {
		super(id,model);
		this.pageParameters = parameters;
	}
	
	public PageParameters getPageParameters() {
		return pageParameters;
	}

	
	@Override
	public void renderHead(IHeaderResponse response) {

//		Bootstrap.renderHead(response);
		
		response.render(CssHeaderItem.forUrl("/jabylon/css/main.css"));
//		response.render(JavaScriptHeaderItem.forUrl("bootstrap/js/bootstrap.js"));
	}

	protected boolean urlEndsOnSlash()
	{
		PageParameters params = getPageParameters();
		for (NamedPair value : params.getAllNamed()) {
			if (value.getValue().isEmpty())
				return true;
		}
		
		// segments.addAll(params.getValues("segment"));
		for (int i = 0; i < params.getIndexedCount(); i++) {
			StringValue value = params.get(i);
			if (value.toString().isEmpty())
				return true;
		}
		return false;
	}
}
