/**
 * 
 */
package de.jutzig.jabylon.scheduler.internal;

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
	public static final String RUNNABLE_KEY = "runnable";
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.thread = Thread.currentThread();
		Runnable runnable = (Runnable) context.getMergedJobDataMap().get(RUNNABLE_KEY);
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
