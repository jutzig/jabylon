package de.jutzig.jabylon.ui.components;

import java.io.IOException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.security.JabylonSecurityBundle;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;

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
					CDOResource resource = MainDashboard.getCurrent().getWorkspace().cdoView().getResource(ServerConstants.USERS_RESOURCE);
					UserManagement userManagement = (UserManagement) resource.getContents().get(0);
					User user = userManagement.findUserByName(loginEvent.getLoginParameter("username"));
					app.setUser(user);
					app.getMainWindow().removeWindow(loginWindow);
					
				} catch (LoginException e) {
                    getWindow().showNotification("Login Failed","Wrong username or password",Notification.TYPE_ERROR_MESSAGE);
				} 
				catch( Exception e ) {
					//TODO: fix login failed
					e.printStackTrace();
                    getWindow().showNotification("Login Failed",e.getLocalizedMessage(),Notification.TYPE_ERROR_MESSAGE);

		
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
