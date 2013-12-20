/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.war;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.eclipse.equinox.servletbridge.BridgeFilter;

public class JabylonFilter extends BridgeFilter {

	private static final long serialVersionUID = 7535666663439525997L;
	private ServletConfig servletConfig;
	private Properties launchProperties = new Properties();
	private String jabylonHome;
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		internalInit();
		super.init(filterConfig);
	}

	private void internalInit() {
		File homeDir = new File(new File(System.getProperty("user.home")), "jabylon");
		jabylonHome = System.getProperty("JABYLON_HOME", homeDir.getAbsolutePath());
		launchProperties.put("JABYLON_HOME", jabylonHome);
		launchProperties.put("osgi.instance.area", homeDir.toURI().toString());
		launchProperties.put("osgi.configuration.area", new File(homeDir, "configuration").toURI().toString());
		launchProperties.put("osgi.clean", "true");
		System.getProperties().putAll(launchProperties);
		copyResources("/WEB-INF/eclipse/configuration/", new File(homeDir, "configuration"));
	}

	private void copyResources(String source, File target) {
		if (source.endsWith("/")) {
			target.mkdirs();
			Set<String> paths = getServletContext().getResourcePaths(source);
			for (String string : paths) {
				log("copying "+string);
				copyResources(string, new File(target, string.substring(source.length())));
			}
		} else {
			if (target.exists())
				return;
			InputStream stream = getServletContext().getResourceAsStream(source);
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(target);
				byte[] buffer = new byte[1024];
				int read = 0;
				while (read != -1) {
					out.write(buffer, 0, read);
					read = stream.read(buffer);
				}
			} catch (FileNotFoundException e) {
				log("Target file not found", e);
			} catch (IOException e) {
				log("failed to copy "+source, e);
			} finally {
				try {
					if (stream != null)
						stream.close();
					if (out != null)
						out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ServletConfig getServletConfig() {
		if (servletConfig == null) {
			servletConfig = new CustomServletConfig(filterConfig, launchProperties);
		}
		return servletConfig;
	}

}

class CustomServletConfig implements ServletConfig {

	private FilterConfig filterConfig;
	private ServletContext context;

	public CustomServletConfig(FilterConfig delegate, Properties props) {
		super();
		this.filterConfig = delegate;
		context = new CustomServletContext(delegate.getServletContext(), props);
	}


	public String getInitParameter(String arg0) {
		return filterConfig.getInitParameter(arg0);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return filterConfig.getInitParameterNames();
	}

	public ServletContext getServletContext() {
		return context;
	}

	public String getServletName() {
		return filterConfig.getFilterName();
	}

}

class CustomServletContext implements ServletContext {

	private ServletContext delegate;
	private Set<String> replacementPaths;
	private File launchProps;

	public CustomServletContext(ServletContext delegate, Properties prop) {
		super();

		this.delegate = delegate;
		replacementPaths = new HashSet<String>();
		replacementPaths.add("/WEB-INF/eclipse/configuration/launch.ini");
		replacementPaths.add("/WEB-INF/eclipse/launch.ini");
		//replacementPaths.add("/WEB-INF/launch.ini");
		try {
			launchProps = File.createTempFile("launch", "ini");
			launchProps.deleteOnExit();
			FileOutputStream out = new FileOutputStream(launchProps);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			log("Failed to store launch properties", e);
		}
	}

	public URL getResource(String path) throws MalformedURLException {
		if (replacementPaths.contains(path)) {
			log("Serving path: " + path);
			return launchProps.toURI().toURL();
		}
		return delegate.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		if (replacementPaths.contains(path)) {
			log("Serving path: " + path);
			try {
				return getResource(path).openStream();
			} catch (MalformedURLException e) {
				log("Failed to server replacement launch.ini", e);
			} catch (IOException e) {
				log("Failed to server replacement launch.ini", e);
			}
		}
		return delegate.getResourceAsStream(path);
	}

	public String getContextPath() {
		return delegate.getContextPath();
	}

	public ServletContext getContext(String uripath) {
		return delegate.getContext(uripath);
	}

	public int getMajorVersion() {
		return delegate.getMajorVersion();
	}

	public int getMinorVersion() {
		return delegate.getMinorVersion();
	}

	public int getEffectiveMajorVersion() {
		return delegate.getEffectiveMajorVersion();
	}

	public int getEffectiveMinorVersion() {
		return delegate.getEffectiveMinorVersion();
	}

	public String getMimeType(String file) {
		return delegate.getMimeType(file);
	}

	public Set<String> getResourcePaths(String path) {
		return delegate.getResourcePaths(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return delegate.getRequestDispatcher(path);
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		return delegate.getNamedDispatcher(name);
	}

	@SuppressWarnings("deprecation")
	public Servlet getServlet(String name) throws ServletException {
		return delegate.getServlet(name);
	}

	@SuppressWarnings("deprecation")
	public Enumeration<Servlet> getServlets() {
		return delegate.getServlets();
	}

	@SuppressWarnings("deprecation")
	public Enumeration<String> getServletNames() {
		return delegate.getServletNames();
	}

	public void log(String msg) {
		delegate.log(msg);
	}

	@SuppressWarnings("deprecation")
	public void log(Exception exception, String msg) {
		delegate.log(exception, msg);
	}

	public void log(String message, Throwable throwable) {
		delegate.log(message, throwable);
	}

	public String getRealPath(String path) {
		return delegate.getRealPath(path);
	}

	public String getServerInfo() {
		return delegate.getServerInfo();
	}

	public String getInitParameter(String name) {
		return delegate.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return delegate.getInitParameterNames();
	}

	public boolean setInitParameter(String name, String value) {
		return delegate.setInitParameter(name, value);
	}

	public Object getAttribute(String name) {
		return delegate.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return delegate.getAttributeNames();
	}

	public void setAttribute(String name, Object object) {
		delegate.setAttribute(name, object);
	}

	public void removeAttribute(String name) {
		delegate.removeAttribute(name);
	}

	public String getServletContextName() {
		return delegate.getServletContextName();
	}

	public Dynamic addServlet(String servletName, String className) {
		return delegate.addServlet(servletName, className);
	}

	public Dynamic addServlet(String servletName, Servlet servlet) {
		return delegate.addServlet(servletName, servlet);
	}

	public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
		return delegate.addServlet(servletName, servletClass);
	}

	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
		return delegate.createServlet(clazz);
	}

	public ServletRegistration getServletRegistration(String servletName) {
		return delegate.getServletRegistration(servletName);
	}

	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return delegate.getServletRegistrations();
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
		return delegate.addFilter(filterName, className);
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
		return delegate.addFilter(filterName, filter);
	}

	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
		return delegate.addFilter(filterName, filterClass);
	}

	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
		return delegate.createFilter(clazz);
	}

	public FilterRegistration getFilterRegistration(String filterName) {
		return delegate.getFilterRegistration(filterName);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return delegate.getFilterRegistrations();
	}

	public SessionCookieConfig getSessionCookieConfig() {
		return delegate.getSessionCookieConfig();
	}

	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
		delegate.setSessionTrackingModes(sessionTrackingModes);
	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return delegate.getDefaultSessionTrackingModes();
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return delegate.getEffectiveSessionTrackingModes();
	}

	public void addListener(String className) {
		delegate.addListener(className);
	}

	public <T extends EventListener> void addListener(T t) {
		delegate.addListener(t);
	}

	public void addListener(Class<? extends EventListener> listenerClass) {
		delegate.addListener(listenerClass);
	}

	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
		return delegate.createListener(clazz);
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		return delegate.getJspConfigDescriptor();
	}

	public ClassLoader getClassLoader() {
		return delegate.getClassLoader();
	}

	public void declareRoles(String... roleNames) {
		delegate.declareRoles(roleNames);
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

}
