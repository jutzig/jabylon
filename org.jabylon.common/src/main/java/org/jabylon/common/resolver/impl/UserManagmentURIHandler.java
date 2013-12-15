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
package org.jabylon.common.resolver.impl;

import java.util.Collection;
import java.util.List;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.common.resolver.URIHandler;
import org.jabylon.users.UserManagement;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class UserManagmentURIHandler implements URIHandler {

    public static final String SECURITY_URI_PREFIX = "security";
    @Reference
    private RepositoryConnector repositoryConnector;
    private CDONet4jSession session;
    private UserManagement userManagment;

    @Activate
    public void activate() {
        session = repositoryConnector.createSession();
        CDOView view = session.openView();
        CDOResource userResource = view.getResource(ServerConstants.USERS_RESOURCE);
        userManagment = (UserManagement) userResource.getContents().get(0);
    }

    @Deactivate
    public void deactivate() {
        session.close();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jabylon.common.resolver.URIResolver#resolve(org.eclipse.emf
     * .common.util.URI)
     */
    @Override
    public Object resolve(URI uri) {
        if (uri == null || uri.isEmpty() || uri.segmentCount() == 0)
            return null;
        String firstSegment = uri.segment(0);
        if (SECURITY_URI_PREFIX.equals(firstSegment)) {
            List<String> list = uri.segmentsList().subList(1, uri.segmentCount());
            Object parent = userManagment;
            for (String segment : list) {
                parent = getChild(parent, URI.decode(segment));
                if (parent == null)
                    return null;
            }
            return parent;
        }
        return null;

    }

    private Object getChild(Object parent, String segment) {
        if (parent instanceof Collection) {
            @SuppressWarnings("rawtypes")
            Collection list = (Collection) parent;
            for (Object object : list) {
                if(matches(object,segment))
                    return object;
            }
        }
        else if (parent instanceof EObject) {
            EObject eobject = (EObject) parent;
            EStructuralFeature feature = eobject.eClass().getEStructuralFeature(segment);
            if(feature!=null)
                return eobject.eGet(feature);
        }
        return null;
    }

    private boolean matches(Object object, String segment) {
        if (object instanceof EObject) {
            EObject eobject = (EObject) object;
            EStructuralFeature feature = eobject.eClass().getEStructuralFeature("name");
            if(feature==null)
                return false;
            return segment.equals(eobject.eGet(feature));
        }
        return false;
    }

    public void bindRepositoryConnector(RepositoryConnector connector) {
        this.repositoryConnector = connector;
    }

    public void unbindRepositoryConnector(RepositoryConnector connector) {
        this.repositoryConnector = null;
    }

    @Override
    public boolean canHandle(URI uri) {
        if (uri == null || uri.isEmpty() || uri.segmentCount() == 0)
            return false;
        return SECURITY_URI_PREFIX.equals(uri.segment(0));
    }

}
