package de.jutzig.jabylon.ui.config.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;

import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.config.ConfigSection;

public class DynamicConfigUtil {

	private static List<IConfigurationElement> configSections;
	private static List<IConfigurationElement> configTabs;

	private DynamicConfigUtil() {
		// hide utility constructor
	}

	public static List<IConfigurationElement> getApplicableElements(
			Object domainObject) {
		List<IConfigurationElement> configSections = getConfigSections();
		List<IConfigurationElement> applicable = new ArrayList<IConfigurationElement>();
		for (IConfigurationElement child : configSections) {

			if (isApplicable(child, domainObject)) {
				applicable.add(child);
			}

		}
		return applicable;
	}

	private static boolean isApplicable(IConfigurationElement child,
			Object domainElement) {
		String objectClass = child.getAttribute("objectClass");

		try {
			Class<?> clazz = Class.forName(objectClass);
			return clazz.isInstance(domainElement);
		} catch (ClassNotFoundException e) {
			// TODO: this will not work for arbitrary classes. Need to explode
			// super classes and interfaces of the domain object
			e.printStackTrace();
			return false;
		}

	}
	
	public static List<IConfigurationElement> getConfigSections()
	{
		if(configSections==null)
		{
			configSections = new ArrayList<IConfigurationElement>();
			IConfigurationElement[] elements = RegistryFactory.getRegistry().getConfigurationElementsFor("de.jutzig.jabylon.ui.config");
			for (IConfigurationElement iConfigurationElement : elements) {
				configSections.add(iConfigurationElement);
			}
		}
		return configSections;
	}
	
	public static List<IConfigurationElement> getConfigTabs()
	{
		if(configTabs==null)
		{
			configTabs = new ArrayList<IConfigurationElement>();
			IConfigurationElement[] elements = RegistryFactory.getRegistry().getConfigurationElementsFor("de.jutzig.jabylon.ui.configTab");
			for (IConfigurationElement iConfigurationElement : elements) {
				configTabs.add(iConfigurationElement);
			}
		}
		return configTabs;
	}
}
