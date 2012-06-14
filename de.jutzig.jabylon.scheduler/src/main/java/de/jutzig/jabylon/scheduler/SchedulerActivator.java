package de.jutzig.jabylon.scheduler;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.quartz.SchedulerException;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.scheduler.internal.JobRegistry;

public class SchedulerActivator implements BundleActivator {

	private static BundleContext context;
	
	public static final String PLUGIN_ID = "de.jutzig.jabylon.scheduler";
	
	private static RepositoryConnector repositoryConnector; 
	
	private static SchedulerActivator INSTANCE;

	static BundleContext getContext() {
		return context;
	}

	private JobRegistry jobRegistry;

	private ServiceReference<RepositoryConnector> connectorService;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		SchedulerActivator.context = bundleContext;
		INSTANCE = this;
		connectorService = bundleContext.getServiceReference(RepositoryConnector.class);
		repositoryConnector = bundleContext.getService(connectorService);
		registerJobs();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		SchedulerActivator.context = null;
		if(jobRegistry!=null)
			jobRegistry.shutdown();
		repositoryConnector = null;
		bundleContext.ungetService(connectorService);
		connectorService = null;
		INSTANCE = null;
		
	}

	private void registerJobs() throws SchedulerException {
		jobRegistry = new JobRegistry();
		jobRegistry.register();
	}

	public static RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}
	
	public JobRegistry getJobRegistry() {
		return jobRegistry;
	}
	
	
	public static SchedulerActivator getDefault() {
		return INSTANCE;
	}
}
