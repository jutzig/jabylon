/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.users;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Auth Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.users.AuthType#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.AuthType#getAuthModule <em>Auth Module</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.users.UsersPackage#getAuthType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AuthType extends CDOObject {
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
     * @see de.jutzig.jabylon.users.UsersPackage#getAuthType_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.users.AuthType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Auth Module</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Auth Module</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Auth Module</em>' attribute.
     * @see #setAuthModule(String)
     * @see de.jutzig.jabylon.users.UsersPackage#getAuthType_AuthModule()
     * @model
     * @generated
     */
    String getAuthModule();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.users.AuthType#getAuthModule <em>Auth Module</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Auth Module</em>' attribute.
     * @see #getAuthModule()
     * @generated
     */
    void setAuthModule(String value);

} // AuthType
