/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.resolver.impl.UserManagmentURIHandler;
import org.jabylon.properties.Project;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.UserImagePanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectPermissionsConfigSection extends BasicPanel<Project> implements RestrictedComponent {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ProjectPermissionsConfigSection.class);
    @Inject
    private URIResolver resolver;
    private IModel<UserManagement> userManagement;
    private List<UserPermission> userPermissions;
    private List<IModel<User>> assignableUsers;

    private List<RolePermission> rolePermissions;
    private List<IModel<Role>> assignableRoles;

    public ProjectPermissionsConfigSection(String id, IModel<Project> model, PageParameters params) {
        super(id, model, params);
    }

    @Override
    protected void preConstruct() {
        Object resolved = resolver.resolve(UserManagmentURIHandler.SECURITY_URI_PREFIX);
        if (resolved instanceof UserManagement) {
            UserManagement management = (UserManagement) resolved;
            userManagement = new EObjectModel<UserManagement>(management);
            assignableUsers = new ArrayList<IModel<User>>();
            userPermissions = createUserPermissions(getModel().getObject(), userManagement.getObject(), assignableUsers);
            assignableRoles = new ArrayList<IModel<Role>>();
            rolePermissions = createRolePermissions(getModel().getObject(), userManagement.getObject(), assignableRoles);
        }
    }

    private List<UserPermission> createUserPermissions(Project project, UserManagement userManagement, List<IModel<User>> assignableUsers) {
        EList<User> users = userManagement.getUsers();
        List<UserPermission> result = new ArrayList<UserPermission>();
        for (User user : users) {
            boolean filterUser = user.hasPermission(CommonPermissions.WILDCARD);
            PermissionSetting highestSetting = PermissionSetting.NONE;
            EList<Permission> allPermissions = user.getPermissions();
            for (Permission permission : allPermissions) {
                String permissionName = permission.getName();
                if (permissionName.startsWith(CommonPermissions.constructPermission(CommonPermissions.PROJECT, project.getName()))) {
                    // user already has permissions
                    if (permissionName.endsWith(CommonPermissions.ACTION_EDIT))
                        highestSetting = PermissionSetting.values()[Math.max(highestSetting.ordinal(),PermissionSetting.EDIT.ordinal())];
                    else if (permissionName.endsWith(CommonPermissions.ACTION_VIEW))
                        highestSetting = PermissionSetting.values()[Math.max(highestSetting.ordinal(),PermissionSetting.READ.ordinal())];
                    else
                        highestSetting = PermissionSetting.CONFIG;
                    filterUser = true;
                }
            }
            if(highestSetting!=PermissionSetting.NONE) {
                UserPermission userPermission = new UserPermission(new EObjectModel<User>(user), highestSetting);
                result.add(userPermission);
            }
            if (!filterUser)
                assignableUsers.add(new EObjectModel<User>(user));
        }
        return result;
    }


    private List<RolePermission> createRolePermissions(Project project, UserManagement userManagement, List<IModel<Role>> assignableRoles) {
        EList<Role> roles = userManagement.getRoles();
        List<RolePermission> result = new ArrayList<RolePermission>();
        for (Role role : roles) {
            boolean filterUser = hasPermission(role,CommonPermissions.WILDCARD);
            PermissionSetting highestSetting = PermissionSetting.NONE;
            EList<Permission> allPermissions = role.getPermissions();
            for (Permission permission : allPermissions) {
                String permissionName = permission.getName();
                if (permissionName.startsWith(CommonPermissions.constructPermission(CommonPermissions.PROJECT, project.getName()))) {
                    // user already has permissions
                    if (permissionName.endsWith(CommonPermissions.ACTION_EDIT))
                        highestSetting = PermissionSetting.values()[Math.max(highestSetting.ordinal(),PermissionSetting.EDIT.ordinal())];
                    else if (permissionName.endsWith(CommonPermissions.ACTION_VIEW))
                        highestSetting = PermissionSetting.values()[Math.max(highestSetting.ordinal(),PermissionSetting.READ.ordinal())];
                    else
                        highestSetting = PermissionSetting.CONFIG;
                    filterUser = true;
                }
            }
            if(highestSetting!=PermissionSetting.NONE) {
                RolePermission rolePermission = new RolePermission(new EObjectModel<Role>(role), highestSetting);
                result.add(rolePermission);
            }
            if (!filterUser)
                assignableRoles.add(new EObjectModel<Role>(role));
        }
        return result;
    }

    private boolean hasPermission(Role role, String permissionName) {
        EList<Permission> allPermissions = role.getAllPermissions();
        for (Permission permission : allPermissions) {
            if(permission.getName().equals(permissionName))
                return true;
        }
        return false;
    }

    @Override
    protected void onDetach() {
        if(userManagement!=null)
            userManagement.detach();
        if(assignableUsers!=null)
        {
            for (IModel<User> user : assignableUsers) {
                user.detach();
            }
        }
        if(assignableRoles!=null)
        {
            for (IModel<Role> role : assignableRoles) {
                role.detach();
            }
        }
        super.onDetach();
    }

    @Override
    protected void construct() {
        setOutputMarkupId(true);

        RefreshingView<RolePermission> roleDataView = new RefreshingView<RolePermission>("rolePermissionRow") {

            private static final long serialVersionUID = 1L;

            @Override
            protected Iterator<IModel<RolePermission>> getItemModels() {
                ModelIteratorAdapter<RolePermission> adapter = new ModelIteratorAdapter<RolePermission>(rolePermissions.iterator()) {

                    @Override
                    protected IModel<RolePermission> model(RolePermission object) {
                        return new CompoundPropertyModel<RolePermission>(object);
                    }
                };
                return adapter;
            }

            @Override
            protected void populateItem(Item<RolePermission> item) {
                item.add(new Label("registrant", item.getModelObject().getRegistrant().getObject().getName()));
                final DropDownChoice<PermissionSetting> permissionChoice = new DropDownChoice<PermissionSetting>("permission", Arrays.asList(PermissionSetting
                        .values()), new PermissionSettingRenderer());
                permissionChoice.setOutputMarkupId(true);

                permissionChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                    private static final long serialVersionUID = 1L;

                    protected void onUpdate(AjaxRequestTarget target) {
                        target.add(permissionChoice);
                    }
                });
                item.add(permissionChoice);

            }

        };
        add(roleDataView);
        addAddRolePermissionForm(assignableRoles);

        RefreshingView<UserPermission> dataView = new RefreshingView<UserPermission>("userPermissionRow") {

            private static final long serialVersionUID = 1L;

            @Override
            protected Iterator<IModel<UserPermission>> getItemModels() {
                ModelIteratorAdapter<UserPermission> adapter = new ModelIteratorAdapter<UserPermission>(userPermissions.iterator()) {

                    @Override
                    protected IModel<UserPermission> model(UserPermission object) {
                        return new CompoundPropertyModel<UserPermission>(object);
                    }
                };
                return adapter;
            }

            @Override
            protected void populateItem(Item<UserPermission> item) {
//				item.add(new Label("registrant", item.getModelObject().getRegistrant().getObject().getName()));
                item.add(new UserImagePanel("registrant", item.getModelObject().getRegistrant()));
                final DropDownChoice<PermissionSetting> permissionChoice = new DropDownChoice<PermissionSetting>("permission", Arrays.asList(PermissionSetting
                        .values()), new PermissionSettingRenderer());
                permissionChoice.setOutputMarkupId(true);

                permissionChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

                    private static final long serialVersionUID = 1L;

                    protected void onUpdate(AjaxRequestTarget target) {
                        target.add(permissionChoice);
                    }
                });
                item.add(permissionChoice);

            }

        };
        add(dataView);
        addAddPermissionForm(assignableUsers);

    }

    protected void commit() {
        UserManagement management = (UserManagement) resolver.resolveWithTransaction(userManagement.getObject().cdoID());
        for (UserPermission permission : userPermissions) {
            User user = permission.getRegistrant().getObject();
            user = management.cdoView().getObject(user);

            /*
             * Issue #101
             * first delete all permission in case they have been reduced
             * http://github.com/jutzig/jabylon/issues/issue/101
             */
            String prefix = CommonPermissions.constructPermission(CommonPermissions.PROJECT, getModel().getObject().getName());
            EList<Permission> userPermissions = user.getPermissions();
            Iterator<Permission> it = userPermissions.iterator();
            while (it.hasNext()) {
                if (it.next().getName().startsWith(prefix))
                    it.remove();
            }

            PermissionSetting permSetting = permission.getPermission();
            if(permSetting!=PermissionSetting.NONE) {
            	Permission perm = getOrCreatePermission(
                        CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, getModel().getObject().getName(), permSetting.getPermissionName()),
                        management);
                user.getPermissions().add(perm);
            }
        }
        //now the roles
        for (RolePermission permission : rolePermissions) {
            Role role = permission.getRegistrant().getObject();
            role = management.cdoView().getObject(role);

            /*
             * Issue #101
             * first delete all permission in case they have been reduced
             * http://github.com/jutzig/jabylon/issues/issue/101
             */
            String prefix = CommonPermissions.constructPermission(CommonPermissions.PROJECT, getModel().getObject().getName());
            EList<Permission> userPermissions = role.getPermissions();
            Iterator<Permission> it = userPermissions.iterator();
            while (it.hasNext()) {
                if (it.next().getName().startsWith(prefix))
                    it.remove();
            }

            PermissionSetting permSetting = permission.getPermission();
            if(permSetting!=PermissionSetting.NONE) {
            	Permission perm = getOrCreatePermission(
                        CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, getModel().getObject().getName(), permSetting.getPermissionName()),
                        management);
                role.getPermissions().add(perm);
            }
        }
        CDOTransaction transaction = (CDOTransaction) management.cdoView();
        try {
            transaction.commit();
        } catch (CommitException e) {
            logger.error("Failed to commit new permission settings",e);
        }
    }

    private Permission getOrCreatePermission(String permissionName, UserManagement management) {
        Permission permission = management.findPermissionByName(permissionName);
        if(permission==null) {
            permission = UsersFactory.eINSTANCE.createPermission();
            management.getPermissions().add(permission);
            permission.setName(permissionName);
        }
        return permission;
    }

    public static class ProjectPermissionsConfigSectionContributor extends AbstractConfigSection<Project> {

        private static final long serialVersionUID = 1L;
        private ProjectPermissionsConfigSection projectPermissionsConfigSection;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences config) {

            projectPermissionsConfigSection = new ProjectPermissionsConfigSection(id, input, new PageParameters());
            return projectPermissionsConfigSection;
        }

        @Override
        public void commit(IModel<Project> input, Preferences config) {
            projectPermissionsConfigSection.commit();

        }

        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if (getDomainObject() != null)
                projectName = getDomainObject().getName();
            return CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, projectName, CommonPermissions.ACTION_CONFIG);
        }
    }

    private void addAddPermissionForm(final List<IModel<User>> assignableUsers) {
        /*
         * Add permission form
         */
        CompoundPropertyModel<UserPermission> addPermissionModel = new CompoundPropertyModel<UserPermission>(new UserPermission());
        Form<UserPermission> addPermissionForm = new Form<UserPermission>("addUserPermissionForm", addPermissionModel);
        DropDownChoice<IModel<User>> registrantChoice = new DropDownChoice<IModel<User>>("registrant", assignableUsers, new UserRenderer());

        addPermissionForm.add(registrantChoice);

        addPermissionForm.add(new DropDownChoice<PermissionSetting>("permission", Arrays.asList(PermissionSetting.values()), new PermissionSettingRenderer()));
        AjaxButton button = new AjaxButton("addPermissionButton", addPermissionForm) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                UserPermission userPermission = (UserPermission) form.getModel().getObject();
                if (userPermission.getPermission() == null) {
                    return;
                }
                UserPermission copy = new UserPermission(userPermission.getRegistrant(), userPermission.getPermission());
                userPermissions.add(copy);

                Collections.sort(userPermissions);

                // remove registrant from available choices
                assignableUsers.remove(userPermission.getRegistrant());

                // force the panel to refresh
                target.add(ProjectPermissionsConfigSection.this);
            }
        };
        addPermissionForm.add(button);

        // only show add permission form if we have a registrant choice
        if (assignableUsers.isEmpty())
            addPermissionForm.setVisible(false);
        else
            registrantChoice.setDefaultModelObject(assignableUsers.get(0));
        add(addPermissionForm);
    }

    private void addAddRolePermissionForm(final List<IModel<Role>> assignableRoles) {
        /*
         * Add permission form
         */
        CompoundPropertyModel<RolePermission> addPermissionModel = new CompoundPropertyModel<RolePermission>(new RolePermission());
        Form<RolePermission> addPermissionForm = new Form<RolePermission>("addRolePermissionForm", addPermissionModel);
        DropDownChoice<IModel<Role>> registrantChoice = new DropDownChoice<IModel<Role>>("registrant", assignableRoles, new RoleRenderer());

        addPermissionForm.add(registrantChoice);

        addPermissionForm.add(new DropDownChoice<PermissionSetting>("permission", Arrays.asList(PermissionSetting.values()), new PermissionSettingRenderer()));
        AjaxButton button = new AjaxButton("addPermissionButton", addPermissionForm) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                RolePermission rolePermission = (RolePermission) form.getModel().getObject();
                if (rolePermission.getPermission() == null) {
                    return;
                }
                RolePermission copy = new RolePermission(rolePermission.getRegistrant(), rolePermission.getPermission());
                rolePermissions.add(copy);

                Collections.sort(rolePermissions);

                // remove registrant from available choices
                assignableRoles.remove(rolePermission.getRegistrant());

                // force the panel to refresh
                target.add(ProjectPermissionsConfigSection.this);
            }
        };
        addPermissionForm.add(button);

        // only show add permission form if we have a registrant choice
        if (assignableRoles.isEmpty())
            addPermissionForm.setVisible(false);
        else
            registrantChoice.setDefaultModelObject(assignableRoles.get(0));
        add(addPermissionForm);
    }

    @Override
    public String getRequiredPermission() {
        return CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, getModelObject().getName(), CommonPermissions.ACTION_CONFIG);
    }


}

enum PermissionSetting {
    NONE(null), READ(CommonPermissions.ACTION_VIEW), SUGGEST(CommonPermissions.ACTION_SUGGEST), EDIT(CommonPermissions.ACTION_EDIT), CONFIG(CommonPermissions.ACTION_CONFIG);
    
    String permissionName;
    
    private PermissionSetting(String permissionName) {
    	this.permissionName = permissionName;
	}
    
    public String getPermissionName() {
		return permissionName;
	}
}

class PermissionSettingRenderer implements IChoiceRenderer<PermissionSetting> {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getDisplayValue(PermissionSetting object) {
        switch (object) {
        case NONE:
            return "None";
        case READ:
            return "View";
        case SUGGEST:
            return "Suggest";
        case EDIT:
            return "Edit";
        case CONFIG:
            return "Configure";
        }
        return "None";
    }

    @Override
    public String getIdValue(PermissionSetting object, int index) {
        return object.name();
    }
}

class UserRenderer implements IChoiceRenderer<IModel<User>> {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getDisplayValue(IModel<User> object) {
        return object.getObject().getName();
    }

    @Override
    public String getIdValue(IModel<User> object, int index) {
        return object.getObject().cdoID().toString();
    }

}

class RoleRenderer implements IChoiceRenderer<IModel<Role>> {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getDisplayValue(IModel<Role> object) {
        return object.getObject()==null ? "" : object.getObject().getName();
    }

    @Override
    public String getIdValue(IModel<Role> object, int index) {
        return object.getObject().cdoID().toString();
    }

}


class UserPermission implements Serializable, Comparable<UserPermission> {

    private static final long serialVersionUID = 1L;
    private IModel<User> registrant;
    private PermissionSetting permission;

    public UserPermission(IModel<User> registrant, PermissionSetting permission) {
        super();
        this.registrant = registrant;
        this.permission = permission;
    }

    public UserPermission() {
    }

    public void setPermission(PermissionSetting permission) {
        this.permission = permission;
    }

    public void setRegistrant(IModel<User> registrant) {
        this.registrant = registrant;
    }

    public IModel<User> getRegistrant() {
        return registrant;
    }

    public PermissionSetting getPermission() {
        return permission;
    }

    @Override
    public int compareTo(UserPermission o) {
        if (registrant == null)
            return -1;
        if (o.getRegistrant().getObject().getName() == null)
            return 1;
        return registrant.getObject().getName().compareTo(o.getRegistrant().getObject().getName());
    }

}

class RolePermission implements Serializable, Comparable<RolePermission> {

    private static final long serialVersionUID = 1L;
    private IModel<Role> registrant;
    private PermissionSetting permission;

    public RolePermission(IModel<Role> registrant, PermissionSetting permission) {
        super();
        this.registrant = registrant;
        this.permission = permission;
    }

    public RolePermission() {
    }

    public void setPermission(PermissionSetting permission) {
        this.permission = permission;
    }

    public void setRegistrant(IModel<Role> registrant) {
        this.registrant = registrant;
    }

    public IModel<Role> getRegistrant() {
        return registrant;
    }

    public PermissionSetting getPermission() {
        return permission;
    }

    @Override
    public int compareTo(RolePermission o) {
        if (registrant == null)
            return -1;
        if (o.getRegistrant().getObject().getName() == null)
            return 1;
        return registrant.getObject().getName().compareTo(o.getRegistrant().getObject().getName());
    }

}
