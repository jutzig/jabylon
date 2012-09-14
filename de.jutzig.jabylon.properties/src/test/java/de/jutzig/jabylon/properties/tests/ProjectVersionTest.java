/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import org.eclipse.emf.common.util.URI;
import org.junit.Ignore;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getProject() <em>Project</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#fullScan(de.jutzig.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale) <em>Get Project Locale</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ProjectVersionTest extends ResolvableTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ProjectVersionTest.class);
	}

	/**
	 * Constructs a new Project Version test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectVersionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Project Version test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ProjectVersion getFixture() {
		return (ProjectVersion)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(PropertiesFactory.eINSTANCE.createProjectVersion());
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
	 * Tests the '{@link de.jutzig.jabylon.properties.ProjectVersion#getProject() <em>Project</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getProject()
	 * @generated NOT
	 */
	public void testGetProject() {
		assertNull(getFixture().getProject());
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.getVersions().add(getFixture());
	}
	
	public void testGetProjectChildVersion() {
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.getVersions().add(getFixture());
		assertSame(project,getFixture().getProject());
	}

	public void testGetProjectMasterVersion() {
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setMaster(getFixture());
		assertSame(project,getFixture().getProject());
	}
	
	/**
	 * Tests the '{@link de.jutzig.jabylon.properties.ProjectVersion#fullScan(de.jutzig.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.ProjectVersion#fullScan(de.jutzig.jabylon.properties.ScanConfiguration)
	 * @generated
	 */
	@Ignore
	public void testFullScan__ScanConfiguration() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}

	/**
	 * Tests the '{@link de.jutzig.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale) <em>Get Project Locale</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale)
	 * @generated
	 */
	public void testGetProjectLocale__Locale() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}
	
	@Override
	public void testRelativePath() {
		URI expected = URI.createHierarchicalURI(new String[] {"test"}, null, null);
		getFixture().setBranch("test");
		assertEquals(expected, getFixture().relativePath());
	}
	
	@Override
	public void testRelativePathNullSafe() {
		URI expected = URI.createHierarchicalURI(new String[] {"master"}, null, null);
		assertEquals("if no branch is set, it is 'master'",expected, getFixture().relativePath());
	}

} //ProjectVersionTest
