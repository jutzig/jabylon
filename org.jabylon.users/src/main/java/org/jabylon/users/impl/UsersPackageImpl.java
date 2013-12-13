/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.jabylon.users.AuthType;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UsersPackageImpl extends EPackageImpl implements UsersPackage {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass userEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass roleEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass permissionEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass authTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass userManagementEClass = null;

    /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.jabylon.users.UsersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
    private UsersPackageImpl() {
		super(eNS_URI, UsersFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link UsersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static UsersPackage init() {
		if (isInited) return (UsersPackage)EPackage.Registry.INSTANCE.getEPackage(UsersPackage.eNS_URI);

		// Obtain or create and register package
		UsersPackageImpl theUsersPackage = (UsersPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UsersPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UsersPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theUsersPackage.createPackageContents();

		// Initialize created meta-data
		theUsersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUsersPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UsersPackage.eNS_URI, theUsersPackage);
		return theUsersPackage;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUser() {
		return userEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUser_Name() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUser_Password() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUser_Roles() {
		return (EReference)userEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUser_Permissions() {
		return (EReference)userEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUser_DisplayName() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUser_Type() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getUser_Email() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Token() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(7);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getRole() {
		return roleEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getRole_Name() {
		return (EAttribute)roleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getRole_Parent() {
		return (EReference)roleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getRole_Permissions() {
		return (EReference)roleEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getPermission() {
		return permissionEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPermission_Name() {
		return (EAttribute)permissionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPermission_Description() {
		return (EAttribute)permissionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getAuthType() {
		return authTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAuthType_Name() {
		return (EAttribute)authTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getAuthType_AuthModule() {
		return (EAttribute)authTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getUserManagement() {
		return userManagementEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUserManagement_Users() {
		return (EReference)userManagementEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUserManagement_Roles() {
		return (EReference)userManagementEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getUserManagement_Permissions() {
		return (EReference)userManagementEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public UsersFactory getUsersFactory() {
		return (UsersFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		userEClass = createEClass(USER);
		createEAttribute(userEClass, USER__NAME);
		createEAttribute(userEClass, USER__PASSWORD);
		createEReference(userEClass, USER__ROLES);
		createEReference(userEClass, USER__PERMISSIONS);
		createEAttribute(userEClass, USER__DISPLAY_NAME);
		createEAttribute(userEClass, USER__TYPE);
		createEAttribute(userEClass, USER__EMAIL);
		createEAttribute(userEClass, USER__TOKEN);

		roleEClass = createEClass(ROLE);
		createEAttribute(roleEClass, ROLE__NAME);
		createEReference(roleEClass, ROLE__PARENT);
		createEReference(roleEClass, ROLE__PERMISSIONS);

		permissionEClass = createEClass(PERMISSION);
		createEAttribute(permissionEClass, PERMISSION__NAME);
		createEAttribute(permissionEClass, PERMISSION__DESCRIPTION);

		authTypeEClass = createEClass(AUTH_TYPE);
		createEAttribute(authTypeEClass, AUTH_TYPE__NAME);
		createEAttribute(authTypeEClass, AUTH_TYPE__AUTH_MODULE);

		userManagementEClass = createEClass(USER_MANAGEMENT);
		createEReference(userManagementEClass, USER_MANAGEMENT__USERS);
		createEReference(userManagementEClass, USER_MANAGEMENT__ROLES);
		createEReference(userManagementEClass, USER_MANAGEMENT__PERMISSIONS);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(userEClass, User.class, "User", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUser_Name(), ecorePackage.getEString(), "name", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Password(), ecorePackage.getEString(), "password", null, 1, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_Roles(), this.getRole(), null, "roles", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_Permissions(), this.getPermission(), null, "permissions", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_DisplayName(), ecorePackage.getEString(), "displayName", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Type(), ecorePackage.getEString(), "type", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Email(), ecorePackage.getEString(), "email", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Token(), ecorePackage.getEString(), "token", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(userEClass, this.getPermission(), "getAllPermissions", 0, -1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(userEClass, ecorePackage.getEBoolean(), "hasPermission", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "permission", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(roleEClass, Role.class, "Role", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRole_Name(), ecorePackage.getEString(), "name", null, 0, 1, Role.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRole_Parent(), this.getRole(), null, "parent", null, 0, 1, Role.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRole_Permissions(), this.getPermission(), null, "permissions", null, 0, -1, Role.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(roleEClass, this.getPermission(), "getAllPermissions", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(permissionEClass, Permission.class, "Permission", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPermission_Name(), ecorePackage.getEString(), "name", null, 0, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPermission_Description(), ecorePackage.getEString(), "description", null, 0, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authTypeEClass, AuthType.class, "AuthType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAuthType_Name(), ecorePackage.getEString(), "name", null, 0, 1, AuthType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthType_AuthModule(), ecorePackage.getEString(), "authModule", null, 0, 1, AuthType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userManagementEClass, UserManagement.class, "UserManagement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUserManagement_Users(), this.getUser(), null, "users", null, 0, -1, UserManagement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserManagement_Roles(), this.getRole(), null, "roles", null, 0, -1, UserManagement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserManagement_Permissions(), this.getPermission(), null, "permissions", null, 0, -1, UserManagement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(userManagementEClass, this.getUser(), "findUserByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(userManagementEClass, this.getPermission(), "findPermissionByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(userManagementEClass, this.getRole(), "findRoleByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(userManagementEClass, this.getUser(), "findUserByToken", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "token", 1, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/CDO/DBStore
		createDBStoreAnnotations();
	}

				/**
	 * Initializes the annotations for <b>http://www.eclipse.org/CDO/DBStore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDBStoreAnnotations() {
		String source = "http://www.eclipse.org/CDO/DBStore";		
		addAnnotation
		  (getUser_Name(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getUser_Password(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getUser_DisplayName(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getUser_Type(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getUser_Email(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getUser_Token(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "32"
		   });		
		addAnnotation
		  (getRole_Name(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getPermission_Name(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getPermission_Description(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });		
		addAnnotation
		  (getAuthType_Name(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getAuthType_AuthModule(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });
	}

} //UsersPackageImpl
