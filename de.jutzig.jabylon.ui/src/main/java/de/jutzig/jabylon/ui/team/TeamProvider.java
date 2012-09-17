package de.jutzig.jabylon.ui.team;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyFileDiff;

public interface TeamProvider {

	/**
	 * update the given ProjectVersion and return a list of files that have been modified due to the update operation
	 * @param project
	 * @param monitor
	 * @return
	 * @throws IOException
	 */
	Collection<PropertyFileDiff> update(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	Collection<PropertyFileDiff> update(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws IOException;
	
	void checkout(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	void commit(ProjectVersion project, IProgressMonitor monitor) throws IOException;
	
	void commit(PropertyFileDescriptor descriptor, IProgressMonitor monitor) throws IOException;
	
}
