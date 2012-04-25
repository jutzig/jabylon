/**
 * 
 */
package de.jutzig.jabylon.ui.util;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.ui.resources.ApplicationConstants;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PreferencesUtil {

	private PreferencesUtil()
	{
		//hide utility class constructor
	}
	
	public static Preferences scopeFor(EObject eobject)
	{
		IEclipsePreferences rootNode = InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE);
		if (eobject instanceof Resolvable) {
			Resolvable resolvable = (Resolvable) eobject;
			return rootNode.node(resolvable.relativePath().path());
		}
		return rootNode;
	}
	
}
