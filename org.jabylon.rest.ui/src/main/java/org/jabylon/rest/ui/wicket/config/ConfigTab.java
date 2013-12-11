/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.osgi.service.prefs.Preferences;

import org.jabylon.properties.Resolvable;

public class ConfigTab <T extends Resolvable<?, ?>> implements ITab {

    private static final long serialVersionUID = 1L;
    private String title;
    private List<ConfigSection<T>> sections;
    private IModel<T> model;
    private Preferences preferences;


    public ConfigTab(String title, List<ConfigSection<T>> sections, IModel<T> model, Preferences preferences) {
        this.title = title;
        this.sections = sections;
        this.model = model;
        this.preferences = preferences;

    }

    @Override
    public IModel<String> getTitle() {
        return Model.of(title);
    }

    @Override
    public WebMarkupContainer getPanel(String containerId) {

        ConfigTabPanel panel = new ConfigTabPanel(containerId, sections, model, preferences);
        return panel;
    }

    @Override
    public boolean isVisible() {
        if(sections==null)
            return false;
        for (ConfigSection<T> section : sections) {
            if(section.isVisible(model, preferences))
                return true;
        }
        return false;
    }

}
