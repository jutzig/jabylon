package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ConfigTab implements ITab {

	private static final long serialVersionUID = 1L;
	private String title;
	private List<ConfigSection<?>> sections;
	private IModel<?> model;

	
	public ConfigTab(String title, List<ConfigSection<?>> sections, IModel<?> model) {
		this.title = title;
		this.sections = sections;
		this.model = model;
		
	}
	
	@Override
	public IModel<String> getTitle() {
		return Model.of(title);
	}

	@Override
	public WebMarkupContainer getPanel(String containerId) {	
		
		ConfigTabPanel<Void> panel = new ConfigTabPanel(containerId, sections, model);
		return panel;
	}

	@Override
	public boolean isVisible() {
		return sections!=null && !sections.isEmpty();
	}

}
