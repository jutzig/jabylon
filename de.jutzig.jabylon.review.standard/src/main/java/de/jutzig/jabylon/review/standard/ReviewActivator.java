/**
 *
 */
package de.jutzig.jabylon.review.standard;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ReviewActivator extends Plugin implements BundleActivator {
    private static ReviewActivator INSTANCE;
    public static final String PLUGIN_ID = "de.jutzig.jabylon.review.standard";
    private PropertyPersistenceService persistenceService;
    private ServiceReference<PropertyPersistenceService> persistenceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        INSTANCE = this;

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        INSTANCE = null;
        persistenceService = null;
        if(persistenceReference!=null)
            context.ungetService(persistenceReference);
    }


    public static ReviewActivator getDefault() {
        return INSTANCE;
    }

    public PropertyPersistenceService getPersistenceService()
    {
        if(persistenceService==null)
        {
            persistenceReference = getBundle().getBundleContext().getServiceReference(PropertyPersistenceService.class);
            persistenceService = getBundle().getBundleContext().getService(persistenceReference);
        }
        return persistenceService;
    }
}
