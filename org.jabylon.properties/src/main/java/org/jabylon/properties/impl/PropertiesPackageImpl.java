/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import java.io.InputStream;
import java.util.Locale;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.jabylon.properties.Comment;
import org.jabylon.properties.DiffKind;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyFileDiff;
import org.jabylon.properties.PropertyType;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.Review;
import org.jabylon.properties.ReviewState;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.Severity;
import org.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesPackageImpl extends EPackageImpl implements PropertiesPackage {
    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass propertyFileEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass propertyEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass propertyFileDescriptorEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass projectEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass projectVersionEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass projectLocaleEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass workspaceEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass resolvableEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass scanConfigurationEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass reviewEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass commentEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass propertyFileDiffEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass resourceFolderEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum propertyTypeEEnum = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum severityEEnum = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum reviewStateEEnum = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum diffKindEEnum = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EDataType localeEDataType = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EDataType uriEDataType = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EDataType inputStreamEDataType = null;

    /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.jabylon.properties.PropertiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
    private PropertiesPackageImpl() {
		super(eNS_URI, PropertiesFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>This method is used to initialize {@link PropertiesPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated NOT
     */
    public static PropertiesPackage init() {
        if (isInited) return (PropertiesPackage)EPackage.Registry.INSTANCE.getEPackage(PropertiesPackage.eNS_URI);

        // Obtain or create and register package
        PropertiesPackageImpl thePropertiesPackage = (PropertiesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PropertiesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PropertiesPackageImpl());

        isInited = true;

        // Create package meta-data objects
        thePropertiesPackage.createPackageContents();

        // Initialize created meta-data
        thePropertiesPackage.initializePackageContents();

        fixedDefaultValues(thePropertiesPackage);

        // Mark meta-data to indicate it can't be changed
        thePropertiesPackage.freeze();


        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(PropertiesPackage.eNS_URI, thePropertiesPackage);
        return thePropertiesPackage;
    }

    private static void fixedDefaultValues(PropertiesPackageImpl thePropertiesPackage) {
        //fix the workaround spaces in the defaults
        EStructuralFeature excludes = thePropertiesPackage.scanConfigurationEClass.getEStructuralFeature(SCAN_CONFIGURATION__EXCLUDE);
        if(excludes.getDefaultValueLiteral()!=null)
        {
            String exclude = excludes.getDefaultValueLiteral().replace(" ", "");
            exclude = exclude.replace("\\\\", "\\");
            excludes.setDefaultValueLiteral(exclude);

        }
        EStructuralFeature includes = thePropertiesPackage.scanConfigurationEClass.getEStructuralFeature(SCAN_CONFIGURATION__INCLUDE);
        if(includes.getDefaultValueLiteral()!=null)
        {
            String include = includes.getDefaultValueLiteral().replace(" ", "");
            include = include.replace("\\\\", "\\");
            includes.setDefaultValueLiteral(include);

        }
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getPropertyFile() {
		return propertyFileEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFile_Properties() {
		return (EReference)propertyFileEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFile_LicenseHeader() {
		return (EAttribute)propertyFileEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getProperty() {
		return propertyEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProperty_Key() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProperty_Value() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProperty_Comment() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getPropertyFileDescriptor() {
		return propertyFileDescriptorEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDescriptor_Variant() {
		return (EAttribute)propertyFileDescriptorEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDescriptor_Location() {
		return (EAttribute)propertyFileDescriptorEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFileDescriptor_Master() {
		return (EReference)propertyFileDescriptorEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFileDescriptor_ProjectLocale() {
		return (EReference)propertyFileDescriptorEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDescriptor_Keys() {
		return (EAttribute)propertyFileDescriptorEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFileDescriptor_Reviews() {
		return (EReference)propertyFileDescriptorEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDescriptor_LastModified() {
		return (EAttribute)propertyFileDescriptorEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFileDescriptor_LastModification() {
		return (EReference)propertyFileDescriptorEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getPropertyFileDescriptor_DerivedDescriptors() {
		return (EReference)propertyFileDescriptorEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getProject() {
		return projectEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProject_RepositoryURI() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProject_PropertyType() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProject_TeamProvider() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProject_Terminology() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getProjectVersion() {
		return projectVersionEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getProjectVersion_Template() {
		return (EReference)projectVersionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getProjectLocale() {
		return projectLocaleEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProjectLocale_Locale() {
		return (EAttribute)projectLocaleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getProjectLocale_Descriptors() {
		return (EReference)projectLocaleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getProjectLocale_PropertyCount() {
		return (EAttribute)projectLocaleEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getWorkspace() {
		return workspaceEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getWorkspace_Root() {
		return (EAttribute)workspaceEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getResolvable() {
		return resolvableEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getResolvable_PercentComplete() {
		return (EAttribute)resolvableEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getResolvable_Children() {
		return (EReference)resolvableEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getResolvable_Parent() {
		return (EReference)resolvableEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getResolvable_Name() {
		return (EAttribute)resolvableEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getScanConfiguration() {
		return scanConfigurationEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScanConfiguration_Excludes() {
		return (EAttribute)scanConfigurationEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScanConfiguration_Includes() {
		return (EAttribute)scanConfigurationEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScanConfiguration_MasterLocale() {
		return (EAttribute)scanConfigurationEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScanConfiguration_Include() {
		return (EAttribute)scanConfigurationEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getScanConfiguration_Exclude() {
		return (EAttribute)scanConfigurationEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getReview() {
		return reviewEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_Message() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_User() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getReview_Comments() {
		return (EReference)reviewEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_State() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_ReviewType() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_Key() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getReview_Severity() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getReview_Created() {
		return (EAttribute)reviewEClass.getEStructuralFeatures().get(7);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getComment() {
		return commentEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComment_User() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getComment_Message() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComment_Created() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(2);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getPropertyFileDiff() {
		return propertyFileDiffEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDiff_NewPath() {
		return (EAttribute)propertyFileDiffEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDiff_OldPath() {
		return (EAttribute)propertyFileDiffEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getPropertyFileDiff_Kind() {
		return (EAttribute)propertyFileDiffEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getResourceFolder() {
		return resourceFolderEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EEnum getPropertyType() {
		return propertyTypeEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EEnum getSeverity() {
		return severityEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EEnum getReviewState() {
		return reviewStateEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EEnum getDiffKind() {
		return diffKindEEnum;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EDataType getLocale() {
		return localeEDataType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EDataType getURI() {
		return uriEDataType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EDataType getInputStream() {
		return inputStreamEDataType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PropertiesFactory getPropertiesFactory() {
		return (PropertiesFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		propertyFileEClass = createEClass(PROPERTY_FILE);
		createEReference(propertyFileEClass, PROPERTY_FILE__PROPERTIES);
		createEAttribute(propertyFileEClass, PROPERTY_FILE__LICENSE_HEADER);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__KEY);
		createEAttribute(propertyEClass, PROPERTY__VALUE);
		createEAttribute(propertyEClass, PROPERTY__COMMENT);

		propertyFileDescriptorEClass = createEClass(PROPERTY_FILE_DESCRIPTOR);
		createEAttribute(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__VARIANT);
		createEAttribute(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__LOCATION);
		createEReference(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__MASTER);
		createEReference(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE);
		createEAttribute(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__KEYS);
		createEReference(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__REVIEWS);
		createEAttribute(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED);
		createEReference(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION);
		createEReference(propertyFileDescriptorEClass, PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS);

		projectEClass = createEClass(PROJECT);
		createEAttribute(projectEClass, PROJECT__REPOSITORY_URI);
		createEAttribute(projectEClass, PROJECT__PROPERTY_TYPE);
		createEAttribute(projectEClass, PROJECT__TEAM_PROVIDER);
		createEAttribute(projectEClass, PROJECT__TERMINOLOGY);

		projectVersionEClass = createEClass(PROJECT_VERSION);
		createEReference(projectVersionEClass, PROJECT_VERSION__TEMPLATE);

		projectLocaleEClass = createEClass(PROJECT_LOCALE);
		createEAttribute(projectLocaleEClass, PROJECT_LOCALE__LOCALE);
		createEReference(projectLocaleEClass, PROJECT_LOCALE__DESCRIPTORS);
		createEAttribute(projectLocaleEClass, PROJECT_LOCALE__PROPERTY_COUNT);

		workspaceEClass = createEClass(WORKSPACE);
		createEAttribute(workspaceEClass, WORKSPACE__ROOT);

		resolvableEClass = createEClass(RESOLVABLE);
		createEAttribute(resolvableEClass, RESOLVABLE__PERCENT_COMPLETE);
		createEReference(resolvableEClass, RESOLVABLE__CHILDREN);
		createEReference(resolvableEClass, RESOLVABLE__PARENT);
		createEAttribute(resolvableEClass, RESOLVABLE__NAME);

		scanConfigurationEClass = createEClass(SCAN_CONFIGURATION);
		createEAttribute(scanConfigurationEClass, SCAN_CONFIGURATION__EXCLUDES);
		createEAttribute(scanConfigurationEClass, SCAN_CONFIGURATION__INCLUDES);
		createEAttribute(scanConfigurationEClass, SCAN_CONFIGURATION__MASTER_LOCALE);
		createEAttribute(scanConfigurationEClass, SCAN_CONFIGURATION__INCLUDE);
		createEAttribute(scanConfigurationEClass, SCAN_CONFIGURATION__EXCLUDE);

		reviewEClass = createEClass(REVIEW);
		createEAttribute(reviewEClass, REVIEW__MESSAGE);
		createEAttribute(reviewEClass, REVIEW__USER);
		createEReference(reviewEClass, REVIEW__COMMENTS);
		createEAttribute(reviewEClass, REVIEW__STATE);
		createEAttribute(reviewEClass, REVIEW__REVIEW_TYPE);
		createEAttribute(reviewEClass, REVIEW__KEY);
		createEAttribute(reviewEClass, REVIEW__SEVERITY);
		createEAttribute(reviewEClass, REVIEW__CREATED);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__USER);
		createEAttribute(commentEClass, COMMENT__MESSAGE);
		createEAttribute(commentEClass, COMMENT__CREATED);

		propertyFileDiffEClass = createEClass(PROPERTY_FILE_DIFF);
		createEAttribute(propertyFileDiffEClass, PROPERTY_FILE_DIFF__NEW_PATH);
		createEAttribute(propertyFileDiffEClass, PROPERTY_FILE_DIFF__OLD_PATH);
		createEAttribute(propertyFileDiffEClass, PROPERTY_FILE_DIFF__KIND);

		resourceFolderEClass = createEClass(RESOURCE_FOLDER);

		// Create enums
		propertyTypeEEnum = createEEnum(PROPERTY_TYPE);
		severityEEnum = createEEnum(SEVERITY);
		reviewStateEEnum = createEEnum(REVIEW_STATE);
		diffKindEEnum = createEEnum(DIFF_KIND);

		// Create data types
		localeEDataType = createEDataType(LOCALE);
		uriEDataType = createEDataType(URI);
		inputStreamEDataType = createEDataType(INPUT_STREAM);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters
		ETypeParameter resolvableEClass_P = addETypeParameter(resolvableEClass, "P");
		ETypeParameter resolvableEClass_C = addETypeParameter(resolvableEClass, "C");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(this.getResolvable());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		resolvableEClass_P.getEBounds().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		resolvableEClass_C.getEBounds().add(g1);

		// Add supertypes to classes
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getResolvable());
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g2 = createEGenericType(this.getPropertyFileDescriptor());
		g1.getETypeArguments().add(g2);
		propertyFileDescriptorEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getWorkspace());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getProjectVersion());
		g1.getETypeArguments().add(g2);
		projectEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getProject());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getProjectLocale());
		g1.getETypeArguments().add(g2);
		projectVersionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getProjectVersion());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getResolvable());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		projectLocaleEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getWorkspace());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getProject());
		g1.getETypeArguments().add(g2);
		workspaceEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType(this.getResolvable());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g2 = createEGenericType(this.getResolvable());
		g1.getETypeArguments().add(g2);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		g3 = createEGenericType();
		g2.getETypeArguments().add(g3);
		resourceFolderEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes and features; add operations and parameters
		initEClass(propertyFileEClass, PropertyFile.class, "PropertyFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyFile_Properties(), this.getProperty(), null, "properties", null, 0, -1, PropertyFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFile_LicenseHeader(), ecorePackage.getEString(), "licenseHeader", null, 0, 1, PropertyFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(propertyFileEClass, this.getProperty(), "getProperty", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "key", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(propertyFileEClass, null, "asMap", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEMap());
		g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getProperty());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_Key(), ecorePackage.getEString(), "key", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyFileDescriptorEClass, PropertyFileDescriptor.class, "PropertyFileDescriptor", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyFileDescriptor_Variant(), this.getLocale(), "variant", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFileDescriptor_Location(), this.getURI(), "location", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyFileDescriptor_Master(), this.getPropertyFileDescriptor(), this.getPropertyFileDescriptor_DerivedDescriptors(), "master", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyFileDescriptor_ProjectLocale(), this.getProjectLocale(), this.getProjectLocale_Descriptors(), "projectLocale", null, 1, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFileDescriptor_Keys(), ecorePackage.getEInt(), "keys", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyFileDescriptor_Reviews(), this.getReview(), null, "reviews", null, 0, -1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFileDescriptor_LastModified(), ecorePackage.getELong(), "lastModified", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyFileDescriptor_LastModification(), this.getComment(), null, "lastModification", null, 0, 1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPropertyFileDescriptor_DerivedDescriptors(), this.getPropertyFileDescriptor(), this.getPropertyFileDescriptor_Master(), "derivedDescriptors", null, 0, -1, PropertyFileDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(propertyFileDescriptorEClass, ecorePackage.getEBoolean(), "isMaster", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(propertyFileDescriptorEClass, this.getPropertyFile(), "loadProperties", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(propertyFileDescriptorEClass, null, "computeLocation", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(propertyFileDescriptorEClass, this.getPropertyFile(), "loadProperties", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getInputStream(), "in", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProject_RepositoryURI(), this.getURI(), "repositoryURI", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProject_PropertyType(), this.getPropertyType(), "propertyType", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProject_TeamProvider(), ecorePackage.getEString(), "teamProvider", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProject_Terminology(), ecorePackage.getEBoolean(), "terminology", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(projectEClass, null, "fullScan", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getScanConfiguration(), "configuration", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectVersionEClass, ProjectVersion.class, "ProjectVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProjectVersion_Template(), this.getProjectLocale(), null, "template", null, 0, 1, ProjectVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(projectVersionEClass, null, "fullScan", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getScanConfiguration(), "configuration", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(projectVersionEClass, this.getProjectLocale(), "getProjectLocale", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLocale(), "locale", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(projectVersionEClass, null, "partialScan", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getScanConfiguration(), "configuration", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPropertyFileDiff(), "fileDiff", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(projectLocaleEClass, ProjectLocale.class, "ProjectLocale", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProjectLocale_Locale(), this.getLocale(), "locale", null, 0, 1, ProjectLocale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectLocale_Descriptors(), this.getPropertyFileDescriptor(), this.getPropertyFileDescriptor_ProjectLocale(), "descriptors", null, 0, -1, ProjectLocale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectLocale_PropertyCount(), ecorePackage.getEInt(), "propertyCount", null, 0, 1, ProjectLocale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(projectLocaleEClass, ecorePackage.getEBoolean(), "isMaster", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(workspaceEClass, Workspace.class, "Workspace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWorkspace_Root(), this.getURI(), "root", null, 0, 1, Workspace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(workspaceEClass, this.getProjectVersion(), "getTerminology", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(resolvableEClass, Resolvable.class, "Resolvable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResolvable_PercentComplete(), ecorePackage.getEInt(), "percentComplete", null, 0, 1, Resolvable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(resolvableEClass_C);
		initEReference(getResolvable_Children(), g1, this.getResolvable_Parent(), "children", null, 0, -1, Resolvable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(resolvableEClass_P);
		initEReference(getResolvable_Parent(), g1, this.getResolvable_Children(), "parent", null, 0, 1, Resolvable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResolvable_Name(), ecorePackage.getEString(), "name", null, 0, 1, Resolvable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(resolvableEClass, this.getURI(), "fullPath", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(resolvableEClass, this.getURI(), "relativePath", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(resolvableEClass, this.getURI(), "absolutPath", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(resolvableEClass, ecorePackage.getEInt(), "updatePercentComplete", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(resolvableEClass, null, "resolveChild", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getURI(), "path", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(this.getResolvable());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		addEOperation(resolvableEClass, this.getURI(), "absoluteFilePath", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(resolvableEClass, this.getURI(), "toURI", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(resolvableEClass, null, "getChild", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(resolvableEClass_C);
		initEOperation(op, g1);

		initEClass(scanConfigurationEClass, ScanConfiguration.class, "ScanConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScanConfiguration_Excludes(), ecorePackage.getEString(), "excludes", "** /.git\\n** /build.properties", 0, -1, ScanConfiguration.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getScanConfiguration_Includes(), ecorePackage.getEString(), "includes", null, 0, -1, ScanConfiguration.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getScanConfiguration_MasterLocale(), ecorePackage.getEString(), "masterLocale", null, 0, 1, ScanConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScanConfiguration_Include(), ecorePackage.getEString(), "include", "** / *.properties", 0, 1, ScanConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScanConfiguration_Exclude(), ecorePackage.getEString(), "exclude", "", 0, 1, ScanConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reviewEClass, Review.class, "Review", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReview_Message(), ecorePackage.getEString(), "message", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_User(), ecorePackage.getEString(), "user", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReview_Comments(), this.getComment(), null, "comments", null, 0, -1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_State(), this.getReviewState(), "state", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_ReviewType(), ecorePackage.getEString(), "reviewType", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_Key(), ecorePackage.getEString(), "key", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_Severity(), this.getSeverity(), "severity", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReview_Created(), ecorePackage.getELong(), "created", null, 0, 1, Review.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_User(), ecorePackage.getEString(), "user", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComment_Message(), ecorePackage.getEString(), "message", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComment_Created(), ecorePackage.getELong(), "created", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyFileDiffEClass, PropertyFileDiff.class, "PropertyFileDiff", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyFileDiff_NewPath(), ecorePackage.getEString(), "newPath", null, 0, 1, PropertyFileDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFileDiff_OldPath(), ecorePackage.getEString(), "oldPath", null, 0, 1, PropertyFileDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPropertyFileDiff_Kind(), this.getDiffKind(), "kind", null, 0, 1, PropertyFileDiff.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceFolderEClass, ResourceFolder.class, "ResourceFolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(resourceFolderEClass, this.getProjectLocale(), "getProjectLocale", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(propertyTypeEEnum, PropertyType.class, "PropertyType");
		addEEnumLiteral(propertyTypeEEnum, PropertyType.ENCODED_ISO);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.UNICODE);

		initEEnum(severityEEnum, Severity.class, "Severity");
		addEEnumLiteral(severityEEnum, Severity.INFO);
		addEEnumLiteral(severityEEnum, Severity.WARNING);
		addEEnumLiteral(severityEEnum, Severity.ERROR);

		initEEnum(reviewStateEEnum, ReviewState.class, "ReviewState");
		addEEnumLiteral(reviewStateEEnum, ReviewState.OPEN);
		addEEnumLiteral(reviewStateEEnum, ReviewState.RESOLVED);
		addEEnumLiteral(reviewStateEEnum, ReviewState.INVALID);
		addEEnumLiteral(reviewStateEEnum, ReviewState.REOPENED);

		initEEnum(diffKindEEnum, DiffKind.class, "DiffKind");
		addEEnumLiteral(diffKindEEnum, DiffKind.ADD);
		addEEnumLiteral(diffKindEEnum, DiffKind.REMOVE);
		addEEnumLiteral(diffKindEEnum, DiffKind.MODIFY);
		addEEnumLiteral(diffKindEEnum, DiffKind.COPY);
		addEEnumLiteral(diffKindEEnum, DiffKind.MOVE);

		// Initialize data types
		initEDataType(localeEDataType, Locale.class, "Locale", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class, "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(inputStreamEDataType, InputStream.class, "InputStream", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/CDO/DBStore
		createDBStoreAnnotations();
	}

				/**
	 * Initializes the annotations for <b>http://www.eclipse.org/CDO/DBStore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDBStoreAnnotations() {
		String source = "http://www.eclipse.org/CDO/DBStore";		
		addAnnotation
		  (getPropertyFileDescriptor_Variant(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "32"
		   });		
		addAnnotation
		  (getPropertyFileDescriptor_Location(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });		
		addAnnotation
		  (getProject_RepositoryURI(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });		
		addAnnotation
		  (getProject_TeamProvider(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "32"
		   });		
		addAnnotation
		  (getProjectLocale_Locale(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "32"
		   });		
		addAnnotation
		  (getWorkspace_Root(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });		
		addAnnotation
		  (getResolvable_Name(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getReview_Message(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });		
		addAnnotation
		  (getReview_User(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getReview_ReviewType(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "255"
		   });		
		addAnnotation
		  (getReview_Key(), 
		   source, 
		   new String[] {
			 "columnType", "VARCHAR",
			 "columnLength", "1024"
		   });
	}

} //PropertiesPackageImpl
