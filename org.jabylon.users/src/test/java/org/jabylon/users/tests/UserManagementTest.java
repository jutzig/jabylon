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
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>User Management</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.users.UserManagement#findUserByName(java.lang.String) <em>Find User By Name</em>}</li>
 *   <li>{@link org.jabylon.users.UserManagement#findPermissionByName(java.lang.String) <em>Find Permission By Name</em>}</li>
 *   <li>{@link org.jabylon.users.UserManagement#findRoleByName(java.lang.String) <em>Find Role By Name</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class UserManagementTest extends TestCase {

    /**
     * The fixture for this User Management test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UserManagement fixture = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(UserManagementTest.class);
    }

    /**
     * Constructs a new User Management test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public UserManagementTest(String name) {
        super(name);
    }

    /**
     * Sets the fixture for this User Management test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void setFixture(UserManagement fixture) {
        this.fixture = fixture;
    }

    /**
     * Returns the fixture for this User Management test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected UserManagement getFixture() {
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
        setFixture(UsersFactory.eINSTANCE.createUserManagement());
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
     * Tests the '{@link org.jabylon.users.UserManagement#findUserByName(java.lang.String) <em>Find User By Name</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.UserManagement#findUserByName(java.lang.String)
     * @generated NOT
     */
    public void testFindUserByName__String() {
        User user = UsersFactory.eINSTANCE.createUser();
        user.setName("test");
        User user2 = UsersFactory.eINSTANCE.createUser();
        user2.setName("test2");
        getFixture().getUsers().add(user);
        getFixture().getUsers().add(user2);

        assertNull(getFixture().findUserByName(null));
        assertNull(getFixture().findUserByName("foo"));
        assertSame(user,getFixture().findUserByName("test"));
        assertSame(user2,getFixture().findUserByName("test2"));
    }

    /**
     * Tests the '{@link org.jabylon.users.UserManagement#findPermissionByName(java.lang.String) <em>Find Permission By Name</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.UserManagement#findPermissionByName(java.lang.String)
     * @generated NOT
     */
    public void testFindPermissionByName__String() {
        Permission object1 = UsersFactory.eINSTANCE.createPermission();
        object1.setName("object1");
        Permission object2 = UsersFactory.eINSTANCE.createPermission();
        object2.setName("object2");
        getFixture().getPermissions().add(object1);
        getFixture().getPermissions().add(object2);

        assertNull(getFixture().findPermissionByName(null));
        assertNull(getFixture().findPermissionByName("foo"));
        assertSame(object1,getFixture().findPermissionByName("object1"));
        assertSame(object2,getFixture().findPermissionByName("object2"));
    }

    /**
     * Tests the '{@link org.jabylon.users.UserManagement#findRoleByName(java.lang.String) <em>Find Role By Name</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.users.UserManagement#findRoleByName(java.lang.String)
     * @generated NOT
     */
    public void testFindRoleByName__String() {
        Role object1 = UsersFactory.eINSTANCE.createRole();
        object1.setName("object1");
        Role object2 = UsersFactory.eINSTANCE.createRole();
        object2.setName("object2");
        getFixture().getRoles().add(object1);
        getFixture().getRoles().add(object2);

        assertNull(getFixture().findRoleByName(null));
        assertNull(getFixture().findRoleByName("foo"));
        assertSame(object1,getFixture().findRoleByName("object1"));
        assertSame(object2,getFixture().findRoleByName("object2"));
    }
    
    public void testUserByToken() {
    	User object0 = UsersFactory.eINSTANCE.createUser();
        object0.setName("object0");
        User object1 = UsersFactory.eINSTANCE.createUser();
        object1.setName("object1");
        object1.setToken("foo");
        User object2 = UsersFactory.eINSTANCE.createUser();
        object2.setName("object2");
        object2.setToken("bar");
        getFixture().getUsers().add(object0);
        getFixture().getUsers().add(object1);
        getFixture().getUsers().add(object2);

        assertNull(getFixture().findUserByToken(null));
        assertNull(getFixture().findUserByToken("test"));
        assertSame(object1,getFixture().findUserByToken("foo"));
        assertSame(object2,getFixture().findUserByToken("bar"));
    }
    
    public void testUserByTokenEmpty() {
    	User object0 = UsersFactory.eINSTANCE.createUser();
        object0.setName("object0");
        object0.setToken("");
        getFixture().getUsers().add(object0);
       
        assertNull("empty tokens are not valid",getFixture().findUserByToken(""));
    }
    
    

} //UserManagementTest
