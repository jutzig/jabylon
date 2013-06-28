/**
 *
 */
package de.jutzig.jabylon.ui.config;

import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public abstract class AbstractConfigSection<T> implements ConfigSection{

    private T domainObject;

    @SuppressWarnings("unchecked")
    @Override
    public void init(Object input, Preferences config) {
        domainObject = (T) input;
        init(config);
    }

    protected abstract void init(Preferences config);


    public T getDomainObject() {
        return domainObject;
    }

    @Override
    public void apply(Preferences config) {
        //base implementation does nothing

    }
}
