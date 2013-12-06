package org.jabylon.common.progress;

import java.io.Serializable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface RunnableWithProgress extends Serializable{

    IStatus run(IProgressMonitor monitor);

}
