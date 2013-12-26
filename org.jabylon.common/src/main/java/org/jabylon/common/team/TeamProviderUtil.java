/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamProviderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamProviderUtil.class); 
    
    public static List<String> getAvailableTeamProviders() {
    	Bundle bundle = FrameworkUtil.getBundle(TeamProviderUtil.class);
    	BundleContext context = bundle.getBundleContext();
    	List<String> providers = new ArrayList<String>();
    	try {
			Collection<ServiceReference<TeamProvider>> references = context.getServiceReferences(TeamProvider.class, null);
			for (ServiceReference<TeamProvider> reference : references) {
				Object property = reference.getProperty(TeamProvider.KEY_KIND);
        		if(property==null)
        		{
        			LOGGER.error("Team provider service has no 'kind' property. Ignoring team provider from "+reference.getBundle());
        		}
        		else
        		{
        			providers.add(property.toString());
        		}
			}
		} catch (InvalidSyntaxException e) {
			// this can't happen
			LOGGER.error("Failed to retrieve service references",e);
		}
        return providers;
    }

    public static TeamProvider getTeamProvider(String name) {
        if(name==null)
            return null;
        try {
        	Bundle bundle = FrameworkUtil.getBundle(TeamProviderUtil.class);
        	BundleContext context = bundle.getBundleContext();
			Collection<ServiceReference<TeamProvider>> references = context.getServiceReferences(TeamProvider.class, null);
			for (ServiceReference<TeamProvider> reference : references) {
				Object property = reference.getProperty(TeamProvider.KEY_KIND);
        		if(name.equals(property))
        			return context.getService(reference);
			}
		} catch (InvalidSyntaxException e) {
			// this can't happen
			LOGGER.error("Failed to retrieve service references",e);
		}
        return null;
    }



}
