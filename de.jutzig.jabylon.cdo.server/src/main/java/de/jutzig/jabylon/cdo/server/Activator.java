package de.jutzig.jabylon.cdo.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSessionConfiguration;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;

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
	 * The repository name
	 */
	private static final String REPOSITORY_NAME = "your_repositoryname";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private IJVMAcceptor acceptor = null;
	private IConnector connector = null;
	private IRepository repository = null;
	private CDOResource resource = null;
	private CDOSession session = null;
	private CDOTransaction transaction = null;

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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				populate();
				
			}
		}).start();

	}

	private void populate() {
//		CDOSession cdoSession = getSession();
//		cdoSession.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);
//		CDOTransaction transaction = cdoSession.openTransaction();
//        CDOResource resource = transaction.getOrCreateResource("/myResource");
//        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
//
//        Property property = PropertiesFactory.eINSTANCE.createProperty();
//        property.setKey("test");
//        property.setValue("a value");
//        property.setComment("comment");
//        file.getProperties().add(property);
//
//
//        resource.getContents().add(file);
//        try {
//			transaction.commit();
//		} catch (CommitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally
//		{
//			
//			cdoSession.close();		
//			cdoSession = null;
//			session = null;
//		}
        
        CDOSession cdoSession = getSession();
        
		transaction = cdoSession.openTransaction();
        resource = transaction.getResource("/myResource");
        PropertyFile file = (PropertyFile) resource.getContents().get(0);
        Property property2 = file.getProperties().get(0);
        System.out.println(property2.getKey());
//        
		
	}

	/**
	 * The usual stop implementation ... BUT including some CDO cleanup.
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;

		if (resource != null)
			LifecycleUtil.deactivate(resource);

		if (transaction != null)
			LifecycleUtil.deactivate(transaction);

		if (session != null)
			LifecycleUtil.deactivate(session);

		if (acceptor != null)
			LifecycleUtil.deactivate(acceptor);

		if (connector != null)
			LifecycleUtil.deactivate(connector);

		if (repository != null)
			LifecycleUtil.deactivate(repository);

		super.stop(context);
	}

	/**
	 * Getter for session including lazy initialization
	 * 
	 * @return the CDO session
	 */
	public CDOSession getSession() {
		if (session == null) {
			IManagedContainer container = IPluginContainer.INSTANCE;

			// initialize acceptor
			if (acceptor == null)
				acceptor = JVMUtil.getAcceptor(container, "default");

			if (repository == null) {
				repository = createRepository();
				CDOServerUtil.addRepository(container, repository);
			}

			if (connector == null)
				connector = JVMUtil.getConnector(container, "default");

			CDOSessionConfiguration config = CDONet4jUtil
					.createSessionConfiguration();
			config.setConnector(connector);
			config.setRepositoryName(REPOSITORY_NAME);
			// config.setLegacySupportEnabled(false);

			// see explanation below
			// config.setLazyPopulatingPackageRegistry();

			session = config.openSession();
			session.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);
		}
		return session;
	}

	/**
	 * Getter for transaction including lazy initialization
	 * 
	 * @return the transaction
	 */
	public CDOTransaction getTransaction() {
		if (transaction == null) {
			transaction = getSession().openTransaction();
		}

		return transaction;
	}

	/**
	 * Getter for resource (e.g. if you use only one central resource)
	 * 
	 * @return the resource
	 */
	public CDOResource getResource() {
		if (resource == null) {
			/*
			 * getOrCreateResource will handle both loading existing resources
			 * (equivalent to transaction.getResource()) and
			 * creating/initializing a new one (equivalent to
			 * transaction.createResource())
			 */
			resource = getTransaction().getOrCreateResource("/test");
		}

		return resource;
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
		return CDOServerUtil.createRepository(REPOSITORY_NAME, createStore(),
				props);
	}

	private IStore createStore() {
		final String DATABASE_NAME = "/home/joe/git/jabylon/de.jutzif.jabylon.cdo.server/work/cdo/derby";
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