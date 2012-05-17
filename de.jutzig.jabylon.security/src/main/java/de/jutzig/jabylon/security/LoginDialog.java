package de.jutzig.jabylon.security;

import java.io.IOException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;

import com.vaadin.Application;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class LoginDialog extends LoginForm implements CallbackHandler {

	private static final String JAAS_CONFIG_FILE = "META-INF/jaas.config";

	private Application app;
	private LoginEvent loginEvent;

	public LoginDialog(Application app) {
		this.app = app;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			Callback cb = callbacks[i];
			if(cb instanceof NameCallback) {
				((NameCallback) cb).setName(loginEvent.getLoginParameter("username"));
			} else if (cb instanceof PasswordCallback) {
				((PasswordCallback) cb).setPassword(loginEvent.getLoginParameter("password").toCharArray());
			}
		}
	}

	public void display() {
		String configName = "DB";
		URL configUrl = JabylonSecurityBundle.getBundleContext().getBundle().getEntry(JAAS_CONFIG_FILE);
 		final ILoginContext loginContext = LoginContextFactory.createContext(configName, configUrl, this);

		final Window loginWindow = new Window("Jabylon - Login");
		HorizontalLayout outerLayout = new HorizontalLayout();
		outerLayout.setMargin(true);
		VerticalLayout loginLayout = new VerticalLayout();
		addListener(new LoginListener() {
			@Override
			public void onLogin(LoginEvent event) {
				try {
					loginEvent = event;
					loginContext.login();
					app.setUser(loginContext.getSubject());
					app.getMainWindow().removeWindow(loginWindow);
				} catch( Throwable e ) {
					//TODO: fix login failed
					e.printStackTrace();
					loginWindow.setComponentError(new UserError("Login failed! Reason: "+e.getMessage()));
				}
			}
		});
		loginLayout.addComponent(this);
		outerLayout.addComponent(loginLayout);
		outerLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		loginWindow.setContent(outerLayout);
		loginWindow.setHeight("250px");
		loginWindow.setWidth("350px");
		loginWindow.setModal(true);
		app.getMainWindow().addWindow(loginWindow);
	}
}
