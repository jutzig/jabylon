/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage
 * @generated
 */
public interface PropertiesFactory extends EFactory {
    /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    PropertiesFactory eINSTANCE = org.jabylon.properties.impl.PropertiesFactoryImpl.init();

    /**
	 * Returns a new object of class '<em>Property File</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property File</em>'.
	 * @generated
	 */
    PropertyFile createPropertyFile();

    /**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
    Property createProperty();

    /**
	 * Returns a new object of class '<em>Property File Descriptor</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property File Descriptor</em>'.
	 * @generated
	 */
    PropertyFileDescriptor createPropertyFileDescriptor();

    /**
	 * Returns a new object of class '<em>Project</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
    Project createProject();

    /**
	 * Returns a new object of class '<em>Project Version</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Version</em>'.
	 * @generated
	 */
    ProjectVersion createProjectVersion();

    /**
	 * Returns a new object of class '<em>Project Locale</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Locale</em>'.
	 * @generated
	 */
    ProjectLocale createProjectLocale();

    /**
	 * Returns a new object of class '<em>Workspace</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Workspace</em>'.
	 * @generated
	 */
    Workspace createWorkspace();

    /**
	 * Returns a new object of class '<em>Scan Configuration</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scan Configuration</em>'.
	 * @generated
	 */
    ScanConfiguration createScanConfiguration();

    /**
	 * Returns a new object of class '<em>Review</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Review</em>'.
	 * @generated
	 */
    Review createReview();

    /**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
    Comment createComment();

    /**
	 * Returns a new object of class '<em>Property File Diff</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property File Diff</em>'.
	 * @generated
	 */
    PropertyFileDiff createPropertyFileDiff();

    /**
	 * Returns a new object of class '<em>Resource Folder</em>'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Folder</em>'.
	 * @generated
	 */
    ResourceFolder createResourceFolder();

    /**
	 * Returns a new object of class '<em>Property Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Annotation</em>'.
	 * @generated
	 */
	PropertyAnnotation createPropertyAnnotation();

				/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
    PropertiesPackage getPropertiesPackage();

} //PropertiesFactory
