/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties.tests;

import java.util.Locale;

import org.eclipse.emf.common.util.URI;

import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project Locale</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectLocale#getProjectVersion() <em>Project Version</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectLocale#isMaster() <em>Is Master</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ProjectLocaleTest extends ResolvableTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static void main(String[] args) {
        TestRunner.run(ProjectLocaleTest.class);
    }

    /**
     * Constructs a new Project Locale test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProjectLocaleTest(String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Project Locale test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected ProjectLocale getFixture() {
        return (ProjectLocale)fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see junit.framework.TestCase#setUp()
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(PropertiesFactory.eINSTANCE.createProjectLocale());
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
     * Tests the '{@link org.jabylon.properties.ProjectLocale#getProjectVersion() <em>Project Version</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ProjectLocale#getProjectVersion()
     * @generated NOT
     */
    public void testGetProjectVersion() {
        assertNull(getFixture().getParent());
        ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
        version.getChildren().add(getFixture());
        assertSame(version,getFixture().getParent());
    }


    /**
     * Tests the '{@link org.jabylon.properties.ProjectLocale#isMaster() <em>Is Master</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ProjectLocale#isMaster()
     * @generated NOT
     */
    public void testIsMaster() {
        assertFalse(getFixture().isMaster());
    }

    public void testIsMasterSlaveLocale() {
        ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
        version.getChildren().add(getFixture());
        assertFalse(getFixture().isMaster());
    }


    public void testIsMasterMasterLocale() {
        ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
        version.getChildren().add(getFixture());
        version.setTemplate(getFixture());
        assertTrue(getFixture().isMaster());
    }

    @Override
    public void testRelativePath() {
        URI expected = URI.createURI("");
        getFixture().setLocale(new Locale("pl","PL"));
        assertEquals("locales are virtual, they have an empty relative path",expected, getFixture().relativePath());
    }

    @Override
    public void testRelativePathNullSafe() {
        URI expected = URI.createURI("");
        getFixture().setLocale(new Locale("pl","PL"));
        assertEquals("locales are virtual, they have an empty relative path",expected, getFixture().relativePath());
    }

} //ProjectLocaleTest
