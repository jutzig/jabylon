/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceProjectSettingsPanel extends BasicResolvablePanel<Workspace> {

	private static final long serialVersionUID = -5358263608301930488L;

	public WorkspaceProjectSettingsPanel(String id, Workspace object, PageParameters parameters) {
		super(id, object, parameters);
		
		ListView<Project> project = new ListView<Project>("projects",getModelObject().getChildren()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Project> item) {
				item.add(new Label("project-name",item.getModelObject().getName()));
			}
		};
		add(project);
	}

}
