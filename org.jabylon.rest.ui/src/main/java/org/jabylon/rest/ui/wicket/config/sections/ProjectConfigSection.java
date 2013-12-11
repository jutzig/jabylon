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

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyType;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;

public class ProjectConfigSection extends GenericPanel<Project> {

    private static final long serialVersionUID = 1L;

    public ProjectConfigSection(String id, IModel<Project> model) {
        super(id, model);
        IModel<String> nameProperty = new EObjectPropertyModel<String, Project>(model, PropertiesPackage.Literals.RESOLVABLE__NAME);
        TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
        field.add(new UniqueNameValidator(getUsedProjectNames(model)));
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
