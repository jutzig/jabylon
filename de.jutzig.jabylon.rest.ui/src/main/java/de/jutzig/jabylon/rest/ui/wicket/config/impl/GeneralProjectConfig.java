/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	private static final long serialVersionUID = 1L;

	@Override
	public WebMarkupContainer createContents(String id, IModel<Project> input, Preferences prefs) {
		return new ProjectConfigSection(id, input);
	}

	@Override
	public void commit(IModel<Project> input, Preferences config) {
		// TODO Auto-generated method stub
		// TODO rename on filesystem 
		
	}
	

}
