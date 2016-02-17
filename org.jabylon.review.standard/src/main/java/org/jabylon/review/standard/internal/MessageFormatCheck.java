/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 * 
 */
package org.jabylon.review.standard.internal;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.Severity;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class MessageFormatCheck implements ReviewParticipant {

	/** as in {0} or {0,choice,0#days|1#day|1<days} */
	private static final Pattern PATTERN = Pattern.compile("\\{(\\d+)(,.*?)?\\}");
	
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
		Set<String> masterPatterns = new HashSet<String>();
		while(masterMatcher.find())
		{
			masterPatterns.add("{"+masterMatcher.group(1)+"}");
		}
		Set<String> mustHavePatterns = new HashSet<String>(masterPatterns);
		
		Matcher slaveMatcher = PATTERN.matcher(slave.getValue());
		while(slaveMatcher.find())
		{
			String patternNumber = slaveMatcher.group(1);
			String pattern = "{"+patternNumber+"}";
			if(!masterPatterns.contains(pattern))
			{
				Review review = PropertiesFactory.eINSTANCE.createReview();
				review.setCreated(System.currentTimeMillis());
				String message = "Translation contains message format ''{0}'' which is not present in the template language";
				review.setMessage(MessageFormat.format(message, pattern));
				review.setUser("Jabylon");
				review.setReviewType("Message Format");
				review.setSeverity(Severity.ERROR);
				return review;
			}
			mustHavePatterns.remove(pattern);
		}
		if(!mustHavePatterns.isEmpty())
		{
			Review review = PropertiesFactory.eINSTANCE.createReview();
			String message = "The template language contains message format ''{0}'' which is not referenced in the translation";
			review.setMessage(MessageFormat.format(message, mustHavePatterns.iterator().next()));
			review.setUser("Jabylon");
			review.setSeverity(Severity.ERROR);
			review.setReviewType(getReviewType());
			return review;
		}
		
		return null;
	}

	@Override
	public String getReviewType() {
		return "Message Format";
	}

	@Override
	public String getID() {
		return "MessageFormatCheck";
	}

	@Override
	public String getDescription() {
		return "%messageformat.check.description";
	}

	@Override
	public String getName() {
		return "%messageformat.check.name";
	}

//TODO: unit test for this:
	/*
	 * {0} {1} doesn''t have a workspace snapshot attached,
	 *Mit {0} {1} ist kein Schnappschuß eines Arbeitsbereiches verknüpft,
	 *
	 *
	 */

}
