/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler.internal;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.jabylon.common.progress.RunnableWithProgress;
import org.jabylon.scheduler.JobExecution;
import org.quartz.JobKey;
import org.quartz.Scheduler;

public class RunnableWithProgressWrapper implements JobExecution {

	private RunnableWithProgress delegate;
	private Scheduler scheduler;
	private String id;
	
	public RunnableWithProgressWrapper(RunnableWithProgress delegate, Scheduler scheduler, String id) {
		super();
		this.delegate = delegate;
		this.scheduler = scheduler;
		this.id = id;
	}

	@Override
	public void run(IProgressMonitor monitor, Map<String, Object> jobContext) throws Exception {
		try {
			IStatus status = delegate.run(monitor);
			if(status!=null && monitor instanceof ProgressionImpl)
				((ProgressionImpl) monitor).setStatus(status);
		} 
		finally {
			scheduler.deleteJob(new JobKey(id));
		}

	}

	@Override
	public boolean retryOnError() {
		return false;
	}

	@Override
	public String getID() {
		return id;
	}

}
