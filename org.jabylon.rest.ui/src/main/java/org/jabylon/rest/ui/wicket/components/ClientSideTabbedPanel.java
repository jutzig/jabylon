package org.jabylon.rest.ui.wicket.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import org.jabylon.rest.ui.util.WebContextUrlResourceReference;

public class ClientSideTabbedPanel<T extends ITab> extends Panel {

    private List<T> tabs;
    private List<WebMarkupContainer> tabContents;
    private IModel<Integer> activeTab= Model.of(0);

    public ClientSideTabbedPanel(final String id, List<T> tabs, boolean vertical, String persistenceKey) {
        super(id);
        WebMarkupContainer tabbable = new WebMarkupContainer("tabbable");

        if (vertical) {
            tabbable.add(new AttributeAppender("class", " tabs-left"));
        }
        if (persistenceKey != null) {
            tabbable.add(new AttributeModifier("data-tabsheet", persistenceKey));
        }
        add(tabbable);

        this.tabs = tabs;
        ListView<T> listView = new ListView<T>("tab-handles", tabs) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<T> item) {
                int index = item.getIndex();
                if (index == activeTab.getObject())
                    item.add(new AttributeAppender("class", " active"));
                item.add(new ExternalLink("link", Model.of("#" + id + index),
                        item.getModelObject().getTitle()));
            }
        };

        tabContents = new ArrayList<WebMarkupContainer>();
        ListView<T> tabContent = new ListView<T>("tab-content", tabs) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<T> item) {
                int index = item.getIndex();
                if (index == 0)
                    item.add(new AttributeAppender("class", " active"));
                item.setMarkupId(id + index);
                Object object = item.getDefaultModelObject();
                if (object instanceof ITab) {
                    ITab tab = (ITab) object;
                    WebMarkupContainer panel = tab.getPanel("content");
                    panel.setOutputMarkupId(true);
                    tabContents.add(panel);
                    item.add(panel);
                }
            }
        };
        tabbable.add(tabContent);
        tabbable.add(listView);
    }

    public ClientSideTabbedPanel(final String id, List<T> tabs) {
        this(id, tabs, false, null);
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("js/persistentTabs.js")));
    }

    public List<WebMarkupContainer> getTabContents() {
        return tabContents;
    }

    public void setActiveTab(IModel<Integer> activeIndex) {
        this.activeTab = activeIndex;
    }

    public void setActiveTab(int activeIndex) {
        this.activeTab = Model.of(activeIndex);
    }
}
