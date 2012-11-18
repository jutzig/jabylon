package de.jutzig.jabylon.rest.ui.wicket.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.inject.Inject;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.injection.IFieldValueFactory;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.proxy.LazyInitProxyFactory;

public class OSGiFieldValueFactory implements IFieldValueFactory {

	boolean wrapInProxies = false;
	
	@Override
	public Object getFieldValue(Field field, Object fieldOwner) {
		Object target = null;
		Inject injectAnnotation = field.getAnnotation(Inject.class);
		if (!Modifier.isStatic(field.getModifiers()) && injectAnnotation != null)
		{
			try
			{
				
				final IProxyTargetLocator locator = new OSGiProxyTargetLocator(field);

				if (wrapInProxies)
				{
					//TODO need net.sf.cglib.proxy.Callback to make this work it seems
					target = LazyInitProxyFactory.createProxy(field.getType(), locator);
				}
				else
				{
					target = locator.locateProxyTarget();
				}

				if (!field.isAccessible())
				{
					field.setAccessible(true);
				}

				field.set(fieldOwner, target);
				return target;
			}
			catch (IllegalAccessException e)
			{
				throw new WicketRuntimeException("Error OSGi-injecting field " +
					field.getName() + " in " + fieldOwner, e);
			}
		}
		return null;
	}

	@Override
	public boolean supportsField(Field field) {
		return field.isAnnotationPresent(Inject.class);
	}
	

}
