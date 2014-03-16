/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 */
package org.jabylon.properties.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyAnnotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.PropertyAnnotationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.PropertyAnnotationImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyAnnotationImpl extends CDOObjectImpl implements PropertyAnnotation {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyAnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROPERTY_ANNOTATION;
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
		return (String)eDynamicGet(PropertiesPackage.PROPERTY_ANNOTATION__NAME, PropertiesPackage.Literals.PROPERTY_ANNOTATION__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(PropertiesPackage.PROPERTY_ANNOTATION__NAME, PropertiesPackage.Literals.PROPERTY_ANNOTATION__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getValues() {
		Map<String, String> values = (Map<String, String>)eDynamicGet(PropertiesPackage.PROPERTY_ANNOTATION__VALUES, PropertiesPackage.Literals.PROPERTY_ANNOTATION__VALUES, true, true);
		if(values==null) {
			values = new LinkedHashMap<String,String>();
			eDynamicSet(PropertiesPackage.PROPERTY_ANNOTATION__VALUES, values);			
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValues(Map<String, String> newValues) {
		eDynamicSet(PropertiesPackage.PROPERTY_ANNOTATION__VALUES, PropertiesPackage.Literals.PROPERTY_ANNOTATION__VALUES, newValues);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_ANNOTATION__NAME:
				return getName();
			case PropertiesPackage.PROPERTY_ANNOTATION__VALUES:
				return getValues();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_ANNOTATION__NAME:
				setName((String)newValue);
				return;
			case PropertiesPackage.PROPERTY_ANNOTATION__VALUES:
				setValues((Map<String, String>)newValue);
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
			case PropertiesPackage.PROPERTY_ANNOTATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_ANNOTATION__VALUES:
				setValues((Map<String, String>)null);
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
			case PropertiesPackage.PROPERTY_ANNOTATION__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case PropertiesPackage.PROPERTY_ANNOTATION__VALUES:
				return getValues() != null;
		}
		return super.eIsSet(featureID);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("@");
		result.append(getName());
		if(!getValues().isEmpty()){
			result.append("(");
			for (Entry<String, String> value : getValues().entrySet()) {
				result.append(value.getKey());
				result.append("=\"");
				result.append(value.getValue());
				result.append("\",");
			}
			//get rid of the last ,
			result.setLength(result.length()-1);
			result.append(")");
		}
		return result.toString();
	}

} //PropertyAnnotationImpl
