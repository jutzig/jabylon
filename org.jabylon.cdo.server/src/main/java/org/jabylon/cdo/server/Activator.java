/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 * (C) Coxcxpyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.h2.H2Adapter;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.IJVMConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.h2.jdbcx.JdbcDataSource;
import org.jabylon.db.migration.DBMigrator;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Workspace;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.jabylon.users.UsersPackage;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	/**
	 * The shared instance
	 */
	private static Activator plugin;

	/**
	 * The Plugin ID
	 */
	public static final String PLUGIN_ID = "org.jabylon.cdo.server";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private IJVMAcceptor acceptor = null;
	private IRepository repository = null;

	private static final Logger logger = LoggerFactory.getLogger(Activator.class);

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * The usual start implementation ...
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		initialize();

	}

	protected void initialize() throws CommitException {

		startRepository();
		CDONet4jSession session = createSession();
		CDOTransaction transaction = session.openTransaction();
		try {
			initializeWorkspace(transaction);
			initializeUserManagement(transaction);
			FrameworkUtil.getBundle(getClass()).getBundleContext().registerService(IAcceptor.class, acceptor, null);
		} catch (CommitException e) {
			logger.error("Failed to initialize repository", e);
		} finally {
			transaction.close();
			session.close();
		}
	}

	private void initializeUserManagement(CDOTransaction transaction) throws CommitException {
		CDOResource resource = transaction.getOrCreateResource(ServerConstants.USERS_RESOURCE);
		if (resource.getContents().isEmpty()) {
			UserManagement userManagement = UsersFactory.eINSTANCE.createUserManagement();
			userManagement.getUsers().add(getAdminUser());
			userManagement.getUsers().add(getAnonymousUser());
			resource.getContents().add(userManagement);
		}
		UserManagement userManagement = (UserManagement) resource.getContents().get(0);

		addAvailablePermissions(userManagement);
		Role adminRole = addOrUpdateAdminRole(userManagement);
		addOrUpdateRegisteredRole(userManagement);
		addOrUpdateAnonymousRole(userManagement);
		addAdminUserIfMissing(adminRole, userManagement);
		transaction.commit();

	}

	private Role addOrUpdateRegisteredRole(UserManagement userManagement) {
		Role role = userManagement.findRoleByName("Registered");
		if (role == null) {
			role = UsersFactory.eINSTANCE.createRole();
			role.setName("Registered");
			addPermission(userManagement, role, "Workspace:view");
			addPermission(userManagement, role, "Project:*:view");
			addPermission(userManagement, role, "Project:*:suggest");
			userManagement.getRoles().add(role);
		}
		return role;
	}

	private void addOrUpdateAnonymousRole(UserManagement userManagement) {
		Role role = userManagement.findRoleByName("Anonymous");
		if (role == null) {
			role = UsersFactory.eINSTANCE.createRole();
			role.setName("Anonymous");
			addPermission(userManagement, role, "Workspace:view");
			addPermission(userManagement, role, "Project:*:view");
			userManagement.getRoles().add(role);
		}
		User anonUser = userManagement.findUserByName(role.getName());
		if (anonUser != null && !anonUser.getRoles().contains(role))
			// add anon role if missing
			anonUser.getRoles().add(role);
	}

	private void addPermission(UserManagement userManagement, Role role, String permissionName) {
		Permission permission = getPermission(userManagement.getPermissions(), permissionName);
		if(permission==null) {
			permission = UsersFactory.eINSTANCE.createPermission();
			permission.setName(permissionName);
			userManagement.getPermissions().add(permission);
		}
		role.getPermissions().add(permission);
	}

	private Role addOrUpdateAdminRole(UserManagement userManagement) {
		Role adminRole = userManagement.findRoleByName("Administrator");

		if (adminRole == null)
			adminRole = addAdminRole(userManagement);
		EList<Permission> allPermissions = userManagement.getPermissions();
		Permission wildcardPermission = getPermission(allPermissions, "*");
		adminRole.getPermissions().add(wildcardPermission);
		return adminRole;
	}

	private User getAnonymousUser() {
		User anonymous = UsersFactory.eINSTANCE.createUser();
		anonymous.setName("Anonymous");
		anonymous.setPassword("ThereIsNoPasswordForAnonymous");
		return anonymous;
	}

	private User getAdminUser() {
		User admin = UsersFactory.eINSTANCE.createUser();
		admin.setName("Administrator");
		admin.setPassword("changeme");
		return admin;
	}

	private Role addAdminRole(UserManagement userManagement) {
		Role adminRole = UsersFactory.eINSTANCE.createRole();
		adminRole.setName("Administrator");
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
		IConfigurationElement[] permissions = RegistryFactory.getRegistry().getConfigurationElementsFor("org.jabylon.security.permission");

		EList<Permission> dbPermissions = userManagement.getPermissions();

		for (IConfigurationElement config : permissions) {
			String permissionName = config.getAttribute("name");
			if (getPermission(dbPermissions, permissionName) != null)
				continue;

			String permissionDescription = config.getAttribute("description");
			Permission perm = UsersFactory.eINSTANCE.createPermission();
			perm.setName(permissionName);
			perm.setDescription(permissionDescription);
			dbPermissions.add(perm);
		}

		if (getPermission(dbPermissions, "*") == null) {
			Permission perm = UsersFactory.eINSTANCE.createPermission();
			perm.setName("*");
			perm.setDescription("All Permissions");
			dbPermissions.add(perm);
		}
	}

	private Permission getPermission(EList<Permission> dbPermissions, String permissionName) {
		for (Permission permission : dbPermissions) {
			if (permission.getName().equals(permissionName))
				return permission;
		}
		return null;
	}

	private void initializeWorkspace(CDOTransaction transaction) throws CommitException {

		if (!transaction.hasResource(ServerConstants.WORKSPACE_RESOURCE)) {
			CDOResource resource = transaction.createResource(ServerConstants.WORKSPACE_RESOURCE);
			Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
			URI uri = URI.createFileURI(ServerConstants.WORKSPACE_DIR);
			File root = new File(ServerConstants.WORKSPACE_DIR);
			if (!root.exists())
				root.mkdirs();
			workspace.setRoot(uri);
			resource.getContents().add(workspace);
			transaction.commit();

		}

	}

	public CDONet4jSession createSession() {

		IManagedContainer container = IPluginContainer.INSTANCE;

		IJVMConnector connector = JVMUtil.getConnector(container, "default");

		CDONet4jSessionConfiguration config = CDONet4jUtil.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(ServerConstants.REPOSITORY_NAME);
		CDONet4jSession session = config.openNet4jSession();
		// see https://github.com/jutzig/jabylon/issues/148
		// disableing the chunking for now in case this is the root cause
		session.options().setCollectionLoadingPolicy(CDOUtil.createCollectionLoadingPolicy(-1, -1));
		session.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);
		session.getPackageRegistry().putEPackage(UsersPackage.eINSTANCE);
		return session;
	}

	private void startRepository() {
		IPluginContainer container = IPluginContainer.INSTANCE;
		logger.info("Starting Repository");
		// initialize acceptor
		if (acceptor == null) {
			acceptor = JVMUtil.getAcceptor(container, "default");

		}

		if (repository == null) {
			repository = createRepository();
			CDOServerUtil.addRepository(container, repository);
			logger.info("Repository Started");
		}

	}

	/**
	 * The usual stop implementation ... BUT including some CDO cleanup.
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (acceptor != null)
			LifecycleUtil.deactivate(acceptor);

		if (repository != null)
			LifecycleUtil.deactivate(repository);
	}

	/**
	 * Create and initialize/configure a repository
	 * 
	 * @return the CDO repository created
	 */
	private IRepository createRepository() {
		logger.info("Creating Repository in {}", ServerConstants.WORKING_DIR);
		Map<String, String> props = new HashMap<String, String>();
		// props.put(Props.PROP_SUPPORTING_REVISION_DELTAS, "false");
		// props.put(Props.PROP_CURRENT_LRU_CAPACITY, "10000");
		// props.put(Props.PROP_REVISED_LRU_CAPACITY, "10000");
		return CDOServerUtil.createRepository(ServerConstants.REPOSITORY_NAME, createStore(), props);
	}

	private IStore createStore() {
		final String DATABASE_NAME = ServerConstants.WORKING_DIR + "/cdo/embedded/h2;DB_CLOSE_ON_EXIT=FALSE";

		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:" + DATABASE_NAME + ";DB_CLOSE_DELAY=-1");

		// myDataSource.setCreateDatabase("create");
		// myDataSource.setPort(3306);
		// myDataSource.setServerName("localhost");
		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(false);
		// IDBStore store = CDODBUtil.createStore(mappingStrategy,
		// DBUtil.getDBAdapter("derby-embedded"),
		// DBUtil.createConnectionProvider(myDataSource));
		H2Adapter adapter = new H2Adapter();
		new DBMigrator().migrate(dataSource);

		IDBStore store = CDODBUtil.createStore(mappingStrategy, adapter, DBUtil.createConnectionProvider(dataSource));
		mappingStrategy.setStore(store);

		return store;
	}
}
