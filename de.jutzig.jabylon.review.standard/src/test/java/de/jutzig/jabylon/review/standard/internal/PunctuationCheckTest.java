package de.jutzig.jabylon.review.standard.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.properties.Property;

public class PunctuationCheckTest extends AbstractReviewTest {

	@Test
	public void testReview() {
		Property templ = createProperty("", "test {'}");
		Property trans = createProperty("", "{' test");
		assertNotNull(getFixture().review(null, templ, trans));
	}
	
	@Test
	public void testReview2() {
		Property templ = createProperty("", "test {'}}}");
		Property trans = createProperty("", "{'}}}' test");
		assertNotNull(getFixture().review(null, templ, trans));
	}
	
	@Test
	public void testReview3() {
		Property templ = createProperty("", "test '''");
		Property trans = createProperty("", "'' test");
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
