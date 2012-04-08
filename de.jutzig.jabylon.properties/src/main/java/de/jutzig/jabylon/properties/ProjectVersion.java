/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getTotal <em>Total</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getProject <em>Project</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getBranch <em>Branch</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getLocales <em>Locales</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getFullPath <em>Full Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getMaster <em>Master</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion()
 * @model
 * @generated
 */
public interface ProjectVersion extends Completable {
	/**
	 * Returns the value of the '<em><b>Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translated</em>' attribute.
	 * @see #setTranslated(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Translated()
	 * @model
	 * @generated
	 */
	int getTranslated();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getTranslated <em>Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translated</em>' attribute.
	 * @see #getTranslated()
	 * @generated
	 */
	void setTranslated(int value);

	/**
	 * Returns the value of the '<em><b>Total</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total</em>' attribute.
	 * @see #setTotal(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Total()
	 * @model
	 * @generated
	 */
	int getTotal();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getTotal <em>Total</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total</em>' attribute.
	 * @see #getTotal()
	 * @generated
	 */
	void setTotal(int value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' reference.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Project()
	 * @model resolveProxies="false" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	Project getProject();

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Branch()
	 * @model
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

	/**
	 * Returns the value of the '<em><b>Locales</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.ProjectLocale}.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.ProjectLocale#getProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locales</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locales</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Locales()
	 * @see de.jutzig.jabylon.properties.ProjectLocale#getProjectVersion
	 * @model opposite="projectVersion" containment="true"
	 * @generated
	 */
	EList<ProjectLocale> getLocales();

	/**
	 * Returns the value of the '<em><b>Full Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full Path</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_FullPath()
	 * @model dataType="de.jutzig.jabylon.properties.URI" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	URI getFullPath();

	/**
	 * Returns the value of the '<em><b>Master</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master</em>' containment reference.
	 * @see #setMaster(ProjectLocale)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Master()
	 * @model containment="true"
	 * @generated
	 */
	ProjectLocale getMaster();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getMaster <em>Master</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master</em>' containment reference.
	 * @see #getMaster()
	 * @generated
	 */
	void setMaster(ProjectLocale value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void fullScan();

} // ProjectVersion
