/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import org.eclipse.emf.common.util.URI;

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
public class WorkspaceTest extends ResolvableTest {

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
	 * Returns the fixture for this Workspace test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Workspace getFixture() {
		return (Workspace)fixture;
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
	

	@Override
	public void testRelativePath() {
		assertEquals(URI.createHierarchicalURI(new String[] {""}, null, null), getFixture().relativePath());
		
	}
	
	@Override
	public void testRelativePathNullSafe() {
		assertEquals("Workspace always has an empty relative path",URI.createHierarchicalURI(new String[] {""}, null, null), getFixture().relativePath());
	}
	

} //WorkspaceTest
