/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.common.util.URI;
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
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLocation <em>Location</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getKeys <em>Keys</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getTranslated <em>Translated</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor()
 * @model
 * @generated
 */
public interface PropertyFileDescriptor extends Resolvable, Completable {
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
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(URI)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Location()
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI getLocation();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(URI value);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	PropertyFile loadProperties();

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

	/**
	 * Returns the value of the '<em><b>Keys</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keys</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keys</em>' attribute.
	 * @see #setKeys(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Keys()
	 * @model
	 * @generated
	 */
	int getKeys();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getKeys <em>Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Keys</em>' attribute.
	 * @see #getKeys()
	 * @generated
	 */
	void setKeys(int value);

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
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Translated()
	 * @model
	 * @generated
	 */
	int getTranslated();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getTranslated <em>Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translated</em>' attribute.
	 * @see #getTranslated()
	 * @generated
	 */
	void setTranslated(int value);

} // PropertyFileDescriptor
