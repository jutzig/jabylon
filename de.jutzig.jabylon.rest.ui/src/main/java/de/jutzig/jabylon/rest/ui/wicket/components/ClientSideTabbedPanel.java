package de.jutzig.jabylon.rest.ui.wicket.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ClientSideTabbedPanel<T extends ITab> extends Panel {

	private List<T> tabs;
	private List<WebMarkupContainer> tabContents; 
	private IModel<Integer> activeTab;

	public ClientSideTabbedPanel(final String id, List<T> tabs) {
		super(id);
		this.tabs = tabs;
		ListView<T> listView = new ListView<T>("tab-handles",tabs) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<T> item) {
				int index = item.getIndex();
				if(index==0)
					item.add(new AttributeAppender("class", " active"));
				item.add(new ExternalLink("link", Model.of("#"+id+index), item.getModelObject().getTitle()));
			}
		};
		tabContents = new ArrayList<WebMarkupContainer>();
		ListView<T> tabContent = new ListView<T>("tab-content",tabs) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<T> item) {
				int index = item.getIndex();
				if(index==0)
					item.add(new AttributeAppender("class", " active"));
				item.setMarkupId(id+index);
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
		add(tabContent);
		add(listView);
	}

	private static final long serialVersionUID = 1L;

	public List<WebMarkupContainer> getTabContents() {
		return tabContents;
	}
	
}

