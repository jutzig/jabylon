/**
 * 
 */
package de.jutzig.jabylon.review.standard.internal;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.Severity;
import de.jutzig.jabylon.ui.review.ReviewParticipant;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class MessageFormatCheck implements ReviewParticipant {

	
	private static final Pattern PATTERN = Pattern.compile("(\\{\\d+.*?\\})");
	
	/**
	 * 
	 */
	public MessageFormatCheck() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Review review(PropertyFileDescriptor descriptor, Property master, Property slave) {
		if(master==null || slave==null || master.getValue()==null || slave.getValue()==null)
			return null;
		Matcher masterMatcher = PATTERN.matcher(master.getValue());
		HashSet<String> masterPatterns = new HashSet<String>();
		while(masterMatcher.find())
		{
			masterPatterns.add(masterMatcher.group(1));
		}
		
		Matcher slaveMatcher = PATTERN.matcher(slave.getValue());
		while(slaveMatcher.find())
		{
			String pattern = slaveMatcher.group(1);
			if(!masterPatterns.remove(pattern))
			{
				Review review = PropertiesFactory.eINSTANCE.createReview();
				String message = "Translation contains message format ''{0}'' which is not present in the template language";
				review.setMessage(MessageFormat.format(message, pattern));
				review.setUser("Message Format Check");
				review.setReviewType("MessageFormatCheck");
				review.setSeverity(Severity.ERROR);
				return review;
			}
		}
		if(!masterPatterns.isEmpty())
		{
			Review review = PropertiesFactory.eINSTANCE.createReview();
			String message = "The template language contains message format ''{0}'' which is not referenced in the translation";
			review.setMessage(MessageFormat.format(message, masterPatterns.iterator().next()));
			review.setUser("Jabylon");
			review.setSeverity(Severity.ERROR);
			review.setReviewType("MessageFormatCheck");
			return review;
		}
		
		return null;
	}

//TODO: unit test for this:
	/*
	 * {0} {1} doesn''t have a workspace snapshot attached,
	 *Mit {0} {1} ist kein Schnappschuß eines Arbeitsbereiches verknüpft,	
	 * 
	 * 
	 */

}
