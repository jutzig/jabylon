/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFile;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property File</b></em>'.
 * <!-- end-user-doc -->
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

} //PropertyFileTest
