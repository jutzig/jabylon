package de.jutzig.jabylon.rest.ui.navbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.RegistryFactory;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

public class NavbarPanel<T extends Resolvable<?, ?>> extends BasicResolvablePanel<T> {

	private static final long serialVersionUID = 1L;

	public NavbarPanel(String id, T object, PageParameters parameters) {
		super(id, object, parameters);
		List<PanelFactory> data = loadNavBarExtensions();

		ListView<PanelFactory> listView = new ListView<PanelFactory>("items", data) {
			@Override
			protected void populateItem(ListItem<PanelFactory> item) {
				Panel newPanel = item.getModelObject().createPanel(getPageParameters(), NavbarPanel.this.getModelObject(), "content");
				item.add(newPanel);
			}
		};
		listView.setRenderBodyOnly(true);
		add(listView);
	}

	private List<PanelFactory> loadNavBarExtensions() {
		List<PanelFactory> extensions = new ArrayList<PanelFactory>();
		IConfigurationElement[] configurationElements = RegistryFactory.getRegistry().getConfigurationElementsFor(
				"de.jutzig.jabylon.rest.ui.navbarItem");

		for (IConfigurationElement element : configurationElements) {
			try {
				PanelFactory extension = (PanelFactory) element.createExecutableExtension("panel");
				extensions.add(extension);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return extensions;
	}

}
