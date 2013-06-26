package de.jutzig.jabylon.rest.ui.wicket.config;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.security.RestrictedComponent;

public interface ConfigSection<T> extends Serializable, RestrictedComponent{
	
	WebMarkupContainer createContents(String id, IModel<T> input, Preferences config);
	
	void apply(Preferences config);
	
	void commit(IModel<T> input, Preferences config);
	
	boolean isVisible(IModel<T> input, Preferences config);
	
}
