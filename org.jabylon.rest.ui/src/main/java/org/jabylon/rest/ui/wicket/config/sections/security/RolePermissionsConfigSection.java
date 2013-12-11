/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;

import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;

public class RolePermissionsConfigSection extends GenericPanel<Role> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RolePermissionsConfigSection(String id, IModel<Role> model) {
        super(id, model);
        add(new RequiredTextField<String>("rolename", new EObjectPropertyModel<String, Role>(model, UsersPackage.Literals.ROLE__NAME)));
        EObject container = model.getObject().eContainer();
        ComplexEObjectListDataProvider<Permission> selected = new ComplexEObjectListDataProvider<Permission>(model, UsersPackage.Literals.ROLE__PERMISSIONS);
        ComplexEObjectListDataProvider<Permission> available = null;

        if (container instanceof UserManagement) {
            UserManagement management = (UserManagement) container;
            available = new ComplexEObjectListDataProvider(new EObjectModel<UserManagement>(management), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
        }
        else if(model instanceof AttachableModel) {
            available = new ComplexEObjectListDataProvider(((AttachableModel)model).getParent(), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
        }

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
