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

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.Review;
import org.junit.Before;
import org.junit.Test;

public class EObjectNameComparatorTest {

	private EObjectNameComparator<ResourceFolder> fixture;
	private ResourceFolder folder1;
	private ResourceFolder folder2;

	@Before
	public void setUp() throws Exception {
		fixture = new EObjectNameComparator<ResourceFolder>();
		folder1 = PropertiesFactory.eINSTANCE.createResourceFolder();
		folder2 = PropertiesFactory.eINSTANCE.createResourceFolder();
	}

	@Test
	public void testCompare() {
		folder1.setName("a");
		folder2.setName("b");
		assertEquals(-1, fixture.compare(folder1, folder2));
		
		folder1.setName("a");
		folder2.setName("B");
		assertEquals(-1, fixture.compare(folder1, folder2));
		
		folder1.setName("A");
		folder2.setName("b");
		assertEquals(-1, fixture.compare(folder1, folder2));
		
		folder1.setName("A");
		folder2.setName("B");
		assertEquals(-1, fixture.compare(folder1, folder2));
	}
	
	@Test
	public void testCompareOtherFeature() {
		Review r1 = PropertiesFactory.eINSTANCE.createReview();
		Review r2 = PropertiesFactory.eINSTANCE.createReview();
		EObjectNameComparator<Review> c = new EObjectNameComparator<Review>(PropertiesPackage.Literals.REVIEW__KEY.getName());
		
		r1.setKey("a");
		r2.setKey("b");
		assertEquals(-1, c.compare(r1, r2));
		
		r1.setKey("a");
		r2.setKey("B");
		assertEquals(-1, c.compare(r1, r2));
		
		r1.setKey("A");
		r2.setKey("b");
		assertEquals(-1, c.compare(r1, r2));
		
		r1.setKey("A");
		r2.setKey("B");
		assertEquals(-1, c.compare(r1, r2));
		
		
	}

}
