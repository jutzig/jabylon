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

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.Severity;

/**
 * @author joe
 *
 */
@Component
@Service
public class ExistenceCheck implements ReviewParticipant {

    /**
     *
     */
    public ExistenceCheck() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.jabylon.common.review.ReviewParticipant#review(org.jabylon.properties.PropertyFileDescriptor, org.jabylon.properties.Property, org.jabylon.properties.Property)
     */
    @Override
    public Review review(PropertyFileDescriptor descriptor, Property master,
            Property slave) {
        boolean masterExists = exists(master);
        //don't check anything if master exists. Missing translations are not worth a review
        if(masterExists)
            return null;

        if(master!=null)
        {
            // master exists, but has an empty value.
            // it's alright if slave value is empty as well
            if(!exists(slave))
                return null;
        }

        if(slave != null)
        {
            Review review = PropertiesFactory.eINSTANCE.createReview();
            review.setCreated(System.currentTimeMillis());
            String message = "The key ''{0}'' is missing in the template language";
            message = MessageFormat.format(message, slave.getKey());
            review.setMessage(message);
            review.setUser("Jabylon");
            review.setReviewType("Missing Key");
            review.setSeverity(Severity.ERROR);
            return review;
        }
        return null;
    }

    private boolean exists(Property property) {
        if(property==null)
            return false;
        if(property.getValue()==null)
            return false;
        if(property.getValue().trim().length()==0)
            return false;
        return true;
    }

    @Override
    public String getID() {
        return "ExistenceCheck";
    }

    @Override
    public String getDescription() {
        return "%existance.check.description";
    }

    @Override
    public String getName() {
        return "%existance.check.name";
    }

}
