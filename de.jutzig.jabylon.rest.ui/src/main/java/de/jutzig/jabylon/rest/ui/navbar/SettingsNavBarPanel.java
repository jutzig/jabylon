/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SettingsNavBarPanel<T extends Resolvable<?, ?>> extends BasicResolvablePanel<T> {

	private static final long serialVersionUID = 1L;

	public SettingsNavBarPanel(String id, T object, PageParameters parameters) {
		super(id, object, parameters);
		add(new BookmarkablePageLink<String>("link",SettingsPage.class,parameters));
	}

	public static class SettingsPanelFactory implements PanelFactory, Serializable
	{

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("rawtypes")
		@Override
		public Panel createPanel(PageParameters params, Object input, String id) {

			if (input instanceof Resolvable) {
				Resolvable r = (Resolvable) input;
				return new SettingsNavBarPanel<Resolvable<?,?>>(id, r, params);
			}
			return null;
		}
		
	}
}
