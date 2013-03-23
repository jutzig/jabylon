/**
 * 
 */
package de.jutzig.jabylon.rest.ui.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.request.component.IRequestableComponent;

import de.jutzig.jabylon.security.CommonPermissions;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PermissionBasedAuthorizationStrategy implements IAuthorizationStrategy{

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		return true;
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		if (component instanceof RestrictedComponent) {
			RestrictedComponent restricted = (RestrictedComponent) component;
			String permission = restricted.getRequiredPermission();
			if(permission==null)
				return true;
			CDOAuthenticatedSession session = (CDOAuthenticatedSession) CDOAuthenticatedSession.get();
			if(session.getUser()==null)
				throw new RestartResponseAtInterceptPageException(LoginPage.class);
			boolean allowed = session.getUser().hasPermission(permission);
			if(allowed)
				return true;
			if(CommonPermissions.isEditRequest(permission))
				throw new UnauthorizedActionException(component,action);
			return false;
		}
		return true;
	}

}
