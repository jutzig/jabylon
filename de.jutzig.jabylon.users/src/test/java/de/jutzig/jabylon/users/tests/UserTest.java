/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.users.tests;

import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UsersFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.users.User#getAllPermissions() <em>Get All Permissions</em>}</li>
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
     * Tests the '{@link de.jutzig.jabylon.users.User#getAllPermissions() <em>Get All Permissions</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.jutzig.jabylon.users.User#getAllPermissions()
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

} //UserTest
