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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.jabylon.properties.ScanConfiguration;
import org.junit.Before;
import org.junit.Test;

public class JavaPropertyScannerTest {

	private JavaPropertyScanner fixture;
	
	@Before
	public void setup(){
		fixture = createFixture();
	}
	
	protected JavaPropertyScanner createFixture() {
		return new JavaPropertyScanner();
	}
	
	public JavaPropertyScanner getFixture() {
		return fixture;
	}

	@Test
	public void testIsTemplate() {
		ScanConfiguration configuration = mock(ScanConfiguration.class);
		when(configuration.getMasterLocale()).thenReturn("de");
		assertTrue(getFixture().isTemplate(new File("test_de.properties"), configuration));
		assertFalse(getFixture().isTemplate(new File("test.properties"), configuration));
		assertFalse(getFixture().isTemplate(new File("test_en.properties"), configuration));
	}

	@Test
	public void testIsTranslation() {
		ScanConfiguration configuration = mock(ScanConfiguration.class);
		when(configuration.getMasterLocale()).thenReturn("de");
		assertFalse(getFixture().isTranslation(new File("test_de.properties"), configuration));
		assertFalse(getFixture().isTranslation(new File("test.properties"), configuration));
		assertTrue(getFixture().isTranslation(new File("test_en.properties"), configuration));
	}

	@Test
	public void testFindTemplate() {
		ScanConfiguration configuration = mock(ScanConfiguration.class);
		assertEquals("test.properties", getFixture().findTemplate(new File("test_de_DE.properties"), configuration).getName());
		assertEquals("test.properties", getFixture().findTemplate(new File("test_de.properties"), configuration).getName());
	}
	
	@Test
	public void testFindTemplateWithMasterLocale() {
		ScanConfiguration configuration = mock(ScanConfiguration.class);
		when(configuration.getMasterLocale()).thenReturn("en_US");
		assertEquals("test_en_US.properties", getFixture().findTemplate(new File("test_de_DE.properties"), configuration).getName());
		assertEquals("test_en_US.properties", getFixture().findTemplate(new File("test_de_US.properties"), configuration).getName());
	}

	@Test
	public void testGetLocale() {
		assertEquals(new Locale("de","DE"),getFixture().getLocale(new File("test_de_DE.properties")));
		assertEquals(new Locale("de","DE"),getFixture().getLocale(new File("tes_en_USt_de_DE.properties")));
	}

	@Test
	public void testFindTranslations() throws IOException {
		File file = mock(File.class);
		when(file.getName()).thenReturn("foo.properties");
		when(file.getAbsolutePath()).thenReturn(new File("foo.properties").getAbsolutePath());
		when(file.getCanonicalPath()).thenReturn(new File("foo.properties").getCanonicalPath());
		when(file.getPath()).thenReturn(new File("foo.properties").getPath());
		File parent = mock(File.class);
		when(file.getParentFile()).thenReturn(parent);
		File[] children = new File[]{new File("foo.properties"),new File("other_fr.properties"),new File("foo_de.properties"),new File("foo_en_US.properties")};
		when(parent.listFiles()).thenReturn(children);
		Map<Locale, File> result = getFixture().findTranslations(file, mock(ScanConfiguration.class));
		assertEquals(2, result.size());
		assertEquals("foo_de.properties", result.get(new Locale("de")).getName());
		assertEquals("foo_en_US.properties", result.get(new Locale("en","US")).getName());
	}

	@Test
	public void testComputeTranslationPath() {
		assertEquals("test_de_DE.properties", getFixture().computeTranslationPath(new File("test.properties"), null, new Locale("de","DE")).getName());
		assertEquals("tes_en_USt_de_DE.properties", getFixture().computeTranslationPath(new File("tes_en_USt.properties"), null, new Locale("de","DE")).getName());
		assertEquals("test_de_DE.properties", getFixture().computeTranslationPath(new File("test_en.properties"), new Locale("en"), new Locale("de","DE")).getName());
	}

	@Test
	public void testIsBilingual() {
		assertFalse(getFixture().isBilingual());
	}


	@Test
	public void testGetEncoding() {
		assertEquals("ISO-8859-1", getFixture().getEncoding());
	}

}
