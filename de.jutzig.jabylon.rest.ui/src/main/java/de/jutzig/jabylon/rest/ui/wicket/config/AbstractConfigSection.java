/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public abstract class AbstractConfigSection<T> implements ConfigSection<T>{


	private static final long serialVersionUID = 1L;
	
	private IModel<T> model;

	
	public T getDomainObject() {
		if(model==null)
			return null;
		return model.getObject();
	}
	
	public IModel<T> getModel()
	{
		return model;
	}
	
	@Override
	public void apply(Preferences config) {
		
		
	}
}
