/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security.internal;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.apache.karaf.jaas.boot.ProxyLoginModule;
import org.apache.karaf.jaas.config.JaasRealm;
import org.jabylon.security.auth.AuthenticatorServiceImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class JabylonJaasRealmService implements JaasRealm
{
    private AppConfigurationEntry[] configEntries;

    public JabylonJaasRealmService() {
        // create the configuration entry field using ProxyLoginModule class

    	Bundle bundle = FrameworkUtil.getBundle(getClass());
    	BundleContext bc = bundle.getBundleContext();
        Map<String, Object> options = new HashMap<String, Object>();
        configEntries = new AppConfigurationEntry[2];
        configEntries[0] = new AppConfigurationEntry(ProxyLoginModule.class.getName(),
            LoginModuleControlFlag.SUFFICIENT, options);
        options.put(ProxyLoginModule.PROPERTY_MODULE, DBLoginModule.class.getName());
        long bundleId = bc.getBundle().getBundleId();
        options.put(ProxyLoginModule.PROPERTY_BUNDLE, String.valueOf(bundleId));
        options.put(BundleContext.class.getName(), bc);
        
        options = new HashMap<String, Object>();
        configEntries[1] = new AppConfigurationEntry(ProxyLoginModule.class.getName(),
                LoginModuleControlFlag.SUFFICIENT, options);
            options.put(ProxyLoginModule.PROPERTY_MODULE, LDAPLoginModule.class.getName());
            options.put(ProxyLoginModule.PROPERTY_BUNDLE, String.valueOf(bundleId));
            options.put(BundleContext.class.getName(), bc);
	}

    @Override
    public AppConfigurationEntry[] getEntries()
    {
        return configEntries;
    }

    @Override
    public String getName()
    {
        return AuthenticatorServiceImpl.REALM_NAME;
    }

    @Override
    public int getRank()
    {
        return 0;
    }   
}