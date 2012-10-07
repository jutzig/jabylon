package de.jutzig.jabylon.rest.ui.wicket.config;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

public interface ConfigSection<T> extends Serializable{
	
	WebMarkupContainer createContents(String id, IModel<T> input, Preferences config);
	
	void apply(Preferences config);
	
	void commit(IModel<T> input, Preferences config);
	
}
