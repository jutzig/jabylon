/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.wicket.GenericPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceSettings extends GenericPage<Workspace> {

	private static final long serialVersionUID = 1L;
	
	public WorkspaceSettings(PageParameters parameters) {
		super(parameters);
		add(new WorkspaceProjectSettingsPanel("content", getModelObject(), getPageParameters()));
	}


}
