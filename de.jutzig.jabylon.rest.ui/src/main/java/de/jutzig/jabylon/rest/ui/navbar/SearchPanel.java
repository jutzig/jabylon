/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	public SearchPanel(String id, IModel<T> object, PageParameters parameters) {
		super(id, object, parameters);
		TextField<Void> field = new TextField<Void>("searchfield");
		
		String placeholder = getPlaceholder(object);
		field.add(new AttributeModifier("placeholder", placeholder));
		add(field);
	}

	private String getPlaceholder(IModel<T> model) {
		if(model==null)
			return "Search";
		T object = model.getObject();
		if (object instanceof Resolvable<?, ?>) {
			Resolvable<?, ?> r = (Resolvable<?, ?>) object;
			if(r.getName()!=null && !r.getName().isEmpty())
			{
				String message = "Search {0}";
				return MessageFormat.format(message, r.getName());
			}
		}
		return "Search";
	}

	public static class SearchPanelFactory implements PanelFactory, Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public <T> Panel createPanel(PageParameters params, IModel<T> input, String id) {

			return new SearchPanel<T>(id, input, params);
		
		}
		
	}
}
