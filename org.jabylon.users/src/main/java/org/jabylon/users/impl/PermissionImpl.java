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
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.jabylon.users.Permission;
import org.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Permission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.users.impl.PermissionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.jabylon.users.impl.PermissionImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PermissionImpl extends CDOObjectImpl implements Permission {
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
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PermissionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return UsersPackage.Literals.PERMISSION;
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
    public String getName() {
        return (String)eDynamicGet(UsersPackage.PERMISSION__NAME, UsersPackage.Literals.PERMISSION__NAME, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        eDynamicSet(UsersPackage.PERMISSION__NAME, UsersPackage.Literals.PERMISSION__NAME, newName);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return (String)eDynamicGet(UsersPackage.PERMISSION__DESCRIPTION, UsersPackage.Literals.PERMISSION__DESCRIPTION, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        eDynamicSet(UsersPackage.PERMISSION__DESCRIPTION, UsersPackage.Literals.PERMISSION__DESCRIPTION, newDescription);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case UsersPackage.PERMISSION__NAME:
                return getName();
            case UsersPackage.PERMISSION__DESCRIPTION:
                return getDescription();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case UsersPackage.PERMISSION__NAME:
                setName((String)newValue);
                return;
            case UsersPackage.PERMISSION__DESCRIPTION:
                setDescription((String)newValue);
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
            case UsersPackage.PERMISSION__NAME:
                setName(NAME_EDEFAULT);
                return;
            case UsersPackage.PERMISSION__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
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
            case UsersPackage.PERMISSION__NAME:
                return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
            case UsersPackage.PERMISSION__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
        }
        return super.eIsSet(featureID);
    }

    @Override
    public String toString() {
        String name = getName();
        if(name!=null)
            return name;
        return super.toString();
    }

} //PermissionImpl
