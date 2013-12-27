/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.users.User;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * A bootstrap styled tabbed panel that uses ajax to render the tabs lazily.
 * <p>
 * You can optionally provide a persistenceKey that is used to store and restore the last selected tab for the current user 
 * @author jutzig.dev@googlemail.com
 *
 * @param <T>
 */
public class BootstrapAjaxTabbedPanel<T extends ITab> extends AjaxTabbedPanel<T> {


	private static final long serialVersionUID = 2818066082911318768L;
	private String persistenceKey;

	/**
     * {@inheritDoc}
     */
    public BootstrapAjaxTabbedPanel(String id, List<T> tabs, String persistenceKey) {
        super(id, tabs);
        this.persistenceKey = persistenceKey;
        if(persistenceKey!=null) {
        	Preferences prefs = PreferencesUtil.scopeFor(getUser());
        	int activeTab = prefs.getInt(persistenceKey, 0);
        	setSelectedTab(activeTab);        	
        }
    }

    /**
     * {@inheritDoc}
     */
    public BootstrapAjaxTabbedPanel(String id, List<T> tabs, IModel<Integer> model, String persistenceKey) {
        super(id, tabs);
        setSelectedTab(model.getObject());
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        checkComponentTag(tag, "div");
        tag.append("class", "tabbable", " ");
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "active";
    }

    @Override
    protected String getLastTabCssClass() {
        return "";
    }
    
    @Override
    public TabbedPanel<T> setSelectedTab(int index) {
    	if(persistenceKey!=null) {
    		Preferences prefs = PreferencesUtil.scopeFor(getUser());
    		if(index != prefs.getInt(persistenceKey, -1))
    		{
    			prefs.putInt(persistenceKey, index);   
    			try {
//    			prefs.sync();
    				prefs.flush();
    			} catch (BackingStoreException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}    			
    		}
    	}
    	return super.setSelectedTab(index);
    }

	protected EObject getUser() {
		Session session = getSession();
		if (session instanceof CDOAuthenticatedSession) {
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			User user = authSession.getUser();
			if(user!=null)
				return user;
			return authSession.getAnonymousUser();
			
		}
		return null;
	}
}