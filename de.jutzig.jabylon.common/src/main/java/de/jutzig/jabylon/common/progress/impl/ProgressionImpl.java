package de.jutzig.jabylon.common.progress.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.jutzig.jabylon.common.progress.Progression;

public class ProgressionImpl implements Progression, IProgressMonitor {

	
	private String taskName;
	private int totalWork;
	private boolean canceled;
	private String subTask;
	private int ticksDone;
	boolean done;
	private IStatus status = Status.OK_STATUS;
	
	@Override
	public String getTaskName() {
		return taskName;
	}

	@Override
	public String getSubTaskName() {
		return subTask;
	}

	@Override
	public int getCompletion() {
		if(done)
			return 100;
		return Math.min(100, (int) ((ticksDone/(double)totalWork) *100));
	}



	@Override
	public void beginTask(String arg0, int arg1) {
		totalWork = arg1;
		taskName = arg0;
		
	}

	@Override
	public void done() {
		done=true;
		
	}

	@Override
	public void internalWorked(double arg0) {
		// nothing to do
		
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setCanceled(boolean arg0) {
		canceled = arg0;
		
	}

	@Override
	public void setTaskName(String arg0) {
		taskName = arg0;
		
	}

	@Override
	public void subTask(String arg0) {
		subTask = arg0;
		
	}

	@Override
	public void worked(int arg0) {
		ticksDone+=arg0;
		
	}

	@Override
	public boolean isDone() {
		return done;
	}

	public void setStatus(IStatus status) {
		this.status = status;  
		
	}
	
	@Override
	public IStatus getStatus() {
		return status;
	}
	
}
