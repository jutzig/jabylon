/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag <em>Bag</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile <em>Property File</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor()
 * @model
 * @generated
 */
public interface PropertyFileDescriptor extends EObject {
	/**
	 * Returns the value of the '<em><b>Variant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant</em>' attribute.
	 * @see #setVariant(Locale)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Variant()
	 * @model dataType="de.jutzig.jabylon.properties.Locale"
	 * @generated
	 */
	Locale getVariant();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getVariant <em>Variant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variant</em>' attribute.
	 * @see #getVariant()
	 * @generated
	 */
	void setVariant(Locale value);

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
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Bag</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.PropertyBag#getDescriptors <em>Descriptors</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bag</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bag</em>' container reference.
	 * @see #setBag(PropertyBag)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Bag()
	 * @see de.jutzig.jabylon.properties.PropertyBag#getDescriptors
	 * @model opposite="descriptors" transient="false"
	 * @generated
	 */
	PropertyBag getBag();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getBag <em>Bag</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bag</em>' container reference.
	 * @see #getBag()
	 * @generated
	 */
	void setBag(PropertyBag value);

	/**
	 * Returns the value of the '<em><b>Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master</em>' attribute.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Master()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isMaster();

	/**
	 * Returns the value of the '<em><b>Property File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property File</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property File</em>' reference.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_PropertyFile()
	 * @model transient="true" changeable="false" derived="true"
	 * @generated
	 */
	PropertyFile getPropertyFile();

} // PropertyFileDescriptor
