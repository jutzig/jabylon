package org.jabylon.common.review;

import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface ReviewParticipant {

    public Review review(PropertyFileDescriptor descriptor, Property master, Property slave);

    String PROPERTY_ID = "id";

    String getID();

    String getDescription();

    String getName();

}
