/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.scheduler.internal;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.jabylon.common.progress.Progression;
import org.jabylon.scheduler.JobExecution;
import org.jabylon.scheduler.JobInstance;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonJob implements InterruptableJob, JobInstance {

    private static final int MAX_RETRIES = 5;
	private static final String KEY_RETRY_COUNT = "count";
	private Thread thread;
	private ProgressionImpl monitor;
	private JobExecutionContext context;
    public static final String CONNECTOR_KEY = "repository.connector";
    /** the execution service instance */
    public static final String EXECUTION_KEY = "execution";
    /** the key to retrieve the domain object for the current execution */
    public static final String DOMAIN_OBJECT_KEY = "domain";
    
    /** the key to retrieve the domain object for the current execution */
    public static final String PROGRESS_MONITOR = "monitor";
    
    private static final Logger LOG = LoggerFactory.getLogger(JabylonJob.class);
    
    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	this.context = context;
        this.thread = Thread.currentThread();
        long time = System.currentTimeMillis();
        JobDataMap dataMap = context.getMergedJobDataMap();
        JobExecution runnable = (JobExecution) dataMap.get(EXECUTION_KEY);
        if(!dataMap.containsKey(KEY_RETRY_COUNT))
        	dataMap.put(KEY_RETRY_COUNT, 0);
        int count = dataMap.getIntValue(KEY_RETRY_COUNT);

        // allow 5 retries
        if(count >= MAX_RETRIES){
            JobExecutionException e = new JobExecutionException("Retries exceeded");
            //make sure it doesn't run again
            LOG.error("Job "+runnable+" failed 5 times in a row. Deactivating");
            e.setUnscheduleAllTriggers(true);
            throw e;
        }
        if(runnable==null)
        	throw new IllegalStateException("No Job Execution was found");
        try {
        	monitor = new ProgressionImpl();
        	context.setResult(monitor);
            runnable.run(monitor, context.getMergedJobDataMap().getWrappedMap());
            LOG.info("Job {} finished in {}ms",runnable,System.currentTimeMillis()-time);
            dataMap.put(KEY_RETRY_COUNT, 0);
        } catch (OperationCanceledException e) {
        	monitor.setStatus(new Status(IStatus.CANCEL, "org.jabylon.common", null,e));
        }
        catch (Exception e) {
        	dataMap.put(KEY_RETRY_COUNT, count);    			
        	if(runnable.retryOnError()) {
        		try {
        			count++;
        			LOG.warn("Job {} failed. Retrying after 1 minute");
					Thread.sleep(TimeUnit.MINUTES.toMillis(1l));
				} catch (InterruptedException e1) {
					//nothing to do
				}
        	}
        	monitor.setStatus(new Status(IStatus.ERROR, "org.jabylon.common", null,e));
            
            throw new JobExecutionException(e,runnable.retryOnError());
        } finally{
        	this.thread = null;
        	if(monitor.isCanceled() && monitor.getStatus()!=null) {
        		//lower the severity if it was canceled
        		IStatus status = monitor.getStatus();
        		IStatus newStatus = new Status(IStatus.CANCEL, status.getPlugin(), status.getMessage());
        		monitor.setStatus(newStatus);
        	}
        	log(monitor.getStatus());
        	if(!runnable.retryOnError() || count >= MAX_RETRIES)
        		monitor.done();
        }

    }

    private void log(IStatus status) {
    	int severity = status.getSeverity();
    	switch (severity) {
    	case IStatus.OK:
    		LOG.debug("finished job {} : {}", getID(), status.getMessage());
    		break;
		case IStatus.CANCEL:
			LOG.debug("canceled job "+getID() + " : "+ status.getMessage(),status.getException());
			break;
		case IStatus.WARNING:
			LOG.warn("job "+getID() + " finished with warning: "+ status.getMessage(),status.getException());
			break;
		case IStatus.ERROR:
			LOG.error("job "+getID() + " failed: "+ status.getMessage(),status.getException());
			break;


		default:
			break;
		}
		
	}

	/* (non-Javadoc)
     * @see org.quartz.InterruptableJob#interrupt()
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
    	if(monitor!=null)
    		monitor.setCanceled(true);
    	//this seems to choke up CDO pretty badly, so it's deactivated for now
//        if(thread!=null)
//            thread.interrupt();

    }

	@Override
	public String getID() {
		return context.getJobDetail().getKey().getName();
	}

	@Override
	public Progression getProgress() {
		return monitor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDomainObject() {
		JobDataMap dataMap = context.getMergedJobDataMap();
		return (T) dataMap.get(DOMAIN_OBJECT_KEY);
	}
    

}
