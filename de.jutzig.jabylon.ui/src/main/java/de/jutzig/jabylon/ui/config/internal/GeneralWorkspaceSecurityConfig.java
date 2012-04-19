package de.jutzig.jabylon.ui.config.internal;

import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;

public class GeneralWorkspaceSecurityConfig extends AbstractConfigSection
		implements ConfigSection {

	public GeneralWorkspaceSecurityConfig() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component createContents() {
		// TODO Auto-generated method stub
		return new Label();
	}

	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init(Preferences config) {
		// TODO Auto-generated method stub

	}

}
