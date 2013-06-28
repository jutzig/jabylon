/**
 *
 */
package de.jutzig.jabylon.review.standard.internal;

import java.text.MessageFormat;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.Severity;

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
     * @see de.jutzig.jabylon.common.review.ReviewParticipant#review(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.Property, de.jutzig.jabylon.properties.Property)
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
        return "Checks if there is any keys in the translated files that are not available in the template";
    }

    @Override
    public String getName() {
        return "Existence Check";
    }

}
