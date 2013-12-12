/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

@Component(enabled=true)
public class JabylonSecurityBundle implements BundleActivator {
    public static final String BUNDLE_ID = "org.jabylon.security";

    private static BundleContext context;
    @Reference(bind="setRepositoryConnector",unbind="unsetRepositoryConnector",policy=ReferencePolicy.DYNAMIC,cardinality=ReferenceCardinality.MANDATORY_UNARY)
    private static RepositoryConnector repositoryConnector;

    @Override
    public void start(BundleContext context) throws Exception {
        JabylonSecurityBundle.context = context;
    }


    @Override
    public void stop(BundleContext context) throws Exception {
        JabylonSecurityBundle.context = context;
    }

    public static BundleContext getBundleContext() {
        return JabylonSecurityBundle.context;
    }

    public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
        JabylonSecurityBundle.repositoryConnector = repositoryConnector;
    }

    public void unsetRepositoryConnector(RepositoryConnector repositoryConnector) {
        JabylonSecurityBundle.repositoryConnector = null;
    }

    public static RepositoryConnector getRepositoryConnector() {
        return JabylonSecurityBundle.repositoryConnector;
    }
}
