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
package org.jabylon.scheduler;

import java.util.Map;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface JobExecution {

    public void run(Map<String, Object> jobContext) throws Exception;

    boolean retryOnError();


}
