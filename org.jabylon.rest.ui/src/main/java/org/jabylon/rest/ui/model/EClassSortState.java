/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import org.eclipse.emf.ecore.EStructuralFeature;

public class EClassSortState {
    private String featureName;
    private boolean descending;

    public EClassSortState(EStructuralFeature feature) {
        featureName = feature.getName();
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

}
