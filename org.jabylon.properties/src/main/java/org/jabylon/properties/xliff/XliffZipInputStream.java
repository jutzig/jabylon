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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;

/**
 * @author c.samulski (2016-02-09)
 */
public final class XliffZipInputStream extends ZipInputStream {

	public XliffZipInputStream(InputStream in) {
		super(in);
	}

	/**
	 * Prevent {@link DocumentBuilder} implementations from closing our {@link ZipInputStream} on
	 * initial {@link ZipEntry} consumption.<br>
	 */
	@Override
	public void close() {
		// do nothing.
	}

	/**
	 * Call {@link ZipInputStream#close()}<br>
	 */
	public final void doClose() throws IOException {
		super.close();
	}
}
