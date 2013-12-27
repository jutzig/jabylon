/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.components.BootstrapTabbedPanel;
import org.jabylon.rest.ui.wicket.pages.GenericPage;
import org.jabylon.updatecenter.repository.OBRRepositoryService;
import org.jabylon.updatecenter.repository.ResourceFilter;


public class UpdatecenterPage extends GenericPage<Serializable> implements RestrictedComponent{

    private static final long serialVersionUID = 1L;

    @Inject
    private transient OBRRepositoryService repositoryConnector;


    public UpdatecenterPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void construct() {
        super.construct();
        BootstrapTabbedPanel<ITab> panel = new BootstrapTabbedPanel<ITab>("tabs", createTabList(), null);
        add(panel);
    }

    private List<ITab> createTabList() {
        List<ITab> tabs = new ArrayList<ITab>();
        tabs.add(new UpdatecenterTab(Model.of("Plugins"), ResourceFilter.PLUGIN));
        tabs.add(new UpdatecenterTab(Model.of("Update"), ResourceFilter.UPDATEABLE));
        tabs.add(new UpdatecenterTab(Model.of("All"), ResourceFilter.INSTALLABLE));
        tabs.add(new UpdatecenterTab(Model.of("Installed"), ResourceFilter.INSTALLED));

        return tabs;
    }

    @Override
    protected IModel<Serializable> createModel(PageParameters params) {
        IModel<Serializable> model = Model.of();
        return model;
    }


    @Override
    public String getRequiredPermission() {
        return "System:software:config";
    }

}
