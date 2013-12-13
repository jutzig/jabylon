/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util.scanner;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.PatternSet.NameEntry;
import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.PropertyScanner;

public class WorkspaceScanner {

    public WorkspaceScanner() {
        // FileSet fs = Util.createFileSet(dir,includes,excludes);

    }

    public void fullScan(PropertyFileAcceptor acceptor, File baseDir, PropertyScanner scanner, ScanConfiguration config, IProgressMonitor monitor) {

        FileSet fs = createFileSet(config);
        fs.setDir(baseDir);
        SubMonitor subMon = SubMonitor.convert(monitor, "Scanning", 100);
        String masterLocale = config.getMasterLocale();
        if (masterLocale != null && masterLocale.isEmpty())
            masterLocale = null;
        if (baseDir.exists()) {
            DirectoryScanner ds = fs.getDirectoryScanner(new org.apache.tools.ant.Project());
            subMon.worked(10);
            String[] files = ds.getIncludedFiles();
            subMon.setWorkRemaining(files.length);
            for (String f : files) {
                File file = new File(baseDir, f);
                if(scanner.isTemplate(file, config)) {
                    subMon.subTask(f);
                    acceptor.newMatch(file);
                    subMon.worked(1);
                }
            }
        }
        if(monitor!=null)
            monitor.done();
    }

    public void partialScan(PropertyFileAcceptor acceptor, File baseDir, PropertyScanner scanner, ScanConfiguration config, File singleFile) {
        Project antProject = new org.apache.tools.ant.Project();
        FileSet fs = createFileSet(config);
        String[] excludes = fs.mergeExcludes(antProject);
        if(excludes!=null)
        {
            for (String exclude : excludes) {
                if(SelectorUtils.match(normalizePattern(exclude), singleFile.getPath()))
                    return;
            }
        }
        String[] includes = fs.mergeIncludes(antProject);
        if(includes==null)
            return;
        for (String include : includes) {
            if(SelectorUtils.match(normalizePattern(include), singleFile.getPath()))
            {
                if (baseDir.exists()) {

//					if(scanner.isTemplate(singleFile, config)) {
                        acceptor.newMatch(singleFile);
//					}

                }
                break;
            }
        }
    }


    public boolean partialScan(File baseDir, PropertyScanner scanner, ScanConfiguration config, File singleFile) {
        SingleFileAcceptor acceptor = new SingleFileAcceptor();
        partialScan(acceptor, baseDir, scanner, config, singleFile);
        return acceptor.isMatch();
    }


    private FileSet createFileSet(ScanConfiguration config) {
        FileSet fs = new FileSet();
        fs.setProject(new Project());
        for (String exclude : config.getExcludes()) {
            NameEntry entry = fs.createExclude();
            entry.setName(exclude);
        }
        for (String include : config.getIncludes()) {
            NameEntry entry = fs.createInclude();
            entry.setName(include);
        }
        return fs;

    }

    private static String normalizePattern(String p) {
        return p.replace('/', File.separatorChar).replace('\\', File.separatorChar);
    }

}
