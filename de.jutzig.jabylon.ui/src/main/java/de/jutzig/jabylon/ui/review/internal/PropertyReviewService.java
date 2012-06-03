/**
 *
 */
package de.jutzig.jabylon.ui.review.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.notify.Notification;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.resources.changes.AbstractCoalescingListener;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.review.ReviewParticipant;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class PropertyReviewService extends AbstractCoalescingListener {

	private Map<String, ReviewParticipant> participants;

	public PropertyReviewService() {
		participants = fillParticipants();
	}

	private Map<String, ReviewParticipant> fillParticipants() {
		Map<String, ReviewParticipant> map = new HashMap<String, ReviewParticipant>();
		IConfigurationElement[] elements = Activator.getDefault().getReviewParticipants();
		for (IConfigurationElement element : elements) {
			try {
				String id = element.getAttribute("id");
				ReviewParticipant participant = (ReviewParticipant) element.createExecutableExtension("class");
				map.put(id, participant);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public void propertyFileAdded(PropertyFileDescriptor descriptor, boolean autoSync) {
		if (descriptor.isMaster()) // TODO: or does the master language need to
									// be checked too
			return;
		Project project = descriptor.getProjectLocale().getProjectVersion().getProject();
		List<ReviewParticipant> activeReviews = getActiveReviews(project);
		if (activeReviews.isEmpty())
			return;
		PropertyFile slaveProperties = descriptor.loadProperties();
		PropertyFile masterProperties = descriptor.getMaster().loadProperties();

		CDOTransaction transaction = retrieveTransaction(descriptor);
		descriptor = transaction.getObject(descriptor);
		boolean dirty = false;
		Set<String> allProperties = new HashSet<String>();
		for (Property prop : slaveProperties.getProperties()) {
			String key = prop.getKey();
			if (key == null) // TODO: how can this happen?
				continue;
			allProperties.add(key);
			Property masterProperty = masterProperties.getProperty(key);
			for (ReviewParticipant reviewer : activeReviews) {

				Review review = null;
				if (descriptor.isMaster()) {
					review = reviewer.review(descriptor, prop, null);
				} else {
					review = reviewer.review(descriptor, masterProperty, prop);
				}
				if (review != null) {
					review.setKey(prop.getKey());
					descriptor.getReviews().add(review);
					dirty = true;
				}
			}
		}
		if (!descriptor.isMaster()) {
			// process all properties that are missing in the slave
			for (Property property : masterProperties.getProperties()) {
				if (allProperties.contains(property.getKey()))
					continue; // already processed
				for (ReviewParticipant reviewer : activeReviews) {
					Review review = reviewer.review(descriptor, property, null);
					if (review != null) {
						review.setKey(property.getKey());
						descriptor.getReviews().add(review);
						dirty = true;
					}
				}
			}
		}

		if (dirty) {
			process();
		}

	}

	private boolean removeKey(String key, List<Review> reviews) {
		Iterator<Review> it = reviews.iterator();
		boolean dirty = false;
		while (it.hasNext()) {
			Review review = (Review) it.next();
			if (key.equals(review.getKey())) {
				it.remove();
				dirty = true;
			}
		}
		return dirty;

	}

	@Override
	public void propertyFileDeleted(PropertyFileDescriptor descriptor, boolean autoSync) {

		// nothing to do

	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes, boolean autoSync) {
		Project project = descriptor.getProjectLocale().getProjectVersion().getProject();
		List<ReviewParticipant> activeReviews = getActiveReviews(project);
		if (activeReviews.isEmpty())
			return;
		CDOTransaction transaction = retrieveTransaction(descriptor);
		descriptor = transaction.getObject(descriptor);
		PropertyFile masterProperties = null;
		if (!descriptor.isMaster())
			masterProperties = descriptor.getMaster().loadProperties();
		boolean dirty = false;
		for (Notification change : changes) {
			Object notifier = change.getNotifier();
			if (notifier instanceof Property) {
				Property prop = (Property) notifier;
				dirty = analyzeProperty(descriptor, activeReviews, masterProperties, prop);
			}
			else if (notifier instanceof PropertyFile) {
				Object newValue = change.getNewValue();
				
				if (change.getEventType()==Notification.ADD && newValue instanceof Property) {
					Property property = (Property) newValue;
					dirty = analyzeProperty(descriptor, activeReviews, masterProperties, property);
				}
				
				else if (change.getEventType()==Notification.ADD_MANY && newValue instanceof Collection) {
					Collection<Property> properties = (Collection<Property>) newValue;
					for (Property property : properties) {
						dirty = analyzeProperty(descriptor, activeReviews, masterProperties, property);						
					}
				}
				
			}
		}

		if (dirty) {

			process();

		}

	}

	protected boolean analyzeProperty(PropertyFileDescriptor descriptor, List<ReviewParticipant> activeReviews, PropertyFile masterProperties, Property prop) {
		boolean dirty = false;
		boolean firstReview = true;
		for (ReviewParticipant reviewer : activeReviews) {

			Review review = null;
			if (descriptor.isMaster()) {
				review = reviewer.review(descriptor, prop, null);
			} else {
				review = reviewer.review(descriptor, masterProperties.getProperty(prop.getKey()), prop);
			}

			if (firstReview && removeKey(prop.getKey(), descriptor.getReviews()))
				dirty = true;
			if (review != null) {
				review.setKey(prop.getKey());
				descriptor.getReviews().add(review);
				dirty = true;
			}
			firstReview = false;
		}
		return dirty;
	}

	private List<ReviewParticipant> getActiveReviews(Project project) {
		List<ReviewParticipant> activeParticipants = new ArrayList<ReviewParticipant>();
		Preferences node = PreferencesUtil.scopeFor(project).node(PreferencesUtil.NODE_CHECKS);
		for (Entry<String, ReviewParticipant> entry : participants.entrySet()) {
			if (node.getBoolean(entry.getKey(), false))
				activeParticipants.add(entry.getValue());
		}
		return activeParticipants;
	}
	
	public Collection<Review> review(PropertyFileDescriptor descriptor,Property master, Property slave)
	{
		List<Review> reviews = new ArrayList<Review>();
		List<ReviewParticipant> participants = getActiveReviews(descriptor.getProjectLocale().getProjectVersion().getProject());
		for (ReviewParticipant reviewParticipant : participants) {
			Review review = reviewParticipant.review(descriptor, master, slave);
			if(review!=null)
				reviews.add(review);
		}
		return reviews;
	}

}
