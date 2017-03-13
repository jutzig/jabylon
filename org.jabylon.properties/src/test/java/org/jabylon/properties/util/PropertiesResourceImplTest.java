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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.impl.JavaPropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScannerUTF8;
import org.junit.Before;
import org.junit.Test;


public class PropertiesResourceImplTest
{

    private PropertiesResourceImpl fixture;


    @Before
    public void setup()
    {
        fixture = createFixture();
    }


    private PropertiesResourceImpl createFixture()
    {
        return new PropertiesResourceImpl(null);
    }


    public PropertiesResourceImpl getFixture()
    {
        return fixture;
    }


    @Test
    public void testIssue55()
        throws IOException
    {
        // tests if files with a BOM and a comment in the first line are parsed correctly
        URI uri = URI.createFileURI("src/test/resources/project/master/org/jabylon/properties/util/Buttons_en.seenls");
        getFixture().setURI(uri);
        getFixture().load(null);
        PropertyFile file = (PropertyFile)getFixture().getContents().get(0);
        EList<Property> properties = file.getProperties();
        assertEquals(1, properties.size());

        Property property = properties.get(0);
        assertEquals("DTM.CASCADE_SESSION_FRAMES_TEXT", property.getKey());
        assertEquals("Cascade", property.getValue());
        assertEquals("test", file.getLicenseHeader());
    }


    /**
     * @see https://github.com/jutzig/jabylon/issues/62
     */
    @Test
    public void testReadLicenseHeaderAndComment()
        throws IOException
    {
        URI uri = URI.createFileURI("src/test/resources/licenseHeader.properties");
        getFixture().setURI(uri);
        getFixture().load(null);
        PropertyFile file = (PropertyFile)getFixture().getContents().get(0);
        EList<Property> properties = file.getProperties();
        assertEquals(1, properties.size());

        Property property = properties.get(0);
        assertEquals("key", property.getKey());
        assertEquals("value", property.getValue());
        assertEquals("licensed under\napache license\nv3", file.getLicenseHeader());
    }


    /**
     * @see https://github.com/jutzig/jabylon/issues/62
     */
    @Test
    public void testReadNoLicenseHeaderAndComment()
        throws IOException
    {
        URI uri = URI.createFileURI("src/test/resources/noLicenseHeader.properties");
        getFixture().setURI(uri);
        getFixture().load(null);
        PropertyFile file = (PropertyFile)getFixture().getContents().get(0);
        EList<Property> properties = file.getProperties();
        assertEquals(1, properties.size());

        Property property = properties.get(0);
        assertEquals("key", property.getKey());
        assertEquals("value", property.getValue());
        assertNull(file.getLicenseHeader());
    }

    @Test
    public void testReadPropertiesWithAnnotations()
        throws IOException
    {
        URI uri = URI.createFileURI("src/test/resources/annotations/annotations.properties");
        getFixture().setURI(uri);
        getFixture().load(null);
        PropertyFile file = (PropertyFile)getFixture().getContents().get(0);
        EList<Property> properties = file.getProperties();
        //there is actually 3, but one needs to be hidden because it is non-translatable
        assertEquals(2, properties.size());

        Property property = properties.get(0);
        assertEquals("key", property.getKey());
        assertEquals("value", property.getValue());
        

        property = properties.get(1);
        assertEquals("key3", property.getKey());
        assertEquals("value", property.getValue());
        
        EList<PropertyAnnotation> annotations = property.getAnnotations();
        PropertyAnnotation annotation = annotations.get(0);
        assertEquals("myannotation", annotation.getName());
        assertEquals("bar", annotation.getValues().get("foo"));
        
    }
    
    /**
     * @see https://github.com/jutzig/jabylon/issues/62
     */
    @Test
    public void testWriteLicenseHeaderAndComment()
        throws IOException
    {
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


    /**
     * @throws IOException
     * @see https://github.com/jutzig/jabylon/issues/5
     */
    @Test
    public void testWriteBOMForUnicodeMode()
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setKey("key");
        property.setValue("value");
        property.setComment("comment");
        file.getProperties().add(property);
        getFixture().getContents().add(file);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(PropertiesResourceImpl.OPTION_FILEMODE, JavaPropertyScannerUTF8.TYPE);
        getFixture().doSave(out, options);
        ByteOrderMark mark = ByteOrderMark.read(new ByteArrayInputStream(out.toByteArray()));
        assertEquals("Must write a BOM in unicode mode", ByteOrderMark.UTF_8, mark);
    }

    @Test
    public void testDoNotWriteBOMForIsoMode()
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setKey("key");
        property.setValue("value");
        property.setComment("comment");
        file.getProperties().add(property);
        getFixture().getContents().add(file);
        Map<String, Object> options = new HashMap<String, Object>();
        options.put(PropertiesResourceImpl.OPTION_FILEMODE, JavaPropertyScanner.TYPE);
        getFixture().doSave(out, options);
        ByteOrderMark mark = ByteOrderMark.read(new ByteArrayInputStream(out.toByteArray()));
        assertNull("Must not write a BOM in iso mode", mark);
    }
}
