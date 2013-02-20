package de.jutzig.jabylon.resources.persistence;

import java.util.concurrent.ExecutionException;

import org.eclipse.emf.cdo.common.id.CDOID;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;

public interface PropertyPersistenceService {

	void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file);
	
	void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoTranslate);
	
//	void savePropertiesAndWait(PropertyFileDescriptor descriptor, PropertyFile file);
	
	void addPropertiesListener(PropertiesListener listener);
	
	void removePropertiesListener(PropertiesListener listener);
	
	PropertyFile loadProperties(PropertyFileDescriptor descriptor) throws ExecutionException;
	
	PropertyFile loadProperties(CDOID descriptor) throws ExecutionException;
	
}
