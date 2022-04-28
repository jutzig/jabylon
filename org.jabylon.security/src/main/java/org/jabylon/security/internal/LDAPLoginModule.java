/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security.internal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.jabylon.security.CommonPermissions;
import org.jabylon.security.GroupMemberAttribute;
import org.jabylon.security.SubjectAttribute;
import org.jabylon.users.UsersPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPLoginModule implements LoginModule {

    /** the ldap server url */
    public static final String KEY_LDAP = "ldap";
    /** the protocol schema (ldap, or ldaps)*/
    public static final String KEY_LDAP_SCHEME = "ldap.scheme";
    /** if certificate checks should be disabled (true) */
    public static final String KEY_LDAP_TRUST_ALL = "ldap.trust.all";
    /** the ldap server port */
    public static final String KEY_LDAP_PORT = "ldap.port";
    /** the uid attribute of a user */
    public static final String KEY_USER_NAME = "user.id";
    /** the full name attribute of a user */
    public static final String KEY_USER_FULL_NAME = "user.name";
    /** the email attribute of a user */
    public static final String KEY_USER_MAIL = "user.mail";
    /** the root dn to query agains */
    public static final String KEY_ROOT_DN = "root.dn";
    /** the ldap manager id */
    public static final String KEY_MANAGER = "manager";
    /** the ldap manager password */
    public static final String KEY_MANAGER_PASSWORD = "manager.password";

    /** the attribute that determines what groups a user is in */
    public static final String KEY_MEMBER_OF = "member.of";
    /** the attribute that determines how the group is named */
    public static final String KEY_GROUP_NAME = "group.name";

    private Subject subj;
    private CallbackHandler cbHandler;
    private Map<String, ?> options;
    private boolean authenticated;
    private String user;
    private static final Logger logger = LoggerFactory.getLogger(LDAPLoginModule.class);

    private DirContext ctx;
    private String email;
    private String fullName;
    private Set<String> groups;

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
        controls.setReturningAttributes(getUserAttributes());
        String filter = MessageFormat.format("({0}={1})",options.get(KEY_USER_NAME),user);
        NamingEnumeration<SearchResult> result = ctx.search("", filter, controls);
        if(result.hasMore()){
            SearchResult searchResult = result.next();
            String userId = searchResult.getNameInNamespace();
            Attributes attributes = searchResult.getAttributes();
            if(options.get(KEY_USER_MAIL) instanceof String) {

                Attribute attribute = attributes.get((String) options.get(KEY_USER_MAIL));
                if(attribute!=null)
                    email = (String) attribute.get();
            }
            if(options.get(KEY_USER_FULL_NAME) instanceof String) {

                Attribute attribute = attributes.get((String) options.get(KEY_USER_FULL_NAME));
                if(attribute!=null)
                    fullName = (String) attribute.get();
            }
            if(options.get(KEY_USER_FULL_NAME) instanceof String) {
                Attribute attribute = attributes.get(((String)options.get(KEY_MEMBER_OF)));
                if(attribute!=null) {
                    NamingEnumeration<?> groupsEnum = attribute.getAll();
                    groups = new HashSet<String>();
                    groups.add(CommonPermissions.ROLE_LDAP_REGISTERED);
                    while (groupsEnum.hasMoreElements()) {
                        Object object = groupsEnum.nextElement();
                        if (object instanceof String) {
                            String groupName = (String) object;
                            groups.add(lookupGroupName(ctx, groupName));
                        }
                    }
                }
            }

            return userId;
        }
        return null;
    }

    private String lookupGroupName(DirContext ctx, String groupName) throws NamingException
    {
        try {
            String nameWithoutRootDn = groupName.substring(0, groupName.indexOf((String)options.get(KEY_ROOT_DN))-1);
            Attributes attr = ctx.getAttributes(nameWithoutRootDn, new String[] {(String)options.get(KEY_GROUP_NAME)});
            String groupDisplayName = (String)attr.get((String)options.get(KEY_GROUP_NAME)).get();
            return groupDisplayName;
        } catch (Exception e) {
            return groupName;
        }
    }

    private String[] getUserAttributes() {
        List<String> attributes = new ArrayList<String>();
        if(options.get(KEY_USER_MAIL) instanceof String)
            attributes.add((String) options.get(KEY_USER_MAIL));
        if(options.get(KEY_USER_FULL_NAME) instanceof String)
            attributes.add((String) options.get(KEY_USER_FULL_NAME));
        if(options.get(KEY_MEMBER_OF) instanceof String)
            attributes.add((String) options.get(KEY_MEMBER_OF));
        return attributes.toArray(new String[attributes.size()]);
    }

    public DirContext createContext(String userDN, String userPassword) {
    	if(!options.containsKey(KEY_LDAP) || !options.containsKey(KEY_LDAP_PORT)) {
    		logger.debug("No LDAP url configured, skipping LDAP authentication");
    		return null;
    	}
        DirContext ctx = null;
        Hashtable<String, String> env = new Hashtable<>();
        String scheme = Optional.ofNullable(options.get(KEY_LDAP_SCHEME)).map(String::valueOf).orElse("ldaps");
        String port = Optional.ofNullable(options.get(KEY_LDAP_PORT)).map(String::valueOf).orElse("636");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        String providerUrl = MessageFormat.format("{3}://{0}:{1}/{2}",  options.get(KEY_LDAP), port, options.get(KEY_ROOT_DN), scheme);
        env.put(Context.PROVIDER_URL, providerUrl);
        if(userDN!=null)
        	env.put(Context.SECURITY_PRINCIPAL, userDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if(userPassword!=null)
        	env.put(Context.SECURITY_CREDENTIALS, userPassword);
        if("ldaps".equals(scheme))
        {
        	env.put(Context.SECURITY_PROTOCOL, "ssl");
        	if(Optional.ofNullable(options.get(KEY_LDAP_TRUST_ALL)).map(String::valueOf).map(Boolean::parseBoolean).orElse(false))
        	{
                env.put("java.naming.ldap.factory.socket", TrustAllSocketFactory.class.getName());
        	}
        }

        try {
            ctx = new InitialDirContext(env);
        } catch (NamingException e) {
            logger.warn("Cannot bind user with userDN = " + userDN + " with exception " + e.getLocalizedMessage(),e);
        }

        return ctx;
    }

    @Override
    public boolean commit() throws LoginException {
        if (this.authenticated) {
            subj.getPublicCredentials().add(user);
            if(email!=null && !email.isEmpty())
                subj.getPublicCredentials().add(new SubjectAttribute(UsersPackage.Literals.USER__EMAIL, email));
            if(fullName!=null && !fullName.isEmpty())
                subj.getPublicCredentials().add(new SubjectAttribute(UsersPackage.Literals.USER__DISPLAY_NAME, fullName));
            if(groups!=null && !groups.isEmpty())
                subj.getPublicCredentials().add(new GroupMemberAttribute(groups));
            subj.getPublicCredentials().add(new SubjectAttribute(UsersPackage.Literals.USER__TYPE, CommonPermissions.AUTH_TYPE_LDAP));

        } else {
            subj.getPublicCredentials().remove(user);
            return false;
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

    public static class TrustAllSocketFactory extends SSLSocketFactory
    {
        private SSLSocketFactory socketFactory;

        public TrustAllSocketFactory() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, new TrustManager[] { new DummyTrustmanager() }, new SecureRandom());
                socketFactory = ctx.getSocketFactory();
            } catch (Exception ex) {
            	logger.error("Failed to create TrustAllSocketFactory");
            }
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return socketFactory.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return socketFactory.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket socket, String string, int i, boolean bln) throws IOException {
            return socketFactory.createSocket(socket, string, i, bln);
        }

        @Override
        public Socket createSocket(String string, int i) throws IOException, UnknownHostException {
            return socketFactory.createSocket(string, i);
        }

        @Override
        public Socket createSocket(String string, int i, InetAddress ia, int i1) throws IOException, UnknownHostException {
            return socketFactory.createSocket(string, i, ia, i1);
        }

        @Override
        public Socket createSocket(InetAddress ia, int i) throws IOException {
            return socketFactory.createSocket(ia, i);
        }

        @Override
        public Socket createSocket(InetAddress ia, int i, InetAddress ia1, int i1) throws IOException {
            return socketFactory.createSocket(ia, i, ia1, i1);
        }

        @Override
        public Socket createSocket() throws IOException {
            return socketFactory.createSocket();
        }
    }

    public static class DummyTrustmanager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            // do nothing
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            // do nothing
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }
}
