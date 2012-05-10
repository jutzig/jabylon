/**
 * 
 */
package de.jutzig.jabylon.ui.review.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;
import de.jutzig.jabylon.review.PropertyFileReview;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.review.ReviewParticipant;
import de.jutzig.jabylon.ui.review.ReviewUtil;
import de.jutzig.jabylon.ui.util.PreferencesUtil;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class PropertyReviewService implements PropertiesListener {

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
	public void propertyFileAdded(PropertyFileDescriptor descriptor) {
		Project project = descriptor.getProjectLocale().getProjectVersion().getProject();
		List<ReviewParticipant> activeReviews = getActiveReviews(project);
		if (activeReviews.isEmpty())
			return;
		PropertyFile slaveProperties = descriptor.loadProperties();
		PropertyFile masterProperties = descriptor.getMaster().loadProperties();

		PropertyFileReview fileReview = ReviewUtil.getOrCreateReview(descriptor);
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
					fileReview.getReviews().add(review);
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
						fileReview.getReviews().add(review);
						dirty = true;
					}
				}
			}
		}
		CDOTransaction transaction = (CDOTransaction) fileReview.cdoView();
		if (dirty) {
			try {
				transaction.commit();
			} catch (CommitException e) {
				Activator.error("Failed to commit review transaction for " + descriptor.fullPath().toString(), e);
			}
		}
		transaction.close();

	}

	private boolean removeKey(String key, PropertyFileReview fileReview) {
		Iterator<Review> it = fileReview.getReviews().iterator();
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
	public void propertyFileDeleted(PropertyFileDescriptor descriptor) {

		//TODO: this is unfortunately rather slow in case of a full rescan when potentially 
		//tens of thousands of review files are deleted (by tens of thousands of commits)...
		if (!ReviewUtil.hasReviewResource(descriptor))
			return;
		CDOSession session = descriptor.cdoView().getSession();
//		System.out.println("Property Review Service: deleting review "+ descriptor.cdoID());
		CDOTransaction transaction = session.openTransaction();
		Resource resource = ReviewUtil.getReviewResource(descriptor, transaction);

		try {
			resource.delete(null);
			transaction.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			transaction.close();
		}

	}

	@Override
	public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes) {
		Project project = descriptor.getProjectLocale().getProjectVersion().getProject();
		List<ReviewParticipant> activeReviews = getActiveReviews(project);
		if (activeReviews.isEmpty())
			return;
		PropertyFileReview fileReview = ReviewUtil.getOrCreateReview(descriptor);

		PropertyFile masterProperties = null;
		if (!descriptor.isMaster())
			masterProperties = descriptor.getMaster().loadProperties();
		boolean dirty = false;
		for (Notification change : changes) {
			Object notifier = change.getNotifier();
			if (notifier instanceof Property) {
				Property prop = (Property) notifier;
				for (ReviewParticipant reviewer : activeReviews) {

					Review review = null;
					if (descriptor.isMaster()) {
						review = reviewer.review(descriptor, prop, null);
					} else {
						review = reviewer.review(descriptor, masterProperties.getProperty(prop.getKey()), prop);
					}

					if (removeKey(prop.getKey(), fileReview))
						dirty = true;
					if (review != null) {
						review.setKey(prop.getKey());
						fileReview.getReviews().add(review);
						dirty = true;
					}
				}

			}
		}
		CDOTransaction transaction = (CDOTransaction) fileReview.cdoView();
		if (dirty) {
			try {
				transaction.commit();
			} catch (CommitException e) {
				Activator.error("Failed to commit review transaction for " + descriptor.fullPath().toString(), e);
			}
		}
		transaction.close();

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

}
