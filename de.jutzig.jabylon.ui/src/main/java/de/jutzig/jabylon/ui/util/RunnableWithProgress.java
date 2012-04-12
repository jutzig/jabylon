package de.jutzig.jabylon.ui.util;

import org.eclipse.core.runtime.IProgressMonitor;

public interface RunnableWithProgress {

	void run(IProgressMonitor monitor);
	
}
