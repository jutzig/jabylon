/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Properties</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertiesAllTests extends TestSuite {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(suite());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static Test suite() {
        TestSuite suite = new PropertiesAllTests("Properties Tests");
        suite.addTest(PropertiesTests.suite());
        return suite;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesAllTests(String name) {
        super(name);
    }

} //PropertiesAllTests
