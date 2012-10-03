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

	
	public ConfigTab(String title, List<ConfigSection<?>> sections) {
		this.title = title;
		this.sections = sections;
	}
	
	@Override
	public IModel<String> getTitle() {
		return Model.of(title);
	}

	@Override
	public WebMarkupContainer getPanel(String containerId) {	
		
		return new ConfigTabPanel(containerId, sections, null);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}
