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
	private Class<T> domainClass;
	
	public AbstractConfigSection(Class<T> domainClass) {
		this.domainClass = domainClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(Object input, Preferences config) {
		domainObject = (T) input;
		init(config);
	}

	protected abstract void init(Preferences config);

	@Override
	public boolean appliesTo(Object input) {
		if(input==null)
			return false;
		return domainClass.isAssignableFrom(input.getClass());
	}

	
	public T getDomainObject() {
		return domainObject;
	}
}
