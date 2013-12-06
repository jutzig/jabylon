/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Review State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage#getReviewState()
 * @model
 * @generated
 */
public enum ReviewState implements Enumerator {
    /**
     * The '<em><b>OPEN</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPEN_VALUE
     * @generated
     * @ordered
     */
    OPEN(0, "OPEN", "OPEN"),

    /**
     * The '<em><b>RESOLVED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #RESOLVED_VALUE
     * @generated
     * @ordered
     */
    RESOLVED(1, "RESOLVED", "RESOLVED"),

    /**
     * The '<em><b>INVALID</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #INVALID_VALUE
     * @generated
     * @ordered
     */
    INVALID(2, "INVALID", "INVALID"),

    /**
     * The '<em><b>REOPENED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REOPENED_VALUE
     * @generated
     * @ordered
     */
    REOPENED(3, "REOPENED", "REOPENED");

    /**
     * The '<em><b>OPEN</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OPEN</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPEN
     * @model
     * @generated
     * @ordered
     */
    public static final int OPEN_VALUE = 0;

    /**
     * The '<em><b>RESOLVED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESOLVED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESOLVED
     * @model
     * @generated
     * @ordered
     */
    public static final int RESOLVED_VALUE = 1;

    /**
     * The '<em><b>INVALID</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>INVALID</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #INVALID
     * @model
     * @generated
     * @ordered
     */
    public static final int INVALID_VALUE = 2;

    /**
     * The '<em><b>REOPENED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REOPENED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REOPENED
     * @model
     * @generated
     * @ordered
     */
    public static final int REOPENED_VALUE = 3;

    /**
     * An array of all the '<em><b>Review State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final ReviewState[] VALUES_ARRAY =
        new ReviewState[] {
            OPEN,
            RESOLVED,
            INVALID,
            REOPENED,
        };

    /**
     * A public read-only list of all the '<em><b>Review State</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<ReviewState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Review State</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ReviewState get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ReviewState result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Review State</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ReviewState getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ReviewState result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Review State</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static ReviewState get(int value) {
        switch (value) {
            case OPEN_VALUE: return OPEN;
            case RESOLVED_VALUE: return RESOLVED;
            case INVALID_VALUE: return INVALID;
            case REOPENED_VALUE: return REOPENED;
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
    private ReviewState(int value, String name, String literal) {
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

} //ReviewState
