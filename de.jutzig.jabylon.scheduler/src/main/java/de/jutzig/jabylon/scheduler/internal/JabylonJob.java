/**
 * 
 */
package de.jutzig.jabylon.scheduler.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonJob implements InterruptableJob {

	private Thread thread;
	private static final String RUNNABLE_KEY = "runnable";
	public static final String ELEMENT_KEY = "configurationElement";
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.thread = Thread.currentThread();
		IConfigurationElement element = (IConfigurationElement) context.getMergedJobDataMap().get(ELEMENT_KEY);
		Runnable runnable;
		try {
			runnable = (Runnable) element.createExecutableExtension("class");
		} catch (CoreException e) {
			throw new JobExecutionException(e);
		}
		runnable.run();

	}

	/* (non-Javadoc)
	 * @see org.quartz.InterruptableJob#interrupt()
	 */
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		if(thread!=null)
			thread.interrupt();

	}

}
