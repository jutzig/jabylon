package de.jutzig.jabylon.security.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.UsersFactory;

public class DBLoginModule implements LoginModule {
	static final String EMPTY_STRING = "";
	Subject subj;
	CallbackHandler cbHandler;
	boolean authenticated = false;
	String user;
	String pw;
	List<Permission> permissions = new ArrayList<Permission>();

	public DBLoginModule() {
	}

	@Override
	public boolean abort() throws LoginException {
		this.authenticated = false;
		return true;
	}

	@Override
	public boolean commit() throws LoginException {
		if(this.authenticated) {
			subj.getPublicCredentials().add(user);
			addPermissions(subj.getPrivateCredentials());
		} else {
			subj.getPublicCredentials().remove(user);
			subj.getPrivateCredentials(Permission.class).clear();
		}
		return true;
	}

	private void addPermissions(Set<Object> privateCredentials) {
		for (Permission permission : permissions) {
			privateCredentials.add(permission);
		}
	}

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subj = subject;
		this.cbHandler = callbackHandler;
	}

	@Override
	public boolean login() throws LoginException {
		NameCallback nameCallback = new NameCallback("User:");
		PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
		try {
			cbHandler.handle(new Callback[]{
			    nameCallback, passwordCallback
			});
		} catch(Exception e) {
			//FIXME
			e.printStackTrace();
		}

		user = nameCallback.getName();
		pw = EMPTY_STRING;
		if(passwordCallback.getPassword() != null) {
			pw = String.valueOf(passwordCallback.getPassword());
		}

		this.authenticated = checkLogin(user, pw);
		return this.authenticated;
	}

	private boolean checkLogin(String user, String pw) {
		Permission permission = UsersFactory.eINSTANCE.createPermission();
		permission.setName("ALL");
		permissions.add(permission);
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		this.authenticated = false;
		return true;
	}

}
