/**
 * 
 */
package de.jutzig.jabylon.rest.ui.security;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Request;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.security.CommonPermissions;
import de.jutzig.jabylon.security.JabylonSecurityBundle;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class CDOAuthenticatedSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	private IModel<User> user;
	
	private static final Logger logger = LoggerFactory.getLogger(CDOAuthenticatedSession.class);
	
	private static final String JAAS_CONFIG_FILE = "jaas.config"; //$NON-NLS-1$
	
	public CDOAuthenticatedSession(Request request) {
		super(request);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.authroles.authentication.AuthenticatedWebSession#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticate(final String username, final String password) {
		
		Map<String, ILoginContext> contexts = createLoginContexts(new CallbackHandler() {
			
			@Override
			public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
				for (int i = 0; i < callbacks.length; i++) {
					Callback cb = callbacks[i];
					if(cb instanceof NameCallback) {
						((NameCallback) cb).setName(username); //$NON-NLS-1$
					} else if (cb instanceof PasswordCallback) {
						((PasswordCallback) cb).setPassword(password.toCharArray()); //$NON-NLS-1$
					}
				}
			}
		});
		for (Entry<String, ILoginContext> entry : contexts.entrySet()) {
			try {
				entry.getValue().login();
				logger.info("{} Login for user {} successful",entry.getKey(),username);
				//TODO: this could be handled without two lookups
				CDOView view = Activator.getDefault().getRepositoryConnector().openView();
				CDOResource resource = view.getResource(ServerConstants.USERS_RESOURCE);
				UserManagement userManagement = (UserManagement) resource.getContents().get(0);
				userManagement = (UserManagement) Activator.getDefault().getRepositoryLookup().resolve(userManagement.cdoID());
				User user = userManagement.findUserByName(username);
				if(user==null) {
					logger.info("User {} logged in for the first time. Creating DB Entry");
					final User newUser = UsersFactory.eINSTANCE.createUser();
					newUser.setName(username);
					Role anonymousRole = userManagement.findRoleByName("Anonymous");
					if(anonymousRole==null)
						throw new RuntimeException("Anonymous role must always exist");
					newUser.getRoles().add(anonymousRole);

					user = TransactionUtil.commit(userManagement, new Modification<UserManagement, User>() {
						
						@Override
						public User apply(UserManagement object) {
							object.getUsers().add(newUser);
							return newUser;
						}
					});
					userManagement.getUsers().add(user);
				}
				this.user = new EObjectModel<User>(user);
				view.close();
				
				return true;
			} catch (LoginException e) {
				logger.error(entry.getKey()+" Login for user "+username+" failed: "+e.getMessage());
			} catch (CommitException e) {
				logger.error("Failed to commit new user after first login from " + entry.getKey(),e);
			}
			
		}
		return false;
	}

	private Map<String,ILoginContext> createLoginContexts(CallbackHandler callbackHandler) {
		
		URL configUrl = getJAASConfig();
		Map<String,ILoginContext> contexts = new LinkedHashMap<String,ILoginContext>();
		contexts.put("DB",LoginContextFactory.createContext("DB", configUrl, callbackHandler));
		contexts.put("LDAP",LoginContextFactory.createContext("LDAP", configUrl, callbackHandler));
		return contexts;
	}

	private URL getJAASConfig() {
		String configArea = System.getProperty("osgi.configuration.area","configuration");
		File jaasConfig = new File(new File(configArea),JAAS_CONFIG_FILE);
		if(jaasConfig.isFile()) {
			try {
				return jaasConfig.toURI().toURL();
			} catch (MalformedURLException e) {
				logger.error("invalid jaas url",e);
			}			
		}
		//fallback
		return JabylonSecurityBundle.getBundleContext().getBundle().getEntry("META-INF/"+JAAS_CONFIG_FILE);
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
		//TODO: this could be handled without two lookups
		CDOView view = Activator.getDefault().getRepositoryConnector().openView();
		CDOResource resource = view.getResource(ServerConstants.USERS_RESOURCE);
		UserManagement userManagement = (UserManagement) resource.getContents().get(0);
		userManagement = (UserManagement) Activator.getDefault().getRepositoryLookup().resolve(userManagement.cdoID());
		Role role = userManagement.findRoleByName(CommonPermissions.ROLE_ANONYMOUS);
		Roles roles = createRoles(role.getAllPermissions());
		view.close();
		return roles;
	}

	public User getUser() {
		if(user == null)
			return null;
		return user.getObject();
	}
	
}
