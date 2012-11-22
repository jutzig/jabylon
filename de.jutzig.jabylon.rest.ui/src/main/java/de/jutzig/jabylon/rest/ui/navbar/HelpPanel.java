/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class HelpPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	public HelpPanel(String id, IModel<T> model, PageParameters parameters) {
		super(id, model, parameters);
		add(new ExternalLink("link","#help"));
	}

	public static class HelpPanelFactory implements PanelFactory, Serializable
	{

		private static final long serialVersionUID = 1L;

		@Override
		public <T> Panel createPanel(PageParameters params, IModel<T> input, String id) {

			return new HelpPanel<T>(id, input , params);
		}
		
	}
}
