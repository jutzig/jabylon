/**
 * 
 */
package de.jutzig.jabylon.team.git.config;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConfigSection extends AbstractConfigSection<Project>{

	private static final long serialVersionUID = 1L;

	private boolean gitSelected(IModel<Project> model) {
		return "Git".equals(model.getObject().getTeamProvider());
	}


	@Override
	public WebMarkupContainer createContents(String id, IModel<Project> input, Preferences config) {
		GitConfigPanel panel = new GitConfigPanel(id, input, config);
		panel.setVisible(gitSelected(input));
		return panel;
	}

	@Override
	public boolean isVisible(IModel<Project> input, Preferences config) {
		return gitSelected(input);
	}


	@Override
	public void commit(IModel<Project> input, Preferences config) {
		// TODO Auto-generated method stub
		
	}

}
