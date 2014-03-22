/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.review.standard.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.review.TerminologyProvider;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class EqualityCheckTest extends AbstractReviewTest {

	
	private TerminologyProvider terminologyProvider;

	@Override
	public void setup() {
		super.setup();
		this.terminologyProvider = mock(TerminologyProvider.class);
		Map<String, Property> terminology = new HashMap<String, Property>();
		terminology.put("test", createProperty("test", "test"));
		when(terminologyProvider.getTerminology((Locale) Mockito.any())).thenReturn(terminology);
		getFixture().bindTerminologyProvider(terminologyProvider);
	}
	
	@Override
	public ReviewParticipant createFixture() {
		return new EqualityCheck();
	}
	
	@Test
	public void testIsEqual() {
		assertNull(getFixture().review(mock(PropertyFileDescriptor.class), createProperty("key", "value"), createProperty("key", "value1")));
		assertNotNull(getFixture().review(mock(PropertyFileDescriptor.class), createProperty("key", "value"), createProperty("key", "value")));
	}
	
	@Test
	public void testIsEqualWithTerminology() {
		assertNull("equality is ok if terminology says so",getFixture().review(mock(PropertyFileDescriptor.class), createProperty("test", "test"), createProperty("test", "test")));
	}
	
	@Override
	public EqualityCheck getFixture() {
		return (EqualityCheck) super.getFixture();
	}

}
