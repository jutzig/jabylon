/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.pages;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.jabylon.rest.ui.util.GlobalResources;

public class StartupPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void renderHead(IHeaderResponse response) {
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(GlobalResources.JS_BOOTSTRAP)));
        response.render(CssHeaderItem.forReference(GlobalResources.MAIN_CSS));
		super.renderHead(response);
	}

}
