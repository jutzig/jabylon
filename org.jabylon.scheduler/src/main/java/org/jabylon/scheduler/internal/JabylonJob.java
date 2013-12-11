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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import org.jabylon.scheduler.JobExecution;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonJob implements InterruptableJob {

    private Thread thread;
    public static final String ELEMENT_KEY = "configurationElement";
    public static final String CONNECTOR_KEY = "repository.connector";

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.thread = Thread.currentThread();
        IConfigurationElement element = (IConfigurationElement) context.getMergedJobDataMap().get(ELEMENT_KEY);
        JobExecution runnable;
        try {
            runnable = (JobExecution) element.createExecutableExtension("class");
        } catch (CoreException e) {
            //no point in refiring if the class cannot be instantiated
            JobExecutionException exception = new JobExecutionException(e,false);
            exception.setUnscheduleFiringTrigger(true);
            throw exception;
        }
        try {
            runnable.run(context.getMergedJobDataMap().getWrappedMap());
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
