package de.jutzig.jabylon.rest.ui.wicket.injector;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IBehaviorInstantiationListener;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.injection.Injector;

public class OSGiInjector extends Injector implements IBehaviorInstantiationListener, IComponentInstantiationListener {

	private static final OSGiFieldValueFactory factory = new OSGiFieldValueFactory();
	
	public OSGiInjector(Application app) {
		bind(app);
	}
	
	@Override
	public void inject(Object object) {
		inject(object, factory);
		
	}

	@Override
	public void onInstantiation(Behavior behavior) {
		inject(behavior);
		
	}

	@Override
	public void onInstantiation(Component component) {
		inject(component);
		
	}
	
	
}
