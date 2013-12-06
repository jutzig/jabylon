package org.jabylon.rest.api.json;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.URI;
import org.junit.Before;
import org.junit.Test;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;

public class JSONEmitterTest {

    private JSONEmitter fixture;

    @Before
    public void setup()
    {
        fixture = new JSONEmitter();
    }

    @Test
    public void testSerializePropertyFile() {
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setComment("comment1");
        property.setKey("key1");
        property.setValue("value1");
        file.getProperties().add(property);

        Property property2 = PropertiesFactory.eINSTANCE.createProperty();
        property2.setComment("comment2");
        property2.setKey("key2");
        property2.setValue("value2");
        file.getProperties().add(property2);

        StringBuilder result = new StringBuilder();
        getFixture().serialize(file, result, 2);
        String expected = "{\"properties\":[{\"key\":\"key1\",\"value\":\"value1\",\"comment\":\"comment1\"},{\"key\":\"key2\",\"value\":\"value2\",\"comment\":\"comment2\"}]}";
        assertEquals(expected, result.toString());
    }

    @Test
    public void testSerializePropertyFileDescriptorWithDepth1AndURI() {

        PropertyFileDescriptor spyDescriptor = mock(PropertyFileDescriptor.class);
        when(spyDescriptor.eGet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION)).thenReturn(URI.createURI("test/uri"));
        when(spyDescriptor.eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION)).thenReturn(true);
        when(spyDescriptor.eClass()).thenReturn(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR);
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        when(spyDescriptor.loadProperties()).thenReturn(file);
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setComment("comment1");
        property.setKey("key1");
        property.setValue("value1");
        file.getProperties().add(property);

        Property property2 = PropertiesFactory.eINSTANCE.createProperty();
        property2.setComment("comment2");
        property2.setKey("key2");
        property2.setValue("value2");
        file.getProperties().add(property2);

        StringBuilder result = new StringBuilder();
        getFixture().serialize(spyDescriptor, result, 1);
        String expected = "{\"location\":\"test/uri\"}";
        assertEquals(expected, result.toString());
    }

    @Test
    public void testSerializePropertyFileDescriptorWithDepth1() {

        PropertyFileDescriptor spyDescriptor = mock(PropertyFileDescriptor.class);
        when(spyDescriptor.eClass()).thenReturn(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR);
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        when(spyDescriptor.loadProperties()).thenReturn(file);
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setComment("comment1");
        property.setKey("key1");
        property.setValue("value1");
        file.getProperties().add(property);

        Property property2 = PropertiesFactory.eINSTANCE.createProperty();
        property2.setComment("comment2");
        property2.setKey("key2");
        property2.setValue("value2");
        file.getProperties().add(property2);

        StringBuilder result = new StringBuilder();
        getFixture().serialize(spyDescriptor, result, 1);
        String expected = "{}";
        assertEquals(expected, result.toString());
    }


    /**
     * http://github.com/jutzig/jabylon/issues/issue/38
     */
    @Test
    public void testSerializeWithEmptyProperties(){
        String expected = "{}";
        PropertyFileDescriptor spyDescriptor = mock(PropertyFileDescriptor.class);
        when(spyDescriptor.eClass()).thenReturn(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR);
        when(spyDescriptor.loadProperties()).thenReturn(PropertiesFactory.eINSTANCE.createPropertyFile());
        StringBuilder result = new StringBuilder();
        getFixture().serialize(spyDescriptor, result, 2);
        assertEquals(expected, result.toString());

    }

    @Test
    public void testSerializeWithFilledLocationAndEmptyProperties(){
        String expected = "{\"location\":\"test/uri\"}";
        PropertyFileDescriptor spyDescriptor = mock(PropertyFileDescriptor.class);
        when(spyDescriptor.eGet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION)).thenReturn(URI.createURI("test/uri"));
        when(spyDescriptor.eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION)).thenReturn(true);
        when(spyDescriptor.eClass()).thenReturn(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR);
        when(spyDescriptor.loadProperties()).thenReturn(PropertiesFactory.eINSTANCE.createPropertyFile());
        StringBuilder result = new StringBuilder();
        getFixture().serialize(spyDescriptor, result, 2);
        assertEquals(expected, result.toString());

    }


    public JSONEmitter getFixture() {
        return fixture;
    }

}
