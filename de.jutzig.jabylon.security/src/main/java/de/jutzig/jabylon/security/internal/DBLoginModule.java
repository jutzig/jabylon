package de.jutzig.jabylon.security.internal;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class DBLoginModule implements LoginModule {
	Subject subj;
	CallbackHandler cbHandler;

	public DBLoginModule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialize(Subject arg0, CallbackHandler arg1, Map<String, ?> arg2, Map<String, ?> arg3) {
		this.subj = arg0;
		this.cbHandler = arg1;
	}

	@Override
	public boolean login() throws LoginException {
		Callback label = new TextOutputCallback(TextOutputCallback.INFORMATION, "Please login to Jabylon");
		NameCallback nameCallback = new NameCallback("User:");
		PasswordCallback passwordCallback = new PasswordCallback("Password:", false);
		try {
			cbHandler.handle(new Callback[]{
			    label, nameCallback, passwordCallback
			});
		} catch(Exception e) {
			//FIXME
			e.printStackTrace();
		}

		String user = nameCallback.getName();
		String pw = "";
		if(passwordCallback.getPassword() != null) {
			pw = String.valueOf(passwordCallback.getPassword());
		}

		return checkLogin(user, pw);
	}

	private boolean checkLogin(String user, String pw) {
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		return true;
	}

}
