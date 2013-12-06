/**
 *
 */
package org.jabylon.rest.ui.wicket;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jabylon.common.resolver.URIResolver;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.EMFFactoryConverter;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.LoginPage;
import org.jabylon.rest.ui.security.PermissionBasedAuthorizationStrategy;
import org.jabylon.rest.ui.util.PageProvider;
import org.jabylon.rest.ui.wicket.components.AjaxFeedbackListener;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.rest.ui.wicket.injector.OSGiInjector;
import org.jabylon.rest.ui.wicket.pages.StartupPage;
import org.jabylon.rest.ui.wicket.pages.WelcomePage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonApplication extends AuthenticatedWebApplication {

    @SuppressWarnings("rawtypes")
    private ServiceTracker pageTracker;

    private static Logger logger = LoggerFactory.getLogger(JabylonApplication.class);

    /*
     * (non-Javadoc)
     *
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        URIResolver connector = Activator.getDefault().getRepositoryLookup();
        if (connector != null)
            return WelcomePage.class;
        return StartupPage.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void init() {
        super.init();
        OSGiInjector injector = new OSGiInjector(this);
        getBehaviorInstantiationListeners().add(injector);
        getComponentInstantiationListeners().add(injector);
        getSecuritySettings().setAuthorizationStrategy(new PermissionBasedAuthorizationStrategy());
        getAjaxRequestTargetListeners().add(new AjaxFeedbackListener());
        final BundleContext bundleContext = Activator.getDefault().getContext();

        pageTracker = new ServiceTracker(bundleContext, PageProvider.class, new ServiceTrackerCustomizer() {

            @Override
            public Object addingService(ServiceReference ref) {
                PageProvider service = (PageProvider)bundleContext.getService(ref);
                Object pathObject = ref.getProperty(PageProvider.MOUNT_PATH_PROPERTY);
                if (pathObject instanceof String) {
                    String path = (String) pathObject;
                    Class pageClass = service.getPageClass();
                    logger.info("Mounting new page {} at {}", pageClass, path); //$NON-NLS-1$
                    mountPage(path, pageClass);

                } else {
                    logger.warn("Ignored Page {} because it was registered with invalid path property '{}'", service, pathObject); //$NON-NLS-1$
                }
                return service;
            }

            @Override
            public void modifiedService(ServiceReference arg0, Object arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void removedService(ServiceReference ref, Object service) {
                Object pathObject = ref.getProperty(PageProvider.MOUNT_PATH_PROPERTY);
                if (pathObject instanceof String) {
                    String path = (String) pathObject;
                    Application application = ThreadContext.getApplication();
                    if (application != null)
                    {
                        //otherwise wicket will throw an exception unfortunately
                        //TODO: how can this be done cleanly?
                        unmount(path);
                    }
                }
            }

        });
        pageTracker.open();

        mountPage("/login", LoginPage.class); //$NON-NLS-1$
        mountPage("/settings", SettingsPage.class); //$NON-NLS-1$
//		mountPage("/workspace", ResourcePage.class);


    }


    protected IConverterLocator newConverterLocator() {
        ConverterLocator converterLocator = new ConverterLocator();
        converterLocator.set(URI.class, new EMFFactoryConverter<URI>(PropertiesPackage.Literals.URI.getName()));
        converterLocator.set(Locale.class, new EMFFactoryConverter<Locale>(PropertiesPackage.Literals.LOCALE.getName()));
        return converterLocator;
    }



    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return CDOAuthenticatedSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

}
