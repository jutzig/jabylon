/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.WorkspaceImpl#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkspaceImpl extends ResolvableImpl<Workspace, Project> implements Workspace {
    /**
     * The default value of the '{@link #getRoot() <em>Root</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRoot()
     * @generated
     * @ordered
     */
    protected static final URI ROOT_EDEFAULT = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkspaceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PropertiesPackage.Literals.WORKSPACE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public URI getRoot() {
        return (URI)eDynamicGet(PropertiesPackage.WORKSPACE__ROOT, PropertiesPackage.Literals.WORKSPACE__ROOT, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRoot(URI newRoot) {
        eDynamicSet(PropertiesPackage.WORKSPACE__ROOT, PropertiesPackage.Literals.WORKSPACE__ROOT, newRoot);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public ProjectVersion getTerminology() {
        EList<Project> projects = getChildren();
        for (Project project : projects) {
            if(project.isTerminology())
                return project.getChildren().get(0);
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PropertiesPackage.WORKSPACE__ROOT:
                return getRoot();
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
            case PropertiesPackage.WORKSPACE__ROOT:
                setRoot((URI)newValue);
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
            case PropertiesPackage.WORKSPACE__ROOT:
                setRoot(ROOT_EDEFAULT);
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
            case PropertiesPackage.WORKSPACE__ROOT:
                return ROOT_EDEFAULT == null ? getRoot() != null : !ROOT_EDEFAULT.equals(getRoot());
        }
        return super.eIsSet(featureID);
    }

    @Override
    public Project getProject(String name) {
        if(name==null)
            return null;
        EList<Project> projects = getChildren();
        for (Project project : projects) {
            if(name.equals(project.getName()))
                return project;
        }
        return null;
    }

    @Override
    public URI relativePath() {
        return null;
    }

    @Override
    public int internalUpdatePercentComplete() {
        return 100;
    }

    @Override
    public String getName() {
        return "workspace";
    }

} //WorkspaceImpl
