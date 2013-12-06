/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.users;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Management</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.users.UserManagement#getUsers <em>Users</em>}</li>
 *   <li>{@link org.jabylon.users.UserManagement#getRoles <em>Roles</em>}</li>
 *   <li>{@link org.jabylon.users.UserManagement#getPermissions <em>Permissions</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.users.UsersPackage#getUserManagement()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface UserManagement extends CDOObject {
    /**
     * Returns the value of the '<em><b>Users</b></em>' containment reference list.
     * The list contents are of type {@link org.jabylon.users.User}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Users</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Users</em>' containment reference list.
     * @see org.jabylon.users.UsersPackage#getUserManagement_Users()
     * @model containment="true"
     * @generated
     */
    EList<User> getUsers();

    /**
     * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
     * The list contents are of type {@link org.jabylon.users.Role}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Roles</em>' containment reference list.
     * @see org.jabylon.users.UsersPackage#getUserManagement_Roles()
     * @model containment="true"
     * @generated
     */
    EList<Role> getRoles();

    /**
     * Returns the value of the '<em><b>Permissions</b></em>' containment reference list.
     * The list contents are of type {@link org.jabylon.users.Permission}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Permissions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Permissions</em>' containment reference list.
     * @see org.jabylon.users.UsersPackage#getUserManagement_Permissions()
     * @model containment="true"
     * @generated
     */
    EList<Permission> getPermissions();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model nameRequired="true"
     * @generated
     */
    User findUserByName(String name);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model nameRequired="true"
     * @generated
     */
    Permission findPermissionByName(String name);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model nameRequired="true"
     * @generated
     */
    Role findRoleByName(String name);

} // UserManagement
