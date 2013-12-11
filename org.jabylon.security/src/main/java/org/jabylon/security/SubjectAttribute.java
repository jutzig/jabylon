/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class SubjectAttribute {

    private EStructuralFeature feature;
    private Object value;

    public SubjectAttribute(EStructuralFeature feature, Object value) {
        super();
        this.feature = feature;
        this.value = value;
    };

    public Object getValue() {
        return value;
    }

    public EStructuralFeature getFeature() {
        return feature;
    }

    public void applyTo(EObject eobject) {
        eobject.eSet(getFeature(), getValue());
    }

}
