/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;

import com.google.common.base.Function;

public class IConfigurationElementLoader implements Function<String, List<IConfigurationElement>>
{
    public List<IConfigurationElement> apply(String extensionPoint)
    {
        List<IConfigurationElement> result = new ArrayList<IConfigurationElement>();
        IConfigurationElement[] elements = RegistryFactory.getRegistry().getConfigurationElementsFor(extensionPoint);
        for (IConfigurationElement iConfigurationElement : elements) {
            result.add(iConfigurationElement);
        }
        return result;
    }
}
