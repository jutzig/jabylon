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
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getPropertyBags <em>Property Bags</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getBase <em>Base</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getTotal <em>Total</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProjectVersion extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Property Bags</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.PropertyBag}.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.PropertyBag#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Bags</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Bags</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_PropertyBags()
	 * @see de.jutzig.jabylon.properties.PropertyBag#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<PropertyBag> getPropertyBags();

	/**
	 * Returns the value of the '<em><b>Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Base()
	 * @model dataType="de.jutzig.jabylon.properties.URI" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	URI getBase();

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
	 * @see #setProject(Project)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Project()
	 * @model resolveProxies="false" transient="true" volatile="true" derived="true"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getProject <em>Project</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void fullScan();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getPercentComplete();

} // ProjectVersion
