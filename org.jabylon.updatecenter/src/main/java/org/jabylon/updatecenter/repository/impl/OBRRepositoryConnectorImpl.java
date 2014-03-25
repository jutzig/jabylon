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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.common.util.config.DynamicConfigUtil;
import org.jabylon.updatecenter.repository.OBRException;
import org.jabylon.updatecenter.repository.OBRRepositoryService;
import org.jabylon.updatecenter.repository.ResourceFilter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.FrameworkWiring;
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
@Component(enabled=true)
@Service
public class OBRRepositoryConnectorImpl implements OBRRepositoryService {

    private static final String DEFAULT_REPOSITORY = "http://jabylon.org/maven/repository.xml";
    @Reference(bind="bindAdmin")
    private RepositoryAdmin admin;

    private static final Logger logger = LoggerFactory.getLogger(OBRRepositoryConnectorImpl.class);
    
    private static final Pattern BUNDLE_PATTERN = Pattern.compile("(.*?)_(.*?)\\.jar");
    
    private static final Comparator<Version> COMPARATOR = new OSGiVersionComparator();
	
    
    /**
     * where we download plugins
     */
    private File pluginDir;

    private BundleContext context;
    
    @Activate
    public void activate() {
    	Bundle bundle = FrameworkUtil.getBundle(getClass());
    	context = bundle.getBundleContext();
    	Thread t = new Thread(new Runnable() {
			//this is expensive so we should do it in a thread instead
			@Override
			public void run() {
				pluginDir = new File(new File(ServerConstants.WORKING_DIR),"addons");
				deployAddons(pluginDir);
				
			}
		},"Addon Deployment");
    	t.start();
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
    	List<String> bundleFiles = getHighestBundleVersions(pluginDir.list());
    	
    	List<Bundle> bundles = new ArrayList<Bundle>();
    	for (String addonName : bundleFiles) {
			try {
				File addon = new File(pluginDir,addonName);
				String uri = addon.toURI().toString();
				Bundle bundle = context.getBundle(uri);
				if(bundle==null)
				{
					logger.info("Installing Addon {}",addonName);
					bundle = context.installBundle(uri);
				}
				bundles.add(bundle);
			} catch (BundleException e) {
				logger.error("Failed to deploy addon "+addonName);
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

	protected List<String> getHighestBundleVersions(String... filenames) {
		if(filenames==null)
			return Collections.emptyList();
		SortedSetMultimap<String, String> map = TreeMultimap.create(Collator.getInstance(), new VersionComparator());
		for (String string : filenames) {
			Matcher matcher = BUNDLE_PATTERN.matcher(string);
			if(matcher.matches()) {
				String name = matcher.group(1);
				String version = matcher.group(2);
				map.put(name, version);
			}
			else {
				logger.warn("{} does not match the pattern {}. Skipping",string,BUNDLE_PATTERN);
			}
		}
		Set<Entry<String, Collection<String>>> entrySet = map.asMap().entrySet();
		List<String> result = new ArrayList<String>(entrySet.size());
		for (Entry<String, Collection<String>> entry : entrySet) {
			result.add(entry.getKey()+"_"+entry.getValue().iterator().next()+".jar");
		}
		return result;
	}
	
	protected String getHighestVersion(List<String> versions) {
		String versionName = versions.get(0);
		Version highest = Version.parseVersion(versionName);
		for (String currentName : versions) {
			Version current = Version.parseVersion(currentName);
			if(current.compareTo(highest)>0)
				highest = current;
		}
		return highest.toString();
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

            if(filter==ResourceFilter.PLUGIN) {
            	//for update we only want to see the latest version of everything updateable
            	filteredResources = removeOldVersions(filteredResources);
            }

        } catch (Exception e) {
            logger.error("Failed to discover resources with filter " + filter, e);
        }
        return filteredResources;
    }

    private List<Resource> removeOldVersions(List<Resource> resources) {
        SortedSetMultimap<String, Resource> map = TreeMultimap.create(Collator.getInstance(), new ResourceComparator());
        for (Resource bundle : resources) {
            map.put(bundle.getSymbolicName(), bundle);
        }
        resources.clear();
		Set<Entry<String, Collection<Resource>>> entries = map.asMap().entrySet();
		for (Entry<String, Collection<Resource>> entry : entries) {
			//add the highest version
			resources.add(entry.getValue().iterator().next());
		}
		return resources;
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
            return COMPARATOR.compare(installed.iterator().next().getVersion(), resource.getVersion()) >0;
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
    	boolean needRefresh = false;
    	for (Bundle bundle : bundles) {
			logger.info("Starting Bundle "+bundle);
			try {
				needRefresh |= checkIfUpdate(bundle);
				bundle.start();
			} catch (BundleException e) {
				logger.error("Failed to start bundle "+ bundle.getSymbolicName(),e);
				throw new OBRException("Failed to start bundle "+ bundle.getSymbolicName(),e);
			}
		}
    	if(!bundles.isEmpty()){
        	DynamicConfigUtil.refresh();
        }
        if(needRefresh)
        {
        	Bundle systemBundle = context.getBundle(0);
        	FrameworkWiring wiring = systemBundle.adapt(FrameworkWiring.class);
        	if(wiring!=null){
        		logger.info("Executing package refresh after update");
        		wiring.refreshBundles(null);
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

    /**
     * checks if the bundle updates an existing one, and if so, deactivates any others with the same name
     * @param bundle
     * @param <code>true</code> if one or more bundles had to be stopped/uninstalled because they are updated
     */
	private boolean checkIfUpdate(Bundle bundle) {
		Bundle[] bundles = context.getBundles();
		boolean updated = false;
		for (Bundle other : bundles) {
			if (other == bundle)
				continue;
			if (other.getSymbolicName().equals(bundle.getSymbolicName())) {
				try {
					if (other.getState() == Bundle.UNINSTALLED)
						break;
					updated = true;
					if (isSingleton(other)) {
						logger.info("{}:{} is a singleton. Uninstalling due to an update", other.getSymbolicName(), other.getVersion());
						other.uninstall();
					} else {
						logger.info("Stopping {}:{} due to an update", other.getSymbolicName(), other.getVersion());
						other.stop();
					}
				} catch (BundleException e) {
					logger.error("Failed to stop " + other, e);
				}

			}
		}
		return updated;
	}

	private boolean isSingleton(Bundle other) {
		String string = other.getHeaders().get("Bundle-SymbolicName");
		return string!=null && string.contains("singleton:=true");
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

	private static class BundleVersionComparator implements Comparator<Bundle> {
		
	    @Override
	    public int compare(Bundle o1, Bundle o2) {
	    	Version v1 = o1.getVersion();
	    	Version v2 = o2.getVersion();
	    	return COMPARATOR.compare(v1, v2);
	    }
	   
	    
	}

	private static class OSGiVersionComparator implements Comparator<Version> {

		
	    @Override
	    public int compare(Version v1, Version v2) {

	       	if(!("SNAPSHOT".equals(v1.getQualifier()) && "SNAPSHOT".equals(v2.getQualifier()))){
	    		if("SNAPSHOT".equals(v1.getQualifier()))
	    		{
	    			if(v1.getMajor()==v2.getMajor() && v1.getMicro()==v2.getMicro() && v1.getMinor()==v2.getMinor()) {
	    				//we consider SNAPSHOT < release
	    				return 1;
	    			}
	    		}
	    		else if("SNAPSHOT".equals(v2.getQualifier()))
	    		{
	    			if(v1.getMajor()==v2.getMajor() && v1.getMicro()==v2.getMicro() && v1.getMinor()==v2.getMinor()) {
	    				//we consider SNAPSHOT < release
	    				return -1;
	    			}
	    		}    		
	    	}
	    	// to have the highest version at the beginning
	    	return -(v1.compareTo(v2)); 
	    }
	    
	}

	private static class VersionComparator implements Comparator<String> {

	    @Override
	    public int compare(String o1, String o2) {

	    	Version v1 = Version.parseVersion(o1);
	    	Version v2 = Version.parseVersion(o2);
	    	return COMPARATOR.compare(v1, v2);
	    }
	}
	
	private static class ResourceComparator implements Comparator<Resource> {

	    @Override
	    public int compare(Resource o1, Resource o2) {

	    	Version v1 = o1.getVersion();
	    	Version v2 = o2.getVersion();
	    	return COMPARATOR.compare(v1, v2);
	    }
	}
	
}

