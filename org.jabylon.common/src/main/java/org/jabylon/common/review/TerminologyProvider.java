/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.review;

import java.util.Locale;
import java.util.Map;

import org.jabylon.properties.Property;

/**
 * gives access to terminology
 * @author jutzig.dev@googlemail.com
 *
 */
public interface TerminologyProvider {

	Map<String, Property> getTerminology(Locale locale);
	
}
