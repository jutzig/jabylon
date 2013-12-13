/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.Workspace#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getWorkspace()
 * @model
 * @generated
 */
public interface Workspace extends Resolvable<Workspace, Project> {
    /**
	 * Returns the value of the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Root</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' attribute.
	 * @see #setRoot(URI)
	 * @see org.jabylon.properties.PropertiesPackage#getWorkspace_Root()
	 * @model dataType="org.jabylon.properties.URI"
	 *        annotation="http://www.eclipse.org/CDO/DBStore columnType='VARCHAR' columnLength='1024'"
	 * @generated
	 */
    URI getRoot();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Workspace#getRoot <em>Root</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' attribute.
	 * @see #getRoot()
	 * @generated
	 */
    void setRoot(URI value);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
    ProjectVersion getTerminology();

    Project getProject(String name);

} // Workspace
