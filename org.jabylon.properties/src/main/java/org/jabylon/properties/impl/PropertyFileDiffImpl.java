/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 */
package org.jabylon.properties.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.jabylon.properties.DiffKind;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFileDiff;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property File Diff</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.PropertyFileDiffImpl#getNewPath <em>New Path</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.PropertyFileDiffImpl#getOldPath <em>Old Path</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.PropertyFileDiffImpl#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyFileDiffImpl extends CDOObjectImpl implements PropertyFileDiff {
    /**
	 * The default value of the '{@link #getNewPath() <em>New Path</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNewPath()
	 * @generated
	 * @ordered
	 */
    protected static final String NEW_PATH_EDEFAULT = null;

    /**
	 * The default value of the '{@link #getOldPath() <em>Old Path</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOldPath()
	 * @generated
	 * @ordered
	 */
    protected static final String OLD_PATH_EDEFAULT = null;

    /**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
    protected static final DiffKind KIND_EDEFAULT = DiffKind.ADD;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected PropertyFileDiffImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROPERTY_FILE_DIFF;
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
    public String getNewPath() {
		return (String)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__NEW_PATH, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setNewPath(String newNewPath) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__NEW_PATH, newNewPath);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getOldPath() {
		return (String)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__OLD_PATH, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOldPath(String newOldPath) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__OLD_PATH, newOldPath);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public DiffKind getKind() {
		return (DiffKind)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DIFF__KIND, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__KIND, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setKind(DiffKind newKind) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DIFF__KIND, PropertiesPackage.Literals.PROPERTY_FILE_DIFF__KIND, newKind);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH:
				return getNewPath();
			case PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH:
				return getOldPath();
			case PropertiesPackage.PROPERTY_FILE_DIFF__KIND:
				return getKind();
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
			case PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH:
				setNewPath((String)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH:
				setOldPath((String)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DIFF__KIND:
				setKind((DiffKind)newValue);
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
			case PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH:
				setNewPath(NEW_PATH_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH:
				setOldPath(OLD_PATH_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DIFF__KIND:
				setKind(KIND_EDEFAULT);
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
			case PropertiesPackage.PROPERTY_FILE_DIFF__NEW_PATH:
				return NEW_PATH_EDEFAULT == null ? getNewPath() != null : !NEW_PATH_EDEFAULT.equals(getNewPath());
			case PropertiesPackage.PROPERTY_FILE_DIFF__OLD_PATH:
				return OLD_PATH_EDEFAULT == null ? getOldPath() != null : !OLD_PATH_EDEFAULT.equals(getOldPath());
			case PropertiesPackage.PROPERTY_FILE_DIFF__KIND:
				return getKind() != KIND_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //PropertyFileDiffImpl
