/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.server;

import java.io.File;

public class ServerConstants {

	/** jabylon home dir */
    public static final String WORKING_DIR;
    public static final String REPOSITORY_NAME = "jabylon";
    /** where the work data goes */
    public static final String WORKSPACE_DIR;
    public static final String WORKSPACE_RESOURCE = "workspace";
    public static final String USERS_RESOURCE = "users";

    static {
        String tmpWorkingDir;
        try {
        	//try in order JABYLON_HOME, -DJABYLON_HOME, osgi.instance.area and user.home/jabylon
        	String path = System.getenv("JABYLON_HOME");
        	if(path==null)
        		path = System.getProperty("JABYLON_HOME",System.getProperty("osgi.instance.area", System.getProperty("user.home")+"/jabylon"));
            if(path.startsWith("file:")) //eclipse does this when using variables in a launch config
                path = path.substring("file:".length());
            File instanceArea = new File(path);
            tmpWorkingDir = instanceArea.getCanonicalPath();
        } catch (Exception e) {
            tmpWorkingDir = System.getProperty("user.home") + "/jabylon";
        }
        WORKING_DIR = tmpWorkingDir;
        WORKSPACE_DIR = WORKING_DIR+"/workspace";
        new File(WORKSPACE_DIR).mkdirs();
    }
}
