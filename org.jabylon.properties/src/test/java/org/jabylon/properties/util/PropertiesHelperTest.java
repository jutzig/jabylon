/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.tools.ant.filters.StringInputStream;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
import org.jabylon.properties.types.impl.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PropertiesHelperTest {

	private PropertiesHelper fixture;
	private StringWriter writer;

	@Before
	public void setup() {
		fixture = new PropertiesHelper();
		writer = new StringWriter();
	}

	@Test
	public void testLoadReadPropertyWikiExample() throws IOException {
		BufferedReader reader = null;
		try {
			reader = loadFile("wiki_example.properties");
			Property property = fixture.readProperty(reader);
			assertEquals("You are reading the \".properties\" entry.\nThe exclamation mark can also mark text as comments.",
					property.getComment());
			assertEquals("website", property.getKey());
			assertEquals("http://en.wikipedia.org/", property.getValue());

			property = fixture.readProperty(reader);
			assertNull(property.getComment());
			assertEquals("language", property.getKey());
			assertEquals("English", property.getValue());

			property = fixture.readProperty(reader);
			assertEquals("The backslash below tells the application to continue reading\nthe value onto the next line.",
					property.getComment());
			assertEquals("message", property.getKey());
			assertEquals("Welcome to Wikipedia!", property.getValue());

			property = fixture.readProperty(reader);
			assertEquals("Add spaces to the key", property.getComment());
			assertEquals("key with spaces", property.getKey());
			assertEquals("This is the value that could be looked up with the key \"key with spaces\".", property.getValue());

			property = fixture.readProperty(reader);
			assertEquals("Unicode", property.getComment());
			assertEquals("tab", property.getKey());
			assertEquals("\t", property.getValue());
		} finally {
			if (reader != null)
				reader.close();
		}

	}

	/**
	 * @see https://github.com/jutzig/jabylon/issues/55
	 * @throws IOException
	 */
	@Test
	public void testLoadPropertyWithUnicodeAndBOM() throws IOException {
		BufferedReader reader = null;
		try {
			InputStream stream = new BufferedInputStream(loadFileAsStream("Buttons_en.seenls"));
			PropertiesHelper.checkForBom(stream);
			reader = new BufferedReader(new InputStreamReader(stream));
			Property property = fixture.readProperty(reader);
			assertEquals("DTM.CASCADE_SESSION_FRAMES_TEXT", property.getKey());
			assertEquals("Cascade", property.getValue());
			assertEquals("test", fixture.getLicenseHeader());
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	@Test
	public void testReadPropertySingleCharComment() throws IOException {
		BufferedReader reader = null;
		try {
			reader = loadFile("plugin.properties");

			Property property = fixture.readProperty(reader);
			assertEquals("<copyright>\n</copyright>\n\n$Id$", property.getComment());
			assertEquals("pluginName", property.getKey());
			assertEquals("Properties Model", property.getValue());

			property = fixture.readProperty(reader);
			assertNull(property.getComment());
			assertEquals("providerName", property.getKey());
			assertEquals("www.example.org", property.getValue());

			assertNull(fixture.readProperty(reader));

		} finally {
			if (reader != null)
				reader.close();
		}
	}

	@Test
	public void testReadUnicodePropertyWithUnicodeEscapes() throws IOException {

		fixture = new PropertiesHelper(false);
		BufferedReader reader = new BufferedReader(new StringReader("äö\\u00DC = aaa"));

		try {
			Property property = fixture.readProperty(reader);
			assertEquals("even with escaping turned of, it must still parse the escaped strings", "äöÜ", property.getKey());
			assertEquals("aaa", property.getValue());
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	@Test
	public void testWriteProperty() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("test");
		property.setKey("key");
		property.setValue("value");
		fixture.writeProperty(writer, property);
		assertEquals("#test\nkey = value\n", writer.toString());
	}

	@Test
	public void testWritePropertyUnicode() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("test");
		property.setKey("ä");
		property.setValue("ü");
		fixture.writeProperty(writer, property);
		assertEquals("#test\n\\u00e4 = \\u00fc\n", writer.toString());

	}

	@Test
	public void testWritePropertyKeyWithSpaces() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("test");
		property.setKey("key with spaces");
		property.setValue("test");
		fixture.writeProperty(writer, property);
		assertEquals("#test\nkey\\ with\\ spaces = test\n", writer.toString());

	}

	@Test
	public void testWritePropertyMultiline() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\ntest");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\\ntest\n", writer.toString());

	}


	@Test
	public void testWritePropertyWindowsMultiline() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\r\ntest");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\\r\\ntest\n", writer.toString());

	}

	@Test
	public void testWritePropertyKeyMultiline() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key\nkey");
		property.setValue("test");
		fixture.writeProperty(writer, property);
		assertEquals("key\\\nkey = test\n", writer.toString());

	}

	@Test
	public void testReadPropertyWindowsMultiline() throws IOException {
		BufferedReader reader = asReader("key = test\\\r\ntest\n");
		Property property = fixture.readProperty(reader);
		assertEquals("key", property.getKey());
		assertEquals("With the leading \\ we need to read into the next line","testtest", property.getValue());
	}
	
	@Test
	public void testReadPropertyMultilineLeadingSpaces() throws IOException {
		BufferedReader reader = asReader("key = test\\\n   test\n");
		Property property = fixture.readProperty(reader);
		assertEquals("key", property.getKey());
		assertEquals("With the leading \\ we need to read into the next line and also ignore leading whitespace there","testtest", property.getValue());
	}

	@Test
	public void testWritePropertyMultilineComment() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("test\ntest");
		property.setKey("key");
		property.setValue("test");
		fixture.writeProperty(writer, property);
		assertEquals("#test\n#test\nkey = test\n", writer.toString());

	}

	private BufferedReader loadFile(String name) throws FileNotFoundException {
		File file = new File("src/test/resources/project/master/org/jabylon/properties/util/" + name);
		return new BufferedReader(new FileReader(file));
	}

	private InputStream loadFileAsStream(String name) throws FileNotFoundException {
		File file = new File("src/test/resources/project/master/org/jabylon/properties/util/" + name);
		return new FileInputStream(file);
	}

	@Test
	public void testCheckForBom() throws Exception {
		byte[] bytes = ByteOrderMark.UTF_16BE.bytes();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(bytes);
		out.write("test".getBytes());

		ByteArrayInputStream stream = new ByteArrayInputStream(out.toByteArray());
		ByteOrderMark bom = PropertiesHelper.checkForBom(stream);
		assertEquals(ByteOrderMark.UTF_16BE, bom);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertEquals("The reader must contain everything after the bom", "test", reader.readLine());

	}

	@Test
	public void testCheckForBomNoBom() throws Exception {
		ByteArrayInputStream stream = new ByteArrayInputStream("test".getBytes());
		ByteOrderMark bom = PropertiesHelper.checkForBom(stream);
		assertNull(bom);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertEquals("The reader must contain everything after the bom", "test", reader.readLine());

	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckForBomWrongStream() throws Exception {
		InputStream in = Mockito.mock(InputStream.class);
		when(in.markSupported()).thenReturn(false);
		PropertiesHelper.checkForBom(in);
	}

	/**
	 * tests that files that contain only a newline are handled correctly
	 * http://github.com/jutzig/jabylon/issues/issue/104
	 * @throws IOException
	 */
    @Test
	public void testEmptyFile() throws IOException {
    	StringInputStream in = new StringInputStream("\n\r");
    	Property property = fixture.readProperty(new BufferedReader(new InputStreamReader(in)));
    	assertNull(property);
	}

    /**
     * tests that unicode non breakable space (\u00A0)is preserved
     * see https://github.com/jutzig/jabylon/issues/149
     * @throws IOException
     */
	@Test
	public void testPreserveNBSPWrite() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\u00A0");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\\u00a0\n", writer.toString());
	}

	/**
     * tests that unicode non breakable space (\u00A0)is preserved
     * see https://github.com/jutzig/jabylon/issues/149
     * @throws IOException
     */
	@Test
	public void testPreserveNBSPWriteNoEscaping() throws IOException {
		fixture = new PropertiesHelper(false);
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\u00A0");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\u00A0\n", writer.toString());
	}

    /**
     * tests that unicode non breakable space (\u00A0)is preserved
     * see https://github.com/jutzig/jabylon/issues/149
     * @throws IOException
     */
	@Test
	public void testPreserveNBSPRead() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("key = test\\u00a0\n"));

		try {
			Property property = fixture.readProperty(reader);
			assertEquals("the NBSP must survive", "test\u00a0", property.getValue());
		} finally {
			if (reader != null)
				reader.close();
		}
	}

    /**
     * tests that unicode non breakable space (\u00A0)is preserved
     * see https://github.com/jutzig/jabylon/issues/149
     * @throws IOException
     */
	@Test
	public void testPreserveNBSPReadNoEscaping() throws IOException {
		fixture = new PropertiesHelper(false);
		BufferedReader reader = new BufferedReader(new StringReader("key = test\u00a0\n"));

		try {
			Property property = fixture.readProperty(reader);
			assertEquals("the NBSP must survive", "test\u00a0", property.getValue());
		} finally {
			if (reader != null)
				reader.close();
		}
	}

    /**
     * tests that quotes in comments are handled properly
     * see https://github.com/jutzig/jabylon/issues/183
     * @throws IOException
     */
    @Test
    public void testQuotesInComments() throws IOException {
        fixture = new PropertiesHelper(false);
        BufferedReader reader = new BufferedReader(new StringReader("# Processes \"display\" button\r\n"
                                                                    +"PROC_INST_DISPLAY_IMG=dashb_display_12.gif"));

        try {
            Property property = fixture.readProperty(reader);
            assertEquals("dashb_display_12.gif", property.getValue());
            assertEquals("PROC_INST_DISPLAY_IMG", property.getKey());
            assertEquals("Processes \"display\" button", property.getComment());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * tests that quotes in comments are handled properly
     * see https://github.com/jutzig/jabylon/issues/183
     * @throws IOException
     */
    @Test
    public void testQuotesInExclamationComments() throws IOException {
        fixture = new PropertiesHelper(false);
        BufferedReader reader = new BufferedReader(new StringReader("! Processes \"display\" button\r\n"
                                                                    +"PROC_INST_DISPLAY_IMG=dashb_display_12.gif"));

        try {
            Property property = fixture.readProperty(reader);
            assertEquals("dashb_display_12.gif", property.getValue());
            assertEquals("PROC_INST_DISPLAY_IMG", property.getKey());
            assertEquals("Processes \"display\" button", property.getComment());
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * tests that the parser resets a key when the newline is not escaped
     * see https://github.com/jutzig/jabylon/issues/183
     * @throws IOException
     */
    @Test
    public void testMultilineHandling() throws IOException {
        fixture = new PropertiesHelper(false);
        BufferedReader reader = new BufferedReader(new StringReader("// foo\n\r"
                                                                    +"PROC_INST_DISPLAY_IMG=dashb_display_12.gif"));

        try {
            Property property = fixture.readProperty(reader);
            assertEquals("dashb_display_12.gif", property.getValue());
            assertEquals("PROC_INST_DISPLAY_IMG", property.getKey());
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    
    /**
     * tests that leading spaces are properly escaped
     * see https://github.com/jutzig/jabylon/issues/186
     * @throws IOException
     */
	@Test
	public void testLeadingSpacesWrite() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue(" test");
		fixture.writeProperty(writer, property);
		assertEquals("key = \\ test\n", writer.toString());
	}
	

    /**
     * tests that leading spaces are preserved during parsing
     * see https://github.com/jutzig/jabylon/issues/186
     * @throws IOException
     */
	@Test
	public void testLeadingSpacesRead() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("key = \\ value"));
		Property property = fixture.readProperty(reader);
		assertEquals("key", property.getKey());
		assertEquals(" value", property.getValue());

	}
	
    /**
     * tests that newlines are converted to \n
     * see https://github.com/jutzig/jabylon/issues/185
     * @throws IOException
     */
	@Test
	public void testLFWrite() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\ntest");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\\ntest\n", writer.toString());
	}
	
	/**
     * tests that CRLFs are converted to \r\n
     * see https://github.com/jutzig/jabylon/issues/185
     * @throws IOException
     */
	@Test
	public void testCRLFWrite() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("test\r\ntest");
		fixture.writeProperty(writer, property);
		assertEquals("key = test\\r\\ntest\n", writer.toString());
	}
	
    /**
     * tests that \n and \r are replaced with actual values during reading
     * see https://github.com/jutzig/jabylon/issues/186
     * @throws IOException
     */
	@Test
	public void testCRLFRead() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("key = value\\r\\n"));
		Property property = fixture.readProperty(reader);
		assertEquals("key", property.getKey());
		assertEquals("value\r\n", property.getValue());

	}
	
	/**
     * tests that annotations are written correctly
     * @throws IOException
     */
	@Test
	public void testWritePropertyWithAnnotation() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("comment");
		PropertyAnnotation ann1 = PropertiesFactory.eINSTANCE.createPropertyAnnotation();
		ann1.setName("foo");
		ann1.getValues().put("test", "value");
		property.getAnnotations().add(ann1);
		
		PropertyAnnotation ann2 = PropertiesFactory.eINSTANCE.createPropertyAnnotation();
		ann2.setName("bar");
		property.getAnnotations().add(ann2);
		property.setKey("key");
		property.setValue("test");
		fixture.writeProperty(writer, property);
		assertEquals("#@foo(test=\"value\")@bar\n#comment\nkey = test\n", writer.toString());
	}
	
    /**
     * tests that annotations are read correctly
     * @throws IOException
     */
	@Test
	public void testReadPropertyWithAnnotation() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("#@foo(test=\"value\")@bar\nkey = value"));
		Property property = fixture.readProperty(reader);
		assertEquals("key", property.getKey());
		assertEquals("value", property.getValue());
		assertEquals(2, property.getAnnotations().size());
		assertEquals("foo", property.getAnnotations().get(0).getName());
	}
	

    /**
     * tests that annotations are read correctly
     * @throws IOException
     */
	@Test
	public void testColonInKey() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("Name\\: = Name\\:"));
		Property property = fixture.readProperty(reader);
		assertEquals("Name:", property.getKey());
		assertEquals("Name:", property.getValue());
	}
	
	@Test
	public void testColonInKey2() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("Name\\: = Name:"));
		Property property = fixture.readProperty(reader);
		assertEquals("Name:", property.getKey());
		assertEquals("Name:", property.getValue());
	}
	

    protected BufferedReader asReader(String string)
    {
    	return new BufferedReader(new StringReader(string));
    }
}
