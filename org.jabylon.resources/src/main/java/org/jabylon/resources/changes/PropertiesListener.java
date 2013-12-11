/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.resources.changes;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.jabylon.properties.PropertyFileDescriptor;

public interface PropertiesListener {

    void propertyFileAdded(PropertyFileDescriptor descriptor, boolean autoSync);

    void propertyFileDeleted(PropertyFileDescriptor descriptor, boolean autoSync);

    void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes, boolean autoSync);

}
