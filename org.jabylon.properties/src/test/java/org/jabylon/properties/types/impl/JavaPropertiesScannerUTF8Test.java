/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types.impl;

import static org.junit.Assert.assertEquals;

public class JavaPropertiesScannerUTF8Test extends JavaPropertyScannerTest {

	@Override
	protected JavaPropertyScanner createFixture() {
		return new JavaPropertyScannerUTF8();
	}
	
	@Override
	public void testGetEncoding() {
		assertEquals("UTF-8", getFixture().getEncoding());
	}
	
}
