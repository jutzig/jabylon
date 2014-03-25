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
package org.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.LoginPage;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.rest.ui.wicket.pages.WelcomePage;
import org.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class LoginPanel<T> extends BasicPanel<T> {

	
    private static final String LOGOUT_KEY = "logout.link.title";
	static final String LOGIN_KEY = "login.link.title";
	private static final long serialVersionUID = 1L;
    private BookmarkablePageLink<String> userLink;

    public LoginPanel(String id, IModel<T> object, PageParameters parameters) {
        super(id, object, parameters);

    }
    
    @Override
    protected void onInitialize() {
    	super.onInitialize();
        String username = "Anonymous";
        Session theSession = getSession();
        PageParameters userLinkParams = new PageParameters();
        userLink = new BookmarkablePageLink<String>("user-link", SettingsPage.class, userLinkParams);

        if (theSession instanceof CDOAuthenticatedSession) {
            final CDOAuthenticatedSession session = (CDOAuthenticatedSession) theSession;
            User user = session.getUser();
            if (user != null) {
                LogoutLink link = new LogoutLink("link");
                link.add(new Label("link-label", new StringResourceModel(LOGOUT_KEY, this,null)));
                add(link);

                userLinkParams.set(0, "security");
                userLinkParams.set(1, "users");
                userLinkParams.set(2, user.getName());
                username = user.getDisplayName();
                if(username==null)
                    username = user.getName();
                userLink.setVisible(true);

            }
            else {
            	Url returnUrl = RequestCycle.get().mapUrlFor(getPage().getClass(), getPage().getPageParameters());
            	
            	BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class, new PageParameters().set("target", returnUrl));
                	
                link.add(new Label("link-label", new StringResourceModel(LOGIN_KEY, this,null)));
                add(link);
                userLink.setVisible(false);
            }
        }

        userLink.setBody(Model.of(username));
        add(userLink);
    }

    public static class LoginPanelFactory implements PanelFactory<Object>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new LoginPanel<Object>(id, input, params);

        }

    }
}

class LogoutLink extends StatelessLink<String> {

    /**
     *
     */
    private static final long serialVersionUID = -8192886483968891414L;

    public LogoutLink(String id) {
        super(id);
    }

    @Override
    public void onClick() {
        Session theSession = getSession();
        if (theSession instanceof AuthenticatedWebSession) {
            AuthenticatedWebSession session = (AuthenticatedWebSession) theSession;
            session.invalidate();
            MarkupContainer parent = getParent();
            BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class);
            link.add(new Label("link-label", new StringResourceModel(LoginPanel.LOGIN_KEY,this,null)));
            parent.addOrReplace(link);
            setResponsePage(WelcomePage.class);
        }

    }

}
