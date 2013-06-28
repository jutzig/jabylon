package de.jutzig.jabylon.common.review;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;

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
