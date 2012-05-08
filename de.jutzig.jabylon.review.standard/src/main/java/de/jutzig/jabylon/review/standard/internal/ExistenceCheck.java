/**
 * 
 */
package de.jutzig.jabylon.review.standard.internal;

import java.text.MessageFormat;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.review.ReviewFactory;
import de.jutzig.jabylon.review.Severity;
import de.jutzig.jabylon.ui.review.ReviewParticipant;

/**
 * @author joe
 *
 */
public class ExistenceCheck implements ReviewParticipant {

	/**
	 * 
	 */
	public ExistenceCheck() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.review.ReviewParticipant#review(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.Property, de.jutzig.jabylon.properties.Property)
	 */
	@Override
	public Review review(PropertyFileDescriptor descriptor, Property master,
			Property slave) {
		boolean masterExists = exists(master);
		boolean slaveExists = exists(slave);
		if(masterExists!=slaveExists)
		{
			Review review = ReviewFactory.eINSTANCE.createReview();
			String message = "";
			if(masterExists)
			{
				message = "The key ''{0}'' is missing in the translation";
				message = MessageFormat.format(message, master.getKey());				
			}
			else
			{
				message = "The key ''{0}'' is missing in the template language";
				message = MessageFormat.format(message, slave.getKey());				
			}
			review.setMessage(message);
			review.setUser("Jabylon");
			review.setReviewType("ExistenceCheck");
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

}
