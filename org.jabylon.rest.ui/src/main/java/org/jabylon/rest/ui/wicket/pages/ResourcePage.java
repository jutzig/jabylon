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
package org.jabylon.rest.ui.wicket.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import org.jabylon.rest.ui.wicket.panels.ProjectResourcePanel;
import org.jabylon.rest.ui.wicket.panels.PropertyEditorPanel;
import org.jabylon.rest.ui.wicket.panels.PropertyEditorSinglePanel;
import org.jabylon.rest.ui.wicket.panels.PropertyListPanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ResourcePage<T extends Resolvable<?, ?>> extends GenericResolvablePage<T> {

    private static final long serialVersionUID = 1L;


    public ResourcePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onBeforeRenderPage() {
        T object = getModelObject();
        if (object instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
            // legacy editor
            if(getPageParameters().get("editor").toString("").equals("full"))
                addOrReplace(new PropertyEditorPanel(descriptor,getPageParameters()));
            else
            {
                if(getPageParameters().get("key").isEmpty())
                    addOrReplace(new PropertyListPanel(descriptor,getPageParameters()));
                else
                    addOrReplace(new PropertyEditorSinglePanel(descriptor,getPageParameters()));
            }
        }
        else
            addOrReplace(new ProjectResourcePanel(object,getPageParameters()));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void construct() {
        BreadcrumbPanel breadcrumbPanel = new BreadcrumbPanel("breadcrumb-panel", (IModel<Resolvable<?, ?>>) getModel() ,getPageParameters());
        add(breadcrumbPanel);
    }


    @SuppressWarnings("unchecked")
    protected T doLookup(List<String> segments) {
        List<String> modified = new ArrayList<String>(segments);
        if(!segments.isEmpty() && !segments.get(0).equals("workspace"))
            modified.add(0, "workspace");
        URI uri = URI.createHierarchicalURI(modified.toArray(new String[modified.size()]), null, null);

        return (T) getLookup().resolve(uri);
    }


}
