/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security.auth;

import org.jabylon.users.User;

public interface AuthenticationService {
	
	/**
	 * tries to authenticate the given credentials
	 * <p>
	 * if the credentials are correct, the matching user object is returned.
	 * Otherwise this method returns <code>null</code>
	 * @param username
	 * @param password
	 * @return the user object or <code>null</code>
	 */
	public User authenticateUser(final String username, final String password);
	
	/**
	 * authenticates the given credentials
	 * @param username
	 * @param password
	 * @return <code>true</code> if the credentials are valid. <code>false</code> otherwise
	 */
	public boolean authenticate(final String username, final String password);

}
