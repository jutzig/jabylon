/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.Comment#getUser <em>User</em>}</li>
 *   <li>{@link org.jabylon.properties.Comment#getMessage <em>Message</em>}</li>
 *   <li>{@link org.jabylon.properties.Comment#getCreated <em>Created</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getComment()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Comment extends CDOObject {
    /**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see org.jabylon.properties.PropertiesPackage#getComment_User()
	 * @model
	 * @generated
	 */
    String getUser();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Comment#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
    void setUser(String value);

    /**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.jabylon.properties.PropertiesPackage#getComment_Message()
	 * @model
	 * @generated
	 */
    String getMessage();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Comment#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
    void setMessage(String value);

				/**
	 * Returns the value of the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created</em>' attribute.
	 * @see #setCreated(long)
	 * @see org.jabylon.properties.PropertiesPackage#getComment_Created()
	 * @model
	 * @generated
	 */
	long getCreated();

				/**
	 * Sets the value of the '{@link org.jabylon.properties.Comment#getCreated <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created</em>' attribute.
	 * @see #getCreated()
	 * @generated
	 */
	void setCreated(long value);

} // Comment
