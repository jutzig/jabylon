/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.common.team.TeamProviderUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.util.PropertyResourceUtil;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.validators.TerminologyProjectValidator;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;

public class ProjectConfigSection extends BasicPanel<Project> {

    private static final long serialVersionUID = 1L;
    
    public ProjectConfigSection(String id, IModel<Project> model) {
        super(id, model, new PageParameters());
        setOutputMarkupId(true);
        ControlGroup nameGroup = new ControlGroup("name-group",nls("ProjectConfigSection.name.label"));
        IModel<String> nameProperty = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.RESOLVABLE__NAME);
        TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
        field.add(new UniqueNameValidator(getUsedProjectNames(model)));
        nameGroup.add(field);
        add(nameGroup);

        ControlGroup typeGroup = new ControlGroup("type-group",nls("ProjectConfigSection.project.type.choice"));
        EObjectPropertyModel<String, Project> typeModel = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE);
        DropDownChoice<String> typeChoice = new DropDownChoice<String>("inputType", typeModel, new ArrayList<String>(PropertyResourceUtil.getPropertyScanners().keySet()));
        
        typeGroup.add(typeChoice);
        add(typeGroup);

        //use actual service
        List<String> teamProviders = new ArrayList<String>();
        List<String> availableTeamProviders = TeamProviderUtil.getAvailableTeamProviders();
        teamProviders.add("None");
        teamProviders.addAll(availableTeamProviders);

        ControlGroup teamproviderGroup = new ControlGroup("teamprovider-group", nls("ProjectConfigSection.team.provider.choice"));
        EObjectPropertyModel<String, Project> teamProviderModel = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.PROJECT__TEAM_PROVIDER);

        DropDownChoice<String> teamProviderChoice = new DropDownChoice<String>("inputTeamProvider", teamProviderModel, teamProviders);
        teamproviderGroup.add(teamProviderChoice);
        add(teamproviderGroup);
        
        ControlGroup terminologyGroup = new ControlGroup("terminology-group", nls("ProjectConfigSection.terminology.label"), nls("ProjectConfigSection.terminology.description"));
        EObjectPropertyModel<Boolean, Project> terminologyModel = new EObjectPropertyModel<Boolean, Project>(model, PropertiesPackage.Literals.PROJECT__TERMINOLOGY);
        CheckBox terminology = new CheckBox("inputTerminology", terminologyModel);
        terminology.add(new TerminologyProjectValidator(model));
        add(terminologyGroup);
        terminologyGroup.add(terminology);
        
        ListView<String> defaultIncludes = new ListView<String>("default-includes",new ArrayList<String>(PropertyResourceUtil.getPropertyScanners().keySet())) {

			private static final long serialVersionUID = 9179714969731553212L;

			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new AttributeAppender("type", item.getModelObject()));
				PropertyScanner scanner = PropertyResourceUtil.getPropertyScanners().get(item.getModelObject());
				String[] includes = scanner.getDefaultIncludes();
				StringBuilder builder = new StringBuilder();
				for (String include : includes) {
					builder.append(include);
					builder.append("\n");
				}
				item.add(new AttributeAppender("include", builder.toString()));
				String[] excludes = scanner.getDefaultExcludes();
				builder = new StringBuilder();
				for (String exclude : excludes) {
					builder.append(exclude);
					builder.append("\n");
				}
				item.add(new AttributeAppender("exclude", builder.toString()));
				
			}
        	
		};
		add(defaultIncludes);
		

        ControlGroup announceGroup = new ControlGroup("announce-group",nls("ProjectConfigSection.project.announce.label"),nls("ProjectConfigSection.project.announce.help"));
        EObjectPropertyModel<String, Project> announceModel = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.PROJECT__ANNOUNCEMENT);
        TextArea<String> announceField = new TextArea<String>("inputAnnounce", announceModel);
        announceGroup.add(announceField);
        add(announceGroup);
    }

    private static Set<String> getUsedProjectNames(IModel<Project> model) {
        Workspace workspace = model.getObject().getParent();
        if(workspace==null) {
            if (model instanceof AttachableModel<?>) {
                AttachableModel<?> a = (AttachableModel<?>) model;
                Object parent = a.getParent().getObject();
                if (parent instanceof Workspace) {
                    workspace = (Workspace) parent;
                }

            }
        }
        Set<String> usedNames = new HashSet<String>();
        if(workspace!=null) {
            for (Project project : workspace.getChildren()) {
                if(project!=model.getObject())
                    usedNames.add(project.getName());
            }
        }
        return usedNames;
    }

}
