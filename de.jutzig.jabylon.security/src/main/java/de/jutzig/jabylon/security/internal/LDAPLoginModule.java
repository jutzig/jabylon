package de.jutzig.jabylon.security.internal;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPLoginModule implements LoginModule {

	public static final String KEY_LDAP = "ldap";
	public static final String KEY_LDAP_PORT = "ldap.port";
	public static final String KEY_USER_NAME = "user.id";
	public static final String KEY_USER_MAIL = "user.mail";
	public static final String KEY_ROOT_DN = "root.dn";
	public static final String KEY_MANAGER = "manager";
	public static final String KEY_MANAGER_PASSWORD = "manager.password";
	private Subject subj;
	private CallbackHandler cbHandler;
	private Map<String, ?> options;
	private boolean authenticated;
	private String user;
	private static final Logger logger = LoggerFactory.getLogger(LDAPLoginModule.class);
	private DirContext ctx;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subj = subject;
		this.cbHandler = callbackHandler;
		this.options = options;

	}

	@Override
	public boolean login() throws LoginException {
		NameCallback nameCallback = new NameCallback("User:");
		PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
		try {
			cbHandler.handle(new Callback[] { nameCallback, passwordCallback });
		} catch (Exception e) {
			logger.error("Login failed", e);
		}

		user = nameCallback.getName();
		String pw = null;
		if (passwordCallback.getPassword() != null) {
			pw = String.valueOf(passwordCallback.getPassword());
		}

		this.authenticated = checkLogin(user, pw);
		return this.authenticated;
	}

	private boolean checkLogin(String user, String pw) {
		ctx = createContext((String)options.get(KEY_MANAGER), (String)options.get(KEY_MANAGER_PASSWORD));
		if(ctx!=null) {
			try {
				String userDN = findUser(user, ctx);
				if(userDN!=null)
				{
					DirContext context = createContext(userDN, pw);
					if(context!=null) {
						context.close();
						return true;
					}
				}
			} catch (NamingException e) {
				logger.error("LDAP search failed for user "+user,e);
			}
			finally {
				try {
					ctx.close();
				} catch (NamingException e) {
					logger.error("Failed to close directory context",e);
				}
			}
		}
		return false;
	}

	private String findUser(String user, DirContext ctx) throws NamingException {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//        controls.setReturningAttributes(new String[]{(String) options.get(KEY_USER_MAIL)});
        String filter = MessageFormat.format("({0}={1})",options.get(KEY_USER_NAME),user);
        NamingEnumeration<SearchResult> result = ctx.search("", filter, controls);
        if(result.hasMore())
        	return result.next().getNameInNamespace();
        return null;
	}


	public DirContext createContext(String userDN, String userPassword) {
		DirContext ctx = null;
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		String providerUrl = MessageFormat.format("ldap://{0}:{1}/{2}", options.get(KEY_LDAP), options.get(KEY_LDAP_PORT), options.get(KEY_ROOT_DN));
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.SECURITY_PRINCIPAL, userDN);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_CREDENTIALS, userPassword);

		try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			logger.warn("Cannot bind user with userDN = " + userDN + " with exception " + e.getLocalizedMessage());
		}

		return ctx;
	}

	@Override
	public boolean commit() throws LoginException {
		if (this.authenticated) {
			subj.getPublicCredentials().add(user);
		} else {
			subj.getPublicCredentials().remove(user);
		}
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		this.authenticated = false;
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		this.authenticated = false;
		return true;
	}

}
