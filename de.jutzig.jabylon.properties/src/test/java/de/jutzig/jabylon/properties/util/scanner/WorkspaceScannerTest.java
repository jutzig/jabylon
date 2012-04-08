package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Workspace;


public class WorkspaceScannerTest {

	@Test
	public void testFullScan(){
		Workspace workspace = PropertiesFactory.eINSTANCE.createWorkspace();
		workspace.setRoot(URI.createFileURI("src/test/resources"));
		
		Project project = PropertiesFactory.eINSTANCE.createProject();
		project.setName("de");
		ProjectVersion masterVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
		project.setMaster(masterVersion);
		
		workspace.getProjects().add(project);
		
		WorkspaceScanner scanner = new WorkspaceScanner();
		scanner.fullScan(new PropertyFileAcceptor() {
			
			@Override
			public void newMatch(File file) {
				System.out.println(file.getAbsolutePath());
				
			}
		}, masterVersion);
	}
	
}
