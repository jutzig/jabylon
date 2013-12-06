package org.jabylon.common.review.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.notify.Notification;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.resources.changes.PropertiesListener;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class PropertyReviewService implements PropertiesListener {

    @Reference(referenceInterface=ReviewParticipant.class,bind="addParticipant",unbind="removeParticipant",cardinality=ReferenceCardinality.MANDATORY_MULTIPLE)
    private List<ReviewParticipant> participants = Collections.synchronizedList(new ArrayList<ReviewParticipant>());

    private static final Logger logger = LoggerFactory.getLogger(PropertyReviewService.class);

    public PropertyReviewService() {

    }

    protected void addParticipant(ReviewParticipant pariticpant)
    {
        logger.info("Adding new review participant {} (ID: {})",pariticpant.getName(),pariticpant.getID());
        participants.add(pariticpant);
    }

    protected void removeParticipant(ReviewParticipant pariticpant)
    {
        logger.info("Deactivating review participant {} (ID: {})",pariticpant.getName(),pariticpant.getID());
        participants.remove(pariticpant);
    }

    @Override
    public void propertyFileAdded(PropertyFileDescriptor descriptor, boolean autoSync) {
        if (descriptor.isMaster()) // TODO: or does the master language need to
                                    // be checked too
            return;
        Project project = descriptor.getProjectLocale().getParent().getParent();
        List<ReviewParticipant> activeReviews = getActiveReviews(project);
        if (activeReviews.isEmpty())
            return;
        PropertyFile slaveProperties = descriptor.loadProperties();
        PropertyFile masterProperties = descriptor.getMaster().loadProperties();

        CDOTransaction transaction = descriptor.cdoView().getSession().openTransaction();
        descriptor = transaction.getObject(descriptor);
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
                    }
                }
            }
        }

        try {
            if (transaction.isDirty()) {
                transaction.commit();
            }
        } catch (CommitException e) {
            logger.error("Commit failed",e);
        } finally {
            transaction.close();
        }

    }

    private void removeKey(String key, List<Review> reviews) {
        Iterator<Review> it = reviews.iterator();
        while (it.hasNext()) {
            Review review = (Review) it.next();
            if (key.equals(review.getKey())) {
                it.remove();
            }
        }

    }

    @Override
    public void propertyFileDeleted(PropertyFileDescriptor descriptor, boolean autoSync) {

        // nothing to do

    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes, boolean autoSync) {
        Project project = descriptor.getProjectLocale().getParent().getParent();
        List<ReviewParticipant> activeReviews = getActiveReviews(project);
        if (activeReviews.isEmpty())
            return;
        CDOTransaction transaction = descriptor.cdoView().getSession().openTransaction();
        descriptor = transaction.getObject(descriptor);
        PropertyFile masterProperties = null;
        if (!descriptor.isMaster())
            masterProperties = descriptor.getMaster().loadProperties();
        for (Notification change : changes) {
            Object notifier = change.getNotifier();
            if (notifier instanceof Property) {
                Property prop = (Property) notifier;
                analyzeProperty(descriptor, activeReviews, masterProperties, prop);
            } else if (notifier instanceof PropertyFile) {
                Object newValue = change.getNewValue();

                if (change.getEventType() == Notification.ADD && newValue instanceof Property) {
                    Property property = (Property) newValue;
                    analyzeProperty(descriptor, activeReviews, masterProperties, property);
                }

                else if (change.getEventType() == Notification.ADD_MANY && newValue instanceof Collection) {
                    Collection<Property> properties = (Collection<Property>) newValue;
                    for (Property property : properties) {
                        analyzeProperty(descriptor, activeReviews, masterProperties, property);
                    }
                }

            }
        }

        try {
            if (transaction.isDirty()) {
                transaction.commit();
            }
        } catch (CommitException e) {
            logger.error("Commit failed",e);
        } finally {
            transaction.close();
        }

    }

    protected void analyzeProperty(PropertyFileDescriptor descriptor, List<ReviewParticipant> activeReviews, PropertyFile masterProperties,
            Property prop) {
        removeKey(prop.getKey(), descriptor.getReviews());
        Property masterProp = masterProperties.getProperty(prop.getKey());
        for (ReviewParticipant reviewer : activeReviews) {

            Review review = null;
            if (descriptor.isMaster()) {
                review = reviewer.review(descriptor, prop, null);
            } else {
                review = reviewer.review(descriptor, masterProp, prop);
            }

            if (review != null) {
                review.setKey(prop.getKey());
                descriptor.getReviews().add(review);
            }
        }
    }

    private List<ReviewParticipant> getActiveReviews(Project project) {
        List<ReviewParticipant> activeParticipants = new ArrayList<ReviewParticipant>();
        Preferences node = PreferencesUtil.scopeFor(project).node(PreferencesUtil.NODE_CHECKS);
        for (ReviewParticipant participant : participants) {
            if (node.getBoolean(participant.getID(), false))
                activeParticipants.add(participant);
        }
        return activeParticipants;
    }

    public Collection<Review> review(PropertyFileDescriptor descriptor, Property master, Property slave) {
        List<Review> reviews = new ArrayList<Review>();
        List<ReviewParticipant> participants = getActiveReviews(descriptor.getProjectLocale().getParent().getParent());
        for (ReviewParticipant reviewParticipant : participants) {
            Review review = reviewParticipant.review(descriptor, master, slave);
            if (review != null)
                reviews.add(review);
        }
        return reviews;
    }

}
