/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties.tests;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyTest extends TestCase {

    /**
     * The fixture for this Property test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Property fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(PropertyTest.class);
    }

    /**
     * Constructs a new Property test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertyTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this Property test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(Property fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this Property test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Property getFixture() {
        return fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#setUp()
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(PropertiesFactory.eINSTANCE.createProperty());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#tearDown()
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }


    public void testDummy()
    {
        //prevent warning for missing tests
    }


} //PropertyTest
