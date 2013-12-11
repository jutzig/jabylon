/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.resources.persistence.internal;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyType;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.util.PropertiesResourceImpl;
import org.jabylon.resources.changes.PropertiesListener;
import org.jabylon.resources.diff.PropertyDifferentiator;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service(PropertyPersistenceService.class)
public class PropertiesPersistenceServiceImpl implements PropertyPersistenceService, Runnable {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesPersistenceServiceImpl.class);
	
    private final class ListeningAdapter extends EContentAdapter {
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
		                // FIXME: delete obsolete resource folders and
		                // derived locale descriptors
		                // this doesn't work because the object is already invalid
//                            for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
//                                firePropertiesDeleted(propertyFileDescriptor, false);
//
//                            }
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
		                    logger.warn("REMOVE_MANY object ID not found: "+notification,e);
		                }
		            }
		        }
		        break;
		    }

		    default:
		        super.handleContainment(notification);
		    }
		}
	}

	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, referenceInterface = PropertiesListener.class, bind="addPropertiesListener",unbind="removePropertiesListener")
    private List<PropertiesListener> listeners;

    private BlockingQueue<PropertyTuple> queue;

    private static Logger logger = LoggerFactory.getLogger(PropertiesPersistenceServiceImpl.class);

    @Reference
    private RepositoryConnector repositoryConnector;

    private Workspace workspace;

    private Thread runner;

    private LoadingCache<CDOID, PropertyFile> cache;

    private boolean active;



    public PropertiesPersistenceServiceImpl() {
        listeners = new CopyOnWriteArrayList<PropertiesListener>();

    }

    @Activate
    public void activate()
    {

        queue = new ArrayBlockingQueue<PropertyTuple>(50);
        runner = new Thread(this, "Properties Persistence Service");
        runner.setDaemon(true);
        runner.start();
        active = true;
    }

    @Deactivate
    public void deactivate()
    {
        shutdownQueue();
        listeners.clear();
        queue = null;
        runner.interrupt();
        runner = null;
    }


    private void shutdownQueue() {
        active = false;
        int size = queue.size();
        logger.info("Shutting down. Queuesize is {}",size);
        while(queue.size()>0) {
            logger.info("Shutting down. Remaining Queuesize is {}",queue.size());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.info("Interrupted while draining queue. Exiting");
            }
        }

    }

    public void bindRepositoryConnector(RepositoryConnector repositoryConnector) {
        this.repositoryConnector = repositoryConnector;
        hookListener(repositoryConnector);
    }

	private void hookListener(final RepositoryConnector repositoryConnector) {
		CDOSession session = repositoryConnector.createSession();
		session.options().setPassiveUpdateMode(PassiveUpdateMode.ADDITIONS);
		
		CDOView view = session.openView();
		view.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
		CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
		workspace = (Workspace) resource.getContents().get(0);
		cache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).concurrencyLevel(5).maximumWeight(20000).weigher(new PropertySizeWeigher()).recordStats().build(new PropertyFileCacheLoader(workspace.cdoView()));	
		
		
		//this is very expensive, so don't do it during the bind phase 
		new Thread(new Runnable() {
			@Override
			public void run() {
				long time = System.currentTimeMillis();
				workspace.eAdapters().add(new ListeningAdapter());
				LOGGER.info("Installed EContentAdapter in {} seconds",(System.currentTimeMillis()-time)/1000);
			}
		},"Install Persistence Listener").start();
	}

    public void unbindRepositoryConnector(RepositoryConnector repositoryConnector) {
        this.repositoryConnector = null;
        if(workspace!=null) {
        	CDOView view = workspace.cdoView();
        	org.eclipse.emf.cdo.session.CDOSession session = view.getSession();
        	LifecycleUtil.deactivate(view);
        	LifecycleUtil.deactivate(session);        	
        	session = null;
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.jabylon.resources.persistence.PropertyPersistenceService#
     * saveProperties(org.jabylon.properties.PropertyFileDescriptor,
     * org.jabylon.properties.PropertyFile)
     */
    @Override
    public void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file) {
        saveProperties(descriptor, file, false);

    }

    @Override
    public void saveProperties(PropertyFileDescriptor descriptor, PropertyFile file, boolean autoTranslate) {
        if(!active)
        {
            logger.error("Received save request while not active");
            throw new IllegalStateException("The PropertiesPersistanceService is deactivated");
        }
        try {
            PropertyFileDescriptor adaptedDescriptor = workspace.cdoView().getObject(descriptor);
            PropertyFile writeCopy = createCopy(file);
            // create a write copy to be independent of future writes
            queue.put(new PropertyTuple(adaptedDescriptor, writeCopy, autoTranslate));
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to save " + descriptor.fullPath(), e);
        }

    }

    private PropertyFile createCopy(PropertyFile file) {
        return EcoreUtil.copy(file);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jabylon.resources.persistence.PropertyPersistenceService#
     * addPropertiesListener
     * (org.jabylon.resources.changes.PropertiesListener)
     */
    @Override
    public void addPropertiesListener(PropertiesListener listener) {
        listeners.add(listener);

    }

    /*
     * (non-Javadoc)
     *
     * @see org.jabylon.resources.persistence.PropertyPersistenceService#
     * removePropertiesListener
     * (org.jabylon.resources.changes.PropertiesListener)
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
                PropertyTuple tuple = queue.take();
                try {
                    if (transaction == null)
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
                        firePropertiesChanges(descriptor, diff, tuple.isAutoSync());
                    } else {
                        PropertiesResourceImpl resource = new PropertiesResourceImpl(path);
                        resource.getContents().add(file);
                        resource.save(options);
                        descriptor.setKeys(resource.getSavedProperties());
                        descriptor.updatePercentComplete();
                        transaction.commit();
                        // FIXME: create resource folders and handle derived
                        // locales
                        firePropertiesAdded(descriptor, tuple.isAutoSync());
                    }
                } catch (IOException e) {
                    logger.error("Exception while processing "+tuple,e);
                } catch (CommitException e) {
                    logger.error("failed to commit while processing "+tuple,e);
                    transaction.close();
                    transaction = null;
                }
            }
        } catch (InterruptedException e) {
            logger.warn("Received Interrupt. Shutting down...");
            // let thread end...
        } finally {
            if(queue!=null)
                queue.clear();
            if (transaction != null)
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

    @Override
    public PropertyFile loadProperties(PropertyFileDescriptor descriptor) throws ExecutionException {

        return loadProperties(descriptor.cdoID());
    }

    @Override
    public PropertyFile loadProperties(CDOID descriptor) throws ExecutionException {

        return cache.get(descriptor);
    }

    @Override
    public void clearCache() {
    	cache.invalidateAll();
    	
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

    @Override
    public String toString() {
        return "PropertyTuple [descriptor=" + descriptor + ", file=" + file + ", autoSync=" + autoSync + "]";
    }



}
