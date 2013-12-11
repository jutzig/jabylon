/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository;

import org.osgi.framework.Bundle;

public enum BundleState {

    INSTALLED(Bundle.INSTALLED, "important"), RESOLVED(Bundle.RESOLVED, "warning"), STARTING(Bundle.STARTING,"info"), ACTIVE(Bundle.ACTIVE,"success"), UNINSTALLED(Bundle.UNINSTALLED,"inverse");

    private int state;
    private String labelClass;

    private BundleState(int state, String labelClass)
    {
        this.state = state;
        this.labelClass = labelClass;
    }

    public static BundleState fromState(int state) {
        for (BundleState bundleState : values()) {
            if(bundleState.state==state)
                return bundleState;
        }
        return null;
    }

    public String getLabelClass() {
        return labelClass;
    }
}
