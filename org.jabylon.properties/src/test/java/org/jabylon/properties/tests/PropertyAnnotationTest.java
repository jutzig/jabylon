/**
 */
package org.jabylon.properties.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyAnnotation;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property Annotation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PropertyAnnotationTest extends TestCase {

	/**
	 * The fixture for this Property Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyAnnotation fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PropertyAnnotationTest.class);
	}

	/**
	 * Constructs a new Property Annotation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyAnnotationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Property Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PropertyAnnotation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Property Annotation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyAnnotation getFixture() {
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
		setFixture(PropertiesFactory.eINSTANCE.createPropertyAnnotation());
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
	
	public void testToStringSimple() {
		getFixture().setName("foo");
		assertEquals("@foo", getFixture().toString());
	}
	
	public void testToStringWithValues() {
		getFixture().setName("foo");
		getFixture().getValues().put("test", "value");
		getFixture().getValues().put("test2", "value2");
		assertEquals("@foo(test=\"value\",test2=\"value2\")", getFixture().toString());
	}

} //PropertyAnnotationTest
