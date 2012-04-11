package de.jutzig.jabylon.ui.team;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

public interface TeamProvider {

	Iterable<File> update(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	Iterable<File> update(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws IOException;
	
	void checkout(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	void commit(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	void commit(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws IOException;
	
}
