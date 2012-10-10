package de.jutzig.jabylon.common.progress;

import java.io.Serializable;

import org.eclipse.core.runtime.IProgressMonitor;

public interface RunnableWithProgress extends Serializable{

	void run(IProgressMonitor monitor);
	
}
