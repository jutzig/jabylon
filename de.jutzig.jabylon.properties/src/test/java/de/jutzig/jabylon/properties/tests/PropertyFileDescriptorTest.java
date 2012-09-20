/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Locale;

import junit.textui.TestRunner;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.junit.Ignore;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster() <em>Is Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#loadProperties() <em>Load Properties</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#computeLocation() <em>Compute Location</em>}</li>
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
	 * Tests the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster() <em>Master</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#isMaster()
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
		
		master.getDescriptors().add(getFixture());
		assertTrue(getFixture().isMaster());
		
		
		ProjectLocale slave = PropertiesFactory.eINSTANCE.createProjectLocale();
		version.getChildren().add(slave);
		slave.getDescriptors().add(getFixture());
		assertFalse(getFixture().isMaster());
		
	}

	/**
	 * Tests the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#loadProperties() <em>Load Properties</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#loadProperties()
	 * @generated
	 */
	@Ignore
	public void testLoadProperties() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
//		fail();
	}
	
	/**
	 * Tests the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#computeLocation() <em>Compute Location</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#computeLocation()
	 * @generated NOT
	 */
	public void testComputeLocation() {
		PropertyFileDescriptor master = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
		master.setLocation(URI.createURI("file://project/dir/master.properties"));
		getFixture().setVariant(new Locale("de", "DE"));
		getFixture().computeLocation(); //must not fail if no master is available
		assertNull(getFixture().getLocation());
		getFixture().setMaster(master);
		getFixture().computeLocation();
		assertEquals("file://project/dir/master_de_DE.properties", getFixture().getLocation().toString());
	}
	
	public void testComputeLocationWithLocaledMaster() {
		PropertyFileDescriptor master = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
		master.setLocation(URI.createURI("file://project/dir/master_en_EN.properties"));
		master.setVariant(new Locale("en_EN"));
		getFixture().setVariant(new Locale("de", "DE"));
		getFixture().setMaster(master);
		getFixture().computeLocation();
		assertEquals("file://project/dir/master_de_DE.properties", getFixture().getLocation().toString());
		
		
		master.setLocation(URI.createURI("file://project/dir/master_en_EN_FO.properties"));
		master.setVariant(new Locale("en","EN","FO"));
		getFixture().computeLocation();
		assertEquals("file://project/dir/master_de_DE.properties", getFixture().getLocation().toString());
	}

	@Override
	public void testRelativePath() {
		URI uri = URI.createFileURI("foo");
		getFixture().setLocation(uri);
		assertSame(uri, getFixture().relativePath());
	}
	
	public void testFullPathWithParent() {
		Resolvable parent = mock(Resolvable.class,withSettings().extraInterfaces(InternalEObject.class));
		getFixture().setLocation(URI.createURI("/bar/blubb"));
		when(parent.fullPath()).thenReturn(URI.createURI("foo"));
		((InternalEObject)getFixture()).eBasicSetContainer((InternalEObject) parent, 0, null);
		URI expected = URI.createURI("foo");
		expected = expected.appendSegments(getFixture().relativePath().segments());
		assertEquals(expected, getFixture().fullPath());
	}
	
//	/**
//	 * Tests the '{@link de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile() <em>Property File</em>}' feature getter.
//	 * <!-- begin-user-doc -->
//	 * <!-- end-user-doc -->
//	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getPropertyFile()
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
//		project.setName("de.jutzig.jabylon.properties");
//		workspace.getProjects().add(project);
//		
//		PropertyBag bag = PropertiesFactory.eINSTANCE.createPropertyBag();
//		bag.setPath(URI.createFileURI("src/test/resources/de/jutzig/jabylon/properties/util"));
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
