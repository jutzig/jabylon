/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui;

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
