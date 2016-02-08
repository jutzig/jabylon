/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jabylon.properties.Property;
import org.jabylon.properties.xliff.PropertyWrapper;
import org.jabylon.properties.xliff.XliffResourceImpl;

/**
 * Basic converter interface for Bilingual translation resources.<br>
 * The reference implementation is for XLIFF (1.2). (see {@link XliffResourceImpl}).<br>
 * 
 * @author c.samulski (2016-02-02)
 */
public interface BilingualPropertyResource {
	/**
	 * Reads an arbitrary translation resource from the {@link InputStream}, converts to
	 * {@link Property}s, held in a {@link PropertyWrapper}, which is returned by this method.<br>
	 * (subject to change, as no implementation exists as of yet)
	 */
	PropertyWrapper read(InputStream stream);

	/**
	 * Converts Two {@link PropertyWrapper}s {@link Property}s to an arbitrary translation format,
	 * which is written to the {@link OutputStream}.<br>
	 */
	void write(PropertyWrapper source, PropertyWrapper target, OutputStream out) throws IOException;
}
