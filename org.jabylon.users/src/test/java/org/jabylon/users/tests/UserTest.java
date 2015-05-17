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
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UsersFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.users.User#getAllPermissions() <em>Get All Permissions</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class UserTest extends TestCase {

    /**
     * The fixture for this User test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected User fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(UserTest.class);
    }

    /**
     * Constructs a new User test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UserTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this User test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(User fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this User test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected User getFixture() {
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
        setFixture(UsersFactory.eINSTANCE.createUser());
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
     * Tests the '{@link org.jabylon.users.User#getAllPermissions() <em>Get All Permissions</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.User#getAllPermissions()
     * @generated NOT
     */
    public void testGetAllPermissions() {
        assertEquals(0, getFixture().getAllPermissions().size());
    }

    public void testGetAllPermissionsWithPermissions() {
        Permission userPermission = UsersFactory.eINSTANCE.createPermission();
        getFixture().getPermissions().add(userPermission);
        assertEquals(1, getFixture().getAllPermissions().size());
        assertSame(userPermission, getFixture().getAllPermissions().get(0));
    }

    public void testGetAllPermissionsWithRolePermissions() {
        Role role = UsersFactory.eINSTANCE.createRole();
        getFixture().getRoles().add(role);
        Permission rolePermission = UsersFactory.eINSTANCE.createPermission();
        role.getPermissions().add(rolePermission);
        assertEquals(1, getFixture().getAllPermissions().size());
        assertSame(rolePermission, getFixture().getAllPermissions().get(0));
    }


    public void testGetAllPermissionsMixed() {
        Role role = UsersFactory.eINSTANCE.createRole();
        getFixture().getRoles().add(role);
        Permission userPermission = UsersFactory.eINSTANCE.createPermission();
        Permission rolePermission = UsersFactory.eINSTANCE.createPermission();
        role.getPermissions().add(rolePermission);
        getFixture().getPermissions().add(userPermission);
        assertEquals(2, getFixture().getAllPermissions().size());
        assertSame(userPermission, getFixture().getAllPermissions().get(0));
        assertSame(rolePermission, getFixture().getAllPermissions().get(1));
    }
    
    public void testHasPermission()
    {
        Role role = UsersFactory.eINSTANCE.createRole();
        getFixture().getRoles().add(role);
        Permission rolePermission = UsersFactory.eINSTANCE.createPermission();
        rolePermission.setName("foo:bar:edit");
        role.getPermissions().add(rolePermission);
        assertTrue(getFixture().hasPermission("foo:bar:edit"));
        assertFalse(getFixture().hasPermission("foo:bar:config"));
        assertFalse(getFixture().hasPermission("foo:bar2:edit"));
    }
    
    
    public void testHasPermissionActionInherit()
    {
        Role role = UsersFactory.eINSTANCE.createRole();
        getFixture().getRoles().add(role);
        Permission rolePermission = UsersFactory.eINSTANCE.createPermission();
        rolePermission.setName("foo:bar:config");
        role.getPermissions().add(rolePermission);
        assertTrue(getFixture().hasPermission("foo:bar:config"));
        assertTrue(getFixture().hasPermission("foo:bar:edit"));
        assertTrue(getFixture().hasPermission("foo:bar:suggest"));
        assertTrue(getFixture().hasPermission("foo:bar:view"));
    }
    
    public void testHasPermissionActionInherit2()
    {
        Role role = UsersFactory.eINSTANCE.createRole();
        getFixture().getRoles().add(role);
        Permission rolePermission = UsersFactory.eINSTANCE.createPermission();
        rolePermission.setName("foo:bar:suggest");
        role.getPermissions().add(rolePermission);
        assertFalse(getFixture().hasPermission("foo:bar:config"));
        assertFalse(getFixture().hasPermission("foo:bar:edit"));
        assertTrue(getFixture().hasPermission("foo:bar:suggest"));
        assertTrue(getFixture().hasPermission("foo:bar:view"));
    }

} //UserTest
