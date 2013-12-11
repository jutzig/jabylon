/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Locale;

import org.apache.tools.ant.property.GetProperty;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.junit.Ignore;

import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Resolvable;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectVersion#getProject() <em>Project</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.ProjectVersion#fullScan(org.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}</li>
 *   <li>{@link org.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale) <em>Get Project Locale</em>}</li>
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
     * Tests the '{@link org.jabylon.properties.ProjectVersion#getProject() <em>Project</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ProjectVersion#getProject()
     * @generated NOT
     */
    public void testGetProject() {
        assertNull(getFixture().getParent());
        Project project = PropertiesFactory.eINSTANCE.createProject();
        project.getChildren().add(getFixture());
    }

    public void testGetProjectChildVersion() {
        Project project = PropertiesFactory.eINSTANCE.createProject();
        project.getChildren().add(getFixture());
        assertSame(project,getFixture().getParent());
    }

    /**
     * Tests the '{@link org.jabylon.properties.ProjectVersion#fullScan(org.jabylon.properties.ScanConfiguration) <em>Full Scan</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ProjectVersion#fullScan(org.jabylon.properties.ScanConfiguration)
     * @generated
     */
    @Ignore
    public void testFullScan__ScanConfiguration() {
        // TODO: implement this operation test method
        // Ensure that you remove @generated or mark it @generated NOT
//		fail();
    }

    /**
     * Tests the '{@link org.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale) <em>Get Project Locale</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.ProjectVersion#getProjectLocale(java.util.Locale)
     * @generated NOT
     */
    public void testGetProjectLocale__Locale() {
        assertNull(getFixture().getProjectLocale(null));
        ProjectLocale locale1 = PropertiesFactory.eINSTANCE.createProjectLocale();
        ProjectLocale locale2 = PropertiesFactory.eINSTANCE.createProjectLocale();
        Locale frenglish = new Locale("fr","EN");
        Locale denglish = new Locale("de","EN");
        locale1.setLocale(frenglish);
        getFixture().getChildren().add(locale1);
        assertNull(getFixture().getProjectLocale(denglish));

        locale2.setLocale(denglish);
        getFixture().getChildren().add(locale2);
        assertSame(locale2, getFixture().getProjectLocale(denglish));

    }

    @Override
    public void testRelativePath() {
        URI expected = URI.createHierarchicalURI(new String[] {"test"}, null, null);
        getFixture().setName("test");
        assertEquals(expected, getFixture().relativePath());
    }



} //ProjectVersionTest
