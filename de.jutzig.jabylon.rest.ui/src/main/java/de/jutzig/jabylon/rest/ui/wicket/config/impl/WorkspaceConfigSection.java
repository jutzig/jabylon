/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceConfigSection extends GenericPanel<Workspace> {

	private static final long serialVersionUID = -5358263608301930488L;

	public WorkspaceConfigSection(String id, IModel<Workspace> object, Preferences prefs) {
		super(id, object);

		ComplexEObjectListDataProvider<Project> provider = new ComplexEObjectListDataProvider<Project>(object.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<Project> project = new ListView<Project>("projects",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Project> item) {
				item.add(new Label("project-name",item.getModelObject().getName()));
			}
		};
		add(project);
	}

}
