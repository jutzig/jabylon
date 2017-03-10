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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConstants {
    private static final Logger LOG = LoggerFactory.getLogger(ServerConstants.class);

    /** jabylon home dir */
    public static final String WORKING_DIR;
    public static final String REPOSITORY_NAME = "jabylon";
    /** where the work data goes */
    public static final String WORKSPACE_DIR;
    public static final String WORKSPACE_RESOURCE = "workspace";
    public static final String USERS_RESOURCE = "users";
    /** indicates if we are running inside of karaf */
    public static final boolean IS_KARAF;

    static {
    	IS_KARAF = System.getProperty("karaf.home", null)!=null;
        String tmpWorkingDir;
        try {
            //try in order jabylon.home, JABYLON_HOME, -DJABYLON_HOME, osgi.instance.area and user.home/jabylon
            String path = System.getenv("JABYLON_HOME");
            if(path==null)
                path = System.getProperty("jabylon.home",System.getProperty("JABYLON_HOME",System.getProperty("osgi.instance.area", System.getProperty("user.home")+"/jabylon")));
            if(path.startsWith("file:")) //eclipse does this when using variables in a launch config
                path = path.substring("file:".length());
            File instanceArea = new File(path);
            tmpWorkingDir = instanceArea.getCanonicalPath();
        } catch (Exception e) {
            LOG.warn("Error while trying to use configured working dir. Fallback to <user.home>/jabylon. Reason: "+e.getMessage(), e);
            tmpWorkingDir = System.getProperty("user.home") + "/jabylon";
        }
        WORKING_DIR = tmpWorkingDir;
        WORKSPACE_DIR = WORKING_DIR+"/workspace";
        LOG.info("Working dir set to: "+WORKING_DIR);
        LOG.info("Workspace dir  set to: "+WORKSPACE_DIR);
        new File(WORKSPACE_DIR).mkdirs();
    }
}
