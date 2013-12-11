/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.resources.persistence;

import java.util.concurrent.ExecutionException;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.resources.changes.PropertiesListener;

public interface PropertyPersistenceService {

    void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file);

    void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoTranslate);

//	void savePropertiesAndWait(PropertyFileDescriptor descriptor, PropertyFile file);

    void addPropertiesListener(PropertiesListener listener);

    void removePropertiesListener(PropertiesListener listener);

    PropertyFile loadProperties(PropertyFileDescriptor descriptor) throws ExecutionException;

    /**
     * Loads the file with given descriptor id
     * <p>
     * <strong>Important:</strong> the returned property file may be a shared copy
     * copy that is used concurrently in other threads.
     * when manipulating the object, or iterating over its contents, the caller needs
     * to synchronize the access.
     * <p>
     * The caller may choose to create a local copy of the object for exclusive access
     * @param descriptor
     * @see EcoreUtil#copy(org.eclipse.emf.ecore.EObject)
     * @return a PropertyFile
     * @throws ExecutionException if the file cannot be loaded
     */
    PropertyFile loadProperties(CDOID descriptor) throws ExecutionException;
    
    /**
     * clears the internal property cache.
     * This is useful for instance after an update or rescan operation to make sure all clients see 
     * up to date information
     */
    void clearCache();

}
