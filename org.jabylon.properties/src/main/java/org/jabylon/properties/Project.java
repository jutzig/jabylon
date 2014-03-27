/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}</li>
 *   <li>{@link org.jabylon.properties.Project#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link org.jabylon.properties.Project#getTeamProvider <em>Team Provider</em>}</li>
 *   <li>{@link org.jabylon.properties.Project#isTerminology <em>Terminology</em>}</li>
 *   <li>{@link org.jabylon.properties.Project#getAnnouncement <em>Announcement</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends Resolvable<Workspace, ProjectVersion> {
    /**
	 * Returns the value of the '<em><b>Repository URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository URI</em>' attribute.
	 * @see #setRepositoryURI(URI)
	 * @see org.jabylon.properties.PropertiesPackage#getProject_RepositoryURI()
	 * @model dataType="org.jabylon.properties.URI"
	 *        annotation="http://www.eclipse.org/CDO/DBStore columnType='VARCHAR' columnLength='1024'"
	 * @generated
	 */
    URI getRepositoryURI();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository URI</em>' attribute.
	 * @see #getRepositoryURI()
	 * @generated
	 */
    void setRepositoryURI(URI value);

    /**
	 * Returns the value of the '<em><b>Property Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' attribute.
	 * @see #setPropertyType(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProject_PropertyType()
	 * @model
	 * @generated
	 */
    String getPropertyType();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Project#getPropertyType <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' attribute.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(String value);

				/**
	 * Returns the value of the '<em><b>Team Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Team Provider</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Team Provider</em>' attribute.
	 * @see #setTeamProvider(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProject_TeamProvider()
	 * @model annotation="http://www.eclipse.org/CDO/DBStore columnType='VARCHAR' columnLength='32'"
	 * @generated
	 */
    String getTeamProvider();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Project#getTeamProvider <em>Team Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Team Provider</em>' attribute.
	 * @see #getTeamProvider()
	 * @generated
	 */
    void setTeamProvider(String value);

    /**
	 * Returns the value of the '<em><b>Terminology</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Terminology</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Terminology</em>' attribute.
	 * @see #setTerminology(boolean)
	 * @see org.jabylon.properties.PropertiesPackage#getProject_Terminology()
	 * @model
	 * @generated
	 */
    boolean isTerminology();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Project#isTerminology <em>Terminology</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terminology</em>' attribute.
	 * @see #isTerminology()
	 * @generated
	 */
    void setTerminology(boolean value);

    /**
	 * Returns the value of the '<em><b>Announcement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Announcement</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Announcement</em>' attribute.
	 * @see #setAnnouncement(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProject_Announcement()
	 * @model
	 * @generated
	 */
	String getAnnouncement();

				/**
	 * Sets the value of the '{@link org.jabylon.properties.Project#getAnnouncement <em>Announcement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Announcement</em>' attribute.
	 * @see #getAnnouncement()
	 * @generated
	 */
	void setAnnouncement(String value);

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    void fullScan(ScanConfiguration configuration);


} // Project
