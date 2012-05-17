package de.jutzig.jabylon.cdo.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.core.runtime.Plugin;
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
import org.eclipse.emf.common.util.URI;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.IJVMConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;

public class Activator extends Plugin {

	/**
	 * The shared instance
	 */
	private static Activator plugin;

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
		super.start(context);
		plugin = this;
		startRepository();
		initializeWorkspace();
		initializeUserManagement();
	}

	private void initializeUserManagement() {
		CDOSession session = createSession();
		CDOTransaction transaction = session.openTransaction();
		if(!transaction.hasResource(ServerConstants.USERS_RESOURCE)) {
			CDOResource resource = transaction.createResource(ServerConstants.USERS_RESOURCE);
			UserManagement userManagement = UsersFactory.eINSTANCE.createUserManagement();
			userManagement.getUsers().add(getAdminUser());
			userManagement.getUsers().add(getAnonymousUser());
			resource.getContents().add(userManagement);
			try {
				transaction.commit();
			} catch (CommitException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		transaction.close();
		session.close();
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

	private void initializeWorkspace() {
		CDOSession session = createSession();
		CDOTransaction transaction = session.openTransaction();
		if(!transaction.hasResource(ServerConstants.WORKSPACE_RESOURCE))
		{
			CDOResource resource = transaction.createResource(ServerConstants.WORKSPACE_RESOURCE);
			Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
			URI uri = URI.createFileURI(ServerConstants.WORKSPACE_DIR);
			File root = new File(ServerConstants.WORKSPACE_DIR);
			if (!root.exists())
				root.mkdirs();
			workspace.setRoot(uri);
			resource.getContents().add(workspace);
			try {
				transaction.commit();
			} catch (final CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		transaction.close();
		session.close();

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

		// initialize acceptor
		if (acceptor == null)
			acceptor = JVMUtil.getAcceptor(container, "default");

		if (repository == null) {
			repository = createRepository();
			CDOServerUtil.addRepository(container, repository);
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

		super.stop(context);
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
}