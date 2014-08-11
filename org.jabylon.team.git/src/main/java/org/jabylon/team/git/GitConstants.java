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
package org.jabylon.team.git;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConstants {

	private GitConstants() {
        // hide utility constructor
    }

    public static final String KEY_COMMIT = "git.commit";
    public static final String KEY_PUSH = "git.push";
    public static final String KEY_USERNAME = "git.username";
    public static final String KEY_PASSWORD = "git.password";
    public static final String KEY_EMAIL = "git.email";
    public static final String KEY_MESSAGE = "git.message";
    public static final String KEY_INSERT_CHANGE_ID = "git.changeId";
    /**
     * the refspec used for pushes
     */
    public static final String KEY_PUSH_REFSPEC = "git.push.refspec";
    /**
     * the default for the refspec
     */
    public static final String DEFAULT_PUSH_REFSPEC = "refs/heads/{0}:refs/heads/{0}";
    
    /**
     * use rebase or merge
     */
	public static final String KEY_REBASE = "git.rebase";
	
	/**
	 * the default for the rebase setting. Default is <code>true</code> (merge)
	 */
	public static final boolean DEFAULT_REBASE = true;
}
