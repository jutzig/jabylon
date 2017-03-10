/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security.internal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.ILoginContextListener;
import org.jabylon.security.auth.AuthenticatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * wraps an equinox login context into a regular one
 *
 */
public class LoginContextWrapper extends LoginContext {

	private Logger logger = LoggerFactory.getLogger(AuthenticatorServiceImpl.class);
	private ILoginContext context;

	public LoginContextWrapper(String name, ILoginContext context) throws LoginException {
		super(name);
		this.context = context;
	}

	public void login() throws LoginException {
		context.login();
	}

	public void logout() throws LoginException {
		context.logout();
	}

	public Subject getSubject() {
		try {
			return context.getSubject();
		} catch (LoginException e) {
			logger.error("Failed to retrieve subject", e);
		}
		return null;
	}

	public void registerListener(ILoginContextListener listener) {
		context.registerListener(listener);
	}

	public void unregisterListener(ILoginContextListener listener) {
		context.unregisterListener(listener);
	}

	public LoginContext getLoginContext() throws LoginException {
		return context.getLoginContext();
	}
}