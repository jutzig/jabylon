/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.jabylon.users.*;
import org.jabylon.users.AuthType;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UsersFactoryImpl extends EFactoryImpl implements UsersFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static UsersFactory init() {
		try {
			UsersFactory theUsersFactory = (UsersFactory)EPackage.Registry.INSTANCE.getEFactory(UsersPackage.eNS_URI);
			if (theUsersFactory != null) {
				return theUsersFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UsersFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UsersFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case UsersPackage.USER: return (EObject)createUser();
			case UsersPackage.ROLE: return (EObject)createRole();
			case UsersPackage.PERMISSION: return (EObject)createPermission();
			case UsersPackage.AUTH_TYPE: return (EObject)createAuthType();
			case UsersPackage.USER_MANAGEMENT: return (EObject)createUserManagement();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public User createUser() {
		UserImpl user = new UserImpl();
		return user;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Permission createPermission() {
		PermissionImpl permission = new PermissionImpl();
		return permission;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public AuthType createAuthType() {
		AuthTypeImpl authType = new AuthTypeImpl();
		return authType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UserManagement createUserManagement() {
		UserManagementImpl userManagement = new UserManagementImpl();
		return userManagement;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UsersPackage getUsersPackage() {
		return (UsersPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static UsersPackage getPackage() {
		return UsersPackage.eINSTANCE;
	}

} //UsersFactoryImpl
