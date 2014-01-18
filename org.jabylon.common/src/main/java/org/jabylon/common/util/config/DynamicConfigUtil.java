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
import org.jabylon.common.util.IConfigurationElementLoader;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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
        return configTabs.get();
    }
}
