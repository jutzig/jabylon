/**
 * 
 */
package de.jutzig.jabylon.common.util;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PreferencesUtil {

	public static final String SCAN_CONFIG_INCLUDE = "include";
	public static final String SCAN_CONFIG_EXCLUDE = "exclude";
	public static final String SCAN_CONFIG_MASTER_LOCALE = "master.locale";
	public static final String NODE_CHECKS = "checks";
	
	private PreferencesUtil()
	{
		//hide utility class constructor
	}
	
	public static Preferences scopeFor(EObject eobject)
	{
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
	
	public static Preferences workspaceScope()
	{
		return scopeFor(null);
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
