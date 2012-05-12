/**
 *
 */
package de.jutzig.jabylon.resources.persistence.internal;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.cdo.CDONotification;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.CDOCommonSession.Options.PassiveUpdateMode;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertiesResourceImpl;
import de.jutzig.jabylon.resources.changes.PropertiesListener;
import de.jutzig.jabylon.resources.diff.PropertyDifferentiator;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class PropertiesPersistenceServiceImpl implements PropertyPersistenceService, Runnable {

	private List<PropertiesListener> listeners;

	private BlockingQueue<PropertyTuple> queue;

	private Workspace workspace;

	public PropertiesPersistenceServiceImpl() {
		listeners = new CopyOnWriteArrayList<PropertiesListener>();
		queue = new ArrayBlockingQueue<PropertyTuple>(50);
		Thread t = new Thread(this, "Properties Persistence Service");
		t.setDaemon(true);
		t.start();

	}

	public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
		CDOSession session = repositoryConnector.createSession();
		session.options().setPassiveUpdateMode(PassiveUpdateMode.ADDITIONS);

		CDOView view = session.openView();
		view.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
		workspace = (Workspace) resource.getContents().get(0);
		workspace.eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {

				super.notifyChanged(notification);
				if (notification.getNotifier() instanceof ProjectVersion) {
					ProjectVersion version = (ProjectVersion) notification.getNotifier();
					if (notification.getEventType() == Notification.ADD) {
						Object object = (Object) notification.getNewValue();

						if (object instanceof ProjectLocale) {
							ProjectLocale locale = (ProjectLocale) object;
							EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
							for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
								firePropertiesAdded(propertyFileDescriptor);

							}
						}

					}

					else if (notification.getEventType() == Notification.REMOVE) {
						Object object = (Object) notification.getOldValue();

						if (object instanceof ProjectLocale) {
							ProjectLocale locale = (ProjectLocale) object;
							EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
							for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
								firePropertiesDeleted(propertyFileDescriptor);

							}
						}

					}

					else if (notification.getEventType() == Notification.SET) {

						// FIXME: this is relevant for the template language.
						// Disable for now, because we cannot display the master
						// language at the moment, so it shouldn't
						// be searchable either

						// Object object = (Object) notification.getNewValue();
						//
						// if (object instanceof ProjectLocale) {
						// ProjectLocale locale = (ProjectLocale) object;
						// EList<PropertyFileDescriptor> descriptors =
						// locale.getDescriptors();
						// for (PropertyFileDescriptor propertyFileDescriptor :
						// descriptors) {
						// firePropertiesAdded(propertyFileDescriptor);
						//
						// }
						// }

					}

				}
				if (notification.getNotifier() instanceof ProjectLocale) {
					// TODO: delete isn't properly working yet
					ProjectLocale locale = (ProjectLocale) notification.getNotifier();
					if (notification.getEventType() == Notification.ADD
							&& PropertiesPackage.Literals.PROJECT_LOCALE__DESCRIPTORS == notification.getFeature()) {

						Object object = notification.getNewValue();
						if (object instanceof PropertyFileDescriptor) {
							PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
							firePropertiesAdded(descriptor);
						}
					}

					if (notification.getEventType() == Notification.REMOVE
							&& PropertiesPackage.Literals.PROJECT_LOCALE__DESCRIPTORS == notification.getFeature()) {

						Object object = notification.getOldValue();
						if (object instanceof PropertyFileDescriptor) {
							PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
							firePropertiesDeleted(descriptor);
						}

					}
				}

				if (notification.getNotifier() instanceof PropertyFileDescriptor) {
					PropertyFileDescriptor descriptor = (PropertyFileDescriptor) notification.getNotifier();
					if (notification.getEventType() == CDONotification.DETACH_OBJECT) {

						firePropertiesDeleted(descriptor);

					}

				}

			}

			@Override
			protected void removeAdapter(Notifier notifier) {
				if (notifier instanceof CDOObject) {
					CDOObject o = (CDOObject) notifier;
					if (!o.cdoInvalid())
						super.removeAdapter(notifier);

				}
			}

			protected void handleContainment(Notification notification) {
				switch (notification.getEventType()) {
				// prevent ClassCastExceptions if old value is not a notified,
				// but a CDOID
				case Notification.REMOVE: {
					Object oldValue = notification.getOldValue();
					if (oldValue instanceof Notifier) {
						Notifier notifier = (Notifier) oldValue;
						removeAdapter(notifier);
					} else if (oldValue instanceof CDOID) {
						CDOID id = (CDOID) oldValue;
						removeAdapter(workspace.cdoView().getObject(id));

					}
					break;
				}
				case Notification.REMOVE_MANY: {
					Collection<?> oldValues = (Collection<?>) notification.getOldValue();
					for (Object oldContentValue : oldValues) {
						if (oldContentValue instanceof Notifier) {
							Notifier notifier = (Notifier) oldContentValue;
							removeAdapter(notifier);
						} else if (oldContentValue instanceof CDOID) {
							CDOID id = (CDOID) oldContentValue;
							try {
								removeAdapter(workspace.cdoView().getObject(id));
							} catch (ObjectNotFoundException e) {
								// object doesn't exist anymore, so we're good
								// TODO: log
							}

						}
					}
					break;
				}

				default:
					super.handleContainment(notification);
				}
			}
		});
	}

	public void unsetRepositoryConnector(RepositoryConnector repositoryConnector) {
		CDOView view = workspace.cdoView();
		org.eclipse.emf.cdo.session.CDOSession session = view.getSession();
		LifecycleUtil.deactivate(view);
		LifecycleUtil.deactivate(session);

		session = null;
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
		listeners.add(listener);

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
		listeners.remove(listener);
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
					Map<String, Object> options = createOptions(descriptor);
					if (new File(path.toFileString()).exists()) {
						PropertyFile original = descriptor.loadProperties();
						PropertyDifferentiator differentiator = new PropertyDifferentiator(original);
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(options);
						List<Notification> diff = differentiator.diff(file);
						firePropertiesChanges(descriptor, diff);
					} else {
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(options);
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

	private Map<String, Object> createOptions(PropertyFileDescriptor descriptor) {
		Map<String, Object> options = new HashMap<String, Object>();
		if (descriptor.getProjectLocale() != null && descriptor.getProjectLocale().getProjectVersion() != null
				&& descriptor.getProjectLocale().getProjectVersion().getProject() != null) {
			ProjectVersion version = descriptor.getProjectLocale().getProjectVersion();
			Project project = version.getProject();
			PropertyType propertyType = project.getPropertyType();
			options.put(PropertiesResourceImpl.OPTION_FILEMODE, propertyType);
		}
		return options;
	}

	private void firePropertiesAdded(PropertyFileDescriptor descriptor) {
		Iterator<PropertiesListener> it = listeners.iterator();

		PropertiesListener listener = it.next();

		listener.propertyFileAdded(descriptor);

	}

	private void firePropertiesDeleted(PropertyFileDescriptor descriptor) {
		Iterator<PropertiesListener> it = listeners.iterator();
		while (it.hasNext()) {
			PropertiesListener listener = it.next();
			listener.propertyFileDeleted(descriptor);
		}

	}

	private void firePropertiesChanges(PropertyFileDescriptor descriptor, List<Notification> diff) {
		Iterator<PropertiesListener> it = listeners.iterator();
		while (it.hasNext()) {
			PropertiesListener listener = it.next();

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