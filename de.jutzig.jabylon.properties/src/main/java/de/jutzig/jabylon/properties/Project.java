/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getWorkspace <em>Workspace</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getVersions <em>Versions</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getPropertyType <em>Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject()
 * @model
 * @generated
 */
public interface Project extends Resolvable {
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
	 * Returns the value of the '<em><b>Versions</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.ProjectVersion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Versions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Versions</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Versions()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectVersion> getVersions();

	/**
	 * Returns the value of the '<em><b>Master</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master</em>' containment reference.
	 * @see #setMaster(ProjectVersion)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_Master()
	 * @model containment="true"
	 * @generated
	 */
	ProjectVersion getMaster();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getMaster <em>Master</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master</em>' containment reference.
	 * @see #getMaster()
	 * @generated
	 */
	void setMaster(ProjectVersion value);

	/**
	 * Returns the value of the '<em><b>Repository URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository URI</em>' attribute.
	 * @see #setRepositoryURI(URI)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_RepositoryURI()
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI getRepositoryURI();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getRepositoryURI <em>Repository URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository URI</em>' attribute.
	 * @see #getRepositoryURI()
	 * @generated
	 */
	void setRepositoryURI(URI value);

	/**
	 * Returns the value of the '<em><b>Property Type</b></em>' attribute.
	 * The literals are from the enumeration {@link de.jutzig.jabylon.properties.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertyType
	 * @see #setPropertyType(PropertyType)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProject_PropertyType()
	 * @model
	 * @generated
	 */
	PropertyType getPropertyType();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Project#getPropertyType <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertyType
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(PropertyType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void fullScan(ScanConfiguration configuration);

} // Project
