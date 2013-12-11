/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Review;

public class ProjectResourcePanelTest {


    private ProjectResourcePanel fixture;

    @Before
    public void createFixture()
    {
        //needed to setup the Thread Context
        new WicketTester();
        fixture = new ProjectResourcePanel(PropertiesFactory.eINSTANCE.createResourceFolder(), new PageParameters());
    }

    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void computeProgressBars() {
        Resolvable mock = mock(Resolvable.class);
        when(mock.getPercentComplete()).thenReturn(15);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals(15, result.getSuccess());
        assertEquals(0, result.getWarning());
        assertEquals(0, result.getDanger());
    }


    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     */
    @Test
    public void computeProgressBarsDescriptorZeroKeys() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(0);
        when(mock.getPercentComplete()).thenReturn(0);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals(0, result.getSuccess());
        assertEquals(0, result.getWarning());
        assertEquals(0, result.getDanger());
    }


    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     */
    @Test
    public void computeProgressBarsDescriptorWithReviews() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(100);
        when(mock.getPercentComplete()).thenReturn(80);
        @SuppressWarnings("unchecked")
        EList<Review> reviews = mock(EList.class);
        when(reviews.size()).thenReturn(10);
        when(mock.getReviews()).thenReturn(reviews);

        Triplet result = fixture.computeProgressBars(mock);
        assertEquals("Must subtract the 'dirty' keys from the total percentage", 70, result.getSuccess());
        assertEquals(10, result.getWarning());
        assertEquals(0, result.getDanger());
    }

    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     */
    @Test
    public void computeProgressBarsDescriptorComplete() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(100);
        when(mock.getPercentComplete()).thenReturn(100);
        @SuppressWarnings("unchecked")
        EList<Review> reviews = mock(EList.class);
        when(reviews.size()).thenReturn(10);
        when(mock.getReviews()).thenReturn(reviews);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals("Must subtract the 'dirty' keys from the total percentage", 90, result.getSuccess());
        assertEquals(10, result.getWarning());
        assertEquals(0, result.getDanger());
    }



    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     */
    @Test
    public void computeProgressBarsDescriptorWithMasterAndReviews() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        PropertyFileDescriptor master = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(50);
        when(master.getKeys()).thenReturn(100);
        when(mock.getMaster()).thenReturn(master);
        when(mock.getPercentComplete()).thenReturn(80);
        @SuppressWarnings("unchecked")
        EList<Review> reviews = mock(EList.class);
        when(reviews.size()).thenReturn(10);
        when(mock.getReviews()).thenReturn(reviews);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals("Must subtract the 'dirty' keys from the amount of master keys", 70, result.getSuccess());
        assertEquals(10, result.getWarning());
        assertEquals(0, result.getDanger());
    }

    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     * https://github.com/jutzig/jabylon/issues/122
     */
    @Test
    public void computeProgressBarsDescriptorWith5PercentMissing() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(0);
        when(mock.getPercentComplete()).thenReturn(95);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals(95, result.getSuccess());
        assertEquals(0, result.getWarning());
        assertEquals("with 5% or less it should show danger",5, result.getDanger());
    }

    /**
     * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
     * https://github.com/jutzig/jabylon/issues/122
     */
    @Test
    public void computeProgressBarsDescriptorWith5PercentMissingAndWarnings() {
        PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
        when(mock.getKeys()).thenReturn(100);
        when(mock.getPercentComplete()).thenReturn(95);
        @SuppressWarnings("unchecked")
        EList<Review> reviews = mock(EList.class);
        when(reviews.size()).thenReturn(10);
        when(mock.getReviews()).thenReturn(reviews);
        Triplet result = fixture.computeProgressBars(mock);
        assertEquals(85, result.getSuccess());
        assertEquals(10, result.getWarning());
        assertEquals("with 5% or less it should show danger",5, result.getDanger());
    }


}
