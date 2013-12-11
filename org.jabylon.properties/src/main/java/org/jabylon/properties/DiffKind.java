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
package org.jabylon.properties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Diff Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage#getDiffKind()
 * @model
 * @generated
 */
public enum DiffKind implements Enumerator {
    /**
     * The '<em><b>ADD</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #ADD_VALUE
     * @generated
     * @ordered
     */
    ADD(0, "ADD", "ADD"),

    /**
     * The '<em><b>REMOVE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REMOVE_VALUE
     * @generated
     * @ordered
     */
    REMOVE(1, "REMOVE", "REMOVE"),

    /**
     * The '<em><b>MODIFY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MODIFY_VALUE
     * @generated
     * @ordered
     */
    MODIFY(2, "MODIFY", "MODIFY"),

    /**
     * The '<em><b>COPY</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #COPY_VALUE
     * @generated
     * @ordered
     */
    COPY(3, "COPY", "COPY"),

    /**
     * The '<em><b>MOVE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #MOVE_VALUE
     * @generated
     * @ordered
     */
    MOVE(4, "MOVE", "MOVE");

    /**
     * The '<em><b>ADD</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ADD</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ADD
     * @model
     * @generated
     * @ordered
     */
    public static final int ADD_VALUE = 0;

    /**
     * The '<em><b>REMOVE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REMOVE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REMOVE
     * @model
     * @generated
     * @ordered
     */
    public static final int REMOVE_VALUE = 1;

    /**
     * The '<em><b>MODIFY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MODIFY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MODIFY
     * @model
     * @generated
     * @ordered
     */
    public static final int MODIFY_VALUE = 2;

    /**
     * The '<em><b>COPY</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>COPY</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #COPY
     * @model
     * @generated
     * @ordered
     */
    public static final int COPY_VALUE = 3;

    /**
     * The '<em><b>MOVE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>MOVE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #MOVE
     * @model
     * @generated
     * @ordered
     */
    public static final int MOVE_VALUE = 4;

    /**
     * An array of all the '<em><b>Diff Kind</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final DiffKind[] VALUES_ARRAY =
        new DiffKind[] {
            ADD,
            REMOVE,
            MODIFY,
            COPY,
            MOVE,
        };

    /**
     * A public read-only list of all the '<em><b>Diff Kind</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<DiffKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Diff Kind</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DiffKind get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DiffKind result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Diff Kind</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DiffKind getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DiffKind result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Diff Kind</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DiffKind get(int value) {
        switch (value) {
            case ADD_VALUE: return ADD;
            case REMOVE_VALUE: return REMOVE;
            case MODIFY_VALUE: return MODIFY;
            case COPY_VALUE: return COPY;
            case MOVE_VALUE: return MOVE;
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
    private DiffKind(int value, String name, String literal) {
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

} //DiffKind
