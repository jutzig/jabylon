package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;
import java.util.regex.Pattern;

import de.jutzig.jabylon.properties.ProjectVersion;

public class WorkspaceScanner {

	public void fullScan(PropertyFileAcceptor acceptor, ProjectVersion project,
			String include, String exclude) {
		File baseDir = new File(project.absolutPath().toFileString())
				.getAbsoluteFile();
		searchDirectory(baseDir, include, exclude, acceptor);
	}

	private void searchDirectory(File baseDir, String includeString,
			String excludeString, PropertyFileAcceptor acceptor) {

		Pattern include = Pattern.compile(includeString);
		Pattern exclude = Pattern.compile(excludeString);
		searchDirectory(baseDir, include, exclude, acceptor);

	}

	private void searchDirectory(File baseDir, Pattern include,
			Pattern exclude, PropertyFileAcceptor acceptor) {
		File[] children = baseDir.listFiles();
		for (File child : children) 
		{
			if (child.isDirectory()) 
			{
				searchDirectory(child, include, exclude, acceptor);
			} else 
			{
				String path = child.getAbsolutePath();
				System.out.println(path);
				if (include != null && include.matcher(path).matches())
				{
					if (exclude != null && exclude.matcher(path).matches())
						continue;
					acceptor.newMatch(child);
				}
			}
		}

	}

	public void fullScan(PropertyFileAcceptor acceptor, ProjectVersion project) {
		fullScan(acceptor, project, "[:\\w/.\\\\&&[^_]]+.properties",".*build.properties");
	}

}
