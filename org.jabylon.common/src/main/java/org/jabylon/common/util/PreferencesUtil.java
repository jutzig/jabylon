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
package org.jabylon.common.util;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.ScanConfiguration;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PreferencesUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesUtil.class);

    public static final String SCAN_CONFIG_INCLUDE = "include";
    public static final String SCAN_CONFIG_EXCLUDE = "exclude";
    public static final String SCAN_CONFIG_MASTER_LOCALE = "master.locale";
    public static final String NODE_CHECKS = "checks";
    public static final Supplier<URIResolver> resolver = Suppliers.memoize(Suppliers.compose(new Function<BundleContext, URIResolver>() {
    	@Override
    	public URIResolver apply(BundleContext input) {
    		ServiceReference<URIResolver> reference = input.getServiceReference(URIResolver.class);
    		if(reference!=null)
    			return input.getService(reference);
    		return null;
    	}
	}, Suppliers.ofInstance(FrameworkUtil.getBundle(PreferencesUtil.class).getBundleContext())));

	private static boolean isMigrated = false;

    private PreferencesUtil()
    {
        //hide utility class constructor
    }

	public static Preferences scopeFor(Object eobject)
    {
    	migrate();
    	URI uri = resolver.get().getURI(eobject);
    	String path = uri.toString();
        IEclipsePreferences rootNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE);
        return rootNode.node(path);
    }
    
    /**
     * converts a pref node path to a URI that can be resolved by an {@link URIResolver}
     * @param prefs
     * @return
     */
    public static URI toLookupURI(Preferences prefs) {
    	String absolutePath = prefs.absolutePath();
    	absolutePath = absolutePath.substring(ApplicationConstants.CONFIG_NODE.length());
    	return URI.createURI(absolutePath);
    }

	private static void migrate() {
		if(isMigrated)
			return;
		IEclipsePreferences oldNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.OLD_PLUGIN_ID);
        try {
			if(oldNode.nodeExists("config"))
			{
				LOGGER.info("Migrating old Preferences");
				IEclipsePreferences newNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.PLUGIN_ID);
				cloneNode(oldNode, newNode);
				deleteNode(oldNode.node("config"));
			}
			IEclipsePreferences node = InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE);
			String[] childrenNames = node.childrenNames();
			for (String child : childrenNames) {
				if(child.equals("workspace") || child.equals("security"))
					continue;
				LOGGER.info("Migrating {} to {}",child,"workspace/"+child);
				Preferences old = node.node(child);
				cloneNode(old, node.node("workspace/"+child));
				deleteNode(old);
			}
			isMigrated = true;
		} catch (BackingStoreException e) {
			LOGGER.error("Failed to check if old config needs to be migrated");
		}
	}
	
	/**
	 * copies all values and child nodes of the source into the target node
	 * @param source
	 * @param target
	 * @throws BackingStoreException 
	 */
	public static void cloneNode(Preferences source, Preferences target) throws BackingStoreException {
		shallowClonePreferences(source, target);
		copyChildPreferences(source, target);
		target.sync();
	}

    public static Preferences workspaceScope()
    {
        return InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE+"/workspace");
    }
    
    /**
     * 
     * @return the jabylon root preference node
     */
    public static Preferences rootScope()
    {
        return InstanceScope.INSTANCE.getNode(ApplicationConstants.PLUGIN_ID);
    }

    public static void deleteNode(Preferences node) throws BackingStoreException
    {
    	Preferences parent = null;
    	try {
			parent = node.parent();
		} catch (Exception e) {
			LOGGER.warn("Failed to retrieve parent node",e);
		}
    	node.removeNode();
    	if(parent!=null)
    		parent.flush();
    }    
    
    
    public static Preferences renamePreferenceNode(Preferences node, String newName)
    {
        Preferences parent = node.parent();
        Preferences clone = parent.node(newName);
        try {
            copyChildPreferences(node, clone);
            deleteNode(parent);
        } catch (BackingStoreException e) {
        	LOGGER.error("Failed to rename preferences from "+node+" to "+clone,e);
        }
        return clone;
    }

    protected static void shallowClonePreferences(Preferences node, Preferences clone) {
        try {
            for (String key : node.keys()) {
                String value = node.get(key, "");
                clone.put(key, value);

            }

        } catch (BackingStoreException e) {
            LOGGER.error("Failed to clone preferences from "+node+" to "+clone,e);
        }
    }

    public static void copyChildPreferences(Preferences oldParent, Preferences newParent) throws BackingStoreException
    {
        for (String name : oldParent.childrenNames()) {
            Preferences child = oldParent.node(name);
            Preferences copy = newParent.node(name);
            shallowClonePreferences(child, copy);
            copyChildPreferences(child, copy);
        }
    }

    public static final ScanConfiguration getScanConfigForProject(Project project)
    {
        Preferences node = scopeFor(project);
        ScanConfiguration configuration = PropertiesFactory.eINSTANCE.createScanConfiguration();
        configuration.setExclude(node.get(SCAN_CONFIG_EXCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__EXCLUDE.getDefaultValueLiteral()));
        configuration.setInclude(node.get(SCAN_CONFIG_INCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__INCLUDE.getDefaultValueLiteral()));
        configuration.setMasterLocale(node.get(SCAN_CONFIG_MASTER_LOCALE, PropertiesPackage.Literals.SCAN_CONFIGURATION__MASTER_LOCALE.getDefaultValueLiteral()));
        return configuration;
    }
    
    public static Preferences getNodeForJob(Preferences context, String jobID)
    {
    	return context.node(ApplicationConstants.JOBS_NODE_NAME).node(jobID);
    }
}
