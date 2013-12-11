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
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;

import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;

public class UserRolesConfigSection extends GenericPanel<User> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public UserRolesConfigSection(String id, IModel<User> model) {
        super(id, model);
        EObject container = model.getObject().eContainer();
        ComplexEObjectListDataProvider<Role> userRoles = new ComplexEObjectListDataProvider<Role>(model, UsersPackage.Literals.USER__ROLES);
        ComplexEObjectListDataProvider<Role> availableRoles = null;

        if (container instanceof UserManagement) {
            UserManagement management = (UserManagement) container;
            availableRoles = new ComplexEObjectListDataProvider(new EObjectModel<UserManagement>(management), UsersPackage.Literals.USER_MANAGEMENT__ROLES);
        }
        else if(model instanceof AttachableModel) {
            availableRoles = new ComplexEObjectListDataProvider(((AttachableModel)model).getParent(), UsersPackage.Literals.USER_MANAGEMENT__ROLES);
        }

        Palette<Role> palette = new Palette<Role>("palette", userRoles, availableRoles, new Renderer(), 10, false);
        add(palette);
    }

    public static class UserRolesConfigSectionContributor extends AbstractConfigSection<User> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<User> input, Preferences config) {

            return new UserRolesConfigSection(id, input);
        }

        @Override
        public void commit(IModel<User> input, Preferences config) {


        }


        @Override
        public String getRequiredPermission() {
            return CommonPermissions.USER_GLOBAL_CONFIG;
        }
    }

    private static class Renderer implements IChoiceRenderer<Role> {

        private static final long serialVersionUID = 1L;

        @Override
        public Object getDisplayValue(Role object) {
            return object.getName();
        }

        @Override
        public String getIdValue(Role object, int index) {
            return object.getName();
        }

    }
}
