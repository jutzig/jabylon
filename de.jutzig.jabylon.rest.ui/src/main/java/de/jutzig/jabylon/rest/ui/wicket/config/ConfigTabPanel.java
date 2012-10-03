package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class ConfigTabPanel<T> extends GenericPanel<T>{

	private List<ConfigSection<T>> sections;

	public ConfigTabPanel(String id, List<ConfigSection<T>> sections, IModel<T> model) {
		super(id, model);
		this.sections = sections;
	}
	
	
	
	
}
