package de.jutzig.jabylon.review.standard.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.properties.Property;

public class MessageFormatCheckTest extends AbstractReviewTest{

	@Test
	public void testReview() {
		
		Property master = createProperty("key", "value {0}");
		Property slave = createProperty("value", "{0} wert");
		assertNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewNoHit() {
		
		Property master = createProperty("key", "value");
		Property slave = createProperty("value", "wert");
		assertNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewWrongNumber() {
		
		Property master = createProperty("key", "value {0}");
		Property slave = createProperty("value", "{1} wert");
		assertNotNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewMissingFormat() {
		
		Property master = createProperty("key", "value {0}");
		Property slave = createProperty("value", " wert");
		assertNotNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewExtraFormat() {
		
		Property master = createProperty("key", "value {0}");
		Property slave = createProperty("value", "{0} wert {2}");
		assertNotNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewDoubleUsageMaster() {
		
		Property master = createProperty("key", "{0} value {0}");
		Property slave = createProperty("value", "{0} wert");
		assertNull(getFixture().review(null, master, slave));
	}
	
	@Test
	public void testReviewDoubleUsageSlave() {
		
		Property master = createProperty("key", "value {0}");
		Property slave = createProperty("value", "{0} wert {0}");
		assertNull(getFixture().review(null, master, slave));
	}
	

	@Override
	public ReviewParticipant createFixture() {
		return new MessageFormatCheck();
	}

}
