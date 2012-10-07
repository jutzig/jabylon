package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;

public class ProjectConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectConfigSection(String id, IModel<Project> model) {
		super(id, model);
		IModel<String> nameProperty = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.RESOLVABLE__NAME); 	
		TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
		add(field);
		
		EObjectPropertyModel<PropertyType, Project> typeModel = new EObjectPropertyModel<PropertyType, Project>(model, PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE);
		DropDownChoice<PropertyType> typeChoice = new DropDownChoice<PropertyType>("inputType", typeModel, PropertyType.VALUES);
		add(typeChoice);
		
		List<String> teamProviders = new ArrayList<String>();
		teamProviders.add("None");
		teamProviders.add("Git");
		teamProviders.add("SVN");
		teamProviders.add("CVS");
		
		EObjectPropertyModel<String, Project> teamProviderModel = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.PROJECT__TEAM_PROVIDER);

		DropDownChoice<String> teamProviderChoice = new DropDownChoice<String>("inputTeamProvider", teamProviderModel, teamProviders);
		add(teamProviderChoice);
		
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
