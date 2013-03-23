package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.users.User;

public class ConfigTabPanel<T extends CDOObject> extends GenericPanel<T> {

	
	private static final long serialVersionUID = 1L;
	

	public ConfigTabPanel(String id, final List<ConfigSection<T>> sections, final IModel<T> model, final Preferences preferences) {
		super(id, model);
		ListView<ConfigSection<T>> view = new ListView<ConfigSection<T>>("sections", sections) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ConfigSection<T>> arg0) {
				ConfigSection<T> object = arg0.getModelObject();
				WebMarkupContainer container = object.createContents("content", model, preferences);
				arg0.add(container);
				container.setVisibilityAllowed(hasPermission(object));
			}

		};
		add(view);
	}

	private boolean hasPermission(ConfigSection<T> object) {
		User user = ((CDOAuthenticatedSession)CDOAuthenticatedSession.get()).getUser();
		if(user==null)
			return false;
		return user.hasPermission(object.getRequiredPermission());
	}
}
