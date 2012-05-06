/**
 * 
 */
package de.jutzig.jabylon.ui.review.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;
import de.jutzig.jabylon.review.PropertyFileReview;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.review.ReviewFactory;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.review.ReviewParticipant;
import de.jutzig.jabylon.ui.util.PreferencesUtil;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PropertyReviewService implements PropertiesListener{

	private Map<String,ReviewParticipant> participants;
	
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
		if(activeReviews.isEmpty())
			return;
		PropertyFile slaveProperties =  descriptor.loadProperties();
		PropertyFile masterProperties = descriptor.getMaster().loadProperties();
		
		PropertyFileReview fileReview = getOrCreateReview(descriptor);
		boolean dirty = false;
		for (Property prop : slaveProperties.getProperties()) {
			for (ReviewParticipant reviewer : activeReviews) {

				Review review = null;
				if(descriptor.isMaster())
				{
					review = reviewer.review(descriptor, prop, null);
				}
				else
				{
					review = reviewer.review(descriptor, masterProperties.getProperty(prop.getKey()), prop);					
				}
				if(review!=null)
				{
					review.setKey(prop.getKey());
					fileReview.getReviews().add(review);
					dirty = true;
				}
			}
		}
		CDOTransaction transaction = (CDOTransaction) fileReview.cdoView();
		if(dirty)
		{
			try {
				transaction.commit();
			} catch (CommitException e) {
				Activator.error("Failed to commit review transaction for "+descriptor.fullPath().toString(), e);
			}
		}
		transaction.close();
		
	}


	private boolean removeKey(String key, PropertyFileReview fileReview) {
		Iterator<Review> it = fileReview.getReviews().iterator();
		boolean dirty = false;
		while (it.hasNext()) {
			Review review = (Review) it.next();
			if(key.equals(review.getKey()))
			{
				it.remove();
				dirty=true;
			}
		}
		return dirty;
		
	}

	private PropertyFileReview getOrCreateReview(PropertyFileDescriptor descriptor) {
		CDOSession session = descriptor.cdoView().getSession();
		CDOTransaction transaction = session.openTransaction();
		CDOResource resource = transaction.getOrCreateResource("review/"+descriptor.fullPath().toString());
		EList<EObject> contents = resource.getContents();
		PropertyFileReview review = null;
		if(contents.isEmpty())
		{
			review = ReviewFactory.eINSTANCE.createPropertyFileReview();
			contents.add(review);
		}
		else
		{
			review = (PropertyFileReview) contents.get(0);
		}
		return review;
		
	}

	@Override
	public void propertyFileDeleted(PropertyFileDescriptor descriptor) {
		
		CDOSession session = descriptor.cdoView().getSession();
		CDOTransaction transaction = session.openTransaction();
		if(transaction.hasResource("review/"+descriptor.fullPath().toString()))
		{
			CDOResource resource = transaction.getOrCreateResource("review/"+descriptor.fullPath().toString());
			try {
				resource.delete(null);
				transaction.commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				transaction.close();
			}
			
		}
	}

	@Override
	public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes) {
		Project project = descriptor.getProjectLocale().getProjectVersion().getProject();
		List<ReviewParticipant> activeReviews = getActiveReviews(project);
		if(activeReviews.isEmpty())
			return;
		PropertyFileReview fileReview = getOrCreateReview(descriptor);
		
		PropertyFile masterProperties = null;
		if(!descriptor.isMaster())
			masterProperties = descriptor.getMaster().loadProperties();
		boolean dirty = false;
		for (Notification change : changes) {
			Object notifier = change.getNotifier();
			if (notifier instanceof Property) {
				Property prop = (Property) notifier;
				for (ReviewParticipant reviewer : activeReviews) {
					
					Review review = null;
					if(descriptor.isMaster())
					{
						review = reviewer.review(descriptor, prop, null);
					}
					else
					{
						review = reviewer.review(descriptor, masterProperties.getProperty(prop.getKey()), prop);					
					}
					
					if(removeKey(prop.getKey(),fileReview))
						dirty=true;
					if(review!=null)
					{
						review.setKey(prop.getKey());
						fileReview.getReviews().add(review);
						dirty = true;
					}
				}
				
			}
		}
		CDOTransaction transaction = (CDOTransaction) fileReview.cdoView();
		if(dirty)
		{
			try {
				transaction.commit();
			} catch (CommitException e) {
				Activator.error("Failed to commit review transaction for "+descriptor.fullPath().toString(), e);
			}
		}
		transaction.close();
		
	}

	private List<ReviewParticipant> getActiveReviews(Project project) {
		List<ReviewParticipant> activeParticipants = new ArrayList<ReviewParticipant>();
		Preferences node = PreferencesUtil.scopeFor(project).node(PreferencesUtil.NODE_CHECKS);
		for (Entry<String, ReviewParticipant> entry : participants.entrySet()) {
			if(node.getBoolean(entry.getKey(), false))
				activeParticipants.add(entry.getValue());
		}
		return activeParticipants;
	}
	
}
