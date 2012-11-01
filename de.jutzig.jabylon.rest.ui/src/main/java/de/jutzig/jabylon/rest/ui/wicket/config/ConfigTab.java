package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.emf.cdo.CDOObject;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Resolvable;

public class ConfigTab <T extends Resolvable<?, ?>> implements ITab {

	private static final long serialVersionUID = 1L;
	private String title;
	private List<ConfigSection<T>> sections;
	private IModel<T> model;
	private Preferences preferences;

	
	public ConfigTab(String title, List<ConfigSection<T>> sections, IModel<T> model, Preferences preferences) {
		this.title = title;
		this.sections = sections;
		this.model = model;
		this.preferences = preferences;
		
	}
	
	@Override
	public IModel<String> getTitle() {
		return Model.of(title);
	}

	@Override
	public WebMarkupContainer getPanel(String containerId) {	
		
		ConfigTabPanel panel = new ConfigTabPanel(containerId, sections, model, preferences);
		return panel;
	}

	@Override
	public boolean isVisible() {
		if(sections==null || sections.isEmpty())
			return false;
		return true;
	}

}
