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

import org.jabylon.properties.PropertyFile;

public interface PropertyConverter {
	
	/**
	 * loads the given input stream and transform it into a {@link PropertyFile}
	 * <p>
	 * The implementation is expected to close the stream upon completion
	 * @param in
	 * @param encoding hint about which encoding to use
	 * @return
	 * @throws IOException
	 */
	PropertyFile load(InputStream in, String encoding) throws IOException;
	
	/**
	 * serializes the given {@link PropertyFile} into the output steam
	 * <p>
	 * The implementation is expected to close the stream upon completion
	 * <p>
	 * The implementation is free to optimize the output by removing empty translations.
	 * 
	 * @param out
	 * @param file
	 * @param encoding hint about which encoding to use
	 * @return number of translated (non-empty) keys 
	 * @throws IOException
	 */
	int write(OutputStream out, PropertyFile file, String encoding) throws IOException;

//	Property readProperty(BufferedReader reader) throws IOException;
//
//	void writeProperty(Writer writer, Property property) throws IOException;
//
//	/**
//	 *
//	 * @return the license header in the file or <code>null</code> if not available
//	 */
//	String getLicenseHeader();
//
//	void writeLicenseHeader(Writer writer, String licenseHeader) throws IOException;

}