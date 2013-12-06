package org.jabylon.common.progress;

import org.eclipse.core.runtime.IStatus;

public interface Progression {

    String getTaskName();

    String getSubTaskName();

    int getCompletion();

    boolean isDone();

    IStatus getStatus();

}
