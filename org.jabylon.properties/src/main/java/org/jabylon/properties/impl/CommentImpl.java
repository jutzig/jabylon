/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import org.jabylon.properties.Comment;
import org.jabylon.properties.PropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.CommentImpl#getUser <em>User</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.CommentImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.CommentImpl#getCreated <em>Created</em>}</li>
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
	 * The default value of the '{@link #getCreated() <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreated()
	 * @generated
	 * @ordered
	 */
	protected static final long CREATED_EDEFAULT = 0L;

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
	public long getCreated() {
		return (Long)eDynamicGet(PropertiesPackage.COMMENT__CREATED, PropertiesPackage.Literals.COMMENT__CREATED, true, true);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreated(long newCreated) {
		eDynamicSet(PropertiesPackage.COMMENT__CREATED, PropertiesPackage.Literals.COMMENT__CREATED, newCreated);
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.COMMENT__USER:
				return getUser();
			case PropertiesPackage.COMMENT__MESSAGE:
				return getMessage();
			case PropertiesPackage.COMMENT__CREATED:
				return getCreated();
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
			case PropertiesPackage.COMMENT__USER:
				setUser((String)newValue);
				return;
			case PropertiesPackage.COMMENT__MESSAGE:
				setMessage((String)newValue);
				return;
			case PropertiesPackage.COMMENT__CREATED:
				setCreated((Long)newValue);
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
			case PropertiesPackage.COMMENT__USER:
				setUser(USER_EDEFAULT);
				return;
			case PropertiesPackage.COMMENT__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case PropertiesPackage.COMMENT__CREATED:
				setCreated(CREATED_EDEFAULT);
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
			case PropertiesPackage.COMMENT__USER:
				return USER_EDEFAULT == null ? getUser() != null : !USER_EDEFAULT.equals(getUser());
			case PropertiesPackage.COMMENT__MESSAGE:
				return MESSAGE_EDEFAULT == null ? getMessage() != null : !MESSAGE_EDEFAULT.equals(getMessage());
			case PropertiesPackage.COMMENT__CREATED:
				return getCreated() != CREATED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //CommentImpl
