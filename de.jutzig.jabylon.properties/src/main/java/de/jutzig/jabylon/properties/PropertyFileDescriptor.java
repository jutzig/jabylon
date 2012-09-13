/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

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
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getProjectLocale <em>Project Locale</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getKeys <em>Keys</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getReviews <em>Reviews</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLastModification <em>Last Modification</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor()
 * @model
 * @generated
 */
public interface PropertyFileDescriptor extends Resolvable {
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
     * <!-- begin-user-doc -->
	 * Computes the location according to the current locale and the location of the master.
	 * Does nothing if this is the master, or there is no master available
	 * 
	 * <!-- end-user-doc -->
     * @model
     * @generated
     */
	void computeLocation();

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
     * @model resolveProxies="false"
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
     * Returns the value of the '<em><b>Project Locale</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Locale</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Project Locale</em>' reference.
     * @see #setProjectLocale(ProjectLocale)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_ProjectLocale()
     * @model resolveProxies="false" required="true"
     * @generated
     */
	ProjectLocale getProjectLocale();

	/**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getProjectLocale <em>Project Locale</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Project Locale</em>' reference.
     * @see #getProjectLocale()
     * @generated
     */
	void setProjectLocale(ProjectLocale value);

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
     * Returns the value of the '<em><b>Reviews</b></em>' containment reference list.
     * The list contents are of type {@link de.jutzig.jabylon.properties.Review}.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviews</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Reviews</em>' containment reference list.
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_Reviews()
     * @model containment="true"
     * @generated
     */
	EList<Review> getReviews();

	/**
     * Returns the value of the '<em><b>Last Modified</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modified</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Last Modified</em>' attribute.
     * @see #setLastModified(long)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_LastModified()
     * @model
     * @generated
     */
	long getLastModified();

	/**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLastModified <em>Last Modified</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Modified</em>' attribute.
     * @see #getLastModified()
     * @generated
     */
	void setLastModified(long value);

	/**
     * Returns the value of the '<em><b>Last Modification</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Modification</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Last Modification</em>' reference.
     * @see #setLastModification(Comment)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDescriptor_LastModification()
     * @model resolveProxies="false"
     * @generated
     */
	Comment getLastModification();

	/**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getLastModification <em>Last Modification</em>}' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Last Modification</em>' reference.
     * @see #getLastModification()
     * @generated
     */
	void setLastModification(Comment value);

} // PropertyFileDescriptor
