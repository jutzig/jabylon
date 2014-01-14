/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.progress;



/**
 * simplified version of the ScheduleService meant for one-time tasks
 * @author jutzig.dev@googlemail.com
 *
 */
public interface ProgressService {

	/**
	 * use this to schedule a one-time task
	 * @param task
	 * @return the id of the task
	 */
	String schedule(RunnableWithProgress task, String description);

    /**
     * retrieves a task currently executed with {@link #schedule(RunnableWithProgress)}
     * @param id
     * @return the progression of the task
     * @throws RuntimeException if the progression cannot be obtained
     */
    Progression progressionOf(String id);
    

    /**
     * attempts to cancel the task with the given id
     * @param id
     */
    void cancel(String id);


    /**
     * shuts the scheduler/progress service down. Tasks will no longer be executed after calling this method.
     * The method is synchronous, so it will wait for the scheduler to be shutdown completely before returning
     */
    void shutdown();
}
