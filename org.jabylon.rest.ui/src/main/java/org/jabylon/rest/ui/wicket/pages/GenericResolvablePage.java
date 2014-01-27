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

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.URI;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.IEObjectModel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GenericResolvablePage<T  extends CDOObject> extends GenericPage<T> {


    private static final long serialVersionUID = 1L;

    @Inject
    private URIResolver lookup;

    public GenericResolvablePage(PageParameters parameters) {
        super(parameters);
    }

    public void setModel(IEObjectModel<T> model) {
        super.setModel(model);
    }

    protected IEObjectModel<T> createModel(T object) {
        return new EObjectModel<T>(object);
    }

    public IEObjectModel<T> getModel() {
        return (IEObjectModel<T>) super.getModel();
    }


    @Override
    protected void preConstruct() {
        T modelObject = getModelObject();
        if(modelObject==null)
        {
            throw new AbortWithHttpErrorCodeException(404, "Path does not exist "+getPageParameters());
        }
    }

    @Override
    protected IModel<T> createModel(PageParameters params) {
    	T result = resolveModel(params);
    	if(result==null)
    		throw new AbortWithHttpErrorCodeException(404, "Path does not exist "+getPageParameters());
        return createModel(result);
    }

    protected T resolveModel(PageParameters params) {
        List<String> segments = new ArrayList<String>(params.getIndexedCount());
        for (StringValue value : params.getValues("segment")) {
            if (value.toString() != null && !value.toString().isEmpty())
                segments.add(value.toString());
        }

        for (int i = 0; i < params.getIndexedCount(); i++) {
            StringValue value = params.get(i);
            if (value.toString() != null && !value.toString().isEmpty())
                segments.add(value.toString());
        }
        T lookup = doLookup(segments);
        return (T)lookup;

    }

    @SuppressWarnings("unchecked")
    protected T doLookup(List<String> segments) {
        String path = getURIPath(segments);
        URI uri = URI.createURI(path,true);
        return (T) lookup.resolve(uri);
    }

    private String getURIPath(List<String> segments) {
        StringBuilder builder = new StringBuilder();
        for (String string : segments) {
            builder.append(URI.encodeSegment(string, true));
            builder.append("/");
        }
        if(builder.length()>0)
        	builder.setLength(builder.length()-1);
        else
        	builder.append("workspace");
        return builder.toString();
    }

    public URIResolver getLookup() {
        return lookup;
    }


}
