/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	@Override
	public WebMarkupContainer createContents() {
		// TODO Auto-generated method stub
		return null;
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
