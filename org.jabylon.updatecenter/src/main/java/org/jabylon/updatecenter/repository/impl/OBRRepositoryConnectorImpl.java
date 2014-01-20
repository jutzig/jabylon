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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.updatecenter.repository.OBRException;
import org.jabylon.updatecenter.repository.OBRRepositoryService;
import org.jabylon.updatecenter.repository.ResourceFilter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
@Component(enabled=true,immediate=true)
@Service
public class OBRRepositoryConnectorImpl implements OBRRepositoryService {

    private static final String DEFAULT_REPOSITORY = "http://jabylon.org/maven/repository.xml";
    @Reference(bind="bindAdmin")
    private RepositoryAdmin admin;

    private static final Logger logger = LoggerFactory.getLogger(OBRRepositoryConnectorImpl.class);
    
    /**
     * where we download plugins
     */
    private File pluginDir;

    private BundleContext context;
    
    @Activate
    public void activate() {
    	pluginDir = new File(new File(ServerConstants.WORKING_DIR),"addons");
    	Bundle bundle = FrameworkUtil.getBundle(getClass());
    	context = bundle.getBundleContext();
    	deployAddons(pluginDir);
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.jabylon.updatecenter.repository.impl.OBRRepositoryService#
     * listInstalledBundles()
     */
    @Override
    public List<Bundle> listInstalledBundles() {
        
        List<Bundle> resources = Arrays.asList(context.getBundles());
        return resources;
    }
    
    public void bindAdmin(RepositoryAdmin admin) {
    	this.admin = admin;
    }
    
    

    private void deployAddons(File pluginDir) {
    	File[] addons = pluginDir.listFiles();
    	if(addons==null)
    		return;
    	List<Bundle> bundles = new ArrayList<Bundle>();
    	for (File addon : addons) {
			try {
				String uri = addon.toURI().toString();
				Bundle bundle = context.getBundle(uri);
				if(bundle==null)
				{
					logger.info("Installing Addon {}",addon.getName());
					bundle = context.installBundle(uri);
				}
				bundles.add(bundle);
			} catch (BundleException e) {
				logger.error("Failed to deploy addon "+addon.getName());
			}
		}
    	
    	for (Bundle bundle : bundles) {
			try {
				bundle.start();
			} catch (BundleException e) {
				logger.error("Failed to start addon "+bundle.getSymbolicName());
			}
		}
		
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
    public void install(String resourceId) throws OBRException {
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
    public void install(Resource... resources) throws OBRException {
        List<Bundle> bundles = new ArrayList<Bundle>();
    	for (Resource resource : resources) {
    		try {
    			
				Bundle bundle = context.installBundle(downloadBundle(resource));
				bundles.add(bundle);
			} catch (BundleException e) {
				OBRException exception = new OBRException("Failed to start bundle "+ resource,e);
				logger.error("Plugin Installation failed",e);
				throw exception;
			} catch (MalformedURLException e) {
				OBRException exception = new OBRException("Incorrect URI for bundle "+ resource,e);
				logger.error("Plugin Installation failed",e);
				throw exception;
			} catch (IOException e) {
				OBRException exception =  new OBRException("Failed to download bundle "+ resource,e);
				logger.error("Plugin Installation failed",e);
				throw exception;
			}
		}
    	for (Bundle bundle : bundles) {
			logger.info("Starting Bundle "+bundle);
			try {
				bundle.start();
			} catch (BundleException e) {
				throw new OBRException("Failed to start bundle "+ bundle.getSymbolicName(),e);
			}
		}
        
        // for some reason this doesn't work in jetty
//        for (Resource resource : resources) {
//        	resolver.add(resource);
//        }
//        
//        if (resolver.resolve(Resolver.NO_OPTIONAL_RESOURCES)) {
//            resolver.deploy(Resolver.START);
//        }
//        else {
//        	Reason[] reasons = resolver.getUnsatisfiedRequirements();
//        	for (Reason reason : reasons) {
//        		logger.error("Failed to install "+reason.getResource() + " missing : " +reason.getRequirement());
//			}
//        	throw new OBRException("Installation Failed", reasons);
//        }
    }

    private String downloadBundle(Resource resource) throws MalformedURLException, IOException {
    	String uriString = resource.getURI();
    	URI uri = URI.create(uriString);
    	InputStream stream = null;
    	OutputStream out = null;
    	File destination;
		try {
			stream = uri.toURL().openStream();
			pluginDir.mkdirs();
			destination = new File(pluginDir,resource.getSymbolicName()+"_"+resource.getVersion()+".jar");
			out = new FileOutputStream(destination);
			byte[] buffer = new byte[4096];
			int read = 0;
			while((read=stream.read(buffer))!=-1) {
				out.write(buffer,0,read);
			}
		} finally {
			if(out!=null)
				out.close();
			if(stream!=null)
				stream.close();
		}
		return destination.toURI().toString();
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
