/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types.impl;

import java.io.File;
import java.util.Locale;

import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.PropertyScanner;

public abstract class AbstractPropertyScanner implements PropertyScanner{

	public AbstractPropertyScanner() {
		super();
	}

	@Override
	public boolean isTemplate(File propertyFile, String masterLocale) {
	    return matchesLocale(getLocale(propertyFile), masterLocale);
	}

	@Override
	public boolean isTranslation(File propertyFile, ScanConfiguration config) {
	    Locale locale = getLocale(propertyFile);
	    return locale!=null && !locale.toString().equals(config.getMasterLocale());
	}

	protected boolean matchesLocale(Locale locale, String desiredLocale) {
	
	    if ((desiredLocale == null || desiredLocale.isEmpty())&& locale==null)
	    {
	    	return true;
	    }
	    if(locale==null)
	    	return false;
	    return locale.toString().equals(desiredLocale);
	}

}