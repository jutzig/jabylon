package de.jutzig.jabylon.rest.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.common.progress.ProgressService;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.rest.ui.model.RepositoryLookup;

public class Activator implements BundleActivator {

	//TODO: use injector instead and get rid of all this utility trackers
	public static final String BUNDLE_ID ="de.jutzig.jabylon.rest.ui";
	private static Activator INSTANCE;
	private BundleContext context;
	private ServiceTracker<RepositoryConnector, RepositoryConnector> repositoryConnectorTracker;
	private ServiceTracker<RepositoryLookup, RepositoryLookup> lookupTracker;
	private ServiceTracker<ProgressService, ProgressService> progressServiceTracker;


	@Override
	public void start(final BundleContext context) throws Exception {
		INSTANCE = this;
		this.context = context;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				startTrackers();
			}
		},"UI Service Tracker").start();

	}

	
	private void startTrackers() {
		repositoryConnectorTracker = new ServiceTracker<RepositoryConnector, RepositoryConnector>(context, RepositoryConnector.class, null);
		repositoryConnectorTracker.open();

		lookupTracker = new ServiceTracker<RepositoryLookup, RepositoryLookup>(context, RepositoryLookup.class, null);
		lookupTracker.open();
		
		progressServiceTracker = new ServiceTracker<ProgressService, ProgressService>(context, ProgressService.class, null);
		progressServiceTracker.open();
		
	}
	@Override
	public void stop(BundleContext context) throws Exception {
//		repositoryTracker.close();
		lookupTracker.close();
		progressServiceTracker.close();
		repositoryConnectorTracker.close();
		INSTANCE = null;
		this.context = null;
	}

	public static Activator getDefault()
	{
		return INSTANCE;
	}

	public RepositoryConnector getRepositoryConnector() {
		return repositoryConnectorTracker.getService();
	}
	
	public ProgressService getProgressService() {
		return progressServiceTracker.getService();
	}

	public RepositoryLookup getRepositoryLookup()
    {
        return lookupTracker.getService();
    }
	
	
	public BundleContext getContext() {
		return context;
	}
}
