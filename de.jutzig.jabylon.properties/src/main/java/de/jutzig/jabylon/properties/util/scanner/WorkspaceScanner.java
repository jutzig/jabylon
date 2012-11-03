package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.PatternSet.NameEntry;
import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import de.jutzig.jabylon.properties.ScanConfiguration;

public class WorkspaceScanner {

	private static final Pattern LOCALE_PATTERN = Pattern.compile(".+?((_\\w\\w){1,3})\\..+");

	public WorkspaceScanner() {
		// FileSet fs = Util.createFileSet(dir,includes,excludes);

	}

	public void fullScan(PropertyFileAcceptor acceptor, File baseDir, ScanConfiguration config, IProgressMonitor monitor) {
		
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
				if (matchesLocale(f, masterLocale)) {
					File file = new File(baseDir, f);
					subMon.subTask(f);
					acceptor.newMatch(file);
					subMon.worked(1);
				}
			}
		}
		if(monitor!=null)
			monitor.done();
	}

	public void partialScan(PropertyFileAcceptor acceptor, File baseDir, ScanConfiguration config, File singleFile) {
		Project antProject = new org.apache.tools.ant.Project();
		FileSet fs = createFileSet(config);
		String[] excludes = fs.mergeExcludes(antProject);
		if(excludes!=null)
		{
			for (String exclude : excludes) {
				if(/*SelectorUtils.matchPatternStart(exclude, singleFile.getPath()) && */ SelectorUtils.match(exclude, singleFile.getPath()))
					return;
			}			
		}
		String[] includes = fs.mergeIncludes(antProject);
		if(includes==null)
			return;
		for (String include : includes) {
			//TODO: matchPatternStart fails for:
			// **/*.properties
			// /home/joe/workspaces/translator/work/workspace/jabylon-testing/master/testfiles/folder/child2/Messages2_de.properties
			if(/*SelectorUtils.matchPatternStart(include, singleFile.getPath()) && */ SelectorUtils.match(include, singleFile.getPath()))
			{
				String masterLocale = config.getMasterLocale();
				if (masterLocale != null && masterLocale.isEmpty())
					masterLocale = null;
				if (baseDir.exists()) {

					if (matchesLocale(singleFile.getName(), masterLocale)) {
						acceptor.newMatch(singleFile);
					}

				}
				break;
			}
		}
	}

	
	public boolean partialScan(File baseDir, ScanConfiguration config, File singleFile) {
		SingleFileAcceptor acceptor = new SingleFileAcceptor();
		partialScan(acceptor, baseDir, config, singleFile);
		return acceptor.isMatch();
	}
	
	private boolean matchesLocale(String f, String masterLocale) {

		if (masterLocale == null)
			return !LOCALE_PATTERN.matcher(f).matches();

		Matcher matcher = LOCALE_PATTERN.matcher(f);
		if (matcher.matches()) {
			String locale = matcher.group(1);
			locale = locale.substring(1);
			return locale.equals(masterLocale);
		}
		return false;
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

}
