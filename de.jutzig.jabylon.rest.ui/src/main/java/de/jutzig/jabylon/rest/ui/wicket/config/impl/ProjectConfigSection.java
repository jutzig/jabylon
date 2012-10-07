package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;

public class ProjectConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectConfigSection(String id, IModel<Project> model) {
		super(id, model);
//		ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(model.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN); 
//		ListView<ProjectVersion> project = new ListView<ProjectVersion>("children", provider) {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected void populateItem(ListItem<ProjectVersion> item) {
//				item.add(new Label("name", item.getModelObject().getName()));
//			}
//		};
//		add(project);
	}

}
