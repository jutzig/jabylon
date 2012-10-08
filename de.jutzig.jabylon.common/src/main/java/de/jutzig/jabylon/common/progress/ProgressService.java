package de.jutzig.jabylon.common.progress;

import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

public class ProgressService {
	
	private static AtomicLong id = new AtomicLong();

	public static long schedule(RunnableWithProgress runnable, IProgressMonitor monitor)
	
	{	
		long currentID = id.getAndIncrement();
		
		return currentID;
	
	}
}

class RunnableWrapper implements Runnable
{
	private RunnableWithProgress progressRunnable;
	
	private IProgressMonitor monitor;
	
	
	public IProgressMonitor getMonitor() {
		return monitor;
	}
	
	@Override
	public void run() {
		monitor = createMonitor();
		progressRunnable.run(monitor);
	}

	private IProgressMonitor createMonitor() {
		// TODO Auto-generated method stub
		return null;
	}
}

class CustomProgressMonitor implements IProgressMonitor
{

	@Override
	public void beginTask(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalWorked(double arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanceled(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTaskName(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subTask(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worked(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}