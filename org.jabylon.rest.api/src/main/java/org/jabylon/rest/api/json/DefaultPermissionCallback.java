package org.jabylon.rest.api.json;

import org.eclipse.emf.ecore.EObject;
import org.jabylon.properties.Resolvable;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;

public class DefaultPermissionCallback implements PermissionCallback {

	private User user;
	
	
	
	public DefaultPermissionCallback(User user) {
		super();
		this.user = user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isAuthorized(EObject target) {
		if(user==null)
    		return false;
		if (target instanceof Resolvable) {
			Resolvable r = (Resolvable) target;
			return CommonPermissions.hasPermission(user, r, CommonPermissions.ACTION_VIEW);	
		}
		//for now we disallow everything non-resolvable
		return false;
	}

}
