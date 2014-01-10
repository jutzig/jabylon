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

import org.jabylon.common.progress.DefaultProgression;
import org.jabylon.scheduler.JobExecution;
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
public class JabylonJob implements InterruptableJob {

    private static final String KEY_RETRY_COUNT = "count";
	private Thread thread;
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
        this.thread = Thread.currentThread();
        long time = System.currentTimeMillis();
        JobDataMap dataMap = context.getMergedJobDataMap();
        JobExecution runnable = (JobExecution) dataMap.get(EXECUTION_KEY);
        if(!dataMap.containsKey(KEY_RETRY_COUNT))
        	dataMap.put(KEY_RETRY_COUNT, 0);
        int count = dataMap.getIntValue(KEY_RETRY_COUNT);

        // allow 5 retries
        if(count >= 5){
            JobExecutionException e = new JobExecutionException("Retries exceeded");
            //make sure it doesn't run again
            LOG.error("Job "+runnable+" failed 5 times in a row. Deactivating");
            e.setUnscheduleAllTriggers(true);
            throw e;
        }
        if(runnable==null)
        	throw new IllegalStateException("No Job Execution was found");
        try {
        	DefaultProgression monitor = new DefaultProgression();
        	context.setResult(monitor);
            runnable.run(monitor, context.getMergedJobDataMap().getWrappedMap());
            LOG.info("Job {} finished in {}ms",runnable,System.currentTimeMillis()-time);
            dataMap.put(KEY_RETRY_COUNT, 0);
        } catch (Exception e) {
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
            throw new JobExecutionException(e,runnable.retryOnError());
        }

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
