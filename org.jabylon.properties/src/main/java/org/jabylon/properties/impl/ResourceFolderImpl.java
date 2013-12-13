/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 */
package org.jabylon.properties.impl;

import org.eclipse.emf.ecore.EClass;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ResourceFolderImpl extends ResolvableImpl<Resolvable<?, ?>, Resolvable<?, ?>> implements ResourceFolder {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ResourceFolderImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PropertiesPackage.Literals.RESOURCE_FOLDER;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public ProjectLocale getProjectLocale() {
        @SuppressWarnings("rawtypes")
        Resolvable parent = this;
        while(parent!=null)
        {
            if (parent instanceof ProjectLocale) {
                ProjectLocale locale = (ProjectLocale) parent;
                return locale;

            }
            parent = parent.getParent();
        }
        return null;
    }


} //ResourceFolderImpl
