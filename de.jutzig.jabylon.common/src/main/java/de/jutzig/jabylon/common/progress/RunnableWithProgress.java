package de.jutzig.jabylon.common.progress;

import org.eclipse.core.runtime.IProgressMonitor;

public interface RunnableWithProgress {

	void run(IProgressMonitor monitor);
	
}
