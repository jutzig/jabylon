/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.tools.PropertyEditorTool;
import org.jabylon.rest.ui.tools.PropertyToolTab;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.BootstrapAjaxTabbedPanel;

/**
 * TODO short description for PropertiesEditorToolbar.
 * <p>
 * Long description for PropertiesEditorToolbar.
 *
 * @author utzig
 */
public class PropertiesTools extends BasicPanel<PropertyPair> {
    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    @Inject
    private List<PropertyEditorTool> tools;

    private BootstrapAjaxTabbedPanel<PropertyToolTab> tabContainer;

    private List<PropertyToolTab> extensions;

    public PropertiesTools(String id, IModel<PropertyPair> model, PageParameters pageParameters) {
        super(id, model, pageParameters);
    }

    @Override
    protected void construct() {
        extensions = createExtensions();
        tabContainer = new BootstrapAjaxTabbedPanel<PropertyToolTab>("tabs", extensions,"propertyTools/activeTab");
        add(tabContainer);
        PropertyPair pair = getModelObject();
        // int selected = tabContainer.getSelectedTab();
        for (PropertyToolTab tool : extensions) {
            tool.setModel(Model.of(pair));
        }
    }

    private List<PropertyToolTab> createExtensions() {
        List<PropertyToolTab> extensions = new ArrayList<PropertyToolTab>(tools.size());
        Collections.sort(tools, new Comparator<PropertyEditorTool>() {
            @Override
            public int compare(PropertyEditorTool o1, PropertyEditorTool o2) {
                return o1.getPrecedence() - o2.getPrecedence();
            }
        });
        for (PropertyEditorTool tool : tools) {
            extensions.add(new PropertyToolTab(tool));
        }
        return extensions;
    }

}
