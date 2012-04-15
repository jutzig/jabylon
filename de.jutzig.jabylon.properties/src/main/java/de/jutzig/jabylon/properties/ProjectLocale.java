/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Locale</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectLocale#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectLocale#getLocale <em>Locale</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectLocale#getDescriptors <em>Descriptors</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectLocale#getPropertyCount <em>Property Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale()
 * @model
 * @generated
 */
public interface ProjectLocale extends Resolvable {
	/**
	 * Returns the value of the '<em><b>Project Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Version</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Version</em>' reference.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale_ProjectVersion()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	ProjectVersion getProjectVersion();

	/**
	 * Returns the value of the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locale</em>' attribute.
	 * @see #setLocale(Locale)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale_Locale()
	 * @model dataType="de.jutzig.jabylon.properties.Locale"
	 * @generated
	 */
	Locale getLocale();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectLocale#getLocale <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locale</em>' attribute.
	 * @see #getLocale()
	 * @generated
	 */
	void setLocale(Locale value);

	/**
	 * Returns the value of the '<em><b>Descriptors</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.PropertyFileDescriptor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptors</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptors</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale_Descriptors()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyFileDescriptor> getDescriptors();

	/**
	 * Returns the value of the '<em><b>Property Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Count</em>' attribute.
	 * @see #setPropertyCount(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale_PropertyCount()
	 * @model
	 * @generated
	 */
	int getPropertyCount();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectLocale#getPropertyCount <em>Property Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Count</em>' attribute.
	 * @see #getPropertyCount()
	 * @generated
	 */
	void setPropertyCount(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isMaster();

} // ProjectLocale
