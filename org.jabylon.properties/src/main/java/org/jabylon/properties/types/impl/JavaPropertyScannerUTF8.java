/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.types.PropertyConverter;
import org.jabylon.properties.types.PropertyScanner;

@Component(enabled=true,immediate=true)
@Service
public class JavaPropertyScannerUTF8 extends JavaPropertyScanner {

	@Property(name=PropertyScanner.TYPE, value="PROPERTIES_UTF8")
	public static final String TYPE = "PROPERTIES_UTF8";
	
	@Override
	public PropertyConverter createConverter(URI resource) {
		return new PropertiesHelper(false, resource);
	}

	@Override
	public String getEncoding(){
		return "UTF-8";
	}
	
}
