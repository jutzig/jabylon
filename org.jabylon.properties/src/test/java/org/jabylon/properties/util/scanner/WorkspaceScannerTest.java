/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util.scanner;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.types.impl.JavaPropertyScanner;

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
        }, baseDir, new JavaPropertyScanner(), PropertiesFactory.eINSTANCE.createScanConfiguration(), null);
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
        }, baseDir, new JavaPropertyScanner(), configuration, null);
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
        }, baseDir, new JavaPropertyScanner(), configuration, null);
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
        }, baseDir, new JavaPropertyScanner(), PropertiesFactory.eINSTANCE.createScanConfiguration(), new File("src/test/resources/project/master/org/jabylon/properties/util/autotranslate/messages.properties"));
        int index = 0;
        assertEquals(1, filenames.size());
        assertEquals("messages.properties", filenames.get(index++));


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
        }, baseDir, new JavaPropertyScanner(), scanConfig, new File("src/test/resources/project/master/org/jabylon/properties/util/autotranslate/messages.properties"));
        int index = 0;
        assertEquals(1, filenames.size());
        assertEquals("messages.properties", filenames.get(index++));
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
        }, baseDir, new JavaPropertyScanner(), scanConfig, new File("src/test/resources/project/master/org/jabylon/properties/util/autotranslate/messages.properties"));
        assertEquals(0, filenames.size());
    }

}
