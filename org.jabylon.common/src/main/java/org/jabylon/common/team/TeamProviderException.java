/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.team;

public class TeamProviderException extends RuntimeException {

    public TeamProviderException() {
        super();
    }

    public TeamProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamProviderException(String message) {
        super(message);
    }

    public TeamProviderException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 1L;

}
