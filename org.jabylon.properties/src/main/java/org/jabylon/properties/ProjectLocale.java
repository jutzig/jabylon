/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import java.util.Locale;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Locale</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectLocale#getLocale <em>Locale</em>}</li>
 *   <li>{@link org.jabylon.properties.ProjectLocale#getDescriptors <em>Descriptors</em>}</li>
 *   <li>{@link org.jabylon.properties.ProjectLocale#getPropertyCount <em>Property Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getProjectLocale()
 * @model
 * @generated
 */
public interface ProjectLocale extends Resolvable<ProjectVersion, Resolvable<?, ?>> {
	
	/**
	 * a pseudo locale indicating the template "language"
	 */
	public static final Locale TEMPLATE_LOCALE = new Locale("template");
	
    /**
	 * Returns the value of the '<em><b>Locale</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Locale</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Locale</em>' attribute.
	 * @see #setLocale(Locale)
	 * @see org.jabylon.properties.PropertiesPackage#getProjectLocale_Locale()
	 * @model dataType="org.jabylon.properties.Locale"
	 *        annotation="http://www.eclipse.org/CDO/DBStore columnType='VARCHAR' columnLength='32'"
	 * @generated
	 */
    Locale getLocale();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.ProjectLocale#getLocale <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locale</em>' attribute.
	 * @see #getLocale()
	 * @generated
	 */
    void setLocale(Locale value);

    /**
	 * Returns the value of the '<em><b>Descriptors</b></em>' reference list.
	 * The list contents are of type {@link org.jabylon.properties.PropertyFileDescriptor}.
	 * It is bidirectional and its opposite is '{@link org.jabylon.properties.PropertyFileDescriptor#getProjectLocale <em>Project Locale</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Descriptors</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Descriptors</em>' reference list.
	 * @see org.jabylon.properties.PropertiesPackage#getProjectLocale_Descriptors()
	 * @see org.jabylon.properties.PropertyFileDescriptor#getProjectLocale
	 * @model opposite="projectLocale" resolveProxies="false"
	 * @generated
	 */
    EList<PropertyFileDescriptor> getDescriptors();

    /**
	 * Returns the value of the '<em><b>Property Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Property Count</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Count</em>' attribute.
	 * @see #setPropertyCount(int)
	 * @see org.jabylon.properties.PropertiesPackage#getProjectLocale_PropertyCount()
	 * @model
	 * @generated
	 */
    int getPropertyCount();

    /**
	 * Sets the value of the '{@link org.jabylon.properties.ProjectLocale#getPropertyCount <em>Property Count</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Count</em>' attribute.
	 * @see #getPropertyCount()
	 * @generated
	 */
    void setPropertyCount(int value);

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
    boolean isMaster();




} // ProjectLocale
