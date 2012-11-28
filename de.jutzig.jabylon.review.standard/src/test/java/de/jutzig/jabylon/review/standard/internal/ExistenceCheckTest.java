/**
 * 
 */
package de.jutzig.jabylon.review.standard.internal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.Review;

/**
 * @author joe
 * 
 */
public class ExistenceCheckTest extends AbstractReviewTest {

	public ReviewParticipant createFixture() {
		return new ExistenceCheck();
	}

	/**
	 * Test method for
	 * {@link de.jutzig.jabylon.review.standard.internal.ExistenceCheck#review(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.Property, de.jutzig.jabylon.properties.Property)}
	 * .
	 */
	@Test
	public void testReview() {
		Property slave = createProperty("key", "value");
		Review review = getFixture().review(null, null, slave);
		assertNotNull("must create a review if slave existis without template",
				review);
	}

	@Test
	public void testReviewWithBothPresent() {
		Property slave = createProperty("key", "value");
		Property master = createProperty("key", "value");
		Review review = getFixture().review(null, master, slave);
		assertNull(review);
	}

	@Test
	public void testReviewWithEmptySlave() {
		Property slave = createProperty("key", "");
		Property master = createProperty("key", "value");
		Review review = getFixture().review(null, master, slave);
		assertNull(
				"empty translation is not worth a review. The UI spots that alone",
				review);
	}

	@Test
	public void testReviewWithEmptyMaster() {
		Property slave = createProperty("key", "value");
		Property master = createProperty("key", "");
		Review review = getFixture().review(null, master, slave);
		assertNotNull(
				"its faulty if the master has no value, but there is a translation",
				review);
	}

	@Test
	public void testReviewWithBothEmpty() {
		Property slave = createProperty("key", "");
		Property master = createProperty("key", "");
		Review review = getFixture().review(null, master, slave);
		assertNull("its alright if both are empty", review);
	}

	@Test
	public void testReviewWithNullAndEmpty() {
		Property slave = createProperty("key", "");
		Review review = getFixture().review(null, null, slave);
		assertNotNull(
				"the value is empty, but the key in the translation is still wrong",
				review);
	}

}
