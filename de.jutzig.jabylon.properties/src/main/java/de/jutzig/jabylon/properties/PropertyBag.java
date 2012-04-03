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
 * A representation of the model object '<em><b>Property Bag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyBag#getDescriptors <em>Descriptors</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyBag#getPath <em>Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyBag#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyBag#getFullPath <em>Full Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyBag#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PropertyBag extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Descriptors</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.PropertyFileDescriptor}.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag <em>Bag</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptors</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag_Descriptors()
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag
	 * @model opposite="bag" containment="true"
	 * @generated
	 */
	EList<PropertyFileDescriptor> getDescriptors();

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(URI)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag_Path()
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI getPath();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyBag#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(URI value);

	/**
	 * Returns the value of the '<em><b>Master</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master</em>' reference.
	 * @see #setMaster(PropertyFileDescriptor)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag_Master()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
	 */
	PropertyFileDescriptor getMaster();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyBag#getMaster <em>Master</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master</em>' reference.
	 * @see #getMaster()
	 * @generated
	 */
	void setMaster(PropertyFileDescriptor value);

	/**
	 * Returns the value of the '<em><b>Full Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full Path</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag_FullPath()
	 * @model dataType="de.jutzig.jabylon.properties.URI" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	URI getFullPath();

	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.Project#getPropertyBags <em>Property Bags</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyBag_Project()
	 * @see de.jutzig.jabylon.properties.Project#getPropertyBags
	 * @model opposite="propertyBags" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyBag#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

} // PropertyBag
