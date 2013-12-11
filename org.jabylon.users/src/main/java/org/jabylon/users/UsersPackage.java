/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.jabylon.users.UsersFactory
 * @model kind="package"
 * @generated
 */
public interface UsersPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "users";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://uri.jabylon.org/users";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "users";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    UsersPackage eINSTANCE = org.jabylon.users.impl.UsersPackageImpl.init();

    /**
     * The meta object id for the '{@link org.jabylon.users.impl.UserImpl <em>User</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.impl.UserImpl
     * @see org.jabylon.users.impl.UsersPackageImpl#getUser()
     * @generated
     */
    int USER = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__NAME = 0;

    /**
     * The feature id for the '<em><b>Password</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__PASSWORD = 1;

    /**
     * The feature id for the '<em><b>Roles</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__ROLES = 2;

    /**
     * The feature id for the '<em><b>Permissions</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__PERMISSIONS = 3;

    /**
     * The feature id for the '<em><b>Display Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__DISPLAY_NAME = 4;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__TYPE = 5;

    /**
     * The feature id for the '<em><b>Email</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER__EMAIL = 6;

    /**
     * The number of structural features of the '<em>User</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link org.jabylon.users.impl.RoleImpl <em>Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.impl.RoleImpl
     * @see org.jabylon.users.impl.UsersPackageImpl#getRole()
     * @generated
     */
    int ROLE = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE__NAME = 0;

    /**
     * The feature id for the '<em><b>Parent</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE__PARENT = 1;

    /**
     * The feature id for the '<em><b>Permissions</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE__PERMISSIONS = 2;

    /**
     * The number of structural features of the '<em>Role</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ROLE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link org.jabylon.users.impl.PermissionImpl <em>Permission</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.impl.PermissionImpl
     * @see org.jabylon.users.impl.UsersPackageImpl#getPermission()
     * @generated
     */
    int PERMISSION = 2;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMISSION__NAME = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMISSION__DESCRIPTION = 1;

    /**
     * The number of structural features of the '<em>Permission</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PERMISSION_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link org.jabylon.users.impl.AuthTypeImpl <em>Auth Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.impl.AuthTypeImpl
     * @see org.jabylon.users.impl.UsersPackageImpl#getAuthType()
     * @generated
     */
    int AUTH_TYPE = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTH_TYPE__NAME = 0;

    /**
     * The feature id for the '<em><b>Auth Module</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTH_TYPE__AUTH_MODULE = 1;

    /**
     * The number of structural features of the '<em>Auth Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AUTH_TYPE_FEATURE_COUNT = 2;


    /**
     * The meta object id for the '{@link org.jabylon.users.impl.UserManagementImpl <em>User Management</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.impl.UserManagementImpl
     * @see org.jabylon.users.impl.UsersPackageImpl#getUserManagement()
     * @generated
     */
    int USER_MANAGEMENT = 4;

    /**
     * The feature id for the '<em><b>Users</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_MANAGEMENT__USERS = 0;

    /**
     * The feature id for the '<em><b>Roles</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_MANAGEMENT__ROLES = 1;

    /**
     * The feature id for the '<em><b>Permissions</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_MANAGEMENT__PERMISSIONS = 2;

    /**
     * The number of structural features of the '<em>User Management</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int USER_MANAGEMENT_FEATURE_COUNT = 3;


    /**
     * Returns the meta object for class '{@link org.jabylon.users.User <em>User</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>User</em>'.
     * @see org.jabylon.users.User
     * @generated
     */
    EClass getUser();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.User#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.jabylon.users.User#getName()
     * @see #getUser()
     * @generated
     */
    EAttribute getUser_Name();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.User#getPassword <em>Password</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Password</em>'.
     * @see org.jabylon.users.User#getPassword()
     * @see #getUser()
     * @generated
     */
    EAttribute getUser_Password();

    /**
     * Returns the meta object for the reference list '{@link org.jabylon.users.User#getRoles <em>Roles</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Roles</em>'.
     * @see org.jabylon.users.User#getRoles()
     * @see #getUser()
     * @generated
     */
    EReference getUser_Roles();

    /**
     * Returns the meta object for the reference list '{@link org.jabylon.users.User#getPermissions <em>Permissions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Permissions</em>'.
     * @see org.jabylon.users.User#getPermissions()
     * @see #getUser()
     * @generated
     */
    EReference getUser_Permissions();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.User#getDisplayName <em>Display Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Name</em>'.
     * @see org.jabylon.users.User#getDisplayName()
     * @see #getUser()
     * @generated
     */
    EAttribute getUser_DisplayName();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.User#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.jabylon.users.User#getType()
     * @see #getUser()
     * @generated
     */
    EAttribute getUser_Type();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.User#getEmail <em>Email</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Email</em>'.
     * @see org.jabylon.users.User#getEmail()
     * @see #getUser()
     * @generated
     */
    EAttribute getUser_Email();

    /**
     * Returns the meta object for class '{@link org.jabylon.users.Role <em>Role</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Role</em>'.
     * @see org.jabylon.users.Role
     * @generated
     */
    EClass getRole();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.Role#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.jabylon.users.Role#getName()
     * @see #getRole()
     * @generated
     */
    EAttribute getRole_Name();

    /**
     * Returns the meta object for the reference '{@link org.jabylon.users.Role#getParent <em>Parent</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Parent</em>'.
     * @see org.jabylon.users.Role#getParent()
     * @see #getRole()
     * @generated
     */
    EReference getRole_Parent();

    /**
     * Returns the meta object for the reference list '{@link org.jabylon.users.Role#getPermissions <em>Permissions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Permissions</em>'.
     * @see org.jabylon.users.Role#getPermissions()
     * @see #getRole()
     * @generated
     */
    EReference getRole_Permissions();

    /**
     * Returns the meta object for class '{@link org.jabylon.users.Permission <em>Permission</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Permission</em>'.
     * @see org.jabylon.users.Permission
     * @generated
     */
    EClass getPermission();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.Permission#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.jabylon.users.Permission#getName()
     * @see #getPermission()
     * @generated
     */
    EAttribute getPermission_Name();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.Permission#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.jabylon.users.Permission#getDescription()
     * @see #getPermission()
     * @generated
     */
    EAttribute getPermission_Description();

    /**
     * Returns the meta object for class '{@link org.jabylon.users.AuthType <em>Auth Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Auth Type</em>'.
     * @see org.jabylon.users.AuthType
     * @generated
     */
    EClass getAuthType();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.AuthType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.jabylon.users.AuthType#getName()
     * @see #getAuthType()
     * @generated
     */
    EAttribute getAuthType_Name();

    /**
     * Returns the meta object for the attribute '{@link org.jabylon.users.AuthType#getAuthModule <em>Auth Module</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Auth Module</em>'.
     * @see org.jabylon.users.AuthType#getAuthModule()
     * @see #getAuthType()
     * @generated
     */
    EAttribute getAuthType_AuthModule();

    /**
     * Returns the meta object for class '{@link org.jabylon.users.UserManagement <em>User Management</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>User Management</em>'.
     * @see org.jabylon.users.UserManagement
     * @generated
     */
    EClass getUserManagement();

    /**
     * Returns the meta object for the containment reference list '{@link org.jabylon.users.UserManagement#getUsers <em>Users</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Users</em>'.
     * @see org.jabylon.users.UserManagement#getUsers()
     * @see #getUserManagement()
     * @generated
     */
    EReference getUserManagement_Users();

    /**
     * Returns the meta object for the containment reference list '{@link org.jabylon.users.UserManagement#getRoles <em>Roles</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Roles</em>'.
     * @see org.jabylon.users.UserManagement#getRoles()
     * @see #getUserManagement()
     * @generated
     */
    EReference getUserManagement_Roles();

    /**
     * Returns the meta object for the containment reference list '{@link org.jabylon.users.UserManagement#getPermissions <em>Permissions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Permissions</em>'.
     * @see org.jabylon.users.UserManagement#getPermissions()
     * @see #getUserManagement()
     * @generated
     */
    EReference getUserManagement_Permissions();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    UsersFactory getUsersFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.jabylon.users.impl.UserImpl <em>User</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.jabylon.users.impl.UserImpl
         * @see org.jabylon.users.impl.UsersPackageImpl#getUser()
         * @generated
         */
        EClass USER = eINSTANCE.getUser();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute USER__NAME = eINSTANCE.getUser_Name();

        /**
         * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute USER__PASSWORD = eINSTANCE.getUser_Password();

        /**
         * The meta object literal for the '<em><b>Roles</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER__ROLES = eINSTANCE.getUser_Roles();

        /**
         * The meta object literal for the '<em><b>Permissions</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER__PERMISSIONS = eINSTANCE.getUser_Permissions();

        /**
         * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute USER__DISPLAY_NAME = eINSTANCE.getUser_DisplayName();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute USER__TYPE = eINSTANCE.getUser_Type();

        /**
         * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute USER__EMAIL = eINSTANCE.getUser_Email();

        /**
         * The meta object literal for the '{@link org.jabylon.users.impl.RoleImpl <em>Role</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.jabylon.users.impl.RoleImpl
         * @see org.jabylon.users.impl.UsersPackageImpl#getRole()
         * @generated
         */
        EClass ROLE = eINSTANCE.getRole();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ROLE__NAME = eINSTANCE.getRole_Name();

        /**
         * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROLE__PARENT = eINSTANCE.getRole_Parent();

        /**
         * The meta object literal for the '<em><b>Permissions</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ROLE__PERMISSIONS = eINSTANCE.getRole_Permissions();

        /**
         * The meta object literal for the '{@link org.jabylon.users.impl.PermissionImpl <em>Permission</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.jabylon.users.impl.PermissionImpl
         * @see org.jabylon.users.impl.UsersPackageImpl#getPermission()
         * @generated
         */
        EClass PERMISSION = eINSTANCE.getPermission();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PERMISSION__NAME = eINSTANCE.getPermission_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PERMISSION__DESCRIPTION = eINSTANCE.getPermission_Description();

        /**
         * The meta object literal for the '{@link org.jabylon.users.impl.AuthTypeImpl <em>Auth Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.jabylon.users.impl.AuthTypeImpl
         * @see org.jabylon.users.impl.UsersPackageImpl#getAuthType()
         * @generated
         */
        EClass AUTH_TYPE = eINSTANCE.getAuthType();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AUTH_TYPE__NAME = eINSTANCE.getAuthType_Name();

        /**
         * The meta object literal for the '<em><b>Auth Module</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute AUTH_TYPE__AUTH_MODULE = eINSTANCE.getAuthType_AuthModule();

        /**
         * The meta object literal for the '{@link org.jabylon.users.impl.UserManagementImpl <em>User Management</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.jabylon.users.impl.UserManagementImpl
         * @see org.jabylon.users.impl.UsersPackageImpl#getUserManagement()
         * @generated
         */
        EClass USER_MANAGEMENT = eINSTANCE.getUserManagement();

        /**
         * The meta object literal for the '<em><b>Users</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_MANAGEMENT__USERS = eINSTANCE.getUserManagement_Users();

        /**
         * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_MANAGEMENT__ROLES = eINSTANCE.getUserManagement_Roles();

        /**
         * The meta object literal for the '<em><b>Permissions</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference USER_MANAGEMENT__PERMISSIONS = eINSTANCE.getUserManagement_Permissions();

    }

} //UsersPackage
