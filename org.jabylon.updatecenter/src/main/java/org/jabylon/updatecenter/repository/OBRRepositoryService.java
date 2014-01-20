/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository;

import java.util.List;

import org.apache.felix.bundlerepository.Resource;
import org.osgi.framework.Bundle;

public interface OBRRepositoryService {

    List<Bundle> listInstalledBundles();

    List<Resource> getAvailableResources(ResourceFilter filter);

    Resource[] findResources(String id);

    void install(String resourceId) throws OBRException;

    void install(Resource... resource) throws OBRException;

}
