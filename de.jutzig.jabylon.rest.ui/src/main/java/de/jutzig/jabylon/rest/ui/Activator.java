package de.jutzig.jabylon.rest.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;

public class Activator implements BundleActivator {

	private static Activator INSTANCE;
	private BundleContext context;
	private RepositoryConnector repositoryConnector;
	private ServiceTracker<RepositoryConnector, RepositoryConnector> repositoryTracker;
	
	@Override
	public void start(final BundleContext context) throws Exception {
		INSTANCE = this;
		this.context = context;
		repositoryTracker = new ServiceTracker<RepositoryConnector, RepositoryConnector>(context, RepositoryConnector.class, new ServiceTrackerAdapter<RepositoryConnector>() {

			@Override
			public RepositoryConnector addingService(ServiceReference<RepositoryConnector> reference) {
				repositoryConnector = context.getService(reference);
				return repositoryConnector;
			}

			@Override
			public void removedService(ServiceReference<RepositoryConnector> reference, RepositoryConnector service) {
				context.ungetService(reference);
				repositoryConnector = null;
				
			}
		});
		repositoryTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		repositoryTracker.close();
		INSTANCE = null;
		this.context = null;
	}
	
	public static Activator getDefault()
	{
		return INSTANCE;
	}
	
	public RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}

}
