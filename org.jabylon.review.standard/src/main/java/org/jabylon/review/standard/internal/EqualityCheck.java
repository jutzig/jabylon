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
public class EqualityCheck implements ReviewParticipant {

    /* (non-Javadoc)
     * @see org.jabylon.common.review.ReviewParticipant#review(org.jabylon.properties.PropertyFileDescriptor, org.jabylon.properties.Property, org.jabylon.properties.Property)
     */
    @Override
    public Review review(PropertyFileDescriptor descriptor, Property master, Property slave) {
        if(master==null||slave==null)
            return null;
        String masterValue = master.getValue();
        String slaveValue = slave.getValue();
        if(masterValue!=null)
        {
            if(masterValue.equals(slaveValue))
            {
                Review review = PropertiesFactory.eINSTANCE.createReview();
                review.setCreated(System.currentTimeMillis());
                review.setMessage("Template and translated string are identical");
                review.setReviewType("Equality Check");
                review.setSeverity(Severity.WARNING);
                review.setUser("Jabylon");
                return review;
            }
        }
        return null;
    }

    @Override
    public String getID() {
        return "EqualityCheck";
    }

    @Override
    public String getDescription() {
        return "Checks if the template and translation are identical (copy/paste)";
    }

    @Override
    public String getName() {
        return "Equality Check";
    }

}
