/**
 * 
 */
package de.jutzig.jabylon.security;

import java.text.MessageFormat;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.users.User;

/**
 * 
 * common permission strings based on shiro syntax
 * http://shiro.apache.org/permissions.html
 * @author jutzig.dev@googlemail.com
 *
 */
public class CommonPermissions {
	
	public static final String WILDCARD = "*";
	
	public static final String ROLE_ANONYMOUS = "Anonymous";
	public static final String ROLE_ADMINISTRATOR = "Administrator";
	
	public static final String USER_ANONYMOUS = ROLE_ANONYMOUS;
	
	
	public static final String PROJECT = "Project";
	public static final String WORKSPACE = "Workspace";
	public static final String USER = "User";
	public static final String SYSTEM = "System";
	
	public static final String ACTION_EDIT = "edit";
	public static final String ACTION_VIEW = "view";
	public static final String ACTION_CONFIG = "config";
	

	public static final String PROJECT_GLOBAL_CONFIG = PROJECT + ":" + WILDCARD + ":" + ACTION_CONFIG;
	public static final String PROJECT_GLOBAL_VIEW = PROJECT + ":" + WILDCARD + ":" + ACTION_VIEW;
	public static final String PROJECT_GLOBAL_EDIT = PROJECT + ":" + WILDCARD + ":" + ACTION_EDIT;

	public static final String WORKSPACE_CONFIG = WORKSPACE + ":" + ACTION_CONFIG;
	
	/**
	 * right to edit any configuration
	 */
	public static final String SYSTEM_GLOBAL_CONFIG = "System:*:config";
	public static final String USER_GLOBAL_CONFIG = "User:*:config";
	
	/**
	 * basic right to access configuration in general
	 */
	public static final String SYSTEM_GENERAL_CONFIG = "System:config";
	
	private static final String PERMISSION_PATTERN = "{0}:{1}:{2}";
	
	public static String constructPermissionName(String kind, String scope, String action){
		return MessageFormat.format(PERMISSION_PATTERN, kind,scope,action);
	}
	
	public static String constructPermissionName(Resolvable<?, ?> r, String action){
		return constructPermissionName(r.eClass().getName(), r.getName(), action);
	}
	
	public static boolean hasPermission(User user, String permission) {
		return user.hasPermission(permission);	
	}
	
	public static boolean hasPermission(User user, Resolvable<?, ?> r, String action) {
		return user.hasPermission(constructPermissionName(r, action));	
	}
	
	public static boolean hasEditPermission(User user, Resolvable<?, ?> r) {
		return user.hasPermission(constructPermissionName(r, ACTION_EDIT));	
	}
	
	public static boolean hasViewPermission(User user, Resolvable<?, ?> r) {
		return user.hasPermission(constructPermissionName(r, ACTION_VIEW));	
	}
	
	public static boolean hasConfigPermission(User user, Resolvable<?, ?> r) {
		return user.hasPermission(constructPermissionName(r, ACTION_CONFIG));	
	}
	
	public static String constructPermission(String... parts) {
		StringBuilder builder = new StringBuilder();
		for (String part : parts) {
			builder.append(part);
			builder.append(":");
		}
		if(builder.length()!=0)
			builder.setLength(builder.length()-1);
		return builder.toString();
	}
	
	public static boolean isEditRequest(String permission) {
		return permission.contains(":"+ACTION_EDIT);
	}
}
