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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.jabylon.rest.ui.util.GlobalResources;
import org.jabylon.rest.ui.util.WicketUtil;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class SettingsOverviewPanel extends GenericPanel<Void> {

    private static final long serialVersionUID = 1L;

    public SettingsOverviewPanel(String id) {
        super(id);

        List<ConfigKind> configs = new ArrayList<ConfigKind>(EnumSet.allOf(ConfigKind.class));
        ListView<ConfigKind> view = new ListView<ConfigKind>("config",configs) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<ConfigKind> item) {
            	AbstractLink link = item.getModelObject().constructLink("link");
                item.add(link);               
                item.add(new Label("description",new StringResourceModel(item.getModelObject().getDescription(),item,null)));
                link.add(new Image("image", item.getModelObject().getIcon()));
                link.add(new Label("text",new StringResourceModel(item.getModelObject().getName(),item,null)));
            }

        };
        add(view);
    }

}

enum ConfigKind {
	
    WORKSPACE("projects.title","projects.description", GlobalResources.IMG_WORKSPACE_SETTINGS){

        @Override
        public Link<Void> constructLink(String id) {
            PageParameters params = new PageParameters();
            params.set(0, "workspace");
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(id, SettingsPage.class, params);
            return link;
        }


    },

    SYSTEM("system.title","system.description", GlobalResources.IMG_SYSTEM_SETTINGS) {
        @Override
        public AbstractLink constructLink(String id) {
        	//TODO: better to do this dynamic with e.g. an extension point/whiteboard pattern
            return new ExternalLink(id, Model.of(WicketUtil.getContextPath() + "/settings/system"));
        }
    },
    LOGGING("logging.title","logging.description", GlobalResources.IMG_LOG_SETTINGS) {
        @Override
        public AbstractLink constructLink(String id) {
        	//TODO: better to do this dynamic with e.g. an extension point/whiteboard pattern
            return new ExternalLink(id, Model.of(WicketUtil.getContextPath() + "/settings/log"));
        }
    }
    , SECURITY("security.title","security.description", GlobalResources.IMG_SECURITY_SETTINGS) {
        @Override
        public AbstractLink constructLink(String id) {
            PageParameters params = new PageParameters();
            params.set(0, "security");
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(id, SettingsPage.class, params);
            return link;
        }
    };
    /** the property id of the name */
    private String name;
    /** the property id of the description */
    private String description;
    /** the icon of the setting */
    private ResourceReference icon;

    private ConfigKind(String name, String description, ResourceReference icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    
    
    public ResourceReference getIcon() {
    	return icon;
    }
    public abstract AbstractLink constructLink(String id);

}
