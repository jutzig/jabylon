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
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.pages.GenericPage;

public class LoginPage extends GenericPage<Resolvable<?, ?>> {

	private static final long serialVersionUID = 2083924576041565751L;


	public LoginPage(PageParameters parameters) {
        super(parameters);
        SignInPanel panel = new BootstrapSignInPanel("sign-in", true);
        panel.addOrReplace(new Label("feedback",""));
        add(panel);
    }
    
    @Override
    public void renderHead(IHeaderResponse response) {

//        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
//        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("bootstrap/js/bootstrap.min.js"))));
//        response.render(CssHeaderItem.forReference(new WebContextUrlResourceReference("css/main.css")));
        super.renderHead(response);
    }


    @Override
    protected IModel<Resolvable<?, ?>> createModel(PageParameters params) {
        return null;
    }
}
