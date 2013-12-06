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
