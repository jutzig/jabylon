package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;

public class ProjectConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectConfigSection(String id, IModel<Project> model) {
		super(id, model);
		IModel<String> nameProperty = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.RESOLVABLE__NAME); 	
		TextField<String> field = new TextField<String>("inputName", nameProperty);
		add(field);
		
		EObjectPropertyModel<PropertyType, Project> typeModel = new EObjectPropertyModel<PropertyType, Project>(model, PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE);
		DropDownChoice<PropertyType> typeChoice = new DropDownChoice<PropertyType>("inputType", typeModel, PropertyType.VALUES);
		add(typeChoice);
		
		//use actual service
		List<String> teamProviders = new ArrayList<String>();
		teamProviders.add("None");
		teamProviders.add("Git");
		teamProviders.add("CVS");
		
		EObjectPropertyModel<String, Project> teamProviderModel = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.PROJECT__TEAM_PROVIDER);

		DropDownChoice<String> teamProviderChoice = new DropDownChoice<String>("inputTeamProvider", teamProviderModel, teamProviders);
		add(teamProviderChoice);
	}

}
