/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util.scanner;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Locale;

import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.types.impl.JavaPropertyScanner;
import org.jabylon.properties.util.PropertyResourceUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PartialScanFileAcceptorTest {


	private PartialScanFileAcceptor fixture;
	private ProjectLocale translation;
	private ProjectLocale template;


	@Before
	public void setup() {
		fixture = createFixture();
	}

	protected PartialScanFileAcceptor createFixture(){
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setPropertyType(JavaPropertyScanner.TYPE);
		workspace.getChildren().add(project);
		URI uri = URI.createFileURI(new File("workspace").getPath());
		workspace.setRoot(uri);
		project.setName("test");
		ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
		version.setName("master");
		project.getChildren().add(version);
		template = PropertiesFactory.eINSTANCE.createProjectLocale();
		template.setName("template");
		version.getChildren().add(template);
		version.setTemplate(template);
		translation = PropertiesFactory.eINSTANCE.createProjectLocale();
		translation.setLocale(Locale.GERMAN);
		version.getChildren().add(translation);

		return new PartialScanFileAcceptor(version, new JavaPropertyScanner(), PropertiesFactory.eINSTANCE.createScanConfiguration());
	}

	/**
	 * tests that an already existing template match is not duplicated
	 * see https://github.com/jutzig/jabylon/issues/210
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testNewMatchExistingTemplate() throws Exception {
		URI uri = URI.createFileURI("foo/test.properties");
		PropertyFileDescriptor descriptor = getFixture().createDescriptor(template, uri);
		template.getDescriptors().add(descriptor);
		Resolvable folder = PropertyResourceUtil.getOrCreateFolder(template, "foo");
		folder.getChildren().add(descriptor);

		uri = URI.createFileURI("foo/test_de.properties");
		PropertyFileDescriptor translationDescriptor = getFixture().createDescriptor(translation, uri);
		translation.getDescriptors().add(translationDescriptor);
		folder = PropertyResourceUtil.getOrCreateFolder(translation, "foo");
		folder.getChildren().add(translationDescriptor);
		translationDescriptor.setMaster(descriptor);
		getFixture().newMatch(new File("workspace/test/master/foo/test.properties"));

		assertEquals("Must not be added again",1,template.getChild("foo").getChildren().size());
		assertEquals("Must not be added again",1,translation.getChild("foo").getChildren().size());
	}

	/**
	 * tests that an already existing template match is not duplicated
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testNewMatchAdditionalTemplate() throws Exception {
		URI uri = URI.createURI("foo/test.properties");
		PropertyFileDescriptor descriptor = getFixture().createDescriptor(template, uri);
		template.getDescriptors().add(descriptor);
		Resolvable folder = PropertyResourceUtil.getOrCreateFolder(template, "foo");
		folder.getChildren().add(descriptor);

		uri = URI.createURI("foo/test_de.properties");
		PropertyFileDescriptor translationDescriptor = getFixture().createDescriptor(template, uri);
		translation.getDescriptors().add(translationDescriptor);
		folder = PropertyResourceUtil.getOrCreateFolder(translation, "foo");
		folder.getChildren().add(translationDescriptor);
		translationDescriptor.setMaster(descriptor);
		getFixture().newMatch(new File("workspace/test/master/foo/test2.properties"));

		assertEquals("Must be added",2,template.getChild("foo").getChildren().size());
		assertEquals("Must be added",2,translation.getChild("foo").getChildren().size());
	}



    /**
     * see https://github.com/jutzig/jabylon/issues/225
     */
    @Test
    public void testComputeLocationWindows()
    {
    	ProjectVersion projectVersion = Mockito.mock(ProjectVersion.class);
    	Mockito.when(projectVersion.absolutPath()).thenReturn(URI.createFileURI("/C:/tests/jabylon/workspace/test/master"));
    	PartialScanFileAcceptor acceptor = new PartialScanFileAcceptor(projectVersion, null, null);
    	URI location = acceptor.calculateLocation(new File("/c:/tests/jabylon/workspace/test/master/core/build/internalartifacts.properties"));
    	assertEquals("windows FS is case insensitive","core/build/internalartifacts.properties", location.toString());
    }

    /**
     * see https://github.com/jutzig/jabylon/issues/225
     */
    @Test
    public void testComputeLocationWindows2()
    {
    	ProjectVersion projectVersion = Mockito.mock(ProjectVersion.class);
    	Mockito.when(projectVersion.absolutPath()).thenReturn(URI.createFileURI("C:/tests/jabylon/workspace/test/master/"));
    	PartialScanFileAcceptor acceptor = new PartialScanFileAcceptor(projectVersion, null, null);
    	URI location = acceptor.calculateLocation(new File("/c:/tests/jabylon/workspace/test/master/core/build/internalartifacts.properties"));
    	assertEquals("must not matter if there was a trailing slash or not","core/build/internalartifacts.properties", location.toString());
    }

    /**
     * see https://github.com/jutzig/jabylon/issues/225
     */
    @Test
    public void testComputeLocationWindows3()
    {
    	ProjectVersion projectVersion = Mockito.mock(ProjectVersion.class);
    	Mockito.when(projectVersion.absolutPath()).thenReturn(URI.createFileURI("C:\\tests\\jabylon\\workspace\\test\\master\\"));
    	PartialScanFileAcceptor acceptor = new PartialScanFileAcceptor(projectVersion, null, null);
    	URI location = acceptor.calculateLocation(new File("/c:/tests/jabylon/workspace/test/master/core/build/internalartifacts.properties"));
    	assertEquals("back slash or forward slash should not matter","core/build/internalartifacts.properties", location.toString());
    }

	public PartialScanFileAcceptor getFixture() {
		return fixture;
	}

}
