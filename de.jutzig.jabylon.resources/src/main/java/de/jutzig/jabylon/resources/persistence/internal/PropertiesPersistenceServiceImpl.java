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
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
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
								firePropertiesAdded(propertyFileDescriptor, false);

							}
						}

					}

					else if (notification.getEventType() == Notification.REMOVE) {
						Object object = (Object) notification.getOldValue();

						if (object instanceof ProjectLocale) {
							ProjectLocale locale = (ProjectLocale) object;
							EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
							//FIXME: delete obsolete resource folders and derived locale descriptors
							for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
								firePropertiesDeleted(propertyFileDescriptor, false);

							}
						}

					}

					else if (notification.getEventType() == Notification.SET) {

						Object object = (Object) notification.getNewValue();

						if (object instanceof ProjectLocale) {
							ProjectLocale locale = (ProjectLocale) object;
							EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
							for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
								firePropertiesAdded(propertyFileDescriptor, false);

							}
						}

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
							firePropertiesAdded(descriptor, false);
						}
					}

					if (notification.getEventType() == Notification.REMOVE
							&& PropertiesPackage.Literals.PROJECT_LOCALE__DESCRIPTORS == notification.getFeature()) {

						Object object = notification.getOldValue();
						if (object instanceof PropertyFileDescriptor) {
							PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
							firePropertiesDeleted(descriptor, false);
						}

					}
				}

				if (notification.getNotifier() instanceof PropertyFileDescriptor) {
					PropertyFileDescriptor descriptor = (PropertyFileDescriptor) notification.getNotifier();
					if (notification.getEventType() == CDONotification.DETACH_OBJECT) {

						firePropertiesDeleted(descriptor, false);

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
		saveProperties(descriptor, file, false);

	}

	@Override
	public void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoTranslate) {
		try {
			PropertyFileDescriptor adaptedDescriptor = workspace.cdoView().getObject(descriptor);
			queue.put(new PropertyTuple(adaptedDescriptor, file, autoTranslate));
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
		CDOTransaction transaction = null;
		try {
			while (true) {
				try {

					PropertyTuple tuple = queue.take();
					if(transaction==null)
						transaction = workspace.cdoView().getSession().openTransaction();

					PropertyFileDescriptor descriptor = transaction.getObject(tuple.getDescriptor());
					PropertyFile file = tuple.getFile();
					URI path = descriptor.absolutPath();
					Map<String, Object> options = createOptions(descriptor);
					if (new File(path.toFileString()).exists()) {
						PropertyFile original = descriptor.loadProperties();
						PropertyDifferentiator differentiator = new PropertyDifferentiator(original);
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(options);
						descriptor.setKeys(resource.getSavedProperties());
						descriptor.updatePercentComplete();
						transaction.commit();
						List<Notification> diff = differentiator.diff(file);
						firePropertiesChanges(descriptor, diff,tuple.isAutoSync());
					} else {
						PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
						resource.getContents().add(file);
						resource.save(options);
						descriptor.setKeys(resource.getSavedProperties());
						descriptor.updatePercentComplete();
						transaction.commit();
						//FIXME: create resource folders and handle derived locales
						firePropertiesAdded(descriptor,tuple.isAutoSync());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//clean up the dirty transaction
					transaction.close();
					transaction = null;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// let thread end...
		}
		finally
		{
			if(transaction!=null)
				transaction.close();
		}

	}

	private Map<String, Object> createOptions(PropertyFileDescriptor descriptor) {
		Map<String, Object> options = new HashMap<String, Object>();
		if (descriptor.getProjectLocale() != null && descriptor.getProjectLocale().getParent() != null
				&& descriptor.getProjectLocale().getParent().getParent() != null) {
			ProjectVersion version = descriptor.getProjectLocale().getParent();
			Project project = version.getParent();
			PropertyType propertyType = project.getPropertyType();
			options.put(PropertiesResourceImpl.OPTION_FILEMODE, propertyType);
		}
		return options;
	}

	private void firePropertiesAdded(PropertyFileDescriptor descriptor, boolean autoSync) {
		for (PropertiesListener listener : listeners) {
			listener.propertyFileAdded(descriptor, autoSync);
		}

	}

	private void firePropertiesDeleted(PropertyFileDescriptor descriptor, boolean autoSync) {
		Iterator<PropertiesListener> it = listeners.iterator();
		while (it.hasNext()) {
			PropertiesListener listener = it.next();
			listener.propertyFileDeleted(descriptor, autoSync);
		}

	}

	private void firePropertiesChanges(PropertyFileDescriptor descriptor, List<Notification> diff, boolean autoSync) {
		Iterator<PropertiesListener> it = listeners.iterator();
		while (it.hasNext()) {
			PropertiesListener listener = it.next();

			listener.propertyFileModified(descriptor, diff, autoSync);
		}

	}

}

class PropertyTuple {
	private PropertyFileDescriptor descriptor;
	private PropertyFile file;
	private boolean autoSync;

	public PropertyTuple(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoSync) {
		super();
		this.descriptor = descriptor;
		this.file = file;
		this.autoSync = autoSync;
	}

	public PropertyFileDescriptor getDescriptor() {
		return descriptor;
	}

	public PropertyFile getFile() {
		return file;
	}

	public boolean isAutoSync() {
		return autoSync;
	}

}