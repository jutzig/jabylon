/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.jabylon.rest.ui.wicket.JabylonApplication;

public class JabylonFilter extends WicketFilter implements IWebApplicationFactory {

	private String rootRedirect = null;

	@Override
	protected IWebApplicationFactory getApplicationFactory() {
		return this;
	}

	@Override
	public WebApplication createApplication(WicketFilter filter) {
		return new JabylonApplication();
	}

	@Override
	public void destroy(WicketFilter filter) {
		// nothing to do

	}

	@Override
	protected String getFilterPathFromConfig(FilterConfig filterConfig) {
		// Wicket gets confused by the servlet bridge.
		// so we set the path manually
		// see https://github.com/jutzig/jabylon/issues/47
		ServletContext context = filterConfig.getServletContext();
		String path = "";
		if (context != null)
			path = context.getContextPath();
		if (path.startsWith("/"))
			path = path.substring(1);
		return path;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (rootRedirect == null) {
			// see https://github.com/jutzig/jabylon/issues/165
			if (request instanceof HttpServletRequest) {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				rootRedirect = getFilterPath();
				if (rootRedirect == null)
					rootRedirect = "";
				if (rootRedirect.endsWith("/"))
					rootRedirect = rootRedirect.substring(0, rootRedirect.length() - 1);
				if (!rootRedirect.isEmpty() && !rootRedirect.startsWith("/"))
					rootRedirect = "/" + rootRedirect;

				HttpServletResponse httpResponse = (HttpServletResponse) response;
				String pathInfo = httpRequest.getRequestURI();
				if (rootRedirect.equals(pathInfo)) {
					/*
					 * Tomcat doesn't do this automatically if a servlet mapping
					 * for /* is defined. But we need the /* mapping because
					 * otherwise the path info won't be filled
					 */
					httpResponse.sendRedirect(rootRedirect + "/");
					return;
				}
			}
		}
		super.doFilter(request, response, chain);
	}
}
