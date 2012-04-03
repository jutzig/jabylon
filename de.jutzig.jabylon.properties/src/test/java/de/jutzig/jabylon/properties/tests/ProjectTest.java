/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import java.util.Locale;

import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.Workspace;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#getBase() <em>Base</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#fullScan() <em>Full Scan</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ProjectTest extends TestCase {

	/**
	 * The fixture for this Project test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Project fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ProjectTest.class);
	}

	/**
	 * Constructs a new Project test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Project test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Project fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Project test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Project getFixture() {
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
		setFixture(PropertiesFactory.eINSTANCE.createProject());
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
	 * Tests the '{@link de.jutzig.jabylon.properties.Project#getBase() <em>Base</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.Project#getBase()
	 * @generated NOT
	 */
	public void testGetBase() {
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("test"));
		workspace.getProjects().add(getFixture());
		getFixture().setName("test2");
		assertEquals(URI.createFileURI("test/test2"), getFixture().getBase());
	}

	/**
	 * Tests the '{@link de.jutzig.jabylon.properties.Project#fullScan() <em>Full Scan</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.Project#fullScan()
	 * @generated NOT
	 */
	public void testFullScan() {
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("src/test/resources"));
		workspace.getProjects().add(getFixture());
		getFixture().setName("de");
		getFixture().fullScan();
		
		assertEquals(1,getFixture().getPropertyBags().size());
		PropertyBag bag = getFixture().getPropertyBags().get(0);
		
		assertEquals(3,bag.getDescriptors().size());
		assertTrue(bag.getDescriptors().get(0).isMaster());
		
		assertEquals(Locale.GERMAN, bag.getDescriptors().get(1).getVariant());
		
		assertEquals(Locale.CANADA, bag.getDescriptors().get(2).getVariant());
	}

} //ProjectTest
