package org.jabylon.rest.ui.navbar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.rest.ui.wicket.pages.WelcomePage;

public class NavbarPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(NavbarPanel.class);

    @SuppressWarnings("rawtypes")
	public NavbarPanel(String id, IModel<T> model, PageParameters parameters) {
        super(id, model, parameters);
        add(new BookmarkablePageLink<String>("jabylon",WelcomePage.class)); //$NON-NLS-1$
        Map<PanelFactory, Boolean> data = loadNavBarExtensions();

        List<PanelFactory> items = new ArrayList<PanelFactory>();
        List<PanelFactory> rightAligned = new ArrayList<PanelFactory>();



        for (Entry<PanelFactory, Boolean> entry : data.entrySet()) {
            if(entry.getValue())
                rightAligned.add(entry.getKey());
            else
                items.add(entry.getKey());
        }

        ListView<PanelFactory> listView = new ListView<PanelFactory>("items", items) { //$NON-NLS-1$

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<PanelFactory> item) {
                Panel newPanel = item.getModelObject().createPanel(getPageParameters(), NavbarPanel.this.getModel(), "content"); //$NON-NLS-1$
                if(newPanel==null)
                    item.add(new Label("content","NONE")); //$NON-NLS-1$ //$NON-NLS-2$
                else
                    item.add(newPanel);
            }
        };
        listView.setRenderBodyOnly(true);
        add(listView);

        ListView<PanelFactory> rightListView = new ListView<PanelFactory>("right-items", rightAligned) { //$NON-NLS-1$

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<PanelFactory> item) {
                Panel newPanel = item.getModelObject().createPanel(getPageParameters(), NavbarPanel.this.getModel(), "content"); //$NON-NLS-1$
                if(newPanel==null)
                    item.add(new Label("content","NONE")); //$NON-NLS-1$ //$NON-NLS-2$
                else
                    item.add(newPanel);
            }
        };
        rightListView.setRenderBodyOnly(true);
        add(rightListView);
    }


    private Map<PanelFactory,Boolean> loadNavBarExtensions() {
        Map<PanelFactory, Boolean> extensions = new LinkedHashMap<PanelFactory, Boolean>();
        IConfigurationElement[] configurationElements = RegistryFactory.getRegistry().getConfigurationElementsFor(
                "org.jabylon.rest.ui.navbarItem"); //$NON-NLS-1$

        for (IConfigurationElement element : configurationElements) {
            try {
                PanelFactory extension = (PanelFactory) element.createExecutableExtension("panel"); //$NON-NLS-1$
                String pullRight = element.getAttribute("pullRight"); //$NON-NLS-1$
                if(pullRight!=null && Boolean.valueOf(pullRight))
                    extensions.put(extension, true);
                else
                    extensions.put(extension, false);
            } catch (CoreException e) {
                logger.error("Failed to load extension "+element,e); //$NON-NLS-1$
            }
        }

        return extensions;
    }

}
