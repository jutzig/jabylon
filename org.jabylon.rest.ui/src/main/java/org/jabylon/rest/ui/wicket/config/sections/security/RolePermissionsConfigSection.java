/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.util.ApplicationConstants;
import org.jabylon.properties.Project;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.model.AbstractEMFModel;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.jabylon.users.UsersPackage;
import org.osgi.service.prefs.Preferences;

public class RolePermissionsConfigSection extends BasicPanel<Role> {

	private static final long serialVersionUID = 1L;
	@Inject
	private transient URIResolver resolver;

	@SuppressWarnings("rawtypes")
	public RolePermissionsConfigSection(String id, IModel<Role> model) {
		super(id, model);
		ControlGroup rolenameGroup = new ControlGroup("rolename-group", nls("rolename"));
		RequiredTextField<String> rolename = new RequiredTextField<String>("rolename", new EObjectPropertyModel<String, Role>(model,
				UsersPackage.Literals.ROLE__NAME));
		rolenameGroup.add(rolename);
		add(rolenameGroup);
		Role role = model.getObject();
		EObject container = role.eContainer();

		UserManagement management = null;
		if (model instanceof AttachableModel) {
			AttachableModel m = (AttachableModel) model;
			management = (UserManagement) m.getParent();
		}
		else {
			management = (UserManagement) container;
		}
		Set<String> usedRolenames = new HashSet<String>();
		for (Role other : management.getRoles()) {
			if (other != role)
				usedRolenames.add(other.getName());
		}
			
		PermissionSettingModel selected = new PermissionSettingModel(model, management);
		IModel<Collection<? extends String>> available = Model.of(computePossiblePermissions(management));

		rolename.add(new UniqueNameValidator(usedRolenames));
		Palette<String> palette = new Palette<String>("palette", selected, available, new Renderer(), 10, false);
		add(palette);
	}
	
	public Collection<String> computePossiblePermissions(UserManagement management) {
		Workspace workspace = (Workspace) resolver.resolve(ApplicationConstants.WORKSPACE_NAME);
		Set<String> permissions = new TreeSet<String>();
		EList<Permission> available = management.getPermissions();
		for (Permission permission : available) {
			permissions.add(permission.getName());
		}
		EList<Project> children = workspace.getChildren();
		for (Project project : children) {
			String name = project.getName();
			permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, name, CommonPermissions.ACTION_CONFIG));
			permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, name, CommonPermissions.ACTION_EDIT));
			permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, name, CommonPermissions.ACTION_VIEW));
		}
		return permissions;
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

	private static class Renderer implements IChoiceRenderer<String> {

		private static final long serialVersionUID = 1L;

		@Override
		public Object getDisplayValue(String object) {
			return object;
		}

		@Override
		public String getIdValue(String object, int index) {
			return object;
		}

	}

	private static class PermissionSettingModel extends AbstractEMFModel<UserManagement, List<? extends String>> {

		private static final long serialVersionUID = 1L;
		private IModel<Role> model;
		

		public PermissionSettingModel(IModel<Role> model, UserManagement management) {
			super(management);
	        this.model = model;
		}
		
		@Override
		public void detach() {
			super.detach();
			model.detach();
		}

		@Override
		public List<String> getObject() { 
			
			EList<Permission> permissions = getDomainObject().getPermissions();
			List<String> names = new ArrayList<String>(permissions.size());
			for (Permission permission : permissions) {
				names.add(permission.getName());
			}
			return names;
		}

		@Override
		public void setObject(List<? extends String> object) {
			Role role = model.getObject();
			//get a user management in the transaction
			UserManagement management = role.cdoView().getObject(getDomainObject());
			EList<Permission> permissions = management.getPermissions();
			Map<String, Permission> permissionMap = new HashMap<String, Permission>();
			for (Permission permission : permissions) {
				permissionMap.put(permission.getName(), permission);
			}
			
			for (String selected : object) {
				Permission permission = permissionMap.get(selected);
				if(permission==null) {
					permission = UsersFactory.eINSTANCE.createPermission();
					permission.setName(selected);
					management.getPermissions().add(permission);
				}
				role.getPermissions().add(permission);
				
			}
			
			
		}
	}
}
