/*
 * JabylonSecurityBundle.java
 *
 * created at 18.04.2012 by vogt <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.security;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;


public class JabylonSecurityBundle implements BundleActivator {
	public static final String BUNDLE_ID = "de.jutzig.jabylon.security";

	private static BundleContext context;
	private static RepositoryConnector repositoryConnector;
	private CDOTransaction transaction = null;

	@Override
	public void start(BundleContext context) throws Exception {
		JabylonSecurityBundle.context = context;
	}

	private void initializeUserManagement() {
		transaction = repositoryConnector.openTransaction();
		try {
			CDOResource resource = transaction.getResource(ServerConstants.USERS_RESOURCE);
			UserManagement userManagement = (UserManagement) resource.getContents().get(0);

			addAvailablePermissions(userManagement);
			Role adminRole = addOrUpdateAdminRole(userManagement);
			addAdminUserIfMissing(adminRole, userManagement);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			transaction.close();
			transaction = null;
		}
	}

	private Role addOrUpdateAdminRole(UserManagement userManagement) {
		Role adminRole = userManagement.findRoleByName("Administrator");

		if (adminRole == null)
			return addAdminRole(userManagement);
		else
			return updateAdminRole(adminRole, userManagement);
	}

	private Role updateAdminRole(Role adminRole, UserManagement userManagement) {
		EList<Permission> allPermissions = userManagement.getPermissions();

		for (Permission perm : allPermissions) {
			if (!adminRole.getPermissions().contains(perm))
				adminRole.getPermissions().add(perm);
		}

		return adminRole;
	}

	private Role addAdminRole(UserManagement userManagement) {
		Role adminRole = UsersFactory.eINSTANCE.createRole();
		adminRole.setName("Administrator");
		EList<Permission> allPermissions = userManagement.getPermissions();
		for (Permission perm : allPermissions) {
			adminRole.getPermissions().add(perm);
		}
		userManagement.getRoles().add(adminRole);
		return adminRole;
	}

	private void addAdminUserIfMissing(Role adminRole, UserManagement userManagement) {
		EList<User> users = userManagement.getUsers();
		for (User user : users) {
			for (Role role : user.getRoles()) {
				if (role.equals(adminRole))
					return;
			}
		}
		User admin = userManagement.findUserByName("Administrator");
		admin.getRoles().add(adminRole);
	}

	private void addAvailablePermissions(UserManagement userManagement) {
		IConfigurationElement[] permissions = RegistryFactory.getRegistry().getConfigurationElementsFor(
				"de.jutzig.jabylon.security.permission");

		EList<Permission> dbPermissions = userManagement.getPermissions();

		for (IConfigurationElement config : permissions) {
			String permissionName = config.getAttribute("name");
			if (dbContainsPermission(dbPermissions, permissionName))
				continue;

			String permissionDescription = config.getAttribute("description");
			Permission perm = UsersFactory.eINSTANCE.createPermission();
			perm.setName(permissionName);
			perm.setDescription(permissionDescription);
			dbPermissions.add(perm);
		}
	}

	private boolean dbContainsPermission(EList<Permission> dbPermissions, String permissionName) {
		for (Permission permission : dbPermissions) {
			if (permission.getName().equals(permissionName))
				return true;
		}
		return false;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		JabylonSecurityBundle.context = context;
	}

	public static BundleContext getBundleContext() {
		return JabylonSecurityBundle.context;
	}

	public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
		JabylonSecurityBundle.repositoryConnector = repositoryConnector;
		initializeUserManagement();
	}

	public void unsetRepositoryConnector(RepositoryConnector repositoryConnector) {
		JabylonSecurityBundle.repositoryConnector = null;
	}

	public static RepositoryConnector getRepositoryConnector() {
		return JabylonSecurityBundle.repositoryConnector;
	}
}
