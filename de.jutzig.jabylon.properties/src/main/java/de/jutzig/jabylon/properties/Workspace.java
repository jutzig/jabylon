/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Workspace#getRoot <em>Root</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Workspace#getProjects <em>Projects</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getWorkspace()
 * @model
 * @generated
 */
public interface Workspace extends Resolvable {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' attribute.
	 * @see #setRoot(URI)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getWorkspace_Root()
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI getRoot();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Workspace#getRoot <em>Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' attribute.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(URI value);

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.Project}.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.Project#getWorkspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getWorkspace_Projects()
	 * @see de.jutzig.jabylon.properties.Project#getWorkspace
	 * @model opposite="workspace" containment="true"
	 * @generated
	 */
	EList<Project> getProjects();
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	ProjectVersion getTerminology();

	Project getProject(String name);

} // Workspace
