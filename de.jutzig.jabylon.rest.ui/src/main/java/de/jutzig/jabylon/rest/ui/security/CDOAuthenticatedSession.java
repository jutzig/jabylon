/**
 *
 */
package de.jutzig.jabylon.rest.ui.security;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.security.auth.Subject;
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
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.common.resolver.impl.UserManagmentURIHandler;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.security.CommonPermissions;
import de.jutzig.jabylon.security.JabylonSecurityBundle;
import de.jutzig.jabylon.security.SubjectAttribute;
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

    private IModel<User> anonymousUser;

    private IModel<UserManagement> userManagementModel;

    private static final Logger logger = LoggerFactory.getLogger(CDOAuthenticatedSession.class);

    private static final String JAAS_CONFIG_FILE = "jaas.config"; //$NON-NLS-1$

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


    private UserManagement getUserManagement()
    {
        if(userManagementModel==null)
        {
            Object resolved = Activator.getDefault().getRepositoryLookup().resolve(UserManagmentURIHandler.SECURITY_URI_PREFIX);
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
        UserManagement management = getUserManagement();
        if(management==null)
            return false;
        User user = management.findUserByName(username);
        for (Entry<String, ILoginContext> entry : contexts.entrySet()) {
            try {
                entry.getValue().login();
                final Subject subject = entry.getValue().getSubject();
                logger.info("{} Login for user {} successful",entry.getKey(),username);

                if(user==null) {
                    logger.info("User {} logged in for the first time. Creating DB Entry");
                    final User newUser = UsersFactory.eINSTANCE.createUser();
                    newUser.setName(username);

                    user = TransactionUtil.commit(management, new Modification<UserManagement, User>() {

                        @Override
                        public User apply(UserManagement object) {
                        	CommonPermissions.addDefaultPermissions(object, newUser);
                            applyAttributes(newUser,subject);
                            object.getUsers().add(newUser);
                            return newUser;
                        }
                    });
                }
                else
                {
                    user = TransactionUtil.commit(user, new Modification<User, User>() {

                        @Override
                        public User apply(User object) {
                            applyAttributes(object ,subject);
                            return object;
                        }
                    });

                }

                this.user = new EObjectModel<User>(user);
                return true;
            } catch (LoginException e) {
                logger.error(entry.getKey()+" Login for user "+username+" failed: "+e.getMessage());
            } catch (CommitException e) {
                logger.error("Failed to commit new user or updating exsiting after first login from " + entry.getKey(),e);
            }

        }
        return false;
    }

    protected void applyAttributes(User user, Subject subject) {
        Set<SubjectAttribute> attributes = subject.getPublicCredentials(SubjectAttribute.class);
        for (SubjectAttribute subjectAttribute : attributes) {
            subjectAttribute.applyTo(user);
        }

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
        try {
            URI uri = new URI(configArea);
            File jaasConfig = new File(new File(uri), JAAS_CONFIG_FILE);
            if(jaasConfig.isFile()) {
                return jaasConfig.toURI().toURL();
            }
        } catch (Exception e) {
            logger.error("invalid jaas url", e);
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
            User anonymous = getUserManagement().findUserByName(CommonPermissions.USER_ANONYMOUS);
            if(anonymous!=null)
                anonymousUser = new EObjectModel<User>(anonymous);
            else
                return null;
        }
        return anonymousUser.getObject();
    }

}
