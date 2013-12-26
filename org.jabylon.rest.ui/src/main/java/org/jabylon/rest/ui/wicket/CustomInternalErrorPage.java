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

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.pages.AbstractErrorPage;
import org.jabylon.rest.ui.util.WebContextUrlResourceReference;
import org.jabylon.rest.ui.util.WicketUtil;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class CustomInternalErrorPage extends AbstractErrorPage {

	private static final long serialVersionUID = 2779965136189309566L;

	public CustomInternalErrorPage() {
		add(homePageLink("home"));
		add(new ExternalLink("log", WicketUtil.getContextPath() + "settings/log"));
	}
	
    @Override
    public void renderHead(IHeaderResponse response) {

        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("bootstrap/js/bootstrap.min.js"))));
        response.render(CssHeaderItem.forReference(new WebContextUrlResourceReference("css/main.css")));
        super.renderHead(response);
    }	
	
}
