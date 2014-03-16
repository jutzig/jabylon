/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.Property#getAnnotations() <em>Annotations</em>}</li>
 *   <li>{@link org.jabylon.properties.Property#getCommentWithoutAnnotations() <em>Comment Without Annotations</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.Property#findAnnotation(java.lang.String) <em>Find Annotation</em>}</li>
 * </ul>
 * </p>
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


    /**
	 * Tests the '{@link org.jabylon.properties.Property#getAnnotations() <em>Annotations</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Property#getAnnotations()
	 * @generated NOT
	 */
	public void testGetAnnotations() {
		assertEquals(0,getFixture().getAnnotations().size());
		getFixture().getAnnotations().add(PropertiesFactory.eINSTANCE.createPropertyAnnotation());
		assertEquals(1,getFixture().getAnnotations().size());
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Property#getCommentWithoutAnnotations() <em>Comment Without Annotations</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Property#getCommentWithoutAnnotations()
	 * @generated NOT
	 */
	public void testGetCommentWithoutAnnotations() {
		getFixture().setComment("foo");
		assertEquals("foo", getFixture().getCommentWithoutAnnotations());
		
		getFixture().setComment("@foo(blah=\"blubb\")@foobar(test=\"test\")comment");
		assertEquals("comment", getFixture().getCommentWithoutAnnotations());
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Property#findAnnotation(java.lang.String) <em>Find Annotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Property#findAnnotation(java.lang.String)
	 * @generated NOT
	 */
	public void testFindAnnotation__String() {
		getFixture().setComment("@foo(blah=\"blubb\")@foobar(test=\"test\")comment");
		assertEquals("foo", getFixture().findAnnotation("foo").getName());
		assertEquals("foobar", getFixture().findAnnotation("foobar").getName());
		assertNull(getFixture().findAnnotation("test"));
	}

} //PropertyTest
