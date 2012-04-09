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
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale()
 * @model
 * @generated
 */
public interface ProjectLocale extends Resolvable, Completable {
	/**
	 * Returns the value of the '<em><b>Project Version</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.ProjectVersion#getLocales <em>Locales</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Version</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Version</em>' container reference.
	 * @see #setProjectVersion(ProjectVersion)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectLocale_ProjectVersion()
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getLocales
	 * @model opposite="locales" transient="false"
	 * @generated
	 */
	ProjectVersion getProjectVersion();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectLocale#getProjectVersion <em>Project Version</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Version</em>' container reference.
	 * @see #getProjectVersion()
	 * @generated
	 */
	void setProjectVersion(ProjectVersion value);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isMaster();

} // ProjectLocale
