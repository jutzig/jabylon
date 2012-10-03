/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.security.LoginPage;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonApplication extends AuthenticatedWebApplication {

	/* (non-Javadoc)
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return WelcomePage.class;
	}

	@Override
	protected void init()
	{
	    super.init();
//	    mountPage("/workspace", WorkspaceView.class);
	    
//	    mountPage("/workspace/${project}/", ProjectView.class);
//	    mountPage("/workspace/${project}/${version}/", ProjectVersionView.class);
//	    mountPage("/workspace/${project}/${version}/${locale}/", ProjectLocaleView.class);
	    
//	    mountPage("/workspace/${project}/${version}/${locale}/${remainder}", ProjectView.class);
	    mountPage("/login",LoginPage.class);
	    mountPage("/settings/workspace",SettingsPage.class);
	    mountPage("/workspace",ResourcePage.class);
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return CDOAuthenticatedSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}


}