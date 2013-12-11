/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Stats</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectStats#getTranslated <em>Translated</em>}</li>
 *   <li>{@link org.jabylon.properties.ProjectStats#getTotal <em>Total</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getProjectStats()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProjectStats extends CDOObject {
    /**
     * Returns the value of the '<em><b>Translated</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Translated</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Translated</em>' attribute.
     * @see #setTranslated(int)
     * @see org.jabylon.properties.PropertiesPackage#getProjectStats_Translated()
     * @model
     * @generated
     */
    int getTranslated();

    /**
     * Sets the value of the '{@link org.jabylon.properties.ProjectStats#getTranslated <em>Translated</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Translated</em>' attribute.
     * @see #getTranslated()
     * @generated
     */
    void setTranslated(int value);

    /**
     * Returns the value of the '<em><b>Total</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Total</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Total</em>' attribute.
     * @see #setTotal(int)
     * @see org.jabylon.properties.PropertiesPackage#getProjectStats_Total()
     * @model
     * @generated
     */
    int getTotal();

    /**
     * Sets the value of the '{@link org.jabylon.properties.ProjectStats#getTotal <em>Total</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Total</em>' attribute.
     * @see #getTotal()
     * @generated
     */
    void setTotal(int value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    int getPercentComplete();

} // ProjectStats
