/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see de.jutzig.jabylon.properties.PropertiesFactory
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
	String eNS_URI = "http://jutzig.de/jabylon/properties";

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
	PropertiesPackage eINSTANCE = de.jutzig.jabylon.properties.impl.PropertiesPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.PropertyFileImpl <em>Property File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.PropertyFileImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFile()
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
	 * The number of structural features of the '<em>Property File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.PropertyImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProperty()
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
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl <em>Property File Descriptor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDescriptor()
	 * @generated
	 */
	int PROPERTY_FILE_DESCRIPTOR = 2;

	/**
	 * The feature id for the '<em><b>Variant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR__VARIANT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR__NAME = 1;

	/**
	 * The feature id for the '<em><b>Bag</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR__BAG = 2;

	/**
	 * The feature id for the '<em><b>Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR__MASTER = 3;

	/**
	 * The feature id for the '<em><b>Property File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE = 4;

	/**
	 * The number of structural features of the '<em>Property File Descriptor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_DESCRIPTOR_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl <em>Property Bag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.PropertyBagImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyBag()
	 * @generated
	 */
	int PROPERTY_BAG = 3;

	/**
	 * The feature id for the '<em><b>Descriptors</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG__DESCRIPTORS = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG__PATH = 1;

	/**
	 * The feature id for the '<em><b>Master</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG__MASTER = 2;

	/**
	 * The feature id for the '<em><b>Full Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG__FULL_PATH = 3;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG__PROJECT = 4;

	/**
	 * The number of structural features of the '<em>Property Bag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_BAG_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.ProjectImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Workspace</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__WORKSPACE = 1;

	/**
	 * The feature id for the '<em><b>Versions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__VERSIONS = 2;

	/**
	 * The feature id for the '<em><b>Master</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__MASTER = 3;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl <em>Project Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.ProjectVersionImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProjectVersion()
	 * @generated
	 */
	int PROJECT_VERSION = 5;

	/**
	 * The feature id for the '<em><b>Property Bags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__PROPERTY_BAGS = 0;

	/**
	 * The feature id for the '<em><b>Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__BASE = 1;

	/**
	 * The feature id for the '<em><b>Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__TRANSLATED = 2;

	/**
	 * The feature id for the '<em><b>Total</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__TOTAL = 3;

	/**
	 * The feature id for the '<em><b>Project</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION__PROJECT = 4;

	/**
	 * The number of structural features of the '<em>Project Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_VERSION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.properties.impl.WorkspaceImpl <em>Workspace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.impl.WorkspaceImpl
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getWorkspace()
	 * @generated
	 */
	int WORKSPACE = 6;

	/**
	 * The feature id for the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKSPACE__ROOT = 0;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKSPACE__PROJECTS = 1;

	/**
	 * The number of structural features of the '<em>Workspace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKSPACE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>Locale</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Locale
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getLocale()
	 * @generated
	 */
	int LOCALE = 7;


	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getURI()
	 * @generated
	 */
	int URI = 8;


	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.PropertyFile <em>Property File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFile
	 * @generated
	 */
	EClass getPropertyFile();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.properties.PropertyFile#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFile#getProperties()
	 * @see #getPropertyFile()
	 * @generated
	 */
	EReference getPropertyFile_Properties();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see de.jutzig.jabylon.properties.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.Property#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see de.jutzig.jabylon.properties.Property#getKey()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Key();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see de.jutzig.jabylon.properties.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.Property#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see de.jutzig.jabylon.properties.Property#getComment()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Comment();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor <em>Property File Descriptor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File Descriptor</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor
	 * @generated
	 */
	EClass getPropertyFileDescriptor();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getVariant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variant</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getVariant()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
	EAttribute getPropertyFileDescriptor_Variant();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getName()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
	EAttribute getPropertyFileDescriptor_Name();

	/**
	 * Returns the meta object for the container reference '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag <em>Bag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Bag</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
	EReference getPropertyFileDescriptor_Bag();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster <em>Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Master</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
	EAttribute getPropertyFileDescriptor_Master();

	/**
	 * Returns the meta object for the reference '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile <em>Property File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property File</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile()
	 * @see #getPropertyFileDescriptor()
	 * @generated
	 */
	EReference getPropertyFileDescriptor_PropertyFile();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.PropertyBag <em>Property Bag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Bag</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag
	 * @generated
	 */
	EClass getPropertyBag();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.properties.PropertyBag#getDescriptors <em>Descriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Descriptors</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag#getDescriptors()
	 * @see #getPropertyBag()
	 * @generated
	 */
	EReference getPropertyBag_Descriptors();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.PropertyBag#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag#getPath()
	 * @see #getPropertyBag()
	 * @generated
	 */
	EAttribute getPropertyBag_Path();

	/**
	 * Returns the meta object for the reference '{@link de.jutzig.jabylon.properties.PropertyBag#getMaster <em>Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Master</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag#getMaster()
	 * @see #getPropertyBag()
	 * @generated
	 */
	EReference getPropertyBag_Master();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.PropertyBag#getFullPath <em>Full Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Full Path</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag#getFullPath()
	 * @see #getPropertyBag()
	 * @generated
	 */
	EAttribute getPropertyBag_FullPath();

	/**
	 * Returns the meta object for the container reference '{@link de.jutzig.jabylon.properties.PropertyBag#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see de.jutzig.jabylon.properties.PropertyBag#getProject()
	 * @see #getPropertyBag()
	 * @generated
	 */
	EReference getPropertyBag_Project();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see de.jutzig.jabylon.properties.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.Project#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.jutzig.jabylon.properties.Project#getName()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Name();

	/**
	 * Returns the meta object for the container reference '{@link de.jutzig.jabylon.properties.Project#getWorkspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Workspace</em>'.
	 * @see de.jutzig.jabylon.properties.Project#getWorkspace()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Workspace();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.properties.Project#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Versions</em>'.
	 * @see de.jutzig.jabylon.properties.Project#getVersions()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Versions();

	/**
	 * Returns the meta object for the containment reference '{@link de.jutzig.jabylon.properties.Project#getMaster <em>Master</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Master</em>'.
	 * @see de.jutzig.jabylon.properties.Project#getMaster()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Master();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.ProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Version</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion
	 * @generated
	 */
	EClass getProjectVersion();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.properties.ProjectVersion#getPropertyBags <em>Property Bags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Bags</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getPropertyBags()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EReference getProjectVersion_PropertyBags();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.ProjectVersion#getBase <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getBase()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EAttribute getProjectVersion_Base();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.ProjectVersion#getTranslated <em>Translated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Translated</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getTranslated()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EAttribute getProjectVersion_Translated();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.ProjectVersion#getTotal <em>Total</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getTotal()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EAttribute getProjectVersion_Total();

	/**
	 * Returns the meta object for the reference '{@link de.jutzig.jabylon.properties.ProjectVersion#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Project</em>'.
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getProject()
	 * @see #getProjectVersion()
	 * @generated
	 */
	EReference getProjectVersion_Project();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.properties.Workspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workspace</em>'.
	 * @see de.jutzig.jabylon.properties.Workspace
	 * @generated
	 */
	EClass getWorkspace();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.properties.Workspace#getRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root</em>'.
	 * @see de.jutzig.jabylon.properties.Workspace#getRoot()
	 * @see #getWorkspace()
	 * @generated
	 */
	EAttribute getWorkspace_Root();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.properties.Workspace#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Projects</em>'.
	 * @see de.jutzig.jabylon.properties.Workspace#getProjects()
	 * @see #getWorkspace()
	 * @generated
	 */
	EReference getWorkspace_Projects();

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
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.PropertyFileImpl <em>Property File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.PropertyFileImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFile()
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
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.PropertyImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProperty()
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
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl <em>Property File Descriptor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyFileDescriptor()
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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_FILE_DESCRIPTOR__NAME = eINSTANCE.getPropertyFileDescriptor_Name();

		/**
		 * The meta object literal for the '<em><b>Bag</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_FILE_DESCRIPTOR__BAG = eINSTANCE.getPropertyFileDescriptor_Bag();

		/**
		 * The meta object literal for the '<em><b>Master</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_FILE_DESCRIPTOR__MASTER = eINSTANCE.getPropertyFileDescriptor_Master();

		/**
		 * The meta object literal for the '<em><b>Property File</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE = eINSTANCE.getPropertyFileDescriptor_PropertyFile();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl <em>Property Bag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.PropertyBagImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getPropertyBag()
		 * @generated
		 */
		EClass PROPERTY_BAG = eINSTANCE.getPropertyBag();

		/**
		 * The meta object literal for the '<em><b>Descriptors</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BAG__DESCRIPTORS = eINSTANCE.getPropertyBag_Descriptors();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_BAG__PATH = eINSTANCE.getPropertyBag_Path();

		/**
		 * The meta object literal for the '<em><b>Master</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BAG__MASTER = eINSTANCE.getPropertyBag_Master();

		/**
		 * The meta object literal for the '<em><b>Full Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_BAG__FULL_PATH = eINSTANCE.getPropertyBag_FullPath();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_BAG__PROJECT = eINSTANCE.getPropertyBag_Project();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.ProjectImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__NAME = eINSTANCE.getProject_Name();

		/**
		 * The meta object literal for the '<em><b>Workspace</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__WORKSPACE = eINSTANCE.getProject_Workspace();

		/**
		 * The meta object literal for the '<em><b>Versions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__VERSIONS = eINSTANCE.getProject_Versions();

		/**
		 * The meta object literal for the '<em><b>Master</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__MASTER = eINSTANCE.getProject_Master();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl <em>Project Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.ProjectVersionImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getProjectVersion()
		 * @generated
		 */
		EClass PROJECT_VERSION = eINSTANCE.getProjectVersion();

		/**
		 * The meta object literal for the '<em><b>Property Bags</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_VERSION__PROPERTY_BAGS = eINSTANCE.getProjectVersion_PropertyBags();

		/**
		 * The meta object literal for the '<em><b>Base</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_VERSION__BASE = eINSTANCE.getProjectVersion_Base();

		/**
		 * The meta object literal for the '<em><b>Translated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_VERSION__TRANSLATED = eINSTANCE.getProjectVersion_Translated();

		/**
		 * The meta object literal for the '<em><b>Total</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_VERSION__TOTAL = eINSTANCE.getProjectVersion_Total();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_VERSION__PROJECT = eINSTANCE.getProjectVersion_Project();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.properties.impl.WorkspaceImpl <em>Workspace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.properties.impl.WorkspaceImpl
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getWorkspace()
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
		 * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKSPACE__PROJECTS = eINSTANCE.getWorkspace_Projects();

		/**
		 * The meta object literal for the '<em>Locale</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Locale
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getLocale()
		 * @generated
		 */
		EDataType LOCALE = eINSTANCE.getLocale();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see de.jutzig.jabylon.properties.impl.PropertiesPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

	}

} //PropertiesPackage
