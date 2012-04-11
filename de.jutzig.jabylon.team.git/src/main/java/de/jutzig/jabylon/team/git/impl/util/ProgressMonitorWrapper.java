package de.jutzig.jabylon.team.git.impl.util;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;

public class ProgressMonitorWrapper implements ProgressMonitor {

	
	private SubMonitor delegate;
	private SubMonitor currentChild;
	
	
	
	public ProgressMonitorWrapper(SubMonitor delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void start(int totalTasks) {
		delegate.beginTask("", totalTasks);

	}

	@Override
	public void beginTask(String title, int totalWork) {
		delegate.newChild(totalWork);
		delegate.setTaskName(title);

	}

	@Override
	public void update(int completed) {
		if(currentChild!=null)
			currentChild.worked(completed);
		else
			delegate.worked(completed);

	}

	@Override
	public void endTask() {
		if(currentChild!=null)
		{
			currentChild.done();			
		}
		currentChild=null;

	}

	@Override
	public boolean isCancelled() {
		return delegate.isCanceled();
	}

}
