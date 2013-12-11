/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Severity</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage#getSeverity()
 * @model
 * @generated
 */
public enum Severity implements Enumerator {
    /**
     * The '<em><b>INFO</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INFO_VALUE
     * @generated
     * @ordered
     */
    INFO(0, "INFO", "INFO"),

    /**
     * The '<em><b>WARNING</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #WARNING_VALUE
     * @generated
     * @ordered
     */
    WARNING(1, "WARNING", "WARNING"),

    /**
     * The '<em><b>ERROR</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ERROR_VALUE
     * @generated
     * @ordered
     */
    ERROR(2, "ERROR", "ERROR");

    /**
     * The '<em><b>INFO</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INFO</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INFO
     * @model
     * @generated
     * @ordered
     */
    public static final int INFO_VALUE = 0;

    /**
     * The '<em><b>WARNING</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>WARNING</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #WARNING
     * @model
     * @generated
     * @ordered
     */
    public static final int WARNING_VALUE = 1;

    /**
     * The '<em><b>ERROR</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ERROR</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ERROR
     * @model
     * @generated
     * @ordered
     */
    public static final int ERROR_VALUE = 2;

    /**
     * An array of all the '<em><b>Severity</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final Severity[] VALUES_ARRAY =
        new Severity[] {
            INFO,
            WARNING,
            ERROR,
        };

    /**
     * A public read-only list of all the '<em><b>Severity</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<Severity> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Severity</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Severity get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Severity result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Severity</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Severity getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Severity result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Severity</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Severity get(int value) {
        switch (value) {
            case INFO_VALUE: return INFO;
            case WARNING_VALUE: return WARNING;
            case ERROR_VALUE: return ERROR;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private Severity(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} //Severity
