/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Workspace;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class WorkspaceTest extends TestCase {

	/**
	 * The fixture for this Workspace test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Workspace fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(WorkspaceTest.class);
	}

	/**
	 * Constructs a new Workspace test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WorkspaceTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Workspace test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Workspace fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Workspace test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Workspace getFixture() {
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
		setFixture(PropertiesFactory.eINSTANCE.createWorkspace());
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

} //WorkspaceTest
