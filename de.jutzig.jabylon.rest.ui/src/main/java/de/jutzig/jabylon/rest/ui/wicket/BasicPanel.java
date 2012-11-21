/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

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

	private static final long serialVersionUID = 1L;
	private transient PageParameters pageParameters;

	public BasicPanel(String id, IModel<T> model, PageParameters parameters) {
		super(id,model);
		this.pageParameters = parameters;
	}
	
	public PageParameters getPageParameters() {
		return pageParameters;
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
