/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getPropertyBags <em>Property Bags</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getWorkspace <em>Workspace</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getBase <em>Base</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_PropertyBags()
	 * @see de.jutzig.jabylon.properties.PropertyBag#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<PropertyBag> getPropertyBags();

	/**
	 * Returns the value of the '<em><b>Workspace</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.Workspace#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workspace</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workspace</em>' container reference.
	 * @see #setWorkspace(Workspace)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Workspace()
	 * @see de.jutzig.jabylon.properties.Workspace#getProjects
	 * @model opposite="projects" transient="false"
	 * @generated
	 */
	Workspace getWorkspace();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getWorkspace <em>Workspace</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workspace</em>' container reference.
	 * @see #getWorkspace()
	 * @generated
	 */
	void setWorkspace(Workspace value);

	/**
	 * Returns the value of the '<em><b>Base</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Base()
	 * @model dataType="de.jutzig.jabylon.properties.URI" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	URI getBase();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void fullScan();

} // Project
