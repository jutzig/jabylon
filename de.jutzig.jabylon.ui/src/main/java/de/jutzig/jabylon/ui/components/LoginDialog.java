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

	private static final String JAAS_CONFIG_FILE = "META-INF/jaas.config"; //$NON-NLS-1$

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
				((NameCallback) cb).setName(loginEvent.getLoginParameter("username")); //$NON-NLS-1$
			} else if (cb instanceof PasswordCallback) {
				((PasswordCallback) cb).setPassword(loginEvent.getLoginParameter("password").toCharArray()); //$NON-NLS-1$
			}
		}
	}

	public void display() {
		String configName = "DB"; //$NON-NLS-1$
		URL configUrl = JabylonSecurityBundle.getBundleContext().getBundle().getEntry(JAAS_CONFIG_FILE);
 		final ILoginContext loginContext = LoginContextFactory.createContext(configName, configUrl, this);

		final Window loginWindow = new Window(Messages.getString("LoginDialog_LOGIN_DIALOG_TITLE")); //$NON-NLS-1$
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
					User user = userManagement.findUserByName(loginEvent.getLoginParameter("username")); //$NON-NLS-1$
					app.setUser(user);
					app.getMainWindow().removeWindow(loginWindow);
					
				} catch (LoginException e) {
                    getWindow().showNotification(Messages.getString("LoginDialog_WRONG_CREDENTIALS_TITLE"),Messages.getString("LoginDialog_WRONG_CREDENTIALS_MESSAGE"),Notification.TYPE_ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
				} 
				catch( Exception e ) {
					//TODO: fix login failed
					e.printStackTrace();
                    getWindow().showNotification(Messages.getString("LoginDialog_LOGIN_FAILED_TITLE"),e.getLocalizedMessage(),Notification.TYPE_ERROR_MESSAGE); //$NON-NLS-1$

		
				}
			}
		});
		loginLayout.addComponent(this);
		outerLayout.addComponent(loginLayout);
		outerLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		loginWindow.setContent(outerLayout);
		loginWindow.setHeight("250px"); //$NON-NLS-1$
		loginWindow.setWidth("350px"); //$NON-NLS-1$
		loginWindow.setModal(true);
		app.getMainWindow().addWindow(loginWindow);
	}
}
