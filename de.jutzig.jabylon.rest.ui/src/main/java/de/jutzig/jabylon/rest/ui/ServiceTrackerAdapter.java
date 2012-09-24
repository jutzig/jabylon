package de.jutzig.jabylon.rest.ui;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class ServiceTrackerAdapter<T> implements ServiceTrackerCustomizer<T, T> {

	@Override
	public T addingService(ServiceReference<T> reference) {
		return null;
	}

	@Override
	public void modifiedService(ServiceReference<T> reference, T service) {
		
	}

	@Override
	public void removedService(ServiceReference<T> reference, T service) {
		
	}

}
