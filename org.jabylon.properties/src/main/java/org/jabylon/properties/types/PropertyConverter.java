/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import org.jabylon.properties.Property;

public interface PropertyConverter {

	public abstract Property readProperty(BufferedReader reader) throws IOException;

	public abstract void writeProperty(Writer writer, Property property) throws IOException;

	/**
	 *
	 * @return the license header in the file or <code>null</code> if not available
	 */
	public abstract String getLicenseHeader();

	public abstract void writeLicenseHeader(Writer writer, String licenseHeader) throws IOException;

}