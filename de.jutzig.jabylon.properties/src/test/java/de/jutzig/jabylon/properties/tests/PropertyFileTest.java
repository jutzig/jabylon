/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.util.scanner.PropertyFileAcceptor;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFile#getProperty(java.lang.String) <em>Get Property</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PropertyFileTest extends TestCase {

    /**
     * The fixture for this Property File test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PropertyFile fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(PropertyFileTest.class);
    }

    /**
     * Constructs a new Property File test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertyFileTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this Property File test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(PropertyFile fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this Property File test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PropertyFile getFixture() {
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
        setFixture(PropertiesFactory.eINSTANCE.createPropertyFile());
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


    /**
     * Tests the '{@link de.jutzig.jabylon.properties.PropertyFile#getProperty(java.lang.String) <em>Get Property</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.jutzig.jabylon.properties.PropertyFile#getProperty(java.lang.String)
     * @generated NOT
     */
    public void testGetProperty__String() {
        assertNull(getFixture().getProperty(null));
        assertNull(getFixture().getProperty("foo"));

        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setKey("foo");
        getFixture().getProperties().add(property);
        assertSame(property, getFixture().getProperty("foo"));
    }

    public void testDummy()
    {
        //prevent warning for missing tests
    }


} //PropertyFileTest
