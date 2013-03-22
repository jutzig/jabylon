package de.jutzig.jabylon.rest.ui.wicket.config.sections.security;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UsersPackage;

public class UserConfigSection extends GenericPanel<User> {

	private static final long serialVersionUID = 1L;

	public UserConfigSection(String id, IModel<User> model) {
		super(id, model);
		add(new RequiredTextField<String>("username",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__NAME)));
		PasswordTextField passwordTextField = new PasswordTextField("userpassword",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__PASSWORD));
		passwordTextField.setRequired(false);
		passwordTextField.setResetPassword(false);
		add(passwordTextField);

	}

	public static class UserConfigSectionContributor extends AbstractConfigSection<User> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer createContents(String id, IModel<User> input, Preferences config) {

			return new UserConfigSection(id, input);
		}

		@Override
		public void commit(IModel<User> input, Preferences config) {


		}

	}
}
