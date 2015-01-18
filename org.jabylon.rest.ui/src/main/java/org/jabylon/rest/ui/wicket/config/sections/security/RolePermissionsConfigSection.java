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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.cdo.connector.Modification;
import org.jabylon.cdo.connector.TransactionUtil;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.util.ApplicationConstants;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesPackage;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolePermissionsConfigSection extends BasicPanel<Role> {

	private static final long serialVersionUID = 1L;
	@Inject
	private URIResolver resolver;
	
	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionsConfigSection.class);

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
		
		ControlGroup typeGroup = new ControlGroup("type-group", nls("role.type"), nls("type.help.block"));
		List<String> typeChoices = new ArrayList<String>();
		typeChoices.add(CommonPermissions.AUTH_TYPE_DB);
		typeChoices.add(CommonPermissions.AUTH_TYPE_LDAP);
		EObjectPropertyModel<String, Role> typeModel = new EObjectPropertyModel<String, Role>(model, UsersPackage.Literals.ROLE__TYPE);
		DropDownChoice<String> typeChoice = new DropDownChoice<String>("type", typeModel, typeChoices);
		
		typeChoice.setEnabled(false);
		typeGroup.add(typeChoice);
		add(typeGroup);

		UserManagement management = null;
		if (model instanceof AttachableModel) {
			AttachableModel m = (AttachableModel) model;
			management = (UserManagement) m.getParent().getObject();
		} else {
			management = (UserManagement) container;
		}
		Set<String> usedRolenames = new HashSet<String>();
		for (Role other : management.getRoles()) {
			if (other != role)
				usedRolenames.add(other.getName());
		}

		PermissionSettingModel selected = new PermissionSettingModel(model, management);
		IModel<List<? extends String>> available = Model.ofList(computePossiblePermissions(management));
		rolename.add(new UniqueNameValidator(usedRolenames));
		Palette<String> palette = new Palette<String>("palette", selected, available, new Renderer(), 10, false);
		add(palette);
	}

	public List<String> computePossiblePermissions(UserManagement management) {
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
			permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, name, CommonPermissions.ACTION_SUGGEST));
			permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, name, CommonPermissions.ACTION_VIEW));
		}
		permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, "*", CommonPermissions.ACTION_CONFIG));
		permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, "*", CommonPermissions.ACTION_EDIT));
		permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, "*", CommonPermissions.ACTION_SUGGEST));
		permissions.add(CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, "*", CommonPermissions.ACTION_VIEW));
		return new ArrayList<String>(permissions);
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
		public List<String> getObject() {

			EList<Permission> permissions = model.getObject().getPermissions();
			List<String> names = new ArrayList<String>(permissions.size());
			for (Permission permission : permissions) {
				names.add(permission.getName());
			}
			return names;
		}

		@Override
		public void setObject(List<? extends String> object) {
			Role role = model.getObject();

			UserManagement management = getDomainObject();
			EList<Permission> permissions = management.getPermissions();
			Map<String, Permission> permissionMap = new HashMap<String, Permission>();
			for (Permission permission : permissions) {
				permissionMap.put(permission.getName(), permission);
			}
			final List<Permission> missingPermissions = new ArrayList<Permission>();
			role.getPermissions().clear();
			for (String selected : object) {
				Permission permission = permissionMap.get(selected);
				if (permission == null) {
					permission = UsersFactory.eINSTANCE.createPermission();
					permission.setName(selected);
					missingPermissions.add(permission);
				}
				role.getPermissions().add(permission);
			}
			if(!missingPermissions.isEmpty())
			{
				try {
					TransactionUtil.commit(management, new Modification<UserManagement, UserManagement>() {
						@Override
						public UserManagement apply(UserManagement object) {
							
							object.getPermissions().addAll(missingPermissions);
							return object;
						}
					});
				} catch (CommitException e) {
					LOG.error("Failed to add missing permissions: "+missingPermissions,e);
				}
			}
		}
	}
}
