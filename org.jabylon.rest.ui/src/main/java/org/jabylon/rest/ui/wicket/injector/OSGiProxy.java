/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.injector;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.wicket.proxy.IProxyTargetLocator;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * wraps an OSGi service in a serializable proxy.
 * See https://github.com/jutzig/jabylon/issues/182
 * @author jutzig.dev@googlemail.com
 *
 */
public class OSGiProxy implements InvocationHandler, Serializable {

	private static final long serialVersionUID = 1L;

	private Supplier<Object> supplier;
			
			public OSGiProxy(IProxyTargetLocator locator) {
				supplier = Suppliers.memoize(Suppliers.compose(new LoadingFunction(), Suppliers.ofInstance(locator)));
			}

	@Override
	public Object invoke(Object proxy, Method arg1, Object[] arg2) throws Throwable {
		
		return arg1.invoke(supplier.get(), arg2);
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T wrap(IProxyTargetLocator locator, Class<T> type) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new OSGiProxy(locator));
	}
}

class LoadingFunction implements Function<IProxyTargetLocator, Object>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object apply(IProxyTargetLocator input) {
		return input.locateProxyTarget();
	}
	
}