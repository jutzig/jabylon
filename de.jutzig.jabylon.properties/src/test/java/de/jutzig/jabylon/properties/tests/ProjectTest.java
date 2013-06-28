/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Project#fullScan(de.jutzig.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ProjectTest extends ResolvableTest {

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
     * Returns the fixture for this Project test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Project getFixture() {
        return (Project)fixture;
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
     * Tests the '{@link de.jutzig.jabylon.properties.Project#fullScan(de.jutzig.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see de.jutzig.jabylon.properties.Project#fullScan(de.jutzig.jabylon.properties.ScanConfiguration)
     * @generated NOT
     */
    public void testFullScan__ScanConfiguration() {
        getFixture().fullScan(PropertiesFactory.eINSTANCE.createScanConfiguration());
        //must not raise an exception
    }

    public void testFullScanWithSingelVersion() {
        ProjectVersion master = mock(ProjectVersion.class,withSettings().extraInterfaces(InternalEObject.class));
        ScanConfiguration scanConfiguration = PropertiesFactory.eINSTANCE.createScanConfiguration();
        getFixture().getChildren().add(master);
        getFixture().fullScan(scanConfiguration);
        verify(master).fullScan(scanConfiguration, null);
    }

    public void testFullScanWithVersions() {
        ProjectVersion v1 = mock(ProjectVersion.class,withSettings().extraInterfaces(InternalEObject.class));
        ProjectVersion v2 = mock(ProjectVersion.class,withSettings().extraInterfaces(InternalEObject.class));
        ScanConfiguration scanConfiguration = PropertiesFactory.eINSTANCE.createScanConfiguration();
        getFixture().getChildren().add(v1);
        getFixture().getChildren().add(v2);
        getFixture().fullScan(scanConfiguration);
        verify(v1).fullScan(scanConfiguration, null);
        verify(v2).fullScan(scanConfiguration, null);
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
        workspace.getChildren().add(getFixture());
        getFixture().setName("test2");
//		assertEquals(URI.createFileURI("test/test2"), getFixture().getBase());
    }

    @Override
    public void testRelativePath() {
        URI expected = URI.createHierarchicalURI(new String[] {"test"}, null, null);
        getFixture().setName("test");
        assertEquals(expected, getFixture().relativePath());
    }

    public void testFullPathWithParent() {
        getFixture().setName("project");
        Resolvable parent = mock(Resolvable.class,withSettings().extraInterfaces(InternalEObject.class));
        when(parent.fullPath()).thenReturn(URI.createURI("foo"));
        ((InternalEObject)getFixture()).eBasicSetContainer((InternalEObject) parent, 0, null);
        URI expected = URI.createURI("foo");
        expected = expected.appendSegments(getFixture().relativePath().segments());
        assertEquals(expected, getFixture().fullPath());
    }

//	/**
//	 * Tests the '{@link de.jutzig.jabylon.properties.Project#fullScan() <em>Full Scan</em>}' operation.
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @see de.jutzig.jabylon.properties.Project#fullScan()
//	 * @generated NOT
//	 */
//	public void testFullScan() {
//		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
//		workspace.setRoot(URI.createFileURI("src/test/resources"));
//		workspace.getProjects().add(getFixture());
//		getFixture().setName("de");
//		getFixture().fullScan();
//
//		assertEquals(1,getFixture().getPropertyBags().size());
//		PropertyBag bag = getFixture().getPropertyBags().get(0);
//
//		assertEquals(3,bag.getDescriptors().size());
//		assertTrue(bag.getDescriptors().get(0).isMaster());
//
//		assertEquals(Locale.GERMAN, bag.getDescriptors().get(1).getVariant());
//
//		assertEquals(Locale.CANADA, bag.getDescriptors().get(2).getVariant());
//	}

} //ProjectTest
