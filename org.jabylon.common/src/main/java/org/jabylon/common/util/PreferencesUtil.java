/**
 *
 */
package org.jabylon.common.util;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.Workspace;
import org.jabylon.users.User;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private PreferencesUtil()
    {
        //hide utility class constructor
    }

    @SuppressWarnings("rawtypes")
	public static Preferences scopeFor(EObject eobject)
    {
        migrate();
        IEclipsePreferences rootNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE);
        if (eobject==null || eobject instanceof Workspace) {
            return rootNode.node("root");
        }
        if (eobject instanceof User) {
            IEclipsePreferences node = InstanceScope.INSTANCE.getNode(ApplicationConstants.USER_NODE);
            User user = (User) eobject;
            return node.node(user.getName());

        }
        else if (eobject instanceof Resolvable) {
            Resolvable resolvable = (Resolvable) eobject;
            return rootNode.node(resolvable.relativePath().path());
        }
        return rootNode;
    }

	private static void migrate() {
		IEclipsePreferences oldNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.OLD_PLUGIN_ID);
        try {
			if(oldNode.nodeExists("config"))
			{
				LOGGER.info("Migrating old Preferences");
				IEclipsePreferences newNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.PLUGIN_ID);
				cloneNode(oldNode, newNode);
				deleteNode(oldNode.node("config"));
			}
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
        return scopeFor(null);
    }

    public static void deleteNode(Preferences node) throws BackingStoreException
    {
    	Preferences parent = node.parent();
    	node.removeNode();
    	parent.flush();;
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
}
