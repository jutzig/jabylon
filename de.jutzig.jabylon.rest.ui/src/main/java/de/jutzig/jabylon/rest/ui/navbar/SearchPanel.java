/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchPanel<T extends Resolvable<?, ?>> extends BasicResolvablePanel<T> {

	private static final long serialVersionUID = 1L;

	public SearchPanel(String id, T object, PageParameters parameters) {
		super(id, object, parameters);
		TextField<Void> field = new TextField<Void>("searchfield");
		String placeholder = object == null || object.getName()==null ? "Search" : "Search "+object.getName();
		field.add(new AttributeModifier("placeholder", placeholder));
		add(field);
	}

	public static class SearchPanelFactory implements PanelFactory, Serializable
	{

		@SuppressWarnings("rawtypes")
		@Override
		public Panel createPanel(PageParameters params, Object input, String id) {

			if (input instanceof Resolvable) {
				Resolvable r = (Resolvable) input;
				return new SearchPanel<Resolvable<?,?>>(id, r, params);
			}
			return null;
		}
		
	}
}
