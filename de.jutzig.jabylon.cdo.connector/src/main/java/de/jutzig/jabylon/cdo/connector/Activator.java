package de.jutzig.jabylon.cdo.connector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

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
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}



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

	}



}
