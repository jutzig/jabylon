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
package org.jabylon.updatecenter.repository.impl;

import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resolver;
import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.updatecenter.repository.OBRRepositoryService;
import org.jabylon.updatecenter.repository.ResourceFilter;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
@Component
@Service
public class OBRRepositoryConnectorImpl implements OBRRepositoryService {

    private static final String DEFAULT_REPOSITORY = "file:///" + System.getProperty("user.home").replace("\\", "/") + "/.m2/repository/repository.xml";
    @Reference
    private RepositoryAdmin admin;

    private static final Logger logger = LoggerFactory.getLogger(OBRRepositoryConnectorImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.jabylon.updatecenter.repository.impl.OBRRepositoryService#
     * listInstalledBundles()
     */
    @Override
    public List<Bundle> listInstalledBundles() {
        Bundle bundle = FrameworkUtil.getBundle(getClass());
        List<Bundle> resources = Arrays.asList(bundle.getBundleContext().getBundles());
        return resources;
    }

    @Override
    public List<Resource> getAvailableResources(ResourceFilter filter) {
        List<Resource> filteredResources = new ArrayList<Resource>();
        List<Bundle> bundles = listInstalledBundles();
        Multimap<String, Bundle> map = buildMap(bundles);
        try {

            Repository repository = getRepository();
            Resource[] resources = repository.getResources();
            for (Resource resource : resources) {
                if (applies(filter, map, resource))
                    filteredResources.add(resource);
            }

        } catch (Exception e) {
            logger.error("Failed to discover resources with filter " + filter, e);
        }
        return filteredResources;
    }

    private Multimap<String, Bundle> buildMap(List<Bundle> bundles) {
        SortedSetMultimap<String, Bundle> result = TreeMultimap.create(Collator.getInstance(), new BundleVersionComparator());
        for (Bundle bundle : bundles) {
            result.put(bundle.getSymbolicName(), bundle);
        }
        return result;
    }

    private Repository getRepository() {
        Preferences node = PreferencesUtil.workspaceScope().node("update");
        String url = node.get("update.url", DEFAULT_REPOSITORY);
        try {
            return admin.addRepository(url);
        } catch (Exception e) {
            logger.error("Failed to add repository " + url, e);
        }
        return null;
    }

    private boolean applies(ResourceFilter filter, Multimap<String, Bundle> bundles, Resource resource) {
        switch (filter) {
        case ALL:
            return true;
        case PLUGIN:
            String[] categories = resource.getCategories();
            if (categories == null)
                return false;
            for (String string : categories) {
                if ("Jabylon-Plugin".equals(string))
                    return !bundles.containsKey(resource.getSymbolicName());
            }
            return false;
        case INSTALLABLE: {
            // can install anything that isn't installed yet
            return !bundles.containsKey(resource.getSymbolicName());
        }
        case UPDATEABLE:
            Collection<Bundle> installed = bundles.get(resource.getSymbolicName());
            if (installed.isEmpty())
                return false;
            // updateable if the latest installed version is less than
            // resource.getVersion
            return installed.iterator().next().getVersion().compareTo(resource.getVersion()) < 0;
        case INSTALLED:
            Collection<Bundle> available = bundles.get(resource.getSymbolicName());
            for (Bundle bundle : available) {
                if (bundle.getVersion().equals(resource.getVersion()))
                    return true;
            }
            return false;
        default:
            break;
        }
        return true;
    }

    @Override
    public void install(String resourceId) {
        Resource[] resources;
        String filter = MessageFormat.format("({0}={1})", Resource.ID, resourceId);
        try {
            resources = admin.discoverResources(filter);
            if (resources.length > 0) {
                install(resources[0]);

            }
        } catch (InvalidSyntaxException e) {
            logger.error("Invalid OSGi filter " + filter, e);
        }

    }

    @Override
    public void install(Resource... resources) {
        Resolver resolver = admin.resolver();
        for (Resource resource : resources) {
            resolver.add(resource);
        }
        if (resolver.resolve()) {
            resolver.deploy(0);
        }
    }

    @Override
    public Resource[] findResources(String id) {
        // String filter = "(&(SYMBOLIC_NAME={0})(VERSION={1}))";
        String filter = "({0}={1})";
        filter = MessageFormat.format(filter, Resource.ID, id);
        try {
            return admin.discoverResources(filter);
        } catch (InvalidSyntaxException e) {
            logger.error("Invalid OSGi filter " + filter, e);
        }
        return new Resource[0];
    }

}

class BundleVersionComparator implements Comparator<Bundle> {

    @Override
    public int compare(Bundle o1, Bundle o2) {
        // to have the highest version at the beginning
        return -(o1.getVersion().compareTo(o2.getVersion()));
    }

}
