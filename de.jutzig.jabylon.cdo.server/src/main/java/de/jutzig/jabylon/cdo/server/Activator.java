package de.jutzig.jabylon.cdo.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.net4j.CDOSessionConfiguration;
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
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.IJVMConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;

public class Activator implements BundleActivator {

	/**
	 * The shared instance
	 */
	private static Activator plugin;
	
	private BundleContext context;

	/**
	 * The Plugin ID
	 */
	public static final String PLUGIN_ID = "de.jutzig.jabylon.cdo.server";

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

	private boolean needsShutdown;
	
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
		this.context = context;
		initialize();
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		
	}

	protected void initialize() throws CommitException {
		Thread repoStarter = new Thread(new Runnable() {
			
			@Override
			public void run() {

				startRepository();
				CDOSession session = createSession();
				CDOTransaction transaction = session.openTransaction();
				try {
					initializeWorkspace(transaction);
					initializeUserManagement(transaction);
					FrameworkUtil.getBundle(getClass()).getBundleContext().registerService(IAcceptor.class, acceptor, null);
				} catch (CommitException e) {
					logger.error("Failed to initialize repository",e);
				} finally{
					transaction.close();
					session.close();
				}
				needsShutdown=true;
			}
		});
		repoStarter.setName("Repository Initializer");
		repoStarter.setDaemon(true);
		repoStarter.start();
	}

	private void initializeUserManagement(CDOTransaction transaction) throws CommitException {
		CDOResource resource = transaction.getOrCreateResource(ServerConstants.USERS_RESOURCE);
		if(resource.getContents().isEmpty())
		{
			UserManagement userManagement = UsersFactory.eINSTANCE.createUserManagement();
			userManagement.getUsers().add(getAdminUser());
			userManagement.getUsers().add(getAnonymousUser());
			resource.getContents().add(userManagement);
		}
		UserManagement userManagement = (UserManagement) resource.getContents().get(0);

		addAvailablePermissions(userManagement);
		Role adminRole = addOrUpdateAdminRole(userManagement);
		addAdminUserIfMissing(adminRole, userManagement);
		transaction.commit();

	}

	private Role addOrUpdateAdminRole(UserManagement userManagement) {
		Role adminRole = userManagement.findRoleByName("Administrator");

		if (adminRole == null)
			return addAdminRole(userManagement);
		else
			return updateAdminRole(adminRole, userManagement);
	}


	private User getAnonymousUser() {
		User anonymous = UsersFactory.eINSTANCE.createUser();
		anonymous.setName("Anonymous");
		anonymous.setPassword("changeme");
		return anonymous;
	}

	private User getAdminUser() {
		User admin = UsersFactory.eINSTANCE.createUser();
		admin.setName("Administrator");
		admin.setPassword("changeme");
		return admin;
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

	public CDOSession createSession() {

		IManagedContainer container = IPluginContainer.INSTANCE;

		IJVMConnector connector = JVMUtil.getConnector(container, "default");

		CDOSessionConfiguration config = CDONet4jUtil.createSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName(ServerConstants.REPOSITORY_NAME);

		CDOSession session = config.openSession();
		session.options().setCollectionLoadingPolicy(CDOUtil.createCollectionLoadingPolicy(0, 300));
		session.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);

		return session;
	}

	private void startRepository() {
		IPluginContainer container = IPluginContainer.INSTANCE;
		logger.info("Starting Repository");
		// initialize acceptor
		if (acceptor == null)
		{
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
		needsShutdown = false;
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
		Map<String, String> props = new HashMap<String, String>();
		// props.put(Props.PROP_SUPPORTING_REVISION_DELTAS, "false");
		// props.put(Props.PROP_CURRENT_LRU_CAPACITY, "10000");
		// props.put(Props.PROP_REVISED_LRU_CAPACITY, "10000");
		return CDOServerUtil.createRepository(ServerConstants.REPOSITORY_NAME, createStore(), props);
	}

	private IStore createStore() {
		final String DATABASE_NAME = ServerConstants.WORKING_DIR + "/cdo/derby";
		final String DATABASE_USER = "scott";
		final String DATABASE_PASS = "tiger";

		EmbeddedDataSource myDataSource = new EmbeddedDataSource();
		// myDataSource.setUser(DATABASE_USER);
		// myDataSource.setPassword(DATABASE_PASS);
		// myDataSource.setAutoReconnect(true);
		myDataSource.setDatabaseName(DATABASE_NAME);

		myDataSource.setCreateDatabase("create");
		// myDataSource.setPort(3306);
		// myDataSource.setServerName("localhost");
		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(false);
		IDBStore store = CDODBUtil.createStore(mappingStrategy, DBUtil.getDBAdapter("derby-embedded"),
				DBUtil.createConnectionProvider(myDataSource));
		mappingStrategy.setStore(store);

		return store;
	}
	
	class ShutdownThread extends Thread{
		@Override
		public void run() {
			if(needsShutdown && plugin!=null)
			{
				
				Bundle bundle = context.getBundle(0); //system bundle
				try {
					context.getBundle().stop();
					bundle.stop();
				} catch (BundleException e) {
					logger.error("Shutdown failed",e);
				}
			}
		}
	}
}