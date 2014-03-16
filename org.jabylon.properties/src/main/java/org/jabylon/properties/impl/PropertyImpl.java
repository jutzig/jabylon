/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.PropertyImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.PropertyImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.PropertyImpl#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated NOPE
 */
public class PropertyImpl extends CDOObjectImpl implements Property, Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Pattern ANNOTATION_PARSER = Pattern.compile("@(\\w+)(\\((.*?)\\))?");
    private static final Pattern ANNOTATION_VALUE_PARSER = Pattern.compile("(\\w+)=\"(.*?)\"");

    /**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
    protected static final String KEY_EDEFAULT = null;

    /**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
    protected static final String VALUE_EDEFAULT = null;

    /**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
    protected static final String COMMENT_EDEFAULT = null;

    /**
	 * The default value of the '{@link #getCommentWithoutAnnotations() <em>Comment Without Annotations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommentWithoutAnnotations()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_WITHOUT_ANNOTATIONS_EDEFAULT = null;

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected PropertyImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROPERTY;
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
    public String getKey() {
		return (String)eDynamicGet(PropertiesPackage.PROPERTY__KEY, PropertiesPackage.Literals.PROPERTY__KEY, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setKey(String newKey) {
		eDynamicSet(PropertiesPackage.PROPERTY__KEY, PropertiesPackage.Literals.PROPERTY__KEY, newKey);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getValue() {
		return (String)eDynamicGet(PropertiesPackage.PROPERTY__VALUE, PropertiesPackage.Literals.PROPERTY__VALUE, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setValue(String newValue) {
		eDynamicSet(PropertiesPackage.PROPERTY__VALUE, PropertiesPackage.Literals.PROPERTY__VALUE, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getComment() {
		return (String)eDynamicGet(PropertiesPackage.PROPERTY__COMMENT, PropertiesPackage.Literals.PROPERTY__COMMENT, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated NOT
	 */
    public void setComment(String newComment) {
    	String result = parseAnnotations(newComment);
		eDynamicSet(PropertiesPackage.PROPERTY__COMMENT, PropertiesPackage.Literals.PROPERTY__COMMENT, result);
	}

    /**
     * parses a comment for annotations
     * @param newComment
     */
    private String parseAnnotations(String string) {
    	getAnnotations().clear();
    	if(string==null)
    		return string;
    	Matcher matcher = ANNOTATION_PARSER.matcher(string);
    	String remainder = string;
    	while(matcher.find())
    	{
    		PropertyAnnotation annotation = PropertiesFactory.eINSTANCE.createPropertyAnnotation();
    		parseAnnotationValues(annotation,matcher.group(3));
    		annotation.setName(matcher.group(1));
    		getAnnotations().add(annotation);
    	}
    	return remainder;
		
	}

	private void parseAnnotationValues(PropertyAnnotation annotation, String contents) {
		if(contents==null)
			return;
		Matcher matcher = ANNOTATION_VALUE_PARSER.matcher(contents);
		while(matcher.find()) {
			annotation.getValues().put(matcher.group(1), matcher.group(2));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<PropertyAnnotation> getAnnotations() {
		return (EList<PropertyAnnotation>)eDynamicGet(PropertiesPackage.PROPERTY__ANNOTATIONS, PropertiesPackage.Literals.PROPERTY__ANNOTATIONS, true, true);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getCommentWithoutAnnotations() {
		String comment = getComment();
		if(comment==null)
			return null;
		return ANNOTATION_PARSER.matcher(comment).replaceAll("");
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyAnnotation findAnnotation(String name) {
		for (PropertyAnnotation annotation : getAnnotations()) {
			if(name.equals(annotation.getName()))
				return annotation;
		}
		return null;
	}

				/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY__KEY:
				return getKey();
			case PropertiesPackage.PROPERTY__VALUE:
				return getValue();
			case PropertiesPackage.PROPERTY__COMMENT:
				return getComment();
			case PropertiesPackage.PROPERTY__ANNOTATIONS:
				return getAnnotations();
			case PropertiesPackage.PROPERTY__COMMENT_WITHOUT_ANNOTATIONS:
				return getCommentWithoutAnnotations();
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
			case PropertiesPackage.PROPERTY__KEY:
				setKey((String)newValue);
				return;
			case PropertiesPackage.PROPERTY__VALUE:
				setValue((String)newValue);
				return;
			case PropertiesPackage.PROPERTY__COMMENT:
				setComment((String)newValue);
				return;
			case PropertiesPackage.PROPERTY__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends PropertyAnnotation>)newValue);
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
			case PropertiesPackage.PROPERTY__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY__ANNOTATIONS:
				getAnnotations().clear();
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
			case PropertiesPackage.PROPERTY__KEY:
				return KEY_EDEFAULT == null ? getKey() != null : !KEY_EDEFAULT.equals(getKey());
			case PropertiesPackage.PROPERTY__VALUE:
				return VALUE_EDEFAULT == null ? getValue() != null : !VALUE_EDEFAULT.equals(getValue());
			case PropertiesPackage.PROPERTY__COMMENT:
				return COMMENT_EDEFAULT == null ? getComment() != null : !COMMENT_EDEFAULT.equals(getComment());
			case PropertiesPackage.PROPERTY__ANNOTATIONS:
				return !getAnnotations().isEmpty();
			case PropertiesPackage.PROPERTY__COMMENT_WITHOUT_ANNOTATIONS:
				return COMMENT_WITHOUT_ANNOTATIONS_EDEFAULT == null ? getCommentWithoutAnnotations() != null : !COMMENT_WITHOUT_ANNOTATIONS_EDEFAULT.equals(getCommentWithoutAnnotations());
		}
		return super.eIsSet(featureID);
	}

    @Override
    public String toString() {
        return "Property ["+getKey() + " = " + getValue() + "] comment = " + getComment();
    }



} //PropertyImpl
