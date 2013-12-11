/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.review.standard.internal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.properties.Property;

public class PunctuationCheckTest extends AbstractReviewTest {

    @Test
    public void testReview() {
        Property templ = createProperty("", "test {\"}");
        Property trans = createProperty("", "{\" test");
        assertNotNull(getFixture().review(null, templ, trans));
    }

    @Test
    public void testReview2() {
        Property templ = createProperty("", "test {\"}}}");
        Property trans = createProperty("", "{\"}}}\" test");
        assertNotNull(getFixture().review(null, templ, trans));
    }

    @Test
    public void testReview3() {
        Property templ = createProperty("", "test \"\"\"");
        Property trans = createProperty("", "\"\" test");
        assertNotNull(getFixture().review(null, templ, trans));
    }

    @Test
    public void testReview4() {
        Property templ = createProperty("", "test ()");
        Property trans = createProperty("", "()( test");
        assertNotNull(getFixture().review(null, templ, trans));
    }

    @Test
    public void testReview5() {
        Property templ = createProperty("", "test ()");
        Property trans = createProperty("", "() test");
        assertNull(getFixture().review(null, templ, trans));
    }

    @Test
    public void testReview6() {
        Property templ = createProperty("", "test (\")");
        Property trans = createProperty("", "(\") test");
        assertNull(getFixture().review(null, templ, trans));
    }


    @Test
    public void testReview7() {
        Property templ = createProperty("", "test '}{[]()\"");
        Property trans = createProperty("", "'}{[])(\" test");
        assertNull(getFixture().review(null, templ, trans));
    }



    @Override
    public ReviewParticipant createFixture() {
        return new PunctuationCheck();
    }

}
