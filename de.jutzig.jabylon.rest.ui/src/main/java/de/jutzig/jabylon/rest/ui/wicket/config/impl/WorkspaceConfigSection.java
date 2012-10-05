/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceConfigSection extends GenericPanel<Workspace> {

	private static final long serialVersionUID = -5358263608301930488L;

	public WorkspaceConfigSection(String id, IModel<Workspace> object) {
		super(id, object);
		
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
