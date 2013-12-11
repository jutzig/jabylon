/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.team;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyFileDiff;

public interface TeamProvider {

    /**
     * update the given ProjectVersion and return a list of files that have been modified due to the update operation
     * @param project
     * @param monitor
     * @return
     * @throws IOException
     */
    Collection<PropertyFileDiff> update(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException;

    Collection<PropertyFileDiff> update(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws TeamProviderException;

    void checkout(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException;

    void commit(ProjectVersion project, IProgressMonitor monitor) throws TeamProviderException;

    void commit(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws TeamProviderException;

}
