/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.connector.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.eclipse.emf.cdo.view.CDOStaleObject;
import org.eclipse.emf.cdo.view.CDOStaleReferencePolicy;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * retries with a proxy handler if the default strategy fails.
 *
 * see https://github.com/jutzig/jabylon/issues/218
 * @author utzig
 *
 */
public class RetryingStaleReferencePolicy implements CDOStaleReferencePolicy {

	public static final CDOStaleReferencePolicy INSTANCE = new RetryingStaleReferencePolicy();

	private static final Logger LOG = LoggerFactory
			.getLogger(RetryingStaleReferencePolicy.class);

	@Override
	public Object processStaleReference(final EObject source,
			EStructuralFeature feature, int index, final CDOID target) {
		try {
			LOG.warn("Processing stale reference {} feature {}",source,feature);
			return CDOStaleReferencePolicy.DEFAULT.processStaleReference(source, feature, index, target);
		} catch (IllegalArgumentException e) {
			LOG.warn("failed to process stale reference. Falling back to different classloader",e);
			final EClassifier type = feature.getEType();
			InvocationHandler handler = new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					String name = method.getName();
					if (name.equals("cdoID")) //$NON-NLS-1$
					{
						return target;
					}

					if (name.equals("eIsProxy")) //$NON-NLS-1$
					{
						return false;
					}

					if (name.equals("eClass")) //$NON-NLS-1$
					{
						return type;
					}

					if (name.equals("eAdapters")) //$NON-NLS-1$
					{
						return source.eAdapters();
					}

					throw new ObjectNotFoundException(target);
				}
			};

			Class<?> instanceClass = type.getInstanceClass();
			Class<?>[] interfaces = null;

			// Be sure to have only interface
			if (instanceClass.isInterface()) {
				interfaces = new Class<?>[] { instanceClass, InternalEObject.class, CDOStaleObject.class };
			} else {
				interfaces = new Class<?>[] { InternalEObject.class, CDOStaleObject.class };
			}

			return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, handler);
		}
	}

}
