package de.jutzig.jabylon.cdo.server;

import java.net.URL;

public class ServerConstants {

	public static final String WORKING_DIR;
	public static final String REPOSITORY_NAME = "jabylon";
	public static final String WORKSPACE_DIR;
	public static final String WORKSPACE_RESOURCE = "workspace";
	public static final String USERS_RESOURCE = "users";

	static {
		String tmpWorkingDir;
		try {
			tmpWorkingDir = (new URL(System.getProperty("osgi.instance.area", "file:/"+System.getProperty("user.home") + "/jabylon"))).getFile();
		} catch (Exception e) {
			tmpWorkingDir = System.getProperty("user.home") + "/jabylon";
		}
		WORKING_DIR = tmpWorkingDir;
		WORKSPACE_DIR = WORKING_DIR+"/workspace";
	}
}
