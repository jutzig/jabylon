package de.jutzig.jabylon.rest.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.rest.ui.model.RepositoryLookup;
import de.jutzig.jabylon.rest.ui.model.RepositoryLookupImpl;

public class Activator implements BundleActivator {

	private static Activator INSTANCE;
	private BundleContext context;
	private RepositoryConnector repositoryConnector;
	private ServiceTracker<RepositoryConnector, RepositoryConnector> repositoryTracker;
	private RepositoryLookupImpl lookup;
	private ServiceTracker<PropertyPersistenceService, PropertyPersistenceService> propertyPersistenceTracker;


	@Override
	public void start(final BundleContext context) throws Exception {
		INSTANCE = this;
		this.context = context;
		repositoryTracker = new ServiceTracker<RepositoryConnector, RepositoryConnector>(context, RepositoryConnector.class, new ServiceTrackerAdapter<RepositoryConnector>() {

			@Override
			public RepositoryConnector addingService(ServiceReference<RepositoryConnector> reference) {
				repositoryConnector = context.getService(reference);
				lookup = new RepositoryLookupImpl(repositoryConnector);
				return repositoryConnector;
			}

			@Override
			public void removedService(ServiceReference<RepositoryConnector> reference, RepositoryConnector service) {
				context.ungetService(reference);
				lookup.dispose();
				lookup = null;
				repositoryConnector = null;

			}
		});
		repositoryTracker.open();
		
		propertyPersistenceTracker = new ServiceTracker<PropertyPersistenceService, PropertyPersistenceService>(context, PropertyPersistenceService.class, null);
		propertyPersistenceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		repositoryTracker.close();
		propertyPersistenceTracker.close();
		INSTANCE = null;
		if(lookup!=null)
		{
		    lookup.dispose();
		    lookup = null;
		}
		this.context = null;
	}

	public static Activator getDefault()
	{
		return INSTANCE;
	}

	public RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}

	public RepositoryLookup getRepositoryLookup()
    {
        return lookup;
    }
	
	public PropertyPersistenceService getPropertyPersistenceService()
	{
		return propertyPersistenceTracker.getService();
	}
}
