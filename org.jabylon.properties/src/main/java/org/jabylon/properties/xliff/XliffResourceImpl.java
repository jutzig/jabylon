/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.xliff;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jabylon.properties.Property;

/**
 * @author c.samulski (2016-02-01)
 */
public class XliffResourceImpl implements BilingualPropertyResource {

	private XliffResourceImpl() {} // no instantiation necessary.

	private static final XliffResourceImpl INSTANCE = new XliffResourceImpl(); // stateless.

	public static final XliffResourceImpl getInstance() {
		return INSTANCE;
	}

	/**
	 * Reads an XLIFF (1.2) resource from the {@link InputStream}, converts to {@link Property}s,
	 * held in a {@link PropertyWrapper}, which is returned by this method.<br>
	 */
	@Override
	public PropertyWrapper read(InputStream stream) {
		return null; // TODO
	}

	/**
	 * Converts Two {@link PropertyWrapper}s {@link Property}s to XLIFF (1.2) translation format,
	 * which is written to the {@link OutputStream}.<br>
	 */
	@Override
	public void write(PropertyWrapper filteredSource, PropertyWrapper filteredTarget, OutputStream out) throws IOException {
		XliffWriter.write(out, filteredSource, filteredTarget, "UTF-8");
	}
}
