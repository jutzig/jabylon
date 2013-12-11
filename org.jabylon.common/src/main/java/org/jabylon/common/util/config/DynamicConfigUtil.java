/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.util.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import org.jabylon.common.util.IConfigurationElementLoader;
import org.jabylon.users.User;

public class DynamicConfigUtil {

    private static Supplier<List<IConfigurationElement>> configSections;
    private static Supplier<List<IConfigurationElement>> configTabs;


    static{
        configTabs = Suppliers.memoize(Suppliers.synchronizedSupplier(Suppliers.compose(new IConfigurationElementLoader(), Suppliers.ofInstance("org.jabylon.rest.ui.configTab"))));
        configSections = Suppliers.memoize(Suppliers.synchronizedSupplier(Suppliers.compose(new IConfigurationElementLoader(), Suppliers.ofInstance("org.jabylon.rest.ui.config"))));
    }

    private DynamicConfigUtil() {
        // hide utility constructor
    }

    public static List<IConfigurationElement> getApplicableElements(Object domainObject, User user) {

        List<IConfigurationElement> configSections = getConfigSections();
        List<IConfigurationElement> applicable = new ArrayList<IConfigurationElement>();
        for (IConfigurationElement child : configSections) {

            if (isApplicable(child, domainObject)) {
                applicable.add(child);
            }

        }
        return applicable;
    }

    public static List<IConfigurationElement> getApplicableElements(Object domainObject) {

        List<IConfigurationElement> configSections = getConfigSections();
        List<IConfigurationElement> applicable = new ArrayList<IConfigurationElement>();
        for (IConfigurationElement child : configSections) {

            if (isApplicable(child, domainObject)) {
                applicable.add(child);
            }

        }
        return applicable;
    }

    private static boolean isApplicable(IConfigurationElement child, Object domainElement) {
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

    public static List<IConfigurationElement> getConfigSections() {
        return configSections.get();
    }

    public static List<IConfigurationElement> getConfigTabs() {
//		if (configTabs == null) {
//			configTabs = new ArrayList<IConfigurationElement>();
//			IConfigurationElement[] elements = RegistryFactory.getRegistry().getConfigurationElementsFor("org.jabylon.ui.configTab");
//			for (IConfigurationElement iConfigurationElement : elements) {
//				configTabs.add(iConfigurationElement);
//			}
//			Collections.sort(configTabs, new Comparator<IConfigurationElement>() {
//
//				@Override
//				public int compare(IConfigurationElement element1, IConfigurationElement element2) {
//					int precedence1 = 0;
//					int precedence2 = 0;
//					String pre1 = element1.getAttribute("precedence");
//					String pre2 = element2.getAttribute("precedence");
//					if (pre1 != null && !pre1.isEmpty())
//						precedence1 = Integer.valueOf(pre1);
//					if (pre2 != null && !pre2.isEmpty())
//						precedence2 = Integer.valueOf(pre2);
//					return precedence2 - precedence1;
//				}
//			});
//		}
        return configTabs.get();
    }
}
