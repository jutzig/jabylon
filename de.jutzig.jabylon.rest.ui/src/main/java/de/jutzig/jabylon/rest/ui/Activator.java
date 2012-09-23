package de.jutzig.jabylon.rest.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		ServiceTracker<HttpService, HttpService> httpTracker = new ServiceTracker<HttpService, HttpService>(context, HttpService.class,
				new ServiceTrackerCustomizer<HttpService, HttpService>() {

					@Override
					public HttpService addingService(ServiceReference<HttpService> reference) {
						HttpService httpService = context.getService(reference);
						try {
							httpService.registerResources("/resources", "/WebContent", null);
						} catch (NamespaceException e) {
							// log that the namespace is already used
						}
						return httpService;
					}

					@Override
					public void modifiedService(ServiceReference<HttpService> reference, HttpService service) {
						// TODO Auto-generated method stub

					}

					@Override
					public void removedService(ServiceReference<HttpService> reference, HttpService service) {
						service.unregister("/resources");

					}
				});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
