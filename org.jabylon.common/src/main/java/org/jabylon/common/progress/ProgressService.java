/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.progress;



public interface ProgressService {

    long schedule(RunnableWithProgress task);

    Progression progressionOf(long id);

    void cancel(long id);

    void shutdown();
}
