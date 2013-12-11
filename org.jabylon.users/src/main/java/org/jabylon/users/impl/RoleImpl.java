/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.users.impl.RoleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.jabylon.users.impl.RoleImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.jabylon.users.impl.RoleImpl#getPermissions <em>Permissions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RoleImpl extends CDOObjectImpl implements Role {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RoleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsersPackage.Literals.ROLE;
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
    @Override
    public String getName() {
        return (String)eDynamicGet(UsersPackage.ROLE__NAME, UsersPackage.Literals.ROLE__NAME, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setName(String newName) {
        eDynamicSet(UsersPackage.ROLE__NAME, UsersPackage.Literals.ROLE__NAME, newName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Role getParent() {
        return (Role)eDynamicGet(UsersPackage.ROLE__PARENT, UsersPackage.Literals.ROLE__PARENT, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Role basicGetParent() {
        return (Role)eDynamicGet(UsersPackage.ROLE__PARENT, UsersPackage.Literals.ROLE__PARENT, false, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setParent(Role newParent) {
        eDynamicSet(UsersPackage.ROLE__PARENT, UsersPackage.Literals.ROLE__PARENT, newParent);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    @SuppressWarnings("unchecked")
    public EList<Permission> getPermissions() {
        return (EList<Permission>)eDynamicGet(UsersPackage.ROLE__PERMISSIONS, UsersPackage.Literals.ROLE__PERMISSIONS, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    @Override
    public EList<Permission> getAllPermissions() {
        EList<Permission> allPermissions = getPermissions();
        if(getParent()!=null) {
            allPermissions.addAll(getParent().getPermissions());
        }
        return allPermissions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case UsersPackage.ROLE__NAME:
                return getName();
            case UsersPackage.ROLE__PARENT:
                if (resolve) return getParent();
                return basicGetParent();
            case UsersPackage.ROLE__PERMISSIONS:
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
            case UsersPackage.ROLE__NAME:
                setName((String)newValue);
                return;
            case UsersPackage.ROLE__PARENT:
                setParent((Role)newValue);
                return;
            case UsersPackage.ROLE__PERMISSIONS:
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
            case UsersPackage.ROLE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case UsersPackage.ROLE__PARENT:
                setParent((Role)null);
                return;
            case UsersPackage.ROLE__PERMISSIONS:
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
            case UsersPackage.ROLE__NAME:
                return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
            case UsersPackage.ROLE__PARENT:
                return basicGetParent() != null;
            case UsersPackage.ROLE__PERMISSIONS:
                return !getPermissions().isEmpty();
        }
        return super.eIsSet(featureID);
    }


} //RoleImpl
