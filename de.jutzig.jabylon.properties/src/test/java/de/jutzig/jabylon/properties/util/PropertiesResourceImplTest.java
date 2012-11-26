package de.jutzig.jabylon.properties.util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.Before;
import org.junit.Test;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;

public class PropertiesResourceImplTest {

	
	private PropertiesResourceImpl fixture;
	
	@Before
	public void setup()
	{
		fixture = createFixture();
	}
	
	private PropertiesResourceImpl createFixture() {
		return new PropertiesResourceImpl(null);
	}

	public PropertiesResourceImpl getFixture() {
		return fixture;
	}
	
	@Test
	public void testIssue55() throws IOException {
		//tests if files with a BOM and a comment in the first line are parsed correctly
		URI uri = URI.createFileURI("src/test/resources/project/master/de/jutzig/jabylon/properties/util/Buttons_en.seenls");
		getFixture().setURI(uri);
		getFixture().load(null);
		PropertyFile file = (PropertyFile) getFixture().getContents().get(0);
		EList<Property> properties = file.getProperties();
		assertEquals(1,properties.size());
		
		Property property = properties.get(0);
        assertEquals("DTM.CASCADE_SESSION_FRAMES_TEXT", property.getKey());
        assertEquals("Cascade", property.getValue());
        assertEquals("test", file.getLicenseHeader());
	}
	
	/**
	 * 	@see https://github.com/jutzig/jabylon/issues/62 
	 */
	@Test
	public void testReadLicenseHeaderAndComment() throws IOException {
		URI uri = URI.createFileURI("src/test/resources/licenseHeader.properties");
		getFixture().setURI(uri);
		getFixture().load(null);
		PropertyFile file = (PropertyFile) getFixture().getContents().get(0);
		EList<Property> properties = file.getProperties();
		assertEquals(1,properties.size());
		
		Property property = properties.get(0);
        assertEquals("key", property.getKey());
        assertEquals("value", property.getValue());
        assertEquals("licensed under\napache license\nv3", file.getLicenseHeader());
	}

	/**
	 * 	@see https://github.com/jutzig/jabylon/issues/62 
	 */
	@Test
	public void testReadNoLicenseHeaderAndComment() throws IOException {
		URI uri = URI.createFileURI("src/test/resources/noLicenseHeader.properties");
		getFixture().setURI(uri);
		getFixture().load(null);
		PropertyFile file = (PropertyFile) getFixture().getContents().get(0);
		EList<Property> properties = file.getProperties();
		assertEquals(1,properties.size());
		
		Property property = properties.get(0);
        assertEquals("key", property.getKey());
        assertEquals("value", property.getValue());
        assertNull(file.getLicenseHeader());
	}
	
	/**
	 * 	@see https://github.com/jutzig/jabylon/issues/62 
	 */
	@Test
	public void testWriteLicenseHeaderAndComment() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
		file.setLicenseHeader("apache license\nv3");
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey("key");
		property.setValue("value");
		property.setComment("comment");
		file.getProperties().add(property);
		getFixture().getContents().add(file);
		getFixture().doSave(out, null);
		String result = out.toString("UTF-8");
		assertEquals("#apache license\n#v3\n\n#comment\nkey = value\n", result);
	}
}
