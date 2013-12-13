/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.jabylon.users.UsersPackage
 * @generated
 */
public interface UsersFactory extends EFactory {
    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    UsersFactory eINSTANCE = org.jabylon.users.impl.UsersFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>User</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>User</em>'.
	 * @generated
	 */
    User createUser();

    /**
	 * Returns a new object of class '<em>Role</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Role</em>'.
	 * @generated
	 */
    Role createRole();

    /**
	 * Returns a new object of class '<em>Permission</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Permission</em>'.
	 * @generated
	 */
    Permission createPermission();

    /**
	 * Returns a new object of class '<em>Auth Type</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Auth Type</em>'.
	 * @generated
	 */
    AuthType createAuthType();

    /**
	 * Returns a new object of class '<em>User Management</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Management</em>'.
	 * @generated
	 */
    UserManagement createUserManagement();

    /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
    UsersPackage getUsersPackage();

} //UsersFactory
