package de.jutzig.jabylon.resources.changes;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;

public interface PropertiesListener {

	void propertyFileAdded(PropertyFileDescriptor descriptor);
	
	void propertyFileDeleted(PropertyFileDescriptor descriptor);
	
	void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes);
	
}
