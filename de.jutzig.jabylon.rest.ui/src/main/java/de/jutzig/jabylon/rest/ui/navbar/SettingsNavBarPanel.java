/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.common.util.config.DynamicConfigUtil;
import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;
import de.jutzig.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SettingsNavBarPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	public SettingsNavBarPanel(String id, IModel<T> object, PageParameters parameters) {
		super(id, object, parameters);
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link",SettingsPage.class,parameters); //$NON-NLS-1$

		//TODO: this looks shitty with bootstrap currently
		if(AuthenticatedWebSession.get().isSignedIn())
		{
			User user = null;
			Session session = getSession();
			if (session instanceof CDOAuthenticatedSession) {
				CDOAuthenticatedSession cdoSession = (CDOAuthenticatedSession) session;
				user = cdoSession.getUser();
			}
			if(object!=null)
				link.setEnabled(!DynamicConfigUtil.getApplicableElements(object.getObject(), user).isEmpty());
		}
		else
		{
			/*
			 * if the user is not authenticated enable the link by default and trust 
			 * in the intercept page of the authorization strategy
			 */
			if(object!=null)
				link.setEnabled(!DynamicConfigUtil.getApplicableElements(object.getObject()).isEmpty());
		}
		add(link);
	}

	public static class SettingsPanelFactory implements PanelFactory<Object>, Serializable
	{

		private static final long serialVersionUID = 1L;

		@Override
		public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

			return new SettingsNavBarPanel<Object>(id, input, params);
		}
		
	}
}
