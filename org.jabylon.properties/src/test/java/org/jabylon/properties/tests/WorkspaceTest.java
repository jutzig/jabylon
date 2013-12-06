/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;

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
