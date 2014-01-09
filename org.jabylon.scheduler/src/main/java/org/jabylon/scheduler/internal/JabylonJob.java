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

import org.jabylon.common.progress.DefaultProgression;
import org.jabylon.scheduler.JobExecution;
import org.quartz.InterruptableJob;
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
        JobExecution runnable = (JobExecution) context.getMergedJobDataMap().get(EXECUTION_KEY);
        try {
        	DefaultProgression monitor = new DefaultProgression();
        	context.setResult(monitor);
            runnable.run(monitor, context.getMergedJobDataMap().getWrappedMap());
            LOG.info("Job {} finished in {}ms",runnable,System.currentTimeMillis()-time);
        } catch (Exception e) {
            throw new JobExecutionException(e,true);
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
