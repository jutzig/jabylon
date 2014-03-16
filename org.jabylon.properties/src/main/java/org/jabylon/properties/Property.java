/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.Property#getKey <em>Key</em>}</li>
 *   <li>{@link org.jabylon.properties.Property#getValue <em>Value</em>}</li>
 *   <li>{@link org.jabylon.properties.Property#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 * @extends java.io.Serializable
 * @see org.jabylon.properties.PropertiesPackage#getProperty()
 * @model
 * @extends CDOObject
 * @generated NOPE
 */
public interface Property extends CDOObject, java.io.Serializable{
    /**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProperty_Key()
	 * @model id="true"
	 * @generated
	 */
    String getKey();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Property#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
    void setKey(String value);

    /**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProperty_Value()
	 * @model
	 * @generated
	 */
    String getValue();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Property#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
    void setValue(String value);

    /**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * The translation comment associated with the comment. If the comment contains any annotations, they are extracted automatically
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.jabylon.properties.PropertiesPackage#getProperty_Comment()
	 * @model
	 * @generated
	 */
    String getComment();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Property#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
    void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' reference list.
	 * The list contents are of type {@link org.jabylon.properties.PropertyAnnotation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' reference list.
	 * @see org.jabylon.properties.PropertiesPackage#getProperty_Annotations()
	 * @model derived="true"
	 * @generated
	 */
	EList<PropertyAnnotation> getAnnotations();

	/**
	 * Returns the value of the '<em><b>Comment Without Annotations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * This is the comment of the property minus any annotations found
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment Without Annotations</em>' attribute.
	 * @see org.jabylon.properties.PropertiesPackage#getProperty_CommentWithoutAnnotations()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getCommentWithoutAnnotations();

	/**
	 * <!-- begin-user-doc -->
	 * Tries to find an annotation of the given name and returns it. If none is found, <code>null</code> is returned
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	PropertyAnnotation findAnnotation(String name);

} // Property
