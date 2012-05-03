package de.jutzig.jabylon.cdo.server;

import java.io.File;

public class ServerConstants {

	public static final String WORKING_DIR;
	public static final String REPOSITORY_NAME = "jabylon";
	public static final String WORKSPACE_DIR;
	public static final String WORKSPACE_RESOURCE = "workspace";
	public static final String USERS_RESOURCE = "users";

	static {
		String tmpWorkingDir;
		try {
			String path = System.getProperty("osgi.instance.area", System.getProperty("user.home"));
			if(path.startsWith("file:")) //eclipse does this when using variables in a launch config
				path = path.substring("file:".length());
			File instanceArea = new File(path);
			tmpWorkingDir = instanceArea.getCanonicalPath();
		} catch (Exception e) {
			tmpWorkingDir = System.getProperty("user.home") + "/jabylon";
		}
		WORKING_DIR = tmpWorkingDir;
		WORKSPACE_DIR = WORKING_DIR+"/workspace";
	}
}
