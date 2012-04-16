/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal;

import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	/**
	 * @param domainClass
	 */
	public GeneralProjectConfig() {
		super(Project.class);
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		Label label = new Label();
		label.setValue("hallo welt!");
		return label;
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		// TODO Auto-generated method stub

	}

}
