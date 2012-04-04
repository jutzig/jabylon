package de.jutzig.jabylon.cdo.connector;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.net4j.CDOSessionConfiguration;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.properties.PropertiesPackage;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static Activator plugin;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		plugin = this;
	}

	
	/**
	 * The repository name
	 */
	private static final String REPOSITORY_NAME = "jabylon";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private IConnector connector = null;
	private CDOResource resource = null;
	private CDOSession session = null;
	private CDOTransaction transaction = null;

	/**
	 * The constructor
	 */
	public Activator() {
	}



	/**
	 * The usual stop implementation ... BUT including some CDO cleanup.
	 */
	public void stop(BundleContext context) throws Exception {
		Activator.plugin = null;
		Activator.context = null;
		if (resource != null)
			LifecycleUtil.deactivate(resource);

		if (transaction != null)
			LifecycleUtil.deactivate(transaction);

		if (session != null)
			LifecycleUtil.deactivate(session);

		if (connector != null)
			LifecycleUtil.deactivate(connector);
	}

	/**
	 * Getter for session including lazy initialization
	 * 
	 * @return the CDO session
	 */
	public CDOSession getSession() {
		if (session == null) {
			IManagedContainer container = IPluginContainer.INSTANCE;

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
	public CDOResource getResource(String path) {
		if (resource == null) {
			/*
			 * getOrCreateResource will handle both loading existing resources
			 * (equivalent to transaction.getResource()) and
			 * creating/initializing a new one (equivalent to
			 * transaction.createResource())
			 */
			resource = getTransaction().getOrCreateResource(path);
		}

		return resource;
	}

}
