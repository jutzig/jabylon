/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.users.tests;

import org.jabylon.users.AuthType;
import org.jabylon.users.UsersFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Auth Type</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AuthTypeTest extends TestCase {

    /**
     * The fixture for this Auth Type test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AuthType fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(AuthTypeTest.class);
    }

    /**
     * Constructs a new Auth Type test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AuthTypeTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this Auth Type test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(AuthType fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this Auth Type test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AuthType getFixture() {
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
        setFixture(UsersFactory.eINSTANCE.createAuthType());
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

    public void testDummy(){
        //prevent error for missing test
    }

} //AuthTypeTest
