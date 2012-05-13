package de.jutzig.jabylon.resources.persistence;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;

public interface PropertyPersistenceService {

	void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file);
	
	void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoTranslate);
	
//	void savePropertiesAndWait(PropertyFileDescriptor descriptor, PropertyFile file);
	
	void addPropertiesListener(PropertiesListener listener);
	
	void removePropertiesListener(PropertiesListener listener);
	
}
