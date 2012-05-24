package de.jutzig.jabylon.properties.util.scanner;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.Workspace;


public class WorkspaceScannerTest {

	@Test
	public void testFullScan(){
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("src/test/resources"));
		
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("project");
		ProjectVersion masterVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
		project.setMaster(masterVersion);
		
		workspace.getProjects().add(project);
		
		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {
			
			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());
				
			}
		}, masterVersion, PropertiesFactory.eINSTANCE.createScanConfiguration());
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("messages2.properties", filenames.get(index++));
		assertEquals("plugin.properties", filenames.get(index++));
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("wiki_example.properties", filenames.get(index++));
		assertEquals(5,filenames.size());
	}
	
	@Test
	public void testFullScanWithFileExcude(){
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("src/test/resources"));
		
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("project");
		ProjectVersion masterVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
		project.setMaster(masterVersion);
		ScanConfiguration configuration = PropertiesFactory.eINSTANCE.createScanConfiguration();
		configuration.setExclude("**/wiki_examp*.properties");
		workspace.getProjects().add(project);
		
		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {
			
			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());
				
			}
		}, masterVersion, configuration);
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("messages2.properties", filenames.get(index++));
		assertEquals("plugin.properties", filenames.get(index++));
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals(4,filenames.size());
	}
	
	@Test
	public void testFullScanWithMasterLocale(){
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("src/test/resources"));
		
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("project");
		ProjectVersion masterVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
		project.setMaster(masterVersion);
		ScanConfiguration configuration = PropertiesFactory.eINSTANCE.createScanConfiguration();
		configuration.setMasterLocale("en_CA");
		workspace.getProjects().add(project);
		
		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {
			
			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());
				
			}
		}, masterVersion, configuration);
		assertEquals(1,filenames.size());
		int index = 0;
		assertEquals("messages_en_CA.properties", filenames.get(index++));
	}
	
}
