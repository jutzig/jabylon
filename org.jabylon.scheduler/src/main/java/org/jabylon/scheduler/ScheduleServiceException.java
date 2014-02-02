/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler;

public class ScheduleServiceException extends Exception{

	private static final long serialVersionUID = 2100033336381044050L;

	public ScheduleServiceException() {
		super();
	}

	public ScheduleServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScheduleServiceException(String message) {
		super(message);
	}

	public ScheduleServiceException(Throwable cause) {
		super(cause);
	}

}
