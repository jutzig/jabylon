/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.connector;

import org.jabylon.cdo.connector.internal.RepositoryConnectorImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private static Activator plugin;
    private RepositoryConnector connector;

    static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
//		context.addFrameworkListener(this);
        plugin = this;
        connector = new RepositoryConnectorImpl();
        bundleContext.registerService(RepositoryConnector.class, connector, null);
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
        connector.close();
        connector = null;

    }

//	@Override
//	public void frameworkEvent(FrameworkEvent event) {
//		if(event.getType()==FrameworkEvent.STOPPED)
//			connector.close();
//
//	}



}
