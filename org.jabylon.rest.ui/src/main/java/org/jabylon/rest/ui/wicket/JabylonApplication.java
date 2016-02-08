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
package org.jabylon.rest.ui.wicket;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.IExceptionMapper;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.CompoundRequestMapper;
import org.apache.wicket.request.mapper.ICompoundRequestMapper;
import org.apache.wicket.util.IProvider;
import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.EMFFactoryConverter;
import org.jabylon.rest.ui.model.OSGiAwareBundleStringResourceLoader;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.LoginPage;
import org.jabylon.rest.ui.security.PermissionBasedAuthorizationStrategy;
import org.jabylon.rest.ui.util.PageProvider;
import org.jabylon.rest.ui.wicket.components.AjaxFeedbackListener;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.rest.ui.wicket.injector.OSGiInjector;
import org.jabylon.rest.ui.wicket.pages.XliffDownloadPage;
import org.jabylon.rest.ui.wicket.pages.ResourcePage;
import org.jabylon.rest.ui.wicket.pages.StartupPage;
import org.jabylon.rest.ui.wicket.pages.WelcomePage;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
//    	if(startupPageMapped=)
//        URIResolver connector = Activator.getDefault().getRepositoryLookup();
//        if (connector != null)
            return WelcomePage.class;
//        return StartupPage.class;
    }

    @Override
    public WebRequest newWebRequest(HttpServletRequest servletRequest, String filterPath) {
//    	https://github.com/jutzig/jabylon/issues/47
    	return new CustomWebRequest(servletRequest, filterPath);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void init() {
        super.init();
        mount(new ResouceAwareMountedMapper("/", StartupPage.class)); //$NON-NLS-1$
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
        OSGiInjector injector = new OSGiInjector(this);
        getBehaviorInstantiationListeners().add(injector);
        getResourceSettings().getStringResourceLoaders().add(new OSGiAwareBundleStringResourceLoader());
        getApplicationSettings().setInternalErrorPage(CustomInternalErrorPage.class);
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

                    if(pageClass==ResourcePage.class) {
                        //workaround so wicket doesn't choke because the thread context isn't filled (wrong thread)
                    	ThreadContext.setApplication(JabylonApplication.this);
                    	ThreadContext.setSession(new WebSession(createFakeRequest(null)));
                		//if the main page is ready, we can mount the rest of the pages
                			initMainPages();
                	}

                    logger.info("Mounting new page {} at {}", pageClass, path); //$NON-NLS-1$
                    mount(new ResouceAwareMountedMapper(path, pageClass));

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
                    internalUmount(path);
                }
            }

        });
        pageTracker.open();
    }

    private void internalUmount(String path){
        //workaround so wicket doesn't choke because the thread context isn't filled (wrong thread)
		Application application = ThreadContext.getApplication();
		if (application == null)
        	ThreadContext.setApplication(JabylonApplication.this);
    	if(ThreadContext.getSession()==null)
    		ThreadContext.setSession(new WebSession(createFakeRequest(null)));
//        unmount(path);
    	/*
    	 * umount seems to be greedy, e.g. a prefix match is enough.
    	 * That's troublesome because umount /settings/log will also umount /settings
    	 */
    	ICompoundRequestMapper rootRequestMapperAsCompound = getRootRequestMapperAsCompound();
    	if (rootRequestMapperAsCompound instanceof CompoundRequestMapper) {
			CompoundRequestMapper compound = (CompoundRequestMapper) rootRequestMapperAsCompound;
			Iterator<IRequestMapper> it = compound.iterator();
			while (it.hasNext()) {
				IRequestMapper iRequestMapper = it.next();
				if (iRequestMapper instanceof ResouceAwareMountedMapper) {
					ResouceAwareMountedMapper mapper = (ResouceAwareMountedMapper) iRequestMapper;
					if(path.equals(mapper.getMountPath()))
					{
						logger.info("Unmounting  {}",path);
						getRootRequestMapperAsCompound().remove(mapper);
					}
				}
			}
		}
    }

    private void initMainPages() {
    	unmount("/");
        mount(new ResouceAwareMountedMapper("/login", LoginPage.class)); //$NON-NLS-1$
        mount(new ResouceAwareMountedMapper("/settings", SettingsPage.class)); //$NON-NLS-1$
		mount(new ResouceAwareMountedMapper("/xliff", XliffDownloadPage.class)); //$NON-NLS-1$
    }


    @Override
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

	@Override
	public IProvider<IExceptionMapper> getExceptionMapperProvider()
	{

		return new CustomExceptionMapperProvider();
	}

	private static class CustomExceptionMapperProvider implements IProvider<IExceptionMapper>
	{
		@Override
		public IExceptionMapper get()
		{
			return new CustomExceptionMapper();
		}
	}



	Request createFakeRequest(final Url url)
	{
		return new Request()
		{
			@Override
			public Url getUrl()
			{
				if(url==null)
					return new Url();
				return url;
			}

			@Override
			public Locale getLocale()
			{
				return Locale.getDefault();
			}

			@Override
			public Object getContainerRequest()
			{
				return null;
			}

			@Override
			public Url getClientUrl()
			{
				return null;
			}

			@Override
			public Charset getCharset()
			{
				return null;
			}
		};
	}
}
