/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SettingsPanel<T extends Resolvable<?, ?>> extends BasicResolvablePanel<T> {

	private static final long serialVersionUID = 1L;

	public SettingsPanel(String id, T object, PageParameters parameters) {
		super(id, object, parameters);
		add(new ExternalLink("link","#settings","Settings"));
	}

	public static class SettingsPanelFactory implements PanelFactory
	{

		@SuppressWarnings("rawtypes")
		@Override
		public Panel createPanel(PageParameters params, Object input, String id) {

			if (input instanceof Resolvable) {
				Resolvable r = (Resolvable) input;
				return new SettingsPanel<Resolvable<?,?>>(id, r, params);
			}
			return null;
		}
		
	}
}
