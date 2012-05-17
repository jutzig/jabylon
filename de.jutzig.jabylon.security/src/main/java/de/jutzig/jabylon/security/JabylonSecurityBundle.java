/*
 * JabylonSecurityBundle.java
 *
 * created at 18.04.2012 by vogt <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.security;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;


public class JabylonSecurityBundle implements BundleActivator {
	public static final String BUNDLE_ID = "de.jutzig.jabylon.security";

	private static BundleContext context;
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
