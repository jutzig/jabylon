package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;

public class ProjectConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectConfigSection(String id, IModel<Project> model) {
		super(id, model);
		IModel<String> nameProperty = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.RESOLVABLE__NAME); 	
		TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
		add(field);
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
