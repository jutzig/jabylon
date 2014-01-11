/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler;

import java.util.Date;
import java.util.List;

import org.osgi.service.prefs.Preferences;

public interface SchedulerService {

	Date nextExecution(Preferences jobConfig) throws ScheduleServiceException;
	
	Date nextExecution(String jobId) throws ScheduleServiceException;
	
	List<JobInstance> getRunningJobs() throws ScheduleServiceException;
	
	void trigger(Preferences jobConfig) throws ScheduleServiceException;
}
