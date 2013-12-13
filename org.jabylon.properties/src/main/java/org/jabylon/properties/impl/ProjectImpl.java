/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyType;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.ProjectImpl#getRepositoryURI <em>Repository URI</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ProjectImpl#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ProjectImpl#getTeamProvider <em>Team Provider</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ProjectImpl#isTerminology <em>Terminology</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectImpl extends ResolvableImpl<Workspace, ProjectVersion> implements Project {
    /**
	 * The default value of the '{@link #getRepositoryURI() <em>Repository URI</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getRepositoryURI()
	 * @generated
	 * @ordered
	 */
    protected static final URI REPOSITORY_URI_EDEFAULT = null;


    /**
	 * The default value of the '{@link #getPropertyType() <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
    protected static final PropertyType PROPERTY_TYPE_EDEFAULT = PropertyType.ENCODED_ISO;


    /**
	 * The default value of the '{@link #getTeamProvider() <em>Team Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTeamProvider()
	 * @generated
	 * @ordered
	 */
    protected static final String TEAM_PROVIDER_EDEFAULT = null;


    /**
	 * The default value of the '{@link #isTerminology() <em>Terminology</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isTerminology()
	 * @generated
	 * @ordered
	 */
    protected static final boolean TERMINOLOGY_EDEFAULT = false;


    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ProjectImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public URI getRepositoryURI() {
		return (URI)eDynamicGet(PropertiesPackage.PROJECT__REPOSITORY_URI, PropertiesPackage.Literals.PROJECT__REPOSITORY_URI, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setRepositoryURI(URI newRepositoryURI) {
		eDynamicSet(PropertiesPackage.PROJECT__REPOSITORY_URI, PropertiesPackage.Literals.PROJECT__REPOSITORY_URI, newRepositoryURI);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PropertyType getPropertyType() {
		return (PropertyType)eDynamicGet(PropertiesPackage.PROJECT__PROPERTY_TYPE, PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPropertyType(PropertyType newPropertyType) {
		eDynamicSet(PropertiesPackage.PROJECT__PROPERTY_TYPE, PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE, newPropertyType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getTeamProvider() {
		return (String)eDynamicGet(PropertiesPackage.PROJECT__TEAM_PROVIDER, PropertiesPackage.Literals.PROJECT__TEAM_PROVIDER, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTeamProvider(String newTeamProvider) {
		eDynamicSet(PropertiesPackage.PROJECT__TEAM_PROVIDER, PropertiesPackage.Literals.PROJECT__TEAM_PROVIDER, newTeamProvider);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean isTerminology() {
		return (Boolean)eDynamicGet(PropertiesPackage.PROJECT__TERMINOLOGY, PropertiesPackage.Literals.PROJECT__TERMINOLOGY, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTerminology(boolean newTerminology) {
		eDynamicSet(PropertiesPackage.PROJECT__TERMINOLOGY, PropertiesPackage.Literals.PROJECT__TERMINOLOGY, newTerminology);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public void fullScan(ScanConfiguration configuration) {
        for (ProjectVersion version : getChildren()) {
            version.fullScan(configuration, null);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public URI getBase() {
        if(getParent()==null)
            return null;
        if(getParent().getRoot()==null)
            return null;
        return getParent().getRoot().appendSegment(getName());
    }




    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT__REPOSITORY_URI:
				return getRepositoryURI();
			case PropertiesPackage.PROJECT__PROPERTY_TYPE:
				return getPropertyType();
			case PropertiesPackage.PROJECT__TEAM_PROVIDER:
				return getTeamProvider();
			case PropertiesPackage.PROJECT__TERMINOLOGY:
				return isTerminology();
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
			case PropertiesPackage.PROJECT__REPOSITORY_URI:
				setRepositoryURI((URI)newValue);
				return;
			case PropertiesPackage.PROJECT__PROPERTY_TYPE:
				setPropertyType((PropertyType)newValue);
				return;
			case PropertiesPackage.PROJECT__TEAM_PROVIDER:
				setTeamProvider((String)newValue);
				return;
			case PropertiesPackage.PROJECT__TERMINOLOGY:
				setTerminology((Boolean)newValue);
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
			case PropertiesPackage.PROJECT__REPOSITORY_URI:
				setRepositoryURI(REPOSITORY_URI_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT__PROPERTY_TYPE:
				setPropertyType(PROPERTY_TYPE_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT__TEAM_PROVIDER:
				setTeamProvider(TEAM_PROVIDER_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT__TERMINOLOGY:
				setTerminology(TERMINOLOGY_EDEFAULT);
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
			case PropertiesPackage.PROJECT__REPOSITORY_URI:
				return REPOSITORY_URI_EDEFAULT == null ? getRepositoryURI() != null : !REPOSITORY_URI_EDEFAULT.equals(getRepositoryURI());
			case PropertiesPackage.PROJECT__PROPERTY_TYPE:
				return getPropertyType() != PROPERTY_TYPE_EDEFAULT;
			case PropertiesPackage.PROJECT__TEAM_PROVIDER:
				return TEAM_PROVIDER_EDEFAULT == null ? getTeamProvider() != null : !TEAM_PROVIDER_EDEFAULT.equals(getTeamProvider());
			case PropertiesPackage.PROJECT__TERMINOLOGY:
				return isTerminology() != TERMINOLOGY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}




} //ProjectImpl
