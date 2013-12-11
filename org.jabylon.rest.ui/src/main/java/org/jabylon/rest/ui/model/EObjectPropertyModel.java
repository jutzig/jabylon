/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EObjectPropertyModel<T, P  extends CDOObject> extends ChainingModel<T> {

    private static final long serialVersionUID = 1L;

    private String featureName;

    public EObjectPropertyModel(IModel<P> model, EStructuralFeature feature)
    {
        super(model);
        this.featureName = feature.getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject()
    {
        P object = getDomainObject();
        return (T)object.eGet(object.eClass().getEStructuralFeature(featureName));
    }

    @Override
    public void setObject(T object)
    {
        P domainObject = getDomainObject();
        domainObject.eSet(domainObject.eClass().getEStructuralFeature(featureName), object);
    }

    @SuppressWarnings("unchecked")
    private P getDomainObject() {
        return (P) getChainedModel().getObject();
    }



}
