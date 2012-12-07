/*
 * PropertiesEditorToolbar.java
 *
 * created at 07.12.2012 by utzig <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;


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

    public PropertiesEditorToolbar(String id, IModel<PropertyFileDescriptor> model, PageParameters parameters)
    {
        super(id, model, parameters);
    }




}



