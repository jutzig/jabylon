/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security.auth;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;
import org.jabylon.cdo.connector.Modification;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.cdo.connector.TransactionUtil;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.security.CommonPermissions;
import org.jabylon.security.JabylonSecurityBundle;
import org.jabylon.security.SubjectAttribute;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(enabled=true,immediate=true)
@Service
public class AuthenticatorServiceImpl implements AuthenticationService {

	private Logger logger = LoggerFactory.getLogger(AuthenticatorServiceImpl.class);
	private static final String JAAS_CONFIG_FILE = "jaas.config"; //$NON-NLS-1$
	@Reference(policy=ReferencePolicy.DYNAMIC,cardinality=ReferenceCardinality.MANDATORY_UNARY,bind="setRepositoryConnector",unbind="unbindRepositoryConnector")
	private RepositoryConnector repositoryConnector;
	private UserManagement userManagement;
	private User anonymous;

	public boolean authenticate(final String username, final String password) {

		Subject subject = doAuthenticate(username, password);
		return subject != null;
	}

	protected Subject doAuthenticate(final String username, final String password) {

		ILoginContext context = createLoginContext(new CallbackHandler() {

			@Override
			public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
				for (int i = 0; i < callbacks.length; i++) {
					Callback cb = callbacks[i];
					if (cb instanceof NameCallback) {
						((NameCallback) cb).setName(username); //$NON-NLS-1$
					} else if (cb instanceof PasswordCallback) {
						((PasswordCallback) cb).setPassword(password.toCharArray()); //$NON-NLS-1$
					}
				}
			}
		});

		try {
			context.login();
			final Subject subject = context.getSubject();
			Set<String> credentials = subject.getPublicCredentials(String.class);
			// in case it was an auth token, the username was null but now we
			// know the right one
			String actualUsername = credentials.isEmpty() ? username : credentials.iterator().next();
			logger.info("Login for user {} successful", actualUsername);
			return subject;

		} catch (LoginException e) {
			logger.error("Login for user " + username + " failed: " + e.getMessage());
		}

		return null;
	}

	public User authenticateUser(final String username, final String password) {

		String actualUsername = username;
		final Subject subject = doAuthenticate(username, password);
		if (subject == null)
			return null;
		Set<String> credentials = subject.getPublicCredentials(String.class);
		if(!credentials.isEmpty())
			actualUsername = credentials.iterator().next();
		UserManagement management = getUserManagement();
		if (management == null)
			return null;
		User user = management.findUserByName(actualUsername);
		try {
			if (user == null) {
				logger.info("User {} logged in for the first time. Creating DB Entry",actualUsername);
				final User newUser = UsersFactory.eINSTANCE.createUser();
				newUser.setName(actualUsername);
				user = TransactionUtil.commit(management, new Modification<UserManagement, User>() {

					@Override
					public User apply(UserManagement object) {
						CommonPermissions.addDefaultPermissions(object, newUser);
						applyAttributes(newUser, subject);
						object.getUsers().add(newUser);
						return newUser;
					}
				});
			} else {
				user = TransactionUtil.commit(user, new Modification<User, User>() {

					@Override
					public User apply(User object) {
						applyAttributes(object, subject);
						return object;
					}
				});

			}
		} catch (CommitException e) {
			logger.error("Failed to commit new user or updating exsiting after login", e);
		}

		return user;
	}

	protected void applyAttributes(User user, Subject subject) {
		Set<SubjectAttribute> attributes = subject.getPublicCredentials(SubjectAttribute.class);
		for (SubjectAttribute subjectAttribute : attributes) {
			subjectAttribute.applyTo(user);
		}

	}

	private ILoginContext createLoginContext(CallbackHandler callbackHandler) {

		URL configUrl = getJAASConfig();
		return LoginContextFactory.createContext("Jabylon", configUrl, callbackHandler);
	}

	private URL getJAASConfig() {
		String configArea = System.getProperty("osgi.configuration.area");
		if (configArea == null || configArea.isEmpty())
			configArea = new File(new File(ServerConstants.WORKING_DIR), "configuration").toURI().toString();
		try {
			URI uri = new URI(configArea);
			File jaasConfig = new File(new File(uri), JAAS_CONFIG_FILE);
			if (jaasConfig.isFile()) {
				return jaasConfig.toURI().toURL();
			}
		} catch (Exception e) {
			logger.error("invalid jaas url", e);
		}
		// fallback
		return JabylonSecurityBundle.getBundleContext().getBundle().getEntry("META-INF/" + JAAS_CONFIG_FILE);
	}
	

    private UserManagement getUserManagement()
    {
        if(userManagement==null)
        {
            Object resolved = getRepositoryConnector().openView().getResource(ServerConstants.USERS_RESOURCE).getContents().get(0);
            if (resolved instanceof UserManagement) {
                userManagement = (UserManagement) resolved;
            }
            else
            {
                logger.error("Failed to obtain UserManagement");
            }
        }
        return userManagement;

    }	
    
    public RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}
	
	public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
		this.repositoryConnector = repositoryConnector;
	}
	
	public void unbindRepositoryConnector(RepositoryConnector repositoryConnector) {
		if(repositoryConnector==this.repositoryConnector) {
			if(userManagement!=null)
				userManagement.cdoView().close();
			userManagement = null;
			this.repositoryConnector = null;
		}
	}

	@Deactivate
	protected void deactivate() {
		if(userManagement!=null)
			userManagement.cdoView().close();
		userManagement = null;
	}

	@Override
	public User getAnonymousUser() {
		if(anonymous==null)
		{
			anonymous = getUserManagement().findUserByName(CommonPermissions.USER_ANONYMOUS); 
		}
		return anonymous;
	}
}
