/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonApplication extends WebApplication {

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
	    
	    mountPage("/workspace/${segment}",ResourcePage.class);
	}


}