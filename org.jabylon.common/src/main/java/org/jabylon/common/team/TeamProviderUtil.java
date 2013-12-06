package org.jabylon.common.team;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;

import org.jabylon.common.util.IConfigurationElementLoader;

public class TeamProviderUtil {

    private static final Supplier<List<IConfigurationElement>> providers = Suppliers.memoize(Suppliers.synchronizedSupplier(Suppliers.compose(new IConfigurationElementLoader(), Suppliers.ofInstance("org.jabylon.common.teamProvider"))));

    public static List<String> getAvailableTeamProviders() {
        return Lists.transform(providers.get(), new Function<IConfigurationElement, String>() {
            public String apply(IConfigurationElement from)
            {
                return from.getAttribute("name");
            }
        });
    }

    public static TeamProvider getTeamProvider(String name) {
        if(name==null)
            return null;
        for (IConfigurationElement element : providers.get()) {
            String providerName = element.getAttribute("name");
            if(name.equals(providerName))
                try {
                    return (TeamProvider) element.createExecutableExtension("class");
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }



}
