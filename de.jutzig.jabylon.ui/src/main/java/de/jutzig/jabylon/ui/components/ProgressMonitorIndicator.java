package de.jutzig.jabylon.ui.components;

import org.eclipse.core.runtime.IProgressMonitor;

import com.vaadin.ui.ProgressIndicator;

public class ProgressMonitorIndicator extends ProgressIndicator implements IProgressMonitor{
	
	private int totalWork;
	private int actualWork;
	
	
	public ProgressMonitorIndicator() {
		super(0f);
	}
	
	@Override
	public Object getValue() {
		return actualWork/(double)totalWork;
	}

	@Override
	public void beginTask(String name, int totalWork) {
		setTaskName(name);
		this.totalWork = totalWork;
		
	}

	@Override
	public void done() {
		actualWork = totalWork;
		
	}

	@Override
	public void internalWorked(double work) {
		
		
	}

	@Override
	public boolean isCanceled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTaskName(String name) {
		setCaption(name);
		
	}

	@Override
	public void subTask(String name) {
		String caption = getCaption() == null ? "" : getCaption(); //$NON-NLS-1$
		setCaption(caption+": "+name); //$NON-NLS-1$
		
	}

	@Override
	public void worked(int work) {
		actualWork += work;
		
	}
	
	
}
