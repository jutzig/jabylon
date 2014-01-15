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
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.tools.PropertyEditorTool;
import org.jabylon.rest.ui.tools.PropertyToolTab;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.components.BootstrapAjaxTabbedPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO short description for PropertiesEditorToolbar.
 * <p>
 * Long description for PropertiesEditorToolbar.
 *
 * @author utzig
 */
public class PropertiesEditorToolbar extends BasicResolvablePanel<PropertyFileDescriptor>
{
    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    private String currentKey;

    @Inject
    private List<PropertyEditorTool> tools;

    private PropertyPersistenceService persistenceService;

    private BootstrapAjaxTabbedPanel<PropertyToolTab> tabContainer;

    private List<PropertyToolTab> extensions;

    private static final Logger logger = LoggerFactory.getLogger(PropertiesEditorToolbar.class);



    public PropertiesEditorToolbar(String id, IModel<PropertyFileDescriptor> model, PageParameters pageParameters) {
        super(id, model, pageParameters);
    }

    @Override
    protected void construct() {
        extensions = createExtensions();
        tabContainer = new BootstrapAjaxTabbedPanel<PropertyToolTab>("tabs", extensions,"propertyTools/activeTab");
        add(tabContainer);
    }


    private List<PropertyToolTab> createExtensions() {
        List<PropertyToolTab> extensions = new ArrayList<PropertyToolTab>(tools.size());
        Collections.sort(tools,new Comparator<PropertyEditorTool>() {
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


    public void setKey(String key)
    {
        if(key!=null && !key.isEmpty() && !key.equals(currentKey))
        {
            PropertyFileDescriptor translation = getModel().getObject();
            PropertyFile properties;
            try {
                properties = persistenceService.loadProperties(translation);
                properties.getProperty(key);
                PropertyFileDescriptor master = translation.getMaster();
                PropertyFile masterFile = persistenceService.loadProperties(master);

                PropertyPair pair = new PropertyPair(masterFile.getProperty(key),properties.getProperty(key), translation.getVariant(),translation.cdoID());
//			int selected = tabContainer.getSelectedTab();
                for (PropertyToolTab tool : extensions) {
                    tool.setModel(Model.of(pair));
                }
            } catch (ExecutionException e) {
                logger.error("Failed to load property file",e);
            }

        }
        this.currentKey = key;
    }

}



