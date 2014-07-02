/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.review.internal;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.common.review.TerminologyProvider;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component(enabled=true, immediate=true)
@Service(value=TerminologyProvider.class)
public class TerminologyProviderImpl extends CacheLoader<Locale, Map<String, Property>> implements TerminologyProvider, Supplier<ProjectVersion> {

    private LoadingCache<Locale, Map<String, Property>> terminologyCache;
    private CDONet4jSession session;
    private CDOView view;
    private Supplier<ProjectVersion> version;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(cardinality=ReferenceCardinality.MANDATORY_UNARY)
    private RepositoryConnector repositoryConnector;

    @Activate
    public void activate() {
        terminologyCache = CacheBuilder.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS).concurrencyLevel(2).build(this);
        version = Suppliers.memoize((Supplier<ProjectVersion>) this);
    }

    @Override
    public Map<String, Property> getTerminology(Locale locale) {
        try {
            return terminologyCache.get(locale);
        } catch (ExecutionException e) {
            logger.error("Failed to retrieve termininology from cache. Skipping check.",e);
            return Collections.emptyMap();
        }
    }

    @Override
    public Map<String, Property> load(Locale locale) throws Exception {
        if(version.get()==null){
            //recreate the supplier in case we have terminology later
            version  = Suppliers.memoize((Supplier<ProjectVersion>) this);
        }
        ProjectVersion projectVersion = version.get();
        if(projectVersion!=null){
            ProjectLocale projectLocale = projectVersion.getProjectLocale(locale);
            if(projectLocale!=null) {
                EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
                if(!descriptors.isEmpty()) {
                    PropertyFileDescriptor descriptor = descriptors.get(0);
                    return descriptor.loadProperties().asMap();
                }
            }
        }
        return Collections.emptyMap();
    }

    @Override
    public ProjectVersion get() {
        if(view==null)
            return null;
        CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
        Workspace workspace = (Workspace) resource.getContents().get(0);
        return workspace.getTerminology();
    }

    public void bindRepositoryConnector(RepositoryConnector connector) {
        session = connector.createSession();
        view = connector.openView(session);
    }

    public void unbindRepositoryConnector(RepositoryConnector connector) {
        view.close();
        view = null;
        session.close();
        session = null;
    }

    @Deactivate
    public void deactivate() {
        if(view!=null)
            view.close();
        if(session!=null)
            session.close();
    }



}
