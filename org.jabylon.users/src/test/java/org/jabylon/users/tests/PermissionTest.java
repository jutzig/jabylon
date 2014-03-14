/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users.tests;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.jabylon.users.Permission;
import org.jabylon.users.UsersFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Permission</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PermissionTest extends TestCase {

    /**
     * The fixture for this Permission test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Permission fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(PermissionTest.class);
    }

    /**
     * Constructs a new Permission test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PermissionTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this Permission test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(Permission fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this Permission test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Permission getFixture() {
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
        setFixture(UsersFactory.eINSTANCE.createPermission());
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
        //prevent failure due to missing test
    }

} //PermissionTest
