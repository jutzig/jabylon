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
package org.jabylon.rest.ui.wicket;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.jabylon.rest.ui.model.OSGiStringResourceModel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BasicPanel<T>extends GenericPanel<T> {

    private static final long serialVersionUID = 1L;
    private transient PageParameters pageParameters;
    private boolean constructed;

    public BasicPanel(String id, IModel<T> model, PageParameters parameters) {
        super(id,model);
        this.pageParameters = parameters;
    }

    public BasicPanel(String id, IModel<T> model) {
        this(id,model,null);
    }
    
    public PageParameters getPageParameters() {
    	if(pageParameters==null)
    		return getPage().getPageParameters();
        return pageParameters;
    }

    protected boolean urlEndsOnSlash()
    {
        PageParameters params = getPageParameters();
        for (NamedPair value : params.getAllNamed()) {
            if (value.getValue().isEmpty())
                return true;
        }

        // segments.addAll(params.getValues("segment"));
        for (int i = 0; i < params.getIndexedCount(); i++) {
            StringValue value = params.get(i);
            if (value.toString().isEmpty())
                return true;
        }
        return false;
    }


    @Override
    protected final void onBeforeRender() {

        internalConstruct();
        onBeforeRenderPanel();
        super.onBeforeRender();
    }

    protected void onBeforeRenderPanel() {

    }

    private final void internalConstruct() {
        if (!constructed) {
            preConstruct();
            construct();
            constructed = true;

        }
    }

    protected void preConstruct() {
        // subclasses may override

    }

    protected void construct() {

        // subclasses may override
    }
    
    /**
     * creates a new StringResourceModel for the given key with <code>this</code> as the component
     * @param key
     * @param parameters
     * 	The parameters to substitute using a Java MessageFormat object
     * @return
     */
    protected StringResourceModel nls(String key, Object... parameters) {
    	return new StringResourceModel(key, this, null, parameters);
    }


    /**
     * creates a new OSGiStringResourceModel for the given key with <code>this</code> as the component.
     * 
     * @param service the service implementation. Its bundle will be used to compute the localization
     * @param key
     * @param parameters
     * 	The parameters to substitute using a Java MessageFormat object
     * @return
     */
    protected StringResourceModel nls(Class<?> service, String key, Object... parameters) {
    	return new OSGiStringResourceModel(service, key, this, null, parameters);
    }

}
