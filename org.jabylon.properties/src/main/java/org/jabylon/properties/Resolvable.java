/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Resolvable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.Resolvable#getPercentComplete <em>Percent Complete</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#getChildren <em>Children</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#getParent <em>Parent</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getResolvable()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface Resolvable<P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> extends CDOObject {
    /**
	 * Returns the value of the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Percent Complete</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Percent Complete</em>' attribute.
	 * @see #setPercentComplete(int)
	 * @see org.jabylon.properties.PropertiesPackage#getResolvable_PercentComplete()
	 * @model
	 * @generated
	 */
    int getPercentComplete();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Resolvable#getPercentComplete <em>Percent Complete</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @param value the new value of the '<em>Percent Complete</em>' attribute.
	 * @see #getPercentComplete()
	 * @generated
	 */
    void setPercentComplete(int value);

    /**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link C}.
	 * It is bidirectional and its opposite is '{@link org.jabylon.properties.Resolvable#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Children</em>' containment reference isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.jabylon.properties.PropertiesPackage#getResolvable_Children()
	 * @see org.jabylon.properties.Resolvable#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
    EList<C> getChildren();

    /**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.jabylon.properties.Resolvable#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Resolvable)
	 * @see org.jabylon.properties.PropertiesPackage#getResolvable_Parent()
	 * @see org.jabylon.properties.Resolvable#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
    P getParent();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Resolvable#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
    void setParent(P value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.jabylon.properties.PropertiesPackage#getResolvable_Name()
     * @model
     * @generated
     */
    String getName();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.Resolvable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model dataType="org.jabylon.properties.URI"
	 * @generated
	 */
    URI fullPath();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model dataType="org.jabylon.properties.URI"
	 * @generated
	 */
    URI relativePath();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model dataType="org.jabylon.properties.URI"
	 * @generated
	 */
    URI absolutPath();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    int updatePercentComplete();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model pathDataType="org.jabylon.properties.URI"
	 * @generated
	 */
    Resolvable<?, ?> resolveChild(URI path);

    Resolvable<?, ?> resolveChild(List<String> path);

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model dataType="org.jabylon.properties.URI"
	 * @generated
	 */
    URI absoluteFilePath();

    /**
	 * <!-- begin-user-doc -->
     * Computes a resolvable URI (not necessarily a file path) to uniquely identify this resolvable
     *  <!-- end-user-doc -->
	 * @model dataType="org.jabylon.properties.URI"
	 * @generated
	 */
    URI toURI();

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
    C getChild(String name);

} // Resolvable
