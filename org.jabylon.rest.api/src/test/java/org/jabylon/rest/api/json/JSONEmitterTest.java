/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.api.json;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Workspace;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class JSONEmitterTest {

    private JSONEmitter fixture;
    private PermissionCallback callback;

    @Before
    public void setup()
    {
    	callback = mock(PermissionCallback.class);
    	when(callback.isAuthorized(Mockito.any(EObject.class))).thenReturn(true);
        fixture = new JSONEmitter(callback);
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

    @Test
    public void testSerializeRestrictedProject(){
    	Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
    	Project project1 = PropertiesFactory.eINSTANCE.createProject();
    	Project project2 = PropertiesFactory.eINSTANCE.createProject();
    	Project project3 = PropertiesFactory.eINSTANCE.createProject();
    	project1.setName("project1");
    	project2.setName("project2");
    	project3.setName("project3");
    	workspace.getChildren().add(project1);
    	workspace.getChildren().add(project2);
    	workspace.getChildren().add(project3);
        String expected = "{\"children\":[{\"name\":\"project2\"},{\"name\":\"project3\"}],\"name\":\"workspace\"}";
        when(callback.isAuthorized(project2)).thenReturn(true);
        when(callback.isAuthorized(project3)).thenReturn(true);
        when(callback.isAuthorized(project1)).thenReturn(false);
        StringBuilder result = new StringBuilder();
        getFixture().serialize(workspace, result, 2);
        assertEquals(expected, result.toString());

    }

    public JSONEmitter getFixture() {
        return fixture;
    }

}
