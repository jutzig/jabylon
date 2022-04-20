/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.team.git;

import static org.junit.Assume.assumeNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.jabylon.common.util.FileUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Workspace;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.service.prefs.Preferences;

public class GitTeamProviderTest {

	//e.g. "ssh://git.seeburger.de:29518/example/playground.git";
	private static final String TEST_REPO = null;
	private GitTeamProvider fixture;
	private Project project;
	private Preferences projectNode;
	private Preferences versionNode;
	private File workspaceRoot;

	@Before
	public void setup() throws IOException
	{
		projectNode = mock(Preferences.class);
		versionNode = mock(Preferences.class);

		fixture = new GitTeamProvider() {
			@Override
			protected Preferences getPreferences(Object o) {
				if (o instanceof ProjectVersion) {
					return versionNode;
				}
				return projectNode;
			}
		};
		Workspace workspace  = PropertiesFactory.eINSTANCE.createWorkspace();

		workspaceRoot = Files.createTempDirectory("gitteamprovider").toFile();
		FileUtil.delete(workspaceRoot);
		workspace.setRoot(URI.createURI(workspaceRoot.toURI().toString()));
		project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("foo");
		project.setParent(workspace);


		ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
		version.setName("master");
		version.setParent(project);
	}

	@After
	public void cleanup() throws InterruptedException
	{
		if(workspaceRoot!=null)
			FileUtil.delete(workspaceRoot);
	}

	@Test
	public void testCheckout() {
		assumeNotNull(TEST_REPO);
		project.setRepositoryURI(URI.createURI(TEST_REPO));
		when(versionNode.get(Mockito.eq(GitConstants.KEY_BRANCH_NAME), Mockito.anyString())).thenReturn("master");
		when(projectNode.get(Mockito.eq(GitConstants.KEY_USERNAME), Mockito.anyString())).thenReturn("utzig");
		when(projectNode.get(Mockito.eq(GitConstants.KEY_PASSWORD), Mockito.anyString())).thenReturn("");

		fixture.checkout(project.getChildren().get(0), SubMonitor.convert(null));
	}

	@Test
	public void testCheckoutWithPrivateKey() throws IOException {
		assumeNotNull(TEST_REPO);
		String key = new String(Files.readAllBytes(new File(System.getProperty("user.home"),".ssh/id_dsa").toPath()), StandardCharsets.UTF_8);
		project.setRepositoryURI(URI.createURI(TEST_REPO));
		when(versionNode.get(Mockito.eq(GitConstants.KEY_BRANCH_NAME), Mockito.anyString())).thenReturn("master");
		when(projectNode.get(Mockito.eq(GitConstants.KEY_USERNAME), Mockito.anyString())).thenReturn("utzig");
		when(projectNode.get(Mockito.eq(GitConstants.KEY_PASSWORD), Mockito.anyString())).thenReturn("");
		when(projectNode.get(Mockito.eq(GitConstants.KEY_PRIVATE_KEY), Mockito.anyString())).thenReturn(key);

		fixture.checkout(project.getChildren().get(0), SubMonitor.convert(null));
	}

}
