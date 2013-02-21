package de.jutzig.jabylon.resources.persistence;

import java.util.concurrent.ExecutionException;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.ecore.util.EcoreUtil;

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
	
	/**
	 * Loads the file with given descriptor id
	 * <p>
	 * <strong>Important:</strong> the returned property file may be a shared copy
	 * copy that is used concurrently in other threads.
	 * when manipulating the object, or iterating over its contents, the caller needs 
	 * to synchronize the access.
	 * <p>
	 * The caller may choose to create a local copy of the object for exclusive access
	 * @param descriptor
	 * @see EcoreUtil#copy(org.eclipse.emf.ecore.EObject)
	 * @return a PropertyFile
	 * @throws ExecutionException if the file cannot be loaded
	 */
	PropertyFile loadProperties(CDOID descriptor) throws ExecutionException;
	
}
