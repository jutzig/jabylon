/**
 * 
 */
package de.jutzig.jabylon.security;

import java.text.MessageFormat;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.users.User;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class CommonPermissions {
	
	public static final String PROJECT_GLOBAL_CONFIG = "#Project.global.config";
	public static final String SYSTEM_GLOBAL_CONFIG = "#System.global.config";
	public static final String USER_GLOBAL_CONFIG = "#User.global.config";
	public static final String WORKSPACE_GLOBAL_EDIT = "#Workspace.global.edit";
	public static final String WORKSPACE_GLOBAL_VIEW = "#Workspace.global.view";
	
	public static final String ROLE_ANONYMOUS = "Anonymous";
	public static final String ROLE_ADMINISTRATOR = "Administrator";
	
	private static final String PERMISSION_PATTERN = "#{0}.{1}.{2}";
	
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
		return user.hasPermission(constructPermissionName(r, "edit"));	
	}
	
	public static boolean hasViewPermission(User user, Resolvable<?, ?> r) {
		return user.hasPermission(constructPermissionName(r, "view"));	
	}
	
	public static boolean hasConfigPermission(User user, Resolvable<?, ?> r) {
		return user.hasPermission(constructPermissionName(r, "config"));	
	}
}
