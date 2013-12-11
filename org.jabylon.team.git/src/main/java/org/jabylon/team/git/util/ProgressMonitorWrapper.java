/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.team.git.util;

import java.text.MessageFormat;

import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;

public class ProgressMonitorWrapper implements ProgressMonitor {


    private SubMonitor delegate;
    private SubMonitor currentChild;

    private static final String SUB_TASK_MESSAGE = "{0} of {1}";

    private int remainingTicks;
    int total;
    int current;


    public ProgressMonitorWrapper(SubMonitor delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public void start(int totalTasks) {
        remainingTicks = (totalTasks)*100;
        delegate.setWorkRemaining(remainingTicks);
    }

    @Override
    public void beginTask(String title, int totalWork) {
        if(currentChild!=null)
            currentChild.done();
        total = totalWork;
        current = 0;
        if(totalWork<=0)
        {
            //don't take those as full tasks since they are undetermined
            currentChild = delegate.newChild(0);
            currentChild.beginTask(title, 100);
        }
        else
        {
            currentChild = delegate.newChild(100);
            currentChild.beginTask(title, totalWork);

        }
        delegate.setTaskName(title);
    }

    @Override
    public void update(int completed) {
        current += completed;

        if(currentChild!=null)
            currentChild.worked(completed);
        else
            delegate.worked(completed);
        delegate.subTask(MessageFormat.format(SUB_TASK_MESSAGE, current,total));
    }

    @Override
    public void endTask() {
        if(currentChild!=null)
        {
            currentChild.done();
        }
        currentChild=null;

    }

    @Override
    public boolean isCancelled() {
        return delegate.isCanceled();
    }

}
