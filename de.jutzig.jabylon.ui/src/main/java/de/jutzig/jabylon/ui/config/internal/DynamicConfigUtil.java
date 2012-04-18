package de.jutzig.jabylon.ui.config.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.config.ConfigSection;

public class DynamicConfigUtil {

	private DynamicConfigUtil() {
		// hide utility constructor
	}

	public static List<IConfigurationElement> getApplicableElements(
			Object domainObject) {
		List<IConfigurationElement> configSections = Activator.getDefault()
				.getConfigSections();
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
}
