package de.jutzig.jabylon.cdo.server;

public class ServerConstants {

	/**
	 * The repository name
	 */
	public static final String REPOSITORY_NAME = "jabylon";

	private static final String USER_HOME = System.getProperty("user.home");
	public static final String WORKING_DIR = USER_HOME+"/workspaces/translator/work";
	public static final String WORKSPACE_DIR = WORKING_DIR+"/workspace";
	public static final String WORKSPACE_RESOURCE = "workspace";
	public static final String USERS_RESOURCE = "users";

}
