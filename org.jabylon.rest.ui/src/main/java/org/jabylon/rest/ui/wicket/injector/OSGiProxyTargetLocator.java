/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.wicket.injector;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.proxy.IProxyTargetLocator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class OSGiProxyTargetLocator implements IProxyTargetLocator {



    private static final long serialVersionUID = 1L;
    private String typeName;
    private boolean isList;

    public OSGiProxyTargetLocator(Class<?> clazz, boolean isList) {
        typeName = clazz.getName();
        this.isList = isList;
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
     */
    @Override
    public Object locateProxyTarget() {
        if(isList)
            return getAllServices();
        return getSingleService();

    }

    private List<?> getAllServices() {
        Bundle bundle = FrameworkUtil.getBundle(OSGiProxyTargetLocator.class);
        BundleContext context = bundle.getBundleContext();
        ServiceReference<?>[] references;
        try {
            references = context.getAllServiceReferences(typeName, null);
            if(references!=null && references.length>0)
            {
                List<Object> services = new ArrayList<Object>(references.length);
                for (ServiceReference<?> serviceReference : references) {
                    Object service = context.getService(serviceReference);
                    if(service!=null)
                        services.add(service);
                }
                if(!services.isEmpty())
                    return services;
            }
        } catch (InvalidSyntaxException e) {
            // can't happen we use no filter
            e.printStackTrace();
        }

        return null;
    }

    private Object getSingleService() {
        Bundle bundle = FrameworkUtil.getBundle(OSGiProxyTargetLocator.class);
        BundleContext context = bundle.getBundleContext();
        ServiceReference<?> reference = context.getServiceReference(typeName);
        if(reference!=null)
            return context.getService(reference);
        return null;
    }

}
