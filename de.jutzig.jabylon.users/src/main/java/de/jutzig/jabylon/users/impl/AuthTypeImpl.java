/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.users.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.users.AuthType;
import de.jutzig.jabylon.users.UsersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Auth Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.users.impl.AuthTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.users.impl.AuthTypeImpl#getAuthModule <em>Auth Module</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthTypeImpl extends CDOObjectImpl implements AuthType {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The default value of the '{@link #getAuthModule() <em>Auth Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthModule()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTH_MODULE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UsersPackage.Literals.AUTH_TYPE;
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
	public String getName() {
		return (String)eDynamicGet(UsersPackage.AUTH_TYPE__NAME, UsersPackage.Literals.AUTH_TYPE__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(UsersPackage.AUTH_TYPE__NAME, UsersPackage.Literals.AUTH_TYPE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthModule() {
		return (String)eDynamicGet(UsersPackage.AUTH_TYPE__AUTH_MODULE, UsersPackage.Literals.AUTH_TYPE__AUTH_MODULE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthModule(String newAuthModule) {
		eDynamicSet(UsersPackage.AUTH_TYPE__AUTH_MODULE, UsersPackage.Literals.AUTH_TYPE__AUTH_MODULE, newAuthModule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UsersPackage.AUTH_TYPE__NAME:
				return getName();
			case UsersPackage.AUTH_TYPE__AUTH_MODULE:
				return getAuthModule();
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
		switch (featureID) {
			case UsersPackage.AUTH_TYPE__NAME:
				setName((String)newValue);
				return;
			case UsersPackage.AUTH_TYPE__AUTH_MODULE:
				setAuthModule((String)newValue);
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
		switch (featureID) {
			case UsersPackage.AUTH_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case UsersPackage.AUTH_TYPE__AUTH_MODULE:
				setAuthModule(AUTH_MODULE_EDEFAULT);
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
		switch (featureID) {
			case UsersPackage.AUTH_TYPE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case UsersPackage.AUTH_TYPE__AUTH_MODULE:
				return AUTH_MODULE_EDEFAULT == null ? getAuthModule() != null : !AUTH_MODULE_EDEFAULT.equals(getAuthModule());
		}
		return super.eIsSet(featureID);
	}

} //AuthTypeImpl
