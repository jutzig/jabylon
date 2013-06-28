/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.users.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Management</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserManagementImpl#getUsers <em>Users</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserManagementImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.UserManagementImpl#getPermissions <em>Permissions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserManagementImpl extends CDOObjectImpl implements UserManagement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UserManagementImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsersPackage.Literals.USER_MANAGEMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected int eStaticFeatureCount() {
        return 0;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public EList<User> getUsers() {
        return (EList<User>)eDynamicGet(UsersPackage.USER_MANAGEMENT__USERS, UsersPackage.Literals.USER_MANAGEMENT__USERS, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public EList<Role> getRoles() {
        return (EList<Role>)eDynamicGet(UsersPackage.USER_MANAGEMENT__ROLES, UsersPackage.Literals.USER_MANAGEMENT__ROLES, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public EList<Permission> getPermissions() {
        return (EList<Permission>)eDynamicGet(UsersPackage.USER_MANAGEMENT__PERMISSIONS, UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * May return null!
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public User findUserByName(String name) {
        for (User user : getUsers()) {
            if(user.getName().equals(name))
                return user;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * May return null!
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Permission findPermissionByName(String name) {
        for (Permission perm : getPermissions()) {
            if(perm.getName().equals(name))
                return perm;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * May return null!
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Role findRoleByName(String name) {
        for (Role role : getRoles()) {
            if(role.getName().equals(name))
                return role;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case UsersPackage.USER_MANAGEMENT__USERS:
                return ((InternalEList<?>)getUsers()).basicRemove(otherEnd, msgs);
            case UsersPackage.USER_MANAGEMENT__ROLES:
                return ((InternalEList<?>)getRoles()).basicRemove(otherEnd, msgs);
            case UsersPackage.USER_MANAGEMENT__PERMISSIONS:
                return ((InternalEList<?>)getPermissions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case UsersPackage.USER_MANAGEMENT__USERS:
                return getUsers();
            case UsersPackage.USER_MANAGEMENT__ROLES:
                return getRoles();
            case UsersPackage.USER_MANAGEMENT__PERMISSIONS:
                return getPermissions();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case UsersPackage.USER_MANAGEMENT__USERS:
                getUsers().clear();
                getUsers().addAll((Collection<? extends User>)newValue);
                return;
            case UsersPackage.USER_MANAGEMENT__ROLES:
                getRoles().clear();
                getRoles().addAll((Collection<? extends Role>)newValue);
                return;
            case UsersPackage.USER_MANAGEMENT__PERMISSIONS:
                getPermissions().clear();
                getPermissions().addAll((Collection<? extends Permission>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case UsersPackage.USER_MANAGEMENT__USERS:
                getUsers().clear();
                return;
            case UsersPackage.USER_MANAGEMENT__ROLES:
                getRoles().clear();
                return;
            case UsersPackage.USER_MANAGEMENT__PERMISSIONS:
                getPermissions().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case UsersPackage.USER_MANAGEMENT__USERS:
                return !getUsers().isEmpty();
            case UsersPackage.USER_MANAGEMENT__ROLES:
                return !getRoles().isEmpty();
            case UsersPackage.USER_MANAGEMENT__PERMISSIONS:
                return !getPermissions().isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //UserManagementImpl
