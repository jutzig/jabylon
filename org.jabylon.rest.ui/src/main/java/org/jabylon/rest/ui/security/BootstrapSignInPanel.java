/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.security;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;

public class BootstrapSignInPanel extends SignInPanel {

	private static final long serialVersionUID = 6449210837745750191L;

	public BootstrapSignInPanel(String id) {
		super(id);
	}

	public BootstrapSignInPanel(String id, boolean includeRememberMe) {
		super(id, includeRememberMe);
	}
}
