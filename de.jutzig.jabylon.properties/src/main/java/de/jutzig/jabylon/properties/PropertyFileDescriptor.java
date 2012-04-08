/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.cdo.CDOObject;

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
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile <em>Property File</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getMaster <em>Master</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor()
 * @model
 * @generated
 */
public interface PropertyFileDescriptor extends Completable {
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
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
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
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Master()
	 * @model
	 * @generated
	 */
	PropertyFileDescriptor getMaster();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getMaster <em>Master</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master</em>' reference.
	 * @see #getMaster()
	 * @generated
	 */
	void setMaster(PropertyFileDescriptor value);

} // PropertyFileDescriptor
