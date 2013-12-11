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

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jabylon.common.util.config.DynamicConfigUtil;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SettingsNavBarPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public SettingsNavBarPanel(String id, IModel<T> object, PageParameters parameters) {
        super(id, object, parameters);
        PageParameters params = parameters;
        if (object != null && object.getObject() instanceof Resolvable && !(object.getObject() instanceof Workspace)) {
            //workspace config isn't all that interesting. Show the overview by default instead
            Resolvable r = (Resolvable) object.getObject();
            params = WicketUtil.buildPageParametersFor(r);
        }
        BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link",SettingsPage.class,params); //$NON-NLS-1$

        //TODO: this looks shitty with bootstrap currently
        if(AuthenticatedWebSession.get().isSignedIn())
        {
            User user = null;
            Session session = getSession();
            if (session instanceof CDOAuthenticatedSession) {
                CDOAuthenticatedSession cdoSession = (CDOAuthenticatedSession) session;
                user = cdoSession.getUser();
            }
            if(object!=null)
                link.setEnabled(!DynamicConfigUtil.getApplicableElements(object.getObject(), user).isEmpty());
        }
        else
        {
            /*
             * if the user is not authenticated enable the link by default and trust
             * in the intercept page of the authorization strategy
             */
            if(object!=null)
                link.setEnabled(!DynamicConfigUtil.getApplicableElements(object.getObject()).isEmpty());
        }
        add(link);
    }

    public static class SettingsPanelFactory implements PanelFactory<Object>, Serializable
    {

        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new SettingsNavBarPanel<Object>(id, input, params);
        }

    }
}
