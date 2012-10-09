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
	public void testFullScan() {
		File baseDir = new File("src/test/resources/project/master");

		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, PropertiesFactory.eINSTANCE.createScanConfiguration(), null);
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("messages2.properties", filenames.get(index++));
		assertEquals("plugin.properties", filenames.get(index++));
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("wiki_example.properties", filenames.get(index++));
		assertEquals(5, filenames.size());
	}

	@Test
	public void testFullScanWithFileExcude() {
		File baseDir = new File("src/test/resources/project/master");
		ScanConfiguration configuration = PropertiesFactory.eINSTANCE.createScanConfiguration();
		configuration.setExclude("**/wiki_examp*.properties");

		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, configuration, null);
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals("messages2.properties", filenames.get(index++));
		assertEquals("plugin.properties", filenames.get(index++));
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals(4, filenames.size());
	}

	@Test
	public void testFullScanWithMasterLocale() {
		File baseDir = new File("src/test/resources/project/master");
		ScanConfiguration configuration = PropertiesFactory.eINSTANCE.createScanConfiguration();
		configuration.setMasterLocale("en_CA");
		

		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.fullScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, configuration, null);
		assertEquals(1, filenames.size());
		int index = 0;
		assertEquals("messages_en_CA.properties", filenames.get(index++));
	}

	@Test
	public void testPartialScan() throws Exception {

		
		File baseDir = new File("src/test/resources/project/master");

		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.partialScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, PropertiesFactory.eINSTANCE.createScanConfiguration(), new File("src/test/resources/project/master/de/jutzig/jabylon/properties/util/autotranslate/messages.properties"));
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals(1, filenames.size());
		
	
	}
	
	@Test
	public void testPartialScanWithComplicatedInclude()
	{
		File baseDir = new File("src/test/resources/project/master");
		ScanConfiguration scanConfig = PropertiesFactory.eINSTANCE.createScanConfiguration();
		scanConfig.setInclude("**/jabylon/properties/util/autotranslate/*.properties");
		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.partialScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, scanConfig, new File("src/test/resources/project/master/de/jutzig/jabylon/properties/util/autotranslate/messages.properties"));
		int index = 0;
		assertEquals("messages.properties", filenames.get(index++));
		assertEquals(1, filenames.size());
	}
	
	@Test
	public void testPartialScanWithExclude()
	{
		File baseDir = new File("src/test/resources/project/master");
		//try more complicated include
		ScanConfiguration scanConfig = PropertiesFactory.eINSTANCE.createScanConfiguration();
		scanConfig.setExclude("**/jabylon/properties/util/autotranslate/*.properties");
		WorkspaceScanner scanner = new WorkspaceScanner();
		final List<String> filenames = new ArrayList<String>();
		scanner.partialScan(new PropertyFileAcceptor() {

			@Override
			public void newMatch(File file) {
				filenames.add(file.getName());

			}
		}, baseDir, scanConfig, new File("src/test/resources/project/master/de/jutzig/jabylon/properties/util/autotranslate/messages.properties"));
		assertEquals(0, filenames.size());
	}

}
