/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository.ui;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

import org.jabylon.rest.ui.util.PageProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component(enabled=true,immediate=true)
@Service
public class UpdatecenterPageProvider implements PageProvider {


    @Property(value="/settings/system")
    static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;

    /**
     * not really needed, just make sure the page doesn't get
     * mounted before the query service is available
     */
    @Reference(cardinality=ReferenceCardinality.MANDATORY_UNARY)
    private RepositoryAdmin service;

    /* (non-Javadoc)
     * @see org.jabylon.rest.ui.util.PageProvider#getPageClass()
     */
    @Override
    public Class<? extends Page> getPageClass() {
        return UpdatecenterPage.class;
    }

    public void bindQueryService(RepositoryAdmin service) {
        this.service = service;
    }

    public void unbindQueryService(RepositoryAdmin service) {
        this.service = service;
    }

}
