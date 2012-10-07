package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;



import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class ConfigTabPanel<T> extends GenericPanel<T>{


	public ConfigTabPanel(String id, List<ConfigSection<T>> sections, final IModel<T> model) {
		super(id, model);
		Form<T> form = new Form<T>("form", model);
		ListView<ConfigSection<T>> view = new ListView<ConfigSection<T>>("sections", sections) {
			@Override
			protected void populateItem(ListItem<ConfigSection<T>> arg0) {
				ConfigSection<T> object = arg0.getModelObject();
				WebMarkupContainer container = object.createContents("content", model);
				arg0.add(container);
			}
		};
		form.add(view);
		add(form);
	}
	
	
	
	
}
