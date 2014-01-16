/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesFactory
 * @model kind="package"
 * @generated
 */
public interface PropertiesPackage extends EPackage {
    /**
	 * The package name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNAME = "properties";

    /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_URI = "http://uri.jabylon.org/properties";

    /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_PREFIX = "prop";

    /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    PropertiesPackage eINSTANCE = org.jabylon.properties.impl.PropertiesPackageImpl.init();

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ResolvableImpl <em>Resolvable</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ResolvableImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getResolvable()
	 * @generated
	 */
    int RESOLVABLE = 7;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.PropertyFileImpl <em>Property File</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.PropertyFileImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFile()
	 * @generated
	 */
    int PROPERTY_FILE = 0;

    /**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE__PROPERTIES = 0;

    /**
	 * The feature id for the '<em><b>License Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE__LICENSE_HEADER = 1;

    /**
	 * The number of structural features of the '<em>Property File</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_FEATURE_COUNT = 2;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.PropertyImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProperty()
	 * @generated
	 */
    int PROPERTY = 1;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY__KEY = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY__VALUE = 1;

    /**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY__COMMENT = 2;

    /**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FEATURE_COUNT = 3;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOLVABLE__PERCENT_COMPLETE = 0;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOLVABLE__CHILDREN = 1;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOLVABLE__PARENT = 2;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOLVABLE__NAME = 3;

    /**
	 * The number of structural features of the '<em>Resolvable</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOLVABLE_FEATURE_COUNT = 4;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.PropertyFileDescriptorImpl <em>Property File Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.PropertyFileDescriptorImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDescriptor()
	 * @generated
	 */
    int PROPERTY_FILE_DESCRIPTOR = 2;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__NAME = RESOLVABLE__NAME;

    /**
	 * The feature id for the '<em><b>Variant</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__VARIANT = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__LOCATION = RESOLVABLE_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Master</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__MASTER = RESOLVABLE_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Project Locale</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE = RESOLVABLE_FEATURE_COUNT + 3;

    /**
	 * The feature id for the '<em><b>Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__KEYS = RESOLVABLE_FEATURE_COUNT + 4;

    /**
	 * The feature id for the '<em><b>Reviews</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__REVIEWS = RESOLVABLE_FEATURE_COUNT + 5;

    /**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED = RESOLVABLE_FEATURE_COUNT + 6;

    /**
	 * The feature id for the '<em><b>Last Modification</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION = RESOLVABLE_FEATURE_COUNT + 7;

    /**
	 * The feature id for the '<em><b>Derived Descriptors</b></em>' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS = RESOLVABLE_FEATURE_COUNT + 8;

    /**
	 * The number of structural features of the '<em>Property File Descriptor</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DESCRIPTOR_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 9;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ProjectImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProject()
	 * @generated
	 */
    int PROJECT = 3;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__NAME = RESOLVABLE__NAME;

    /**
	 * The feature id for the '<em><b>Repository URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__REPOSITORY_URI = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Property Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__PROPERTY_TYPE = RESOLVABLE_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Team Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__TEAM_PROVIDER = RESOLVABLE_FEATURE_COUNT + 2;

    /**
	 * The feature id for the '<em><b>Terminology</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT__TERMINOLOGY = RESOLVABLE_FEATURE_COUNT + 3;

    /**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 4;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ProjectVersionImpl <em>Project Version</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ProjectVersionImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProjectVersion()
	 * @generated
	 */
    int PROJECT_VERSION = 4;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION__NAME = RESOLVABLE__NAME;

    /**
	 * The feature id for the '<em><b>Template</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION__TEMPLATE = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__READ_ONLY = RESOLVABLE_FEATURE_COUNT + 1;

				/**
	 * The number of structural features of the '<em>Project Version</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_VERSION_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 2;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ProjectLocaleImpl <em>Project Locale</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ProjectLocaleImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProjectLocale()
	 * @generated
	 */
    int PROJECT_LOCALE = 5;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__NAME = RESOLVABLE__NAME;

    /**
	 * The feature id for the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__LOCALE = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The feature id for the '<em><b>Descriptors</b></em>' reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__DESCRIPTORS = RESOLVABLE_FEATURE_COUNT + 1;

    /**
	 * The feature id for the '<em><b>Property Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE__PROPERTY_COUNT = RESOLVABLE_FEATURE_COUNT + 2;

    /**
	 * The number of structural features of the '<em>Project Locale</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROJECT_LOCALE_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 3;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.WorkspaceImpl <em>Workspace</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.WorkspaceImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getWorkspace()
	 * @generated
	 */
    int WORKSPACE = 6;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE__NAME = RESOLVABLE__NAME;

    /**
	 * The feature id for the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE__ROOT = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The number of structural features of the '<em>Workspace</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WORKSPACE_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 1;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ScanConfigurationImpl <em>Scan Configuration</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ScanConfigurationImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getScanConfiguration()
	 * @generated
	 */
    int SCAN_CONFIGURATION = 8;

    /**
	 * The feature id for the '<em><b>Excludes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION__EXCLUDES = 0;

    /**
	 * The feature id for the '<em><b>Includes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION__INCLUDES = 1;

    /**
	 * The feature id for the '<em><b>Master Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION__MASTER_LOCALE = 2;

    /**
	 * The feature id for the '<em><b>Include</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION__INCLUDE = 3;

    /**
	 * The feature id for the '<em><b>Exclude</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION__EXCLUDE = 4;

    /**
	 * The number of structural features of the '<em>Scan Configuration</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int SCAN_CONFIGURATION_FEATURE_COUNT = 5;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ReviewImpl <em>Review</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ReviewImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getReview()
	 * @generated
	 */
    int REVIEW = 9;

    /**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__MESSAGE = 0;

    /**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__USER = 1;

    /**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__COMMENTS = 2;

    /**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__STATE = 3;

    /**
	 * The feature id for the '<em><b>Review Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__REVIEW_TYPE = 4;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__KEY = 5;

    /**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW__SEVERITY = 6;

    /**
	 * The feature id for the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__CREATED = 7;

				/**
	 * The number of structural features of the '<em>Review</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int REVIEW_FEATURE_COUNT = 8;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.CommentImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getComment()
	 * @generated
	 */
    int COMMENT = 10;

    /**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMENT__USER = 0;

    /**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMENT__MESSAGE = 1;

    /**
	 * The feature id for the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__CREATED = 2;

				/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int COMMENT_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.PropertyFileDiffImpl <em>Property File Diff</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.PropertyFileDiffImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDiff()
	 * @generated
	 */
    int PROPERTY_FILE_DIFF = 11;

    /**
	 * The feature id for the '<em><b>New Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DIFF__NEW_PATH = 0;

    /**
	 * The feature id for the '<em><b>Old Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DIFF__OLD_PATH = 1;

    /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DIFF__KIND = 2;

    /**
	 * The number of structural features of the '<em>Property File Diff</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PROPERTY_FILE_DIFF_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.impl.ResourceFolderImpl <em>Resource Folder</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.impl.ResourceFolderImpl
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getResourceFolder()
	 * @generated
	 */
    int RESOURCE_FOLDER = 12;

    /**
	 * The feature id for the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOURCE_FOLDER__PERCENT_COMPLETE = RESOLVABLE__PERCENT_COMPLETE;

    /**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOURCE_FOLDER__CHILDREN = RESOLVABLE__CHILDREN;

    /**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOURCE_FOLDER__PARENT = RESOLVABLE__PARENT;

    /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOURCE_FOLDER__NAME = RESOLVABLE__NAME;

    /**
	 * The number of structural features of the '<em>Resource Folder</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int RESOURCE_FOLDER_FEATURE_COUNT = RESOLVABLE_FEATURE_COUNT + 0;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.PropertyType <em>Property Type</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.PropertyType
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyType()
	 * @generated
	 */
    int PROPERTY_TYPE = 13;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.Severity <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Severity
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getSeverity()
	 * @generated
	 */
    int SEVERITY = 14;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.ReviewState <em>Review State</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.ReviewState
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getReviewState()
	 * @generated
	 */
    int REVIEW_STATE = 15;

    /**
	 * The meta object id for the '{@link org.jabylon.properties.DiffKind <em>Diff Kind</em>}' enum.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.DiffKind
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getDiffKind()
	 * @generated
	 */
    int DIFF_KIND = 16;

    /**
	 * The meta object id for the '<em>Locale</em>' data type.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see java.util.Locale
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getLocale()
	 * @generated
	 */
    int LOCALE = 17;


    /**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getURI()
	 * @generated
	 */
    int URI = 18;


    /**
	 * The meta object id for the '<em>Input Stream</em>' data type.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see java.io.InputStream
	 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getInputStream()
	 * @generated
	 */
    int INPUT_STREAM = 19;


    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.PropertyFile <em>Property File</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File</em>'.
	 * @see org.jabylon.properties.PropertyFile
	 * @generated
	 */
    EClass getPropertyFile();

    /**
	 * Returns the meta object for the containment reference list '{@link org.jabylon.properties.PropertyFile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.jabylon.properties.PropertyFile#getProperties()
	 * @see #getPropertyFile()
	 * @generated
	 */
    EReference getPropertyFile_Properties();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFile#getLicenseHeader <em>License Header</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>License Header</em>'.
	 * @see org.jabylon.properties.PropertyFile#getLicenseHeader()
	 * @see #getPropertyFile()
	 * @generated
	 */
    EAttribute getPropertyFile_LicenseHeader();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.jabylon.properties.Property
	 * @generated
	 */
    EClass getProperty();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Property#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.jabylon.properties.Property#getKey()
	 * @see #getProperty()
	 * @generated
	 */
    EAttribute getProperty_Key();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.jabylon.properties.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
    EAttribute getProperty_Value();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Property#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.jabylon.properties.Property#getComment()
	 * @see #getProperty()
	 * @generated
	 */
    EAttribute getProperty_Comment();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.PropertyFileDescriptor <em>Property File Descriptor</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File Descriptor</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor
	 * @generated
	 */
    EClass getPropertyFileDescriptor();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDescriptor#getVariant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variant</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getVariant()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EAttribute getPropertyFileDescriptor_Variant();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDescriptor#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getLocation()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EAttribute getPropertyFileDescriptor_Location();

    /**
	 * Returns the meta object for the reference '{@link org.jabylon.properties.PropertyFileDescriptor#getMaster <em>Master</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Master</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getMaster()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EReference getPropertyFileDescriptor_Master();

    /**
	 * Returns the meta object for the reference '{@link org.jabylon.properties.PropertyFileDescriptor#getProjectLocale <em>Project Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Project Locale</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getProjectLocale()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EReference getPropertyFileDescriptor_ProjectLocale();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDescriptor#getKeys <em>Keys</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keys</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getKeys()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EAttribute getPropertyFileDescriptor_Keys();

    /**
	 * Returns the meta object for the containment reference list '{@link org.jabylon.properties.PropertyFileDescriptor#getReviews <em>Reviews</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reviews</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getReviews()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EReference getPropertyFileDescriptor_Reviews();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDescriptor#getLastModified <em>Last Modified</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getLastModified()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EAttribute getPropertyFileDescriptor_LastModified();

    /**
	 * Returns the meta object for the reference '{@link org.jabylon.properties.PropertyFileDescriptor#getLastModification <em>Last Modification</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Last Modification</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getLastModification()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EReference getPropertyFileDescriptor_LastModification();

    /**
	 * Returns the meta object for the reference list '{@link org.jabylon.properties.PropertyFileDescriptor#getDerivedDescriptors <em>Derived Descriptors</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Derived Descriptors</em>'.
	 * @see org.jabylon.properties.PropertyFileDescriptor#getDerivedDescriptors()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
    EReference getPropertyFileDescriptor_DerivedDescriptors();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.jabylon.properties.Project
	 * @generated
	 */
    EClass getProject();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository URI</em>'.
	 * @see org.jabylon.properties.Project#getRepositoryURI()
	 * @see #getProject()
	 * @generated
	 */
    EAttribute getProject_RepositoryURI();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Project#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Type</em>'.
	 * @see org.jabylon.properties.Project#getPropertyType()
	 * @see #getProject()
	 * @generated
	 */
    EAttribute getProject_PropertyType();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Project#getTeamProvider <em>Team Provider</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Team Provider</em>'.
	 * @see org.jabylon.properties.Project#getTeamProvider()
	 * @see #getProject()
	 * @generated
	 */
    EAttribute getProject_TeamProvider();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Project#isTerminology <em>Terminology</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Terminology</em>'.
	 * @see org.jabylon.properties.Project#isTerminology()
	 * @see #getProject()
	 * @generated
	 */
    EAttribute getProject_Terminology();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.ProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Version</em>'.
	 * @see org.jabylon.properties.ProjectVersion
	 * @generated
	 */
    EClass getProjectVersion();

    /**
	 * Returns the meta object for the reference '{@link org.jabylon.properties.ProjectVersion#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Template</em>'.
	 * @see org.jabylon.properties.ProjectVersion#getTemplate()
	 * @see #getProjectVersion()
	 * @generated
	 */
    EReference getProjectVersion_Template();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ProjectVersion#isReadOnly <em>Read Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Read Only</em>'.
	 * @see org.jabylon.properties.ProjectVersion#isReadOnly()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EAttribute getProjectVersion_ReadOnly();

				/**
	 * Returns the meta object for class '{@link org.jabylon.properties.ProjectLocale <em>Project Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Locale</em>'.
	 * @see org.jabylon.properties.ProjectLocale
	 * @generated
	 */
    EClass getProjectLocale();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ProjectLocale#getLocale <em>Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locale</em>'.
	 * @see org.jabylon.properties.ProjectLocale#getLocale()
	 * @see #getProjectLocale()
	 * @generated
	 */
    EAttribute getProjectLocale_Locale();

    /**
	 * Returns the meta object for the reference list '{@link org.jabylon.properties.ProjectLocale#getDescriptors <em>Descriptors</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Descriptors</em>'.
	 * @see org.jabylon.properties.ProjectLocale#getDescriptors()
	 * @see #getProjectLocale()
	 * @generated
	 */
    EReference getProjectLocale_Descriptors();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ProjectLocale#getPropertyCount <em>Property Count</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Count</em>'.
	 * @see org.jabylon.properties.ProjectLocale#getPropertyCount()
	 * @see #getProjectLocale()
	 * @generated
	 */
    EAttribute getProjectLocale_PropertyCount();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.Workspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workspace</em>'.
	 * @see org.jabylon.properties.Workspace
	 * @generated
	 */
    EClass getWorkspace();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Workspace#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root</em>'.
	 * @see org.jabylon.properties.Workspace#getRoot()
	 * @see #getWorkspace()
	 * @generated
	 */
    EAttribute getWorkspace_Root();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.Resolvable <em>Resolvable</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resolvable</em>'.
	 * @see org.jabylon.properties.Resolvable
	 * @generated
	 */
    EClass getResolvable();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Resolvable#getPercentComplete <em>Percent Complete</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Percent Complete</em>'.
	 * @see org.jabylon.properties.Resolvable#getPercentComplete()
	 * @see #getResolvable()
	 * @generated
	 */
    EAttribute getResolvable_PercentComplete();

    /**
	 * Returns the meta object for the containment reference list '{@link org.jabylon.properties.Resolvable#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.jabylon.properties.Resolvable#getChildren()
	 * @see #getResolvable()
	 * @generated
	 */
    EReference getResolvable_Children();

    /**
	 * Returns the meta object for the container reference '{@link org.jabylon.properties.Resolvable#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.jabylon.properties.Resolvable#getParent()
	 * @see #getResolvable()
	 * @generated
	 */
    EReference getResolvable_Parent();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Resolvable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.jabylon.properties.Resolvable#getName()
	 * @see #getResolvable()
	 * @generated
	 */
    EAttribute getResolvable_Name();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.ScanConfiguration <em>Scan Configuration</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scan Configuration</em>'.
	 * @see org.jabylon.properties.ScanConfiguration
	 * @generated
	 */
    EClass getScanConfiguration();

    /**
	 * Returns the meta object for the attribute list '{@link org.jabylon.properties.ScanConfiguration#getExcludes <em>Excludes</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Excludes</em>'.
	 * @see org.jabylon.properties.ScanConfiguration#getExcludes()
	 * @see #getScanConfiguration()
	 * @generated
	 */
    EAttribute getScanConfiguration_Excludes();

    /**
	 * Returns the meta object for the attribute list '{@link org.jabylon.properties.ScanConfiguration#getIncludes <em>Includes</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Includes</em>'.
	 * @see org.jabylon.properties.ScanConfiguration#getIncludes()
	 * @see #getScanConfiguration()
	 * @generated
	 */
    EAttribute getScanConfiguration_Includes();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ScanConfiguration#getMasterLocale <em>Master Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Master Locale</em>'.
	 * @see org.jabylon.properties.ScanConfiguration#getMasterLocale()
	 * @see #getScanConfiguration()
	 * @generated
	 */
    EAttribute getScanConfiguration_MasterLocale();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ScanConfiguration#getInclude <em>Include</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Include</em>'.
	 * @see org.jabylon.properties.ScanConfiguration#getInclude()
	 * @see #getScanConfiguration()
	 * @generated
	 */
    EAttribute getScanConfiguration_Include();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.ScanConfiguration#getExclude <em>Exclude</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exclude</em>'.
	 * @see org.jabylon.properties.ScanConfiguration#getExclude()
	 * @see #getScanConfiguration()
	 * @generated
	 */
    EAttribute getScanConfiguration_Exclude();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.Review <em>Review</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review</em>'.
	 * @see org.jabylon.properties.Review
	 * @generated
	 */
    EClass getReview();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.jabylon.properties.Review#getMessage()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_Message();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see org.jabylon.properties.Review#getUser()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_User();

    /**
	 * Returns the meta object for the containment reference list '{@link org.jabylon.properties.Review#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see org.jabylon.properties.Review#getComments()
	 * @see #getReview()
	 * @generated
	 */
    EReference getReview_Comments();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.jabylon.properties.Review#getState()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_State();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getReviewType <em>Review Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Review Type</em>'.
	 * @see org.jabylon.properties.Review#getReviewType()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_ReviewType();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.jabylon.properties.Review#getKey()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_Key();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.jabylon.properties.Review#getSeverity()
	 * @see #getReview()
	 * @generated
	 */
    EAttribute getReview_Severity();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Review#getCreated <em>Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created</em>'.
	 * @see org.jabylon.properties.Review#getCreated()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_Created();

				/**
	 * Returns the meta object for class '{@link org.jabylon.properties.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.jabylon.properties.Comment
	 * @generated
	 */
    EClass getComment();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Comment#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see org.jabylon.properties.Comment#getUser()
	 * @see #getComment()
	 * @generated
	 */
    EAttribute getComment_User();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Comment#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.jabylon.properties.Comment#getMessage()
	 * @see #getComment()
	 * @generated
	 */
    EAttribute getComment_Message();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.Comment#getCreated <em>Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created</em>'.
	 * @see org.jabylon.properties.Comment#getCreated()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Created();

				/**
	 * Returns the meta object for class '{@link org.jabylon.properties.PropertyFileDiff <em>Property File Diff</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File Diff</em>'.
	 * @see org.jabylon.properties.PropertyFileDiff
	 * @generated
	 */
    EClass getPropertyFileDiff();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDiff#getNewPath <em>New Path</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Path</em>'.
	 * @see org.jabylon.properties.PropertyFileDiff#getNewPath()
	 * @see #getPropertyFileDiff()
	 * @generated
	 */
    EAttribute getPropertyFileDiff_NewPath();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDiff#getOldPath <em>Old Path</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Path</em>'.
	 * @see org.jabylon.properties.PropertyFileDiff#getOldPath()
	 * @see #getPropertyFileDiff()
	 * @generated
	 */
    EAttribute getPropertyFileDiff_OldPath();

    /**
	 * Returns the meta object for the attribute '{@link org.jabylon.properties.PropertyFileDiff#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.jabylon.properties.PropertyFileDiff#getKind()
	 * @see #getPropertyFileDiff()
	 * @generated
	 */
    EAttribute getPropertyFileDiff_Kind();

    /**
	 * Returns the meta object for class '{@link org.jabylon.properties.ResourceFolder <em>Resource Folder</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Folder</em>'.
	 * @see org.jabylon.properties.ResourceFolder
	 * @generated
	 */
    EClass getResourceFolder();

    /**
	 * Returns the meta object for enum '{@link org.jabylon.properties.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Type</em>'.
	 * @see org.jabylon.properties.PropertyType
	 * @generated
	 */
    EEnum getPropertyType();

    /**
	 * Returns the meta object for enum '{@link org.jabylon.properties.Severity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see org.jabylon.properties.Severity
	 * @generated
	 */
    EEnum getSeverity();

    /**
	 * Returns the meta object for enum '{@link org.jabylon.properties.ReviewState <em>Review State</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Review State</em>'.
	 * @see org.jabylon.properties.ReviewState
	 * @generated
	 */
    EEnum getReviewState();

    /**
	 * Returns the meta object for enum '{@link org.jabylon.properties.DiffKind <em>Diff Kind</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Diff Kind</em>'.
	 * @see org.jabylon.properties.DiffKind
	 * @generated
	 */
    EEnum getDiffKind();

    /**
	 * Returns the meta object for data type '{@link java.util.Locale <em>Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Locale</em>'.
	 * @see java.util.Locale
	 * @model instanceClass="java.util.Locale"
	 * @generated
	 */
    EDataType getLocale();

    /**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI"
	 * @generated
	 */
    EDataType getURI();

    /**
	 * Returns the meta object for data type '{@link java.io.InputStream <em>Input Stream</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Input Stream</em>'.
	 * @see java.io.InputStream
	 * @model instanceClass="java.io.InputStream"
	 * @generated
	 */
    EDataType getInputStream();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
    PropertiesFactory getPropertiesFactory();

    /**
	 * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
	 * @generated
	 */
    interface Literals {
        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.PropertyFileImpl <em>Property File</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.PropertyFileImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFile()
		 * @generated
		 */
        EClass PROPERTY_FILE = eINSTANCE.getPropertyFile();

        /**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE__PROPERTIES = eINSTANCE.getPropertyFile_Properties();

        /**
		 * The meta object literal for the '<em><b>License Header</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE__LICENSE_HEADER = eINSTANCE.getPropertyFile_LicenseHeader();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.PropertyImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProperty()
		 * @generated
		 */
        EClass PROPERTY = eINSTANCE.getProperty();

        /**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY__KEY = eINSTANCE.getProperty_Key();

        /**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

        /**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY__COMMENT = eINSTANCE.getProperty_Comment();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.PropertyFileDescriptorImpl <em>Property File Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.PropertyFileDescriptorImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDescriptor()
		 * @generated
		 */
        EClass PROPERTY_FILE_DESCRIPTOR = eINSTANCE.getPropertyFileDescriptor();

        /**
		 * The meta object literal for the '<em><b>Variant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DESCRIPTOR__VARIANT = eINSTANCE.getPropertyFileDescriptor_Variant();

        /**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DESCRIPTOR__LOCATION = eINSTANCE.getPropertyFileDescriptor_Location();

        /**
		 * The meta object literal for the '<em><b>Master</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE_DESCRIPTOR__MASTER = eINSTANCE.getPropertyFileDescriptor_Master();

        /**
		 * The meta object literal for the '<em><b>Project Locale</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE = eINSTANCE.getPropertyFileDescriptor_ProjectLocale();

        /**
		 * The meta object literal for the '<em><b>Keys</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DESCRIPTOR__KEYS = eINSTANCE.getPropertyFileDescriptor_Keys();

        /**
		 * The meta object literal for the '<em><b>Reviews</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE_DESCRIPTOR__REVIEWS = eINSTANCE.getPropertyFileDescriptor_Reviews();

        /**
		 * The meta object literal for the '<em><b>Last Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED = eINSTANCE.getPropertyFileDescriptor_LastModified();

        /**
		 * The meta object literal for the '<em><b>Last Modification</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION = eINSTANCE.getPropertyFileDescriptor_LastModification();

        /**
		 * The meta object literal for the '<em><b>Derived Descriptors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS = eINSTANCE.getPropertyFileDescriptor_DerivedDescriptors();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ProjectImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProject()
		 * @generated
		 */
        EClass PROJECT = eINSTANCE.getProject();

        /**
		 * The meta object literal for the '<em><b>Repository URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT__REPOSITORY_URI = eINSTANCE.getProject_RepositoryURI();

        /**
		 * The meta object literal for the '<em><b>Property Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT__PROPERTY_TYPE = eINSTANCE.getProject_PropertyType();

        /**
		 * The meta object literal for the '<em><b>Team Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT__TEAM_PROVIDER = eINSTANCE.getProject_TeamProvider();

        /**
		 * The meta object literal for the '<em><b>Terminology</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT__TERMINOLOGY = eINSTANCE.getProject_Terminology();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ProjectVersionImpl <em>Project Version</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ProjectVersionImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProjectVersion()
		 * @generated
		 */
        EClass PROJECT_VERSION = eINSTANCE.getProjectVersion();

        /**
		 * The meta object literal for the '<em><b>Template</b></em>' reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROJECT_VERSION__TEMPLATE = eINSTANCE.getProjectVersion_Template();

        /**
		 * The meta object literal for the '<em><b>Read Only</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_VERSION__READ_ONLY = eINSTANCE.getProjectVersion_ReadOnly();

								/**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ProjectLocaleImpl <em>Project Locale</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ProjectLocaleImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getProjectLocale()
		 * @generated
		 */
        EClass PROJECT_LOCALE = eINSTANCE.getProjectLocale();

        /**
		 * The meta object literal for the '<em><b>Locale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT_LOCALE__LOCALE = eINSTANCE.getProjectLocale_Locale();

        /**
		 * The meta object literal for the '<em><b>Descriptors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference PROJECT_LOCALE__DESCRIPTORS = eINSTANCE.getProjectLocale_Descriptors();

        /**
		 * The meta object literal for the '<em><b>Property Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROJECT_LOCALE__PROPERTY_COUNT = eINSTANCE.getProjectLocale_PropertyCount();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.WorkspaceImpl <em>Workspace</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.WorkspaceImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getWorkspace()
		 * @generated
		 */
        EClass WORKSPACE = eINSTANCE.getWorkspace();

        /**
		 * The meta object literal for the '<em><b>Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute WORKSPACE__ROOT = eINSTANCE.getWorkspace_Root();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ResolvableImpl <em>Resolvable</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ResolvableImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getResolvable()
		 * @generated
		 */
        EClass RESOLVABLE = eINSTANCE.getResolvable();

        /**
		 * The meta object literal for the '<em><b>Percent Complete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute RESOLVABLE__PERCENT_COMPLETE = eINSTANCE.getResolvable_PercentComplete();

        /**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference RESOLVABLE__CHILDREN = eINSTANCE.getResolvable_Children();

        /**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference RESOLVABLE__PARENT = eINSTANCE.getResolvable_Parent();

        /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute RESOLVABLE__NAME = eINSTANCE.getResolvable_Name();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ScanConfigurationImpl <em>Scan Configuration</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ScanConfigurationImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getScanConfiguration()
		 * @generated
		 */
        EClass SCAN_CONFIGURATION = eINSTANCE.getScanConfiguration();

        /**
		 * The meta object literal for the '<em><b>Excludes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute SCAN_CONFIGURATION__EXCLUDES = eINSTANCE.getScanConfiguration_Excludes();

        /**
		 * The meta object literal for the '<em><b>Includes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute SCAN_CONFIGURATION__INCLUDES = eINSTANCE.getScanConfiguration_Includes();

        /**
		 * The meta object literal for the '<em><b>Master Locale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute SCAN_CONFIGURATION__MASTER_LOCALE = eINSTANCE.getScanConfiguration_MasterLocale();

        /**
		 * The meta object literal for the '<em><b>Include</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute SCAN_CONFIGURATION__INCLUDE = eINSTANCE.getScanConfiguration_Include();

        /**
		 * The meta object literal for the '<em><b>Exclude</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute SCAN_CONFIGURATION__EXCLUDE = eINSTANCE.getScanConfiguration_Exclude();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ReviewImpl <em>Review</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ReviewImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getReview()
		 * @generated
		 */
        EClass REVIEW = eINSTANCE.getReview();

        /**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__MESSAGE = eINSTANCE.getReview_Message();

        /**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__USER = eINSTANCE.getReview_User();

        /**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EReference REVIEW__COMMENTS = eINSTANCE.getReview_Comments();

        /**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__STATE = eINSTANCE.getReview_State();

        /**
		 * The meta object literal for the '<em><b>Review Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__REVIEW_TYPE = eINSTANCE.getReview_ReviewType();

        /**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__KEY = eINSTANCE.getReview_Key();

        /**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute REVIEW__SEVERITY = eINSTANCE.getReview_Severity();

        /**
		 * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__CREATED = eINSTANCE.getReview_Created();

								/**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.CommentImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getComment()
		 * @generated
		 */
        EClass COMMENT = eINSTANCE.getComment();

        /**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute COMMENT__USER = eINSTANCE.getComment_User();

        /**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute COMMENT__MESSAGE = eINSTANCE.getComment_Message();

        /**
		 * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__CREATED = eINSTANCE.getComment_Created();

								/**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.PropertyFileDiffImpl <em>Property File Diff</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.PropertyFileDiffImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDiff()
		 * @generated
		 */
        EClass PROPERTY_FILE_DIFF = eINSTANCE.getPropertyFileDiff();

        /**
		 * The meta object literal for the '<em><b>New Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DIFF__NEW_PATH = eINSTANCE.getPropertyFileDiff_NewPath();

        /**
		 * The meta object literal for the '<em><b>Old Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DIFF__OLD_PATH = eINSTANCE.getPropertyFileDiff_OldPath();

        /**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute PROPERTY_FILE_DIFF__KIND = eINSTANCE.getPropertyFileDiff_Kind();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.impl.ResourceFolderImpl <em>Resource Folder</em>}' class.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.impl.ResourceFolderImpl
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getResourceFolder()
		 * @generated
		 */
        EClass RESOURCE_FOLDER = eINSTANCE.getResourceFolder();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.PropertyType <em>Property Type</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.PropertyType
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getPropertyType()
		 * @generated
		 */
        EEnum PROPERTY_TYPE = eINSTANCE.getPropertyType();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.Severity <em>Severity</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.Severity
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getSeverity()
		 * @generated
		 */
        EEnum SEVERITY = eINSTANCE.getSeverity();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.ReviewState <em>Review State</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.ReviewState
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getReviewState()
		 * @generated
		 */
        EEnum REVIEW_STATE = eINSTANCE.getReviewState();

        /**
		 * The meta object literal for the '{@link org.jabylon.properties.DiffKind <em>Diff Kind</em>}' enum.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.jabylon.properties.DiffKind
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getDiffKind()
		 * @generated
		 */
        EEnum DIFF_KIND = eINSTANCE.getDiffKind();

        /**
		 * The meta object literal for the '<em>Locale</em>' data type.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see java.util.Locale
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getLocale()
		 * @generated
		 */
        EDataType LOCALE = eINSTANCE.getLocale();

        /**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getURI()
		 * @generated
		 */
        EDataType URI = eINSTANCE.getURI();

        /**
		 * The meta object literal for the '<em>Input Stream</em>' data type.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @see java.io.InputStream
		 * @see org.jabylon.properties.impl.PropertiesPackageImpl#getInputStream()
		 * @generated
		 */
        EDataType INPUT_STREAM = eINSTANCE.getInputStream();

    }

} //PropertiesPackage
