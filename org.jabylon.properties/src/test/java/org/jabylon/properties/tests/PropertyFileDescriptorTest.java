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

import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.junit.Ignore;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.jabylon.properties.PropertyFileDescriptor#isMaster() <em>Is Master</em>}</li>
 *   <li>{@link org.jabylon.properties.PropertyFileDescriptor#loadProperties() <em>Load Properties</em>}</li>
 *   <li>{@link org.jabylon.properties.PropertyFileDescriptor#loadProperties(java.io.InputStream) <em>Load Properties</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PropertyFileDescriptorTest extends ResolvableTest {

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public static void main(String[] args) {
		TestRunner.run(PropertyFileDescriptorTest.class);
	}

    /**
	 * Constructs a new Property File Descriptor test case with the given name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PropertyFileDescriptorTest(String name) {
		super(name);
	}

    /**
	 * Returns the fixture for this Property File Descriptor test case.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected PropertyFileDescriptor getFixture() {
		return (PropertyFileDescriptor)fixture;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
    @Override
    protected void setUp() throws Exception {
		setFixture(PropertiesFactory.eINSTANCE.createPropertyFileDescriptor());
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
     * Tests the '{@link org.jabylon.properties.PropertyFileDescriptor#isMaster() <em>Master</em>}' feature getter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.PropertyFileDescriptor#isMaster()
     * @generated NOT
     */
    public void testIsMaster() {
        assertFalse(getFixture().isMaster());
        getFixture().setVariant(Locale.FRENCH);
        assertFalse(getFixture().isMaster());
        getFixture().setVariant(null);
        assertFalse(getFixture().isMaster());

        ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
        ProjectLocale master = PropertiesFactory.eINSTANCE.createProjectLocale();
        version.setTemplate(master);
        version.getChildren().add(master);
        master.getDescriptors().add(getFixture());
        assertTrue(getFixture().isMaster());


        ProjectLocale slave = PropertiesFactory.eINSTANCE.createProjectLocale();
        version.getChildren().add(slave);
        slave.getDescriptors().add(getFixture());
        assertFalse(getFixture().isMaster());

    }

    /**
     * Tests the '{@link org.jabylon.properties.PropertyFileDescriptor#loadProperties() <em>Load Properties</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.jabylon.properties.PropertyFileDescriptor#loadProperties()
     * @generated NOT
     */
    @Ignore
    public void testLoadProperties() {
        // TODO: implement this operation test method
        // Ensure that you remove @generated or mark it @generated NOT
//		fail();
    }


    /**
	 * Tests the '{@link org.jabylon.properties.PropertyFileDescriptor#loadProperties(java.io.InputStream) <em>Load Properties</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.jabylon.properties.PropertyFileDescriptor#loadProperties(java.io.InputStream)
	 * @generated NOT
	 */
	public void testLoadProperties__InputStream() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}

	public void testFullPathWithParent() {
        Resolvable parent = mock(Resolvable.class,withSettings().extraInterfaces(InternalEObject.class));
        getFixture().setName("blubb");
        when(parent.fullPath()).thenReturn(URI.createURI("foo"));
        ((InternalEObject)getFixture()).eBasicSetContainer((InternalEObject) parent, 0, null);
        URI expected = URI.createURI("foo");
        expected = expected.appendSegments(getFixture().relativePath().segments());
        assertEquals(expected, getFixture().fullPath());
    }

//	/**
//	 * Tests the '{@link org.jabylon.properties.PropertyFileDescriptor#getPropertyFile() <em>Property File</em>}' feature getter.
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @see org.jabylon.properties.PropertyFileDescriptor#getPropertyFile()
//	 * @generated NOT
//	 */
//	public void testGetPropertyFile() {
//		assertNull(getFixture().getPropertyFile());
//		getFixture().setName("wiki_example.properties");
//		ResourceSet set = new ResourceSetImpl();
//		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Registry.DEFAULT_EXTENSION, new PropertiesResourceFactoryImpl());
//		Resource resource = set.createResource(URI.createFileURI("src"));
//		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
//		resource.getContents().add(workspace);
//		workspace.setRoot(URI.createFileURI("../"));
//
//		Project project = PropertiesFactory.eINSTANCE.createProject();
//		project.setName("org.jabylon.properties");
//		workspace.getProjects().add(project);
//
//		PropertyBag bag = PropertiesFactory.eINSTANCE.createPropertyBag();
//		bag.setPath(URI.createFileURI("src/test/resources/org/jabylon/properties/util"));
//		bag.getDescriptors().add(getFixture());
//		project.getPropertyBags().add(bag);
//
//		PropertyFile propertyFile = getFixture().getPropertyFile();
//		assertNotNull(propertyFile);
//		assertEquals(5, propertyFile.getProperties().size());
//
////		resource
//	}



} //PropertyFileDescriptorTest
