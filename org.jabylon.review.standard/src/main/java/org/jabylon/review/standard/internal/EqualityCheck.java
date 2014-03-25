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

import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.review.TerminologyProvider;
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
	
    @Reference(cardinality=ReferenceCardinality.OPTIONAL_UNARY,policy=ReferencePolicy.DYNAMIC)
    private TerminologyProvider terminologyProvider;

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
            	if(terminologyProvider!=null) {
            		Map<String, Property> terminology = terminologyProvider.getTerminology(descriptor.getVariant());
            		Property terminologyEntry = terminology.get(slave.getValue());
            		if(terminologyEntry!=null && slave.getValue().equals(terminologyEntry.getValue())){
            			// equality is ok if it is like that in terminology. That could be the case for e.g. a product name
            			// or short terms like "OK"
            			return null;
            		}            		
            	}
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

    public void bindTerminologyProvider(TerminologyProvider provider) {
        this.terminologyProvider = provider;
    }

    public void unbindTerminologyProvider(TerminologyProvider provider) {
        if(terminologyProvider==provider)
        	terminologyProvider = null;
    }
    
    @Override
    public String getID() {
        return "EqualityCheck";
    }

    @Override
    public String getDescription() {
        return "%equality.check.description";
    }

    @Override
    public String getName() {
        return "%equality.check.name";
    }

}
