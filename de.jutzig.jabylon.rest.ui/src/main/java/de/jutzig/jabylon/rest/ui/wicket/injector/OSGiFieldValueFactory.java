package de.jutzig.jabylon.rest.ui.wicket.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

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
		if (!Modifier.isStatic(field.getModifiers()) && injectAnnotation != null) {
			try {
				
				Class<?> type = field.getType();
				boolean isList = isCollection(field);
				if(isList)
					type = extractType(field);
				
				final IProxyTargetLocator locator = new OSGiProxyTargetLocator(type, isList);

				if (wrapInProxies) {
					// TODO need net.sf.cglib.proxy.Callback to make this work
					// it seems
					target = LazyInitProxyFactory.createProxy(type, locator);
				} else {
					target = locator.locateProxyTarget();
				}

				if (!field.isAccessible()) {
					field.setAccessible(true);
				}

				field.set(fieldOwner, target);
				return target;
			} catch (IllegalAccessException e) {
				throw new WicketRuntimeException("Error OSGi-injecting field " + field.getName() + " in " + fieldOwner, e);
			}
		}
		return null;
	}

	private boolean isCollection(Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

	private Class<?> extractType(Field field) {

		Type genericType = field.getGenericType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			genericType = pt.getActualTypeArguments()[0];
					
		}
		if (genericType instanceof Class<?>) {
			Class<?> c = (Class<?>) genericType;
			return c;
		}

		return null;
	}

	@Override
	public boolean supportsField(Field field) {
		return field.isAnnotationPresent(Inject.class);
	}

}
