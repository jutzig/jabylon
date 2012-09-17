/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import de.jutzig.jabylon.properties.*;
import java.io.InputStream;
import java.util.Locale;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.jutzig.jabylon.properties.Comment;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.ReviewState;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.Severity;
import de.jutzig.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesFactoryImpl extends EFactoryImpl implements PropertiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertiesFactory init() {
		try {
			PropertiesFactory thePropertiesFactory = (PropertiesFactory)EPackage.Registry.INSTANCE.getEFactory("http://jutzig.de/jabylon/properties"); 
			if (thePropertiesFactory != null) {
				return thePropertiesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PropertiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PropertiesPackage.PROPERTY_FILE: return (EObject)createPropertyFile();
			case PropertiesPackage.PROPERTY: return (EObject)createProperty();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR: return (EObject)createPropertyFileDescriptor();
			case PropertiesPackage.PROJECT: return (EObject)createProject();
			case PropertiesPackage.PROJECT_VERSION: return (EObject)createProjectVersion();
			case PropertiesPackage.PROJECT_LOCALE: return (EObject)createProjectLocale();
			case PropertiesPackage.WORKSPACE: return (EObject)createWorkspace();
			case PropertiesPackage.SCAN_CONFIGURATION: return (EObject)createScanConfiguration();
			case PropertiesPackage.REVIEW: return (EObject)createReview();
			case PropertiesPackage.COMMENT: return (EObject)createComment();
			case PropertiesPackage.PROPERTY_FILE_DIFF: return (EObject)createPropertyFileDiff();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case PropertiesPackage.PROPERTY_TYPE:
				return createPropertyTypeFromString(eDataType, initialValue);
			case PropertiesPackage.SEVERITY:
				return createSeverityFromString(eDataType, initialValue);
			case PropertiesPackage.REVIEW_STATE:
				return createReviewStateFromString(eDataType, initialValue);
			case PropertiesPackage.DIFF_KIND:
				return createDiffKindFromString(eDataType, initialValue);
			case PropertiesPackage.LOCALE:
				return createLocaleFromString(eDataType, initialValue);
			case PropertiesPackage.URI:
				return createURIFromString(eDataType, initialValue);
			case PropertiesPackage.INPUT_STREAM:
				return createInputStreamFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case PropertiesPackage.PROPERTY_TYPE:
				return convertPropertyTypeToString(eDataType, instanceValue);
			case PropertiesPackage.SEVERITY:
				return convertSeverityToString(eDataType, instanceValue);
			case PropertiesPackage.REVIEW_STATE:
				return convertReviewStateToString(eDataType, instanceValue);
			case PropertiesPackage.DIFF_KIND:
				return convertDiffKindToString(eDataType, instanceValue);
			case PropertiesPackage.LOCALE:
				return convertLocaleToString(eDataType, instanceValue);
			case PropertiesPackage.URI:
				return convertURIToString(eDataType, instanceValue);
			case PropertiesPackage.INPUT_STREAM:
				return convertInputStreamToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFile createPropertyFile() {
		PropertyFileImpl propertyFile = new PropertyFileImpl();
		return propertyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor createPropertyFileDescriptor() {
		PropertyFileDescriptorImpl propertyFileDescriptor = new PropertyFileDescriptorImpl();
		return propertyFileDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectVersion createProjectVersion() {
		ProjectVersionImpl projectVersion = new ProjectVersionImpl();
		return projectVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectLocale createProjectLocale() {
		ProjectLocaleImpl projectLocale = new ProjectLocaleImpl();
		return projectLocale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Workspace createWorkspace() {
		WorkspaceImpl workspace = new WorkspaceImpl();
		return workspace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScanConfiguration createScanConfiguration() {
		ScanConfigurationImpl scanConfiguration = new ScanConfigurationImpl();
		return scanConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Review createReview() {
		ReviewImpl review = new ReviewImpl();
		return review;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDiff createPropertyFileDiff() {
		PropertyFileDiffImpl propertyFileDiff = new PropertyFileDiffImpl();
		return propertyFileDiff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyTypeFromString(EDataType eDataType, String initialValue) {
		PropertyType result = PropertyType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPropertyTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Severity createSeverityFromString(EDataType eDataType, String initialValue) {
		Severity result = Severity.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeverityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewState createReviewStateFromString(EDataType eDataType, String initialValue) {
		ReviewState result = ReviewState.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertReviewStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiffKind createDiffKindFromString(EDataType eDataType, String initialValue) {
		DiffKind result = DiffKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiffKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Locale createLocaleFromString(EDataType eDataType, String initialValue) {
		if(initialValue==null)
			return null;
		String[] strings = initialValue.split("_");
		String language = strings.length>0 ? strings[0] : "";
		String country = strings.length>1 ? strings[1] : "";
		String variant = strings.length>2 ? strings[2] : "";
		return new Locale(language, country, variant);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String convertLocaleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI createURIFromString(EDataType eDataType, String initialValue) {
		if(initialValue==null)
			return null;
		return URI.createURI(initialValue, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String convertURIToString(EDataType eDataType, Object instanceValue) {
		if(instanceValue==null)
			return null;
		return instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InputStream createInputStreamFromString(EDataType eDataType, String initialValue) {
		return (InputStream)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInputStreamToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesPackage getPropertiesPackage() {
		return (PropertiesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PropertiesPackage getPackage() {
		return PropertiesPackage.eINSTANCE;
	}

} //PropertiesFactoryImpl
