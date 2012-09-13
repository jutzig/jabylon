/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.Comment;
import de.jutzig.jabylon.properties.PropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.CommentImpl#getUser <em>User</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.CommentImpl#getMessage <em>Message</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommentImpl extends CDOObjectImpl implements Comment {
	/**
     * The default value of the '{@link #getUser() <em>User</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getUser()
     * @generated
     * @ordered
     */
	protected static final String USER_EDEFAULT = null;

	/**
     * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getMessage()
     * @generated
     * @ordered
     */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected CommentImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return PropertiesPackage.Literals.COMMENT;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected int eStaticFeatureCount() {
        return 0;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getUser() {
        return (String)eDynamicGet(PropertiesPackage.COMMENT__USER, PropertiesPackage.Literals.COMMENT__USER, true, true);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setUser(String newUser) {
        eDynamicSet(PropertiesPackage.COMMENT__USER, PropertiesPackage.Literals.COMMENT__USER, newUser);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getMessage() {
        return (String)eDynamicGet(PropertiesPackage.COMMENT__MESSAGE, PropertiesPackage.Literals.COMMENT__MESSAGE, true, true);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setMessage(String newMessage) {
        eDynamicSet(PropertiesPackage.COMMENT__MESSAGE, PropertiesPackage.Literals.COMMENT__MESSAGE, newMessage);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID)
        {
            case PropertiesPackage.COMMENT__USER:
                return getUser();
            case PropertiesPackage.COMMENT__MESSAGE:
                return getMessage();
        }
        return super.eGet(featureID, resolve, coreType);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eSet(int featureID, Object newValue) {
        switch (featureID)
        {
            case PropertiesPackage.COMMENT__USER:
                setUser((String)newValue);
                return;
            case PropertiesPackage.COMMENT__MESSAGE:
                setMessage((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eUnset(int featureID) {
        switch (featureID)
        {
            case PropertiesPackage.COMMENT__USER:
                setUser(USER_EDEFAULT);
                return;
            case PropertiesPackage.COMMENT__MESSAGE:
                setMessage(MESSAGE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public boolean eIsSet(int featureID) {
        switch (featureID)
        {
            case PropertiesPackage.COMMENT__USER:
                return USER_EDEFAULT == null ? getUser() != null : !USER_EDEFAULT.equals(getUser());
            case PropertiesPackage.COMMENT__MESSAGE:
                return MESSAGE_EDEFAULT == null ? getMessage() != null : !MESSAGE_EDEFAULT.equals(getMessage());
        }
        return super.eIsSet(featureID);
    }

} //CommentImpl
