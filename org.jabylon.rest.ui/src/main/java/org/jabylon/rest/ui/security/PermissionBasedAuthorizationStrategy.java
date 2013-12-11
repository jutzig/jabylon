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
package org.jabylon.rest.ui.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.request.component.IRequestableComponent;

import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;

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
            {
                User anonymousUser = session.getAnonymousUser();
                boolean allowed = anonymousUser.hasPermission(permission);
                if(allowed)
                    return true;
                throw new RestartResponseAtInterceptPageException(LoginPage.class);
            }
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
