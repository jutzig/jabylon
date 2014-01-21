/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class OBRRepositoryConnectorImplTest {

	private OBRRepositoryConnectorImpl fixture;
	
	@Before
	public void setup() {
		fixture = new OBRRepositoryConnectorImpl();
	}
	
	@Test
	public void testGetHighestBundleVersions() {
		List<String> versions = fixture.getHighestBundleVersions("a.b_1.0.0.jar","a.b_1.0.1.jar","a.b_2.0.0.jar","a.b_19.0.1.jar");
		assertEquals(1, versions.size());
		assertEquals("a.b_19.0.1.jar", versions.get(0));
	}
	
	@Test
	public void testGetHighestBundleVersionsMixed() {
		List<String> versions = fixture.getHighestBundleVersions("a.b_1.0.0.jar","a.b_1.0.1.jar","b_2.0.0.jar");
		assertEquals(2, versions.size());
		assertEquals("a.b_1.0.1.jar", versions.get(0));
		assertEquals("b_2.0.0.jar", versions.get(1));
	}	

	@Test
	public void testGetHighestBundleVersionsSnapshot() {
		List<String> versions = fixture.getHighestBundleVersions("a_1.0.0.SNAPSHOT.jar","a_1.0.0.jar");
		assertEquals(1, versions.size());
		assertEquals("a_1.0.0.jar", versions.get(0));
	}	
	

	@Test
	public void testGetHighestBundleVersionsShort() {
		List<String> versions = fixture.getHighestBundleVersions("a_2.0.0.jar","a_2.1.jar");
		assertEquals(1, versions.size());
		assertEquals("a_2.1.jar", versions.get(0));
	}		
	
}
