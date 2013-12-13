/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectVersion#getTemplate <em>Template</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getProjectVersion()
 * @model
 * @generated
 */
public interface ProjectVersion extends Resolvable<Project, ProjectLocale> {
    /**
	 * Returns the value of the '<em><b>Template</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Template</em>' reference.
	 * @see #setTemplate(ProjectLocale)
	 * @see org.jabylon.properties.PropertiesPackage#getProjectVersion_Template()
	 * @model
	 * @generated
	 */
    ProjectLocale getTemplate();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.ProjectVersion#getTemplate <em>Template</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template</em>' reference.
	 * @see #getTemplate()
	 * @generated
	 */
    void setTemplate(ProjectLocale value);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    void fullScan(ScanConfiguration configuration);


    void fullScan(ScanConfiguration configuration, IProgressMonitor monitor);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model localeDataType="org.jabylon.properties.Locale"
	 * @generated
	 */
    ProjectLocale getProjectLocale(Locale locale);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    void partialScan(ScanConfiguration configuration, PropertyFileDiff fileDiff);

} // ProjectVersion
