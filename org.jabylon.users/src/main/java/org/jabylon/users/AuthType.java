/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Auth Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.users.AuthType#getName <em>Name</em>}</li>
 *   <li>{@link org.jabylon.users.AuthType#getAuthModule <em>Auth Module</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.users.UsersPackage#getAuthType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AuthType extends CDOObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.jabylon.users.UsersPackage#getAuthType_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.jabylon.users.AuthType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Auth Module</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Auth Module</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Auth Module</em>' attribute.
     * @see #setAuthModule(String)
     * @see org.jabylon.users.UsersPackage#getAuthType_AuthModule()
     * @model
     * @generated
     */
    String getAuthModule();

    /**
     * Sets the value of the '{@link org.jabylon.users.AuthType#getAuthModule <em>Auth Module</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Auth Module</em>' attribute.
     * @see #getAuthModule()
     * @generated
     */
    void setAuthModule(String value);

} // AuthType
