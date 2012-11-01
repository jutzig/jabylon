/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.security.LoginPage;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;
import de.jutzig.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class LoginPanel<T extends Resolvable<?, ?>> extends BasicResolvablePanel<T> {

	private static final long serialVersionUID = 1L;

	public LoginPanel(String id, T object, PageParameters parameters) {
		super(id, object, parameters);
		Session theSession = getSession();

		if (theSession instanceof CDOAuthenticatedSession) {
			final CDOAuthenticatedSession session = (CDOAuthenticatedSession) theSession;
			User user = session.getUser();
			if (user != null) {
				LogoutLink link = new LogoutLink("link");
				link.add(new Label("link-label", "Logout " + user.getName()));
				add(link);
				return;
			}
		}
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class);
		link.add(new Label("link-label", "Login"));
		add(link);

	}

	public static class LoginPanelFactory implements PanelFactory, Serializable {

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("rawtypes")
		@Override
		public Panel createPanel(PageParameters params, Object input, String id) {

			if (input instanceof Resolvable) {
				Resolvable r = (Resolvable) input;
				return new LoginPanel<Resolvable<?, ?>>(id, r, params);
			}
			return null;
		}

	}
}

class LogoutLink extends StatelessLink<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8192886483968891414L;

	public LogoutLink(String id) {
		super(id);
	}

	@Override
	public void onClick() {
		Session theSession = getSession();
		if (theSession instanceof AuthenticatedWebSession) {
			AuthenticatedWebSession session = (AuthenticatedWebSession) theSession;
			session.invalidate();
			MarkupContainer parent = getParent();
			BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", LoginPage.class);
			link.add(new Label("link-label", "Login"));
			parent.addOrReplace(link);
		}

	}

}
