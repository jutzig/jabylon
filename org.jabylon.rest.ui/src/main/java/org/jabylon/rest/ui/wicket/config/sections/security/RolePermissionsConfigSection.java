/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;
import org.osgi.service.prefs.Preferences;

public class RolePermissionsConfigSection extends BasicPanel<Role> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RolePermissionsConfigSection(String id, IModel<Role> model) {
        super(id, model);
        ControlGroup rolenameGroup = new ControlGroup("rolename-group",nls("rolename"));
        RequiredTextField<String> rolename = new RequiredTextField<String>("rolename", new EObjectPropertyModel<String, Role>(model, UsersPackage.Literals.ROLE__NAME));
        rolenameGroup.add(rolename);
        add(rolenameGroup);
        EObject container = model.getObject().eContainer();
        ComplexEObjectListDataProvider<Permission> selected = new ComplexEObjectListDataProvider<Permission>(model, UsersPackage.Literals.ROLE__PERMISSIONS);
        ComplexEObjectListDataProvider<Permission> available = null;
        Set<String> usedRolenames = new HashSet<String>();
        if (container instanceof UserManagement) {
            UserManagement management = (UserManagement) container;
            available = new ComplexEObjectListDataProvider(new EObjectModel<UserManagement>(management), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
            for (Role role : management.getRoles()) {
            	if(role!=model.getObject())
            		usedRolenames.add(role.getName());
			}
        }
        else if(model instanceof AttachableModel) {
        	IModel parent = ((AttachableModel)model).getParent();
        	if (parent.getObject() instanceof UserManagement) {
				UserManagement management = (UserManagement) parent.getObject();
				for (Role role : management.getRoles()) {
	            	usedRolenames.add(role.getName());
				}
			}
            available = new ComplexEObjectListDataProvider(((AttachableModel)model).getParent(), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
            
        }

        rolename.add(new UniqueNameValidator(usedRolenames));
        Palette<Permission> palette = new Palette<Permission>("palette", selected, available, new Renderer(), 10, false);
        add(palette);
    }

    public static class RolePermissionsConfigSectionContributor extends AbstractConfigSection<Role> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Role> input, Preferences config) {

            return new RolePermissionsConfigSection(id, input);
        }

        @Override
        public void commit(IModel<Role> input, Preferences config) {


        }

        @Override
        public String getRequiredPermission() {
            return CommonPermissions.USER_GLOBAL_CONFIG;
        }

    }

    private static class Renderer implements IChoiceRenderer<Permission> {

        private static final long serialVersionUID = 1L;

        @Override
        public Object getDisplayValue(Permission object) {
            return object.getName();
        }

        @Override
        public String getIdValue(Permission object, int index) {
            return object.getName();
        }

    }
}
