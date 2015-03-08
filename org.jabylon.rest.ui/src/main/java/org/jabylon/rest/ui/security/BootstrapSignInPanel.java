/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.security;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.util.string.StringValue;
import org.jabylon.rest.ui.wicket.pages.RegisterPage;
import org.jabylon.security.CommonPermissions;

public class BootstrapSignInPanel extends SignInPanel {

	private static final long serialVersionUID = 6449210837745750191L;

	public BootstrapSignInPanel(String id) {
		super(id);
	}

	public BootstrapSignInPanel(String id, boolean includeRememberMe) {
		super(id, includeRememberMe);
		WebMarkupContainer registerContainer = new WebMarkupContainer("register.container");
        BookmarkablePageLink<Object> link = new BookmarkablePageLink<Object>("register.link", RegisterPage.class);
        link.setBody(new StringResourceModel("register.link.label",this,null));
		registerContainer.add(link);
		registerContainer.setVisibilityAllowed(canRegister());
		getForm().add(registerContainer);
	}
	
	private boolean canRegister() {
		Session session = getSession();
		if (session instanceof CDOAuthenticatedSession) {
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			return CommonPermissions.hasPermission(authSession.getAnonymousUser(), CommonPermissions.USER_REGISTER);
		}
		return false;
	}

	@Override
	protected void onSignInSucceeded() {
		StringValue destination = getPage().getPageParameters().get("target");
		if(!destination.isEmpty()) {
			throw new RedirectToUrlException(destination.toString());
		}
		super.onSignInSucceeded();
	}
}
