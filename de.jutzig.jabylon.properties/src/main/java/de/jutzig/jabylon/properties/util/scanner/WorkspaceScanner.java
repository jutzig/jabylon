package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.PatternSet.NameEntry;

import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.ScanConfiguration;

public class WorkspaceScanner {

	private static final Pattern LOCALE_PATTERN = Pattern.compile(".+?((_\\w\\w){1,3})\\..+");

	public WorkspaceScanner() {
		// FileSet fs = Util.createFileSet(dir,includes,excludes);

	}

	public void fullScan(PropertyFileAcceptor acceptor, ProjectVersion project, ScanConfiguration config) {
		File baseDir = new File(project.absolutPath().toFileString()).getAbsoluteFile();
		FileSet fs = createFileSet(baseDir, config);
		String masterLocale = config.getMasterLocale();
		if (masterLocale != null && masterLocale.isEmpty())
			masterLocale = null;
		if (baseDir.exists()) {
			DirectoryScanner ds = fs.getDirectoryScanner(new org.apache.tools.ant.Project());
			for (String f : ds.getIncludedFiles()) {
				if (matchesLocale(f, masterLocale)) {
					File file = new File(baseDir, f);
					acceptor.newMatch(file);
				}
			}
		}

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

	private FileSet createFileSet(File baseDir, ScanConfiguration config) {
		FileSet fs = new FileSet();
		fs.setDir(baseDir);
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
