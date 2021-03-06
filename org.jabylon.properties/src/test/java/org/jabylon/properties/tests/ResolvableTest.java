/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.jabylon.properties.Resolvable;
import org.junit.Ignore;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Resolvable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.Resolvable#fullPath() <em>Full Path</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#relativePath() <em>Relative Path</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#absolutPath() <em>Absolut Path</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#updatePercentComplete() <em>Update Percent Complete</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#resolveChild(org.eclipse.emf.common.util.URI) <em>Resolve Child</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#absoluteFilePath() <em>Absolute File Path</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#toURI() <em>To URI</em>}</li>
 *   <li>{@link org.jabylon.properties.Resolvable#getChild(java.lang.String) <em>Get Child</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class ResolvableTest extends TestCase {

    /**
	 * The fixture for this Resolvable test case.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected Resolvable<?, ?> fixture = null;

    /**
	 * Constructs a new Resolvable test case with the given name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ResolvableTest(String name) {
		super(name);
	}

    /**
	 * Sets the fixture for this Resolvable test case.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected void setFixture(Resolvable<?, ?> fixture) {
		this.fixture = fixture;
	}

    /**
	 * Returns the fixture for this Resolvable test case.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected Resolvable<?, ?> getFixture() {
		return fixture;
	}

    /**
     * Tests the '{@link org.jabylon.properties.Resolvable#fullPath() <em>Full Path</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.Resolvable#fullPath()
     * @generated NOT
     */
    public void testFullPath() {
        assertEquals("no parent, so relative path equals full path",getFixture().relativePath(), getFixture().fullPath());
    }


    public void testFullPathWithParent() {
        getFixture().setName("name");
        Resolvable parent = mock(Resolvable.class,withSettings().extraInterfaces(InternalEObject.class));
        when(parent.fullPath()).thenReturn(URI.createURI("foo"));
        ((InternalEObject)getFixture()).eBasicSetContainer((InternalEObject) parent, 0, null);
        URI expected = URI.createURI("foo");
        expected = expected.appendSegments(getFixture().relativePath().segments());
        assertEquals(expected, getFixture().fullPath());
    }

    /**
     * Tests the '{@link org.jabylon.properties.Resolvable#relativePath() <em>Relative Path</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.Resolvable#relativePath()
     * @generated NOT
     */
    public void testRelativePath()
    {
        URI uri = URI.createURI("foo");
        getFixture().setName("foo");
        assertSame(uri, getFixture().relativePath());
    }


    public void testRelativePathNullSafe()
    {
        assertNull(getFixture().relativePath());
    }

    /**
	 * Tests the '{@link org.jabylon.properties.Resolvable#absolutPath() <em>Absolut Path</em>}' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#absolutPath()
	 * @generated NOT
	 */
    @Ignore
    public void testAbsolutPath() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

    /**
	 * Tests the '{@link org.jabylon.properties.Resolvable#updatePercentComplete() <em>Update Percent Complete</em>}' operation.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#updatePercentComplete()
	 * @generated NOT
	 */
    @Ignore
    public void testUpdatePercentComplete() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Resolvable#resolveChild(org.eclipse.emf.common.util.URI) <em>Resolve Child</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#resolveChild(org.eclipse.emf.common.util.URI)
	 * @generated NOT
	 */
	public void testResolveChild__URI() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Resolvable#absoluteFilePath() <em>Absolute File Path</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#absoluteFilePath()
	 * @generated NOT
	 */
	public void testAbsoluteFilePath() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Resolvable#toURI() <em>To URI</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#toURI()
	 * @generated NOT
	 */
	public void testToURI() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

				/**
	 * Tests the '{@link org.jabylon.properties.Resolvable#getChild(java.lang.String) <em>Get Child</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Resolvable#getChild(java.lang.String)
	 * @generated NOT
	 */
	public void testGetChild__String() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

} //ResolvableTest
