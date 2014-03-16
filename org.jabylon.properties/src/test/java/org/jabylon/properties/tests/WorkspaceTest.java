/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.tests;

import junit.textui.TestRunner;

import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Workspace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.Workspace#getTerminology() <em>Get Terminology</em>}</li>
 * </ul>
 * </p>
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


    /**
	 * Tests the '{@link org.jabylon.properties.Workspace#getTerminology() <em>Get Terminology</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.Workspace#getTerminology()
	 * @generated NOT
	 */
	public void testGetTerminology() {
		assertNull(getFixture().getTerminology());
		Project project = PropertiesFactory.eINSTANCE.createProject();
		ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
		project.getChildren().add(version);
		getFixture().getChildren().add(project);
		assertNull(getFixture().getTerminology());
		project.setTerminology(true);
		assertSame(version,getFixture().getTerminology());
	}

				@Override
    public void testRelativePath() {
        assertNull(getFixture().relativePath());

    }

    @Override
    public void testRelativePathNullSafe() {
        assertNull(getFixture().relativePath());
    }

    public void testFullPathWithParent() {
        assertNull(getFixture().fullPath());
    }

} //WorkspaceTest
