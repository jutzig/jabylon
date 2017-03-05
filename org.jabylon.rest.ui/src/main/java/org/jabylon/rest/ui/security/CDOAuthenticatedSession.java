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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Request;
import org.eclipse.emf.common.util.EList;
import org.jabylon.common.resolver.impl.URIConstants;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.security.CommonPermissions;
import org.jabylon.security.auth.AuthenticationService;
import org.jabylon.users.Permission;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class CDOAuthenticatedSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = 1L;

    private IModel<User> user;

    private IModel<User> anonymousUser;

    private IModel<UserManagement> userManagementModel;

    private static final Logger logger = LoggerFactory.getLogger(CDOAuthenticatedSession.class);

    public CDOAuthenticatedSession(Request request) {
        super(request);

    }

    @Override
    public void detach() {
        super.detach();
        if(user!=null)
            user.detach();
        if(anonymousUser!=null)
            anonymousUser.detach();
        if(userManagementModel!=null)
            userManagementModel.detach();
    }

    public boolean hasPermission(String permission) {
    	User user = getUser();
    	if(user==null)
    		user = getAnonymousUser();
    	if(user!=null)
    		return user.hasPermission(permission);
    	return false;
    }

    private UserManagement getUserManagement()
    {
        if(userManagementModel==null)
        {
            Object resolved = Activator.getDefault().getRepositoryLookup().resolve(URIConstants.SECURITY_URI_PREFIX);
            if (resolved instanceof UserManagement) {
                UserManagement managment = (UserManagement) resolved;
                userManagementModel = new EObjectModel<UserManagement>(managment);
                return managment;
            }
            else
            {
                logger.error("Failed to obtain UserManagement");
                return null;
            }
        }
        return userManagementModel.getObject();

    }

    /* (non-Javadoc)
     * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#authenticate(java.lang.String, java.lang.String)
     */
    @Override
    public boolean authenticate(final String username, final String password) {

    	AuthenticationService service = Activator.getDefault().getAuthenticationService();
    	User user = service.authenticateUser(username, password);
    	if(user==null)
    		return false;
    	this.user = new EObjectModel<User>(user);
        return true;
    }


    /* (non-Javadoc)
     * @see org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession#getRoles()
     */
    @Override
    public Roles getRoles() {
        // TODO Auto-generated method stub
        if(isSignedIn())
        {
            //in our case permissions are wicket roles
            EList<Permission> permissions = user.getObject().getAllPermissions();
            return createRoles(permissions);
        }
        return getAnonymousRoles();
    }

    private Roles createRoles(EList<Permission> permissions) {
        List<String> roleNames = new ArrayList<String>(permissions.size());
        for (Permission permission : permissions) {
            roleNames.add(permission.getName());
        }
        return new Roles(roleNames.toArray(new String[permissions.size()]));
    }

    private Roles getAnonymousRoles() {
        logger.info("Computing Anonymous Roles");
        Role role = getUserManagement().findRoleByName(CommonPermissions.ROLE_ANONYMOUS);
        Roles roles = createRoles(role.getAllPermissions());
        return roles;
    }

    public User getUser() {
        if(user == null)
            return null;
        return user.getObject();
    }

    public User getAnonymousUser() {
        if(anonymousUser == null)
        {
            if(getUserManagement()==null)
                return null;
            User anonymous = Activator.getDefault().getAuthenticationService().getAnonymousUser();
            if(anonymous!=null)
                anonymousUser = new EObjectModel<User>(anonymous);
            else
                return null;
        }
        return anonymousUser.getObject();
    }

}
