/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.xliff;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.jabylon.properties.Property;

/**
 * Wraps a {@link Map} of {@link Properties} (with keys), including their {@link Locale}.<br>
 * 
 * @author c.samulski (2016-02-03)
 */
public class PropertyWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Locale locale;
	private final Map<String, Property> properties;

	public PropertyWrapper(Locale locale, Map<String, Property> properties) {
		this.locale = locale;
		this.properties = properties;
	}

	public Locale getLocale() {
		return locale;
	}

	public Map<String, Property> getPropertyFile() {
		return properties;
	}
}