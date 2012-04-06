package de.jutzig.jabylon.cdo.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.framework.BundleContext;

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
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		startRepository();

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
		return CDOServerUtil.createRepository(ServerConstants.REPOSITORY_NAME, createStore(),
				props);
	}

	private IStore createStore() {
		final String DATABASE_NAME = ServerConstants.WORKING_DIR+"/cdo/derby";
		final String DATABASE_USER = "scott";
		final String DATABASE_PASS = "tiger";

		EmbeddedDataSource myDataSource = new EmbeddedDataSource();
//		myDataSource.setUser(DATABASE_USER);
//		myDataSource.setPassword(DATABASE_PASS);
//		myDataSource.setAutoReconnect(true);
		myDataSource.setDatabaseName(DATABASE_NAME);
		
		myDataSource.setCreateDatabase("create");
//		myDataSource.setPort(3306);
//		myDataSource.setServerName("localhost");
		IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy(false);
		IDBStore store = CDODBUtil.createStore(mappingStrategy,
				DBUtil.getDBAdapter("derby-embedded"),
				DBUtil.createConnectionProvider(myDataSource));
		mappingStrategy.setStore(store);

		return store;
	}
}