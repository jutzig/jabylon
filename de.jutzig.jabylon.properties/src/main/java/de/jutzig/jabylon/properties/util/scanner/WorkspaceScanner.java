package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;

import org.apache.tools.ant.types.selectors.SelectorUtils;

import de.jutzig.jabylon.properties.Project;

public class WorkspaceScanner {
	
	public void fullScan(PropertyFileAcceptor acceptor, Project project, String include, String exclude)
	{
		File baseDir = new File(project.getBase().toFileString()).getAbsoluteFile();
		searchDirectory(baseDir, include, exclude, acceptor);
	}
	
	
	private void searchDirectory(File baseDir, String include, String exclude, PropertyFileAcceptor acceptor) {
		File[] children = baseDir.listFiles();
		for (File child : children) {
			if(child.isDirectory())
			{
				if(include!=null && SelectorUtils.matchPatternStart(include, child.getAbsolutePath()))
				{
					if(exclude!=null && SelectorUtils.matchPatternStart(exclude, child.getAbsolutePath()))
						continue;
					searchDirectory(child, include, exclude, acceptor);
				}
			}
			else
			{
				if(include!=null && SelectorUtils.matchPath(include, child.getAbsolutePath()))
				{
					if(exclude!=null && SelectorUtils.matchPath(exclude, child.getAbsolutePath()))
						continue;
					acceptor.newMatch(child);
				}
			}
		}
		
	}


	public void fullScan(PropertyFileAcceptor acceptor, Project project)
	{
		fullScan(acceptor, project, "/**/messages.properties",null);
	}
	
	
}
