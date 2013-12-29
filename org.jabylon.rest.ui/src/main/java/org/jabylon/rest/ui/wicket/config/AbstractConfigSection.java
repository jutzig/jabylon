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
package org.jabylon.rest.ui.wicket.config;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.LoginPage;
import org.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public abstract class AbstractConfigSection<T> implements ConfigSection<T> {

	private static final long serialVersionUID = 1L;

	private IModel<T> model;

	public T getDomainObject() {
		if (model == null)
			return null;
		return model.getObject();
	}

	public IModel<T> getModel() {
		return model;
	}

	@Override
	public void apply(Preferences config) {

	}

	@Override
	public boolean isVisible(IModel<T> input, Preferences config) {
		model = input;
		CDOAuthenticatedSession session = (CDOAuthenticatedSession) CDOAuthenticatedSession.get();
		User user = session.getUser();
		if (user != null)
			return user.hasPermission(getRequiredPermission());
		else {
			User anonymousUser = session.getAnonymousUser();
			boolean allowed = anonymousUser.hasPermission(getRequiredPermission());
			if (allowed)
				return true;
			throw new RestartResponseAtInterceptPageException(LoginPage.class);
		}
	}

	@Override
	public final WebMarkupContainer createContents(String id, IModel<T> input, Preferences config) {
		this.model = input;
		return doCreateContents(id, input, config);
	}

	protected abstract WebMarkupContainer doCreateContents(String id, IModel<T> input, Preferences config);
}
