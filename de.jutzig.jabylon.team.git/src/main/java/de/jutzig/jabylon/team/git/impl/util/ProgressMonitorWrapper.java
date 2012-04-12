package de.jutzig.jabylon.team.git.impl.util;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;

public class ProgressMonitorWrapper implements ProgressMonitor {

	
	private SubMonitor delegate;
	private SubMonitor currentChild;
	private boolean inIndeterminatedMode;
	private int totalTicks;
	
	
	public ProgressMonitorWrapper(SubMonitor delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public void start(int totalTasks) {
		totalTicks = totalTasks*100;
		delegate.setWorkRemaining(totalTicks);
	}

	@Override
	public void beginTask(String title, int totalWork) {
		if(totalWork<=0)
		{
			totalTicks -= 10;
			currentChild = delegate.newChild(10); //don't take those as full tasks
			currentChild.beginTask(title, 100);	
			inIndeterminatedMode = true;
		}
		else
		{
			totalTicks -= 100;
			currentChild = delegate.newChild(100);
			currentChild.beginTask(title, totalWork);			
			inIndeterminatedMode = false;
		}

		delegate.subTask(title);

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
		if(inIndeterminatedMode)
			delegate.setWorkRemaining(totalTicks);
		currentChild=null;

	}

	@Override
	public boolean isCancelled() {
		return delegate.isCanceled();
	}

}
