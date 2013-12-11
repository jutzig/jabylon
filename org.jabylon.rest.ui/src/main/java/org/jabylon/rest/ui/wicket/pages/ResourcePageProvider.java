/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.pages;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

import org.jabylon.common.resolver.URIResolver;
import org.jabylon.rest.ui.util.PageProvider;

@Component
@Service(PageProvider.class)
public class ResourcePageProvider implements PageProvider{

    @Property(value="/workspace")
    static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;

    /**
     * this reference isn't really needed, but it prevents the service from becoming active
     * before a RepositoryLookup Service is available
     */
    @Reference(policy=ReferencePolicy.STATIC,cardinality=ReferenceCardinality.MANDATORY_UNARY)
    private URIResolver lookup;

    @Override
    public Class<? extends Page> getPageClass() {
        return ResourcePage.class;
    }

    public void bindLookup(URIResolver lookup)
    {

    }

    public void unbindLookup(URIResolver lookup)
    {

    }


}
