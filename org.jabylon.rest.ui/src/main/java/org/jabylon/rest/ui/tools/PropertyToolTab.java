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
package org.jabylon.rest.ui.tools;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.model.OSGiStringResourceModel;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.wicket.CustomWebRequest;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PropertyToolTab implements ITab {

    private static final long serialVersionUID = 1L;
    private PropertyEditorTool tool;
    private IModel<PropertyPair> model;



    public PropertyToolTab(PropertyEditorTool tool) {
        super();
        this.tool = tool;
        model = new Model<PropertyPair>();
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getTitle()
     */
    @Override
    public IModel<String> getTitle() {
        return new OSGiStringResourceModel(tool.getClass(),tool.getName(),null);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.tabs.ITab#getPanel(java.lang.String)
     */
    @Override
    public WebMarkupContainer getPanel(String containerId) {
    	Request request = RequestCycle.get().getRequest();
    	if (request instanceof CustomWebRequest) {
			CustomWebRequest cwr = (CustomWebRequest) request;
			//performance optimization. We don't need the tools on a post request
			//https://github.com/jutzig/jabylon/issues/197
			if(cwr.isPost())
				return new WebMarkupContainer(containerId);
			
		}
        return tool.createPanel(new PageParameters(), model, containerId);
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.tabs.ITab#isVisible()
     */
    @Override
    public boolean isVisible() {
        return true;
    }

    public void setModel(IModel<PropertyPair> model) {
        this.model.setObject(model.getObject());

    }



}
