package de.jutzig.jabylon.properties.util;

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
import java.io.ObjectInputStream.GetField;

import org.apache.tools.ant.filters.StringInputStream;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;

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
			assertEquals("Welcome to \nWikipedia!", property.getValue());

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
			fixture.checkForBom(stream);
			reader = new BufferedReader(new InputStreamReader(stream));
			Property property = fixture.readProperty(reader);
			assertEquals("DTM.CASCADE_SESSION_FRAMES_TEXT", property.getKey());
			assertEquals("Cascade", property.getValue());
			assertEquals("test", property.getComment());
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
		assertEquals("key = test\\\ntest\n", writer.toString());

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
	public void testWritePropertyMultilineComment() throws IOException {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setComment("test\ntest");
		property.setKey("key");
		property.setValue("test");
		fixture.writeProperty(writer, property);
		assertEquals("#test\n#test\nkey = test\n", writer.toString());

	}

	private BufferedReader loadFile(String name) throws FileNotFoundException {
		File file = new File("src/test/resources/project/master/de/jutzig/jabylon/properties/util/" + name);
		return new BufferedReader(new FileReader(file));
	}

	private InputStream loadFileAsStream(String name) throws FileNotFoundException {
		File file = new File("src/test/resources/project/master/de/jutzig/jabylon/properties/util/" + name);
		return new FileInputStream(file);
	}

	@Test
	public void testCheckForBom() throws Exception {
		byte[] bytes = ByteOrderMark.UTF_16BE.bytes();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(bytes);
		out.write("test".getBytes());

		ByteArrayInputStream stream = new ByteArrayInputStream(out.toByteArray());
		ByteOrderMark bom = fixture.checkForBom(stream);
		assertEquals(ByteOrderMark.UTF_16BE, bom);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertEquals("The reader must contain everything after the bom", "test", reader.readLine());

	}

	@Test
	public void testCheckForBomNoBom() throws Exception {
		ByteArrayInputStream stream = new ByteArrayInputStream("test".getBytes());
		ByteOrderMark bom = fixture.checkForBom(stream);
		assertNull(bom);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertEquals("The reader must contain everything after the bom", "test", reader.readLine());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckForBomWrongStream() throws Exception {
		InputStream in = Mockito.mock(InputStream.class);
		when(in.markSupported()).thenReturn(false);
		fixture.checkForBom(in);
	}
}
