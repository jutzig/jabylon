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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyAnnotations;
import org.junit.Before;
import org.junit.Test;

public class TMXConverterTest {

	private TMXConverter fixture;
	
	@Before
	public void setup()
	{
		fixture = new TMXConverter();
	}
	
	@Test
	public void testLoad() throws FileNotFoundException, IOException {
		PropertyFile file = fixture.load(new FileInputStream("src/test/resources/tmx/sample.tmx"), "UTF-8");
		assertEquals(5,file.getProperties().size());
		int i = 0;
		assertEquals("Start Game", file.getProperties().get(i).getKey());
		assertEquals("Iniciar el juego", file.getProperties().get(i++).getValue());
		
		assertEquals("Options", file.getProperties().get(i).getKey());
		assertEquals("Opciones", file.getProperties().get(i++).getValue());
		
		assertEquals("Credits", file.getProperties().get(i).getKey());
		assertEquals("Creditos", file.getProperties().get(i++).getValue());
		
		assertEquals("Exit", file.getProperties().get(i).getKey());
		assertEquals("Salir", file.getProperties().get(i++).getValue());
		
		assertEquals("Resume", file.getProperties().get(i).getKey());
		assertEquals("Reanudar", file.getProperties().get(i++).getValue());
		
		Property property = file.getProperties().get(0);
		PropertyAnnotation annotation = property.findAnnotation(PropertyAnnotations.ANNOTATION_LANGUAGE);
		assertEquals("en_US", annotation.getValues().get(PropertyAnnotations.SOURCE_LANGUAGE));
		assertEquals("es_MX", annotation.getValues().get(PropertyAnnotations.TARGET_LANGUAGE));
	}

}
