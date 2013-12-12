/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.common.progress.ProgressService;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.security.auth.AuthenticationService;

public class Activator implements BundleActivator {

    //TODO: use injector instead and get rid of all this utility trackers
    public static final String BUNDLE_ID ="org.jabylon.rest.ui"; //$NON-NLS-1$
    private static Activator INSTANCE;
    private BundleContext context;
    private ServiceTracker<RepositoryConnector, RepositoryConnector> repositoryConnectorTracker;
    private ServiceTracker<URIResolver, URIResolver> lookupTracker;
    private ServiceTracker<ProgressService, ProgressService> progressServiceTracker;
    private ServiceTracker<AuthenticationService, AuthenticationService> authenticationServiceTracker;


    @Override
    public void start(final BundleContext context) throws Exception {
        INSTANCE = this;
        this.context = context;
        new Thread(new Runnable() {

            @Override
            public void run() {
                startTrackers();
            }
        },"UI Service Tracker").start(); //$NON-NLS-1$

    }


    private void startTrackers() {
        repositoryConnectorTracker = new ServiceTracker<RepositoryConnector, RepositoryConnector>(context, RepositoryConnector.class, null);
        repositoryConnectorTracker.open();

        lookupTracker = new ServiceTracker<URIResolver, URIResolver>(context, URIResolver.class, null);
        lookupTracker.open();

        progressServiceTracker = new ServiceTracker<ProgressService, ProgressService>(context, ProgressService.class, null);
        progressServiceTracker.open();
        
        authenticationServiceTracker = new ServiceTracker<AuthenticationService, AuthenticationService>(context, AuthenticationService.class, null);
        authenticationServiceTracker.open();

    }
    @Override
    public void stop(BundleContext context) throws Exception {
//		repositoryTracker.close();
        lookupTracker.close();
        progressServiceTracker.close();
        repositoryConnectorTracker.close();
        authenticationServiceTracker.close();
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
    
    public AuthenticationService getAuthenticationService() {
        return authenticationServiceTracker.getService();
    }

    public URIResolver getRepositoryLookup()
    {
        return lookupTracker.getService();
    }


    public BundleContext getContext() {
        return context;
    }
}
