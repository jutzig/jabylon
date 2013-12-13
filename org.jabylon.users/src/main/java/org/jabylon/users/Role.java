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
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Role</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.users.Role#getName <em>Name</em>}</li>
 *   <li>{@link org.jabylon.users.Role#getParent <em>Parent</em>}</li>
 *   <li>{@link org.jabylon.users.Role#getPermissions <em>Permissions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.users.UsersPackage#getRole()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Role extends CDOObject {
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
	 * @see org.jabylon.users.UsersPackage#getRole_Name()
	 * @model annotation="http://www.eclipse.org/CDO/DBStore columnType='VARCHAR' columnLength='255'"
	 * @generated
	 */
    String getName();

    /**
	 * Sets the value of the '{@link org.jabylon.users.Role#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Role)
	 * @see org.jabylon.users.UsersPackage#getRole_Parent()
	 * @model
	 * @generated
	 */
    Role getParent();

    /**
	 * Sets the value of the '{@link org.jabylon.users.Role#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
    void setParent(Role value);

    /**
	 * Returns the value of the '<em><b>Permissions</b></em>' reference list.
	 * The list contents are of type {@link org.jabylon.users.Permission}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Permissions</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Permissions</em>' reference list.
	 * @see org.jabylon.users.UsersPackage#getRole_Permissions()
	 * @model
	 * @generated
	 */
    EList<Permission> getPermissions();

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
    EList<Permission> getAllPermissions();

} // Role
