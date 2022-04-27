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

/**
 * custom version of StringResourceModel that includes the old and removed constructors
 * @author utzig
 *
 */
public class CustomStringResourceModel extends StringResourceModel {

	private static final long serialVersionUID = 4283827834998656703L;

	public CustomStringResourceModel(String resourceKey, Component component, IModel<?> model) {
		super(resourceKey, component, model);
	}

	public CustomStringResourceModel(String resourceKey, Component component) {
		super(resourceKey, component);
	}

	public CustomStringResourceModel(String resourceKey, IModel<?> model) {
		super(resourceKey, model);
	}

	public CustomStringResourceModel(String resourceKey) {
		super(resourceKey);
	}

	public CustomStringResourceModel(String resourceKey, Component component, IModel<?> model, Object... parameters) {
		this(resourceKey, component, model, null, parameters);
	}

	public CustomStringResourceModel(String resourceKey, Component component, IModel<?> model, String defaultValue, Object... parameters) {
		super(resourceKey, component, model);
		if(defaultValue!=null)
			setDefaultValue(defaultValue);
		if(parameters!=null && parameters.length>0)
			setParameters(parameters);
	}

}
