/**
 * 
 */
package de.jutzig.jabylon.resources.persistence.internal;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.util.PropertiesResourceImpl;
import de.jutzig.jabylon.resources.changes.PropertiesListener;
import de.jutzig.jabylon.resources.diff.PropertyDifferentiator;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class PropertiesPersistenceServiceImpl implements PropertyPersistenceService, Runnable {

	private List<WeakReference<PropertiesListener>> listeners;
	private BlockingQueue<PropertyTuple> queue;

	public PropertiesPersistenceServiceImpl() {
		listeners = new ArrayList<WeakReference<PropertiesListener>>();
		queue = new ArrayBlockingQueue<PropertyTuple>(50);
		Thread t = new Thread(this, "Properties Persistence Service");
		t.setDaemon(true);
		t.start();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jutzig.jabylon.resources.persistence.PropertyPersistenceService#
	 * saveProperties(de.jutzig.jabylon.properties.PropertyFileDescriptor,
	 * de.jutzig.jabylon.properties.PropertyFile)
	 */
	@Override
	public void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file) {
		try {
			queue.put(new PropertyTuple(descriptor, file));
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while trying to save " + descriptor.fullPath(), e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jutzig.jabylon.resources.persistence.PropertyPersistenceService#
	 * addPropertiesListener
	 * (de.jutzig.jabylon.resources.changes.PropertiesListener)
	 */
	@Override
	public void addPropertiesListener(PropertiesListener listener) {
		listeners.add(new WeakReference<PropertiesListener>(listener));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jutzig.jabylon.resources.persistence.PropertyPersistenceService#
	 * removePropertiesListener
	 * (de.jutzig.jabylon.resources.changes.PropertiesListener)
	 */
	@Override
	public void removePropertiesListener(PropertiesListener listener) {
		Iterator<WeakReference<PropertiesListener>> it = listeners.iterator();
		while (it.hasNext()) {
			WeakReference<PropertiesListener> reference = it.next();
			PropertiesListener propertiesListener = reference.get();
			if (propertiesListener == null)
				it.remove();
			else if (propertiesListener == listener) {
				it.remove();
				break;
			}
		}

	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					PropertyTuple tuple = queue.take();
					PropertyFileDescriptor descriptor = tuple.getDescriptor();
					PropertyFile file = tuple.getFile();
					URI path = descriptor.absolutPath();
					if (new File(path.toFileString()).exists()) {
						PropertyFile original = descriptor.loadProperties();
						PropertyDifferentiator differentiator = new PropertyDifferentiator(original);
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(null);
						List<Notification> diff = differentiator.diff(file);
						firePropertiesChanges(descriptor, diff);
					} else {
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(null);
						firePropertiesAdded(descriptor);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// let thread end...
		}

	}

	private void firePropertiesAdded(PropertyFileDescriptor descriptor) {
		Iterator<WeakReference<PropertiesListener>> it = listeners.iterator();
		while (it.hasNext()) {
			WeakReference<PropertiesListener> ref = it.next();
			PropertiesListener listener = ref.get();
			if (listener == null)
				it.remove();
			else
				listener.propertyFileAdded(descriptor);
		}

	}

	private void firePropertiesChanges(PropertyFileDescriptor descriptor, List<Notification> diff) {
		Iterator<WeakReference<PropertiesListener>> it = listeners.iterator();
		while (it.hasNext()) {
			WeakReference<PropertiesListener> ref = it.next();
			PropertiesListener listener = ref.get();
			if (listener == null)
				it.remove();
			else
				listener.propertyFileModified(descriptor, diff);
		}

	}

}

class PropertyTuple {
	private PropertyFileDescriptor descriptor;
	private PropertyFile file;

	public PropertyTuple(PropertyFileDescriptor descriptor, PropertyFile file) {
		super();
		this.descriptor = descriptor;
		this.file = file;
	}

	public PropertyFileDescriptor getDescriptor() {
		return descriptor;
	}

	public PropertyFile getFile() {
		return file;
	}

}