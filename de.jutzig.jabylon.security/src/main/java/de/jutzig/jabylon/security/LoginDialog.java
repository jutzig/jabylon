package de.jutzig.jabylon.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.vaadin.ui.LoginForm;

@SuppressWarnings("serial")
public class LoginDialog extends LoginForm implements CallbackHandler {

	@Override
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
		// TODO Auto-generated method stub

	}

}
