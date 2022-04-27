/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.osgi.framework.FrameworkUtil;

public class OSGiStringResourceModel extends StringResourceModel {

	private static final long serialVersionUID = 1L;

	public OSGiStringResourceModel(Class<?> owner, String resourceKey, Component component, IModel<?> model, Object... parameters) {
		this(owner, resourceKey, component, model, getDefault(resourceKey,null), parameters);
	}


	public OSGiStringResourceModel(Class<?> owner, String resourceKey, Component component, IModel<?> model, String defaultValue, Object... parameters) {
		super(transformKey(owner, resourceKey), component, model);
		if(defaultValue!=null)
			setDefaultValue(getDefault(resourceKey,defaultValue));
		if(parameters!=null && parameters.length>0)
			setParameters(parameters);
	}

	public OSGiStringResourceModel(Class<?> owner, String resourceKey, IModel<?> model, Object... parameters) {
		this(owner, resourceKey, null, model, parameters);
	}

	public OSGiStringResourceModel(Class<?> owner, String resourceKey, IModel<?> model, String defaultValue, Object... parameters) {
		this(owner, resourceKey, null, model, getDefault(resourceKey,defaultValue), parameters);
	}

	protected static String getDefault(String resourceKey, String defaultValue) {
		if(!resourceKey.startsWith("%"))
			return defaultValue;
		return resourceKey;
	}

	protected static String transformKey(Class<?> owner, String resourceKey) {
		if(!resourceKey.startsWith("%"))
			return "|"+resourceKey;
		long bundleId = FrameworkUtil.getBundle(owner).getBundleId();
		resourceKey = resourceKey.substring(1);
		resourceKey = "%" + bundleId + "|" + resourceKey;
		return resourceKey;
	}


}
