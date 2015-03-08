/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.pages;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.AttachableWritableModel;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class RegisterPage extends GenericPage<User> implements RestrictedComponent {
	
	private static final long serialVersionUID = 6952350972929880454L;
	private static final Logger logger = LoggerFactory.getLogger(RegisterPage.class);

	public RegisterPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void construct() {
		super.construct();
		Form<User> form = new Form<User>("form", getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {

				IModel<User> model = RegisterPage.this.getModel();
				User user = model.getObject();
				CDOView cdoView = getUserManagement().cdoView();
				CDOTransaction transaction = cdoView.getSession().openTransaction();
				UserManagement userManagement = transaction.getObject(getUserManagement());
				userManagement.getUsers().add(user);

				model.getObject().setType(CommonPermissions.AUTH_TYPE_DB);
				CommonPermissions.addDefaultPermissions(userManagement, user);
				String username = user.getName();
				String password = user.getPassword();
				commit(userManagement, transaction);
				AuthenticatedWebSession.get().signIn(username, password);
				super.onSubmit();
			}

			protected void commit(CDOObject object, CDOTransaction transaction) {

				
				try {
					transaction.commit();

					setResponsePage(WelcomePage.class);
				} catch (CommitException e) {
					logger.error("failed to commit configuration for " + object, e);
				} finally {
					 transaction.close();
				}
			}
		};
		add(form);
		createContents(form);
	}

	private void createContents(Form<User> form) {

		UserManagement userManagement = getUserManagement();
		ControlGroup usernameGroup = new ControlGroup("username-group", nls("username.label"), nls("username.help.block"));
		RequiredTextField<String> userID = new RequiredTextField<String>("username", new EObjectPropertyModel<String, User>(getModel(),
				UsersPackage.Literals.USER__NAME));
		if (userManagement != null)
			userID.add(UniqueNameValidator.fromCollection(getUserManagement().getUsers(), UsersPackage.Literals.USER__NAME, getModel().getObject()));
		usernameGroup.add(userID);
		form.add(usernameGroup);

		ControlGroup passwordGroup = new ControlGroup("password-group", nls("userpassword.label"), nls("userpassword.help.block"));
		PasswordTextField passwordTextField = new PasswordTextField("userpassword", new EObjectPropertyModel<String, User>(getModel(),
				UsersPackage.Literals.USER__PASSWORD));
		passwordTextField.setRequired(true);
		passwordTextField.setResetPassword(false);
		passwordGroup.add(passwordTextField);
		form.add(passwordGroup);
		
		ControlGroup passwordRepeat = new ControlGroup("password-repeat-group", nls("userpassword.repeat.label"));
		PasswordTextField passwordRepeatTextField = new PasswordTextField("userpassword-repeat", Model.of(""));
		passwordRepeatTextField.setRequired(true);
		passwordRepeatTextField.setResetPassword(false);
		passwordRepeat.add(passwordRepeatTextField);
		form.add(passwordRepeat);

		ControlGroup emailGroup = new ControlGroup("email-group", nls("email.label"), nls("email.help.block"));
		TextField<String> emailField = new TextField<String>("email", new EObjectPropertyModel<String, User>(getModel(), UsersPackage.Literals.USER__EMAIL));
		emailGroup.add(emailField);
		emailField.add(EmailAddressValidator.getInstance());
		emailField.setRequired(true);
		form.add(emailGroup);

		ControlGroup displaynameGroup = new ControlGroup("displayname-group", nls("displayName.label"), nls("displayName.help.block"));
		TextField<String> displayName = new TextField<String>("displayName", new EObjectPropertyModel<String, User>(getModel(),
				UsersPackage.Literals.USER__DISPLAY_NAME));
		displaynameGroup.add(displayName);
		displayName.setRequired(true);
		form.add(displaynameGroup);
		form.add(new EqualPasswordInputValidator(passwordTextField, passwordRepeatTextField));
		
		ControlGroup submitGroup = new ControlGroup("submit-group", null);
		Button submitButton = new Button("submit", new StringResourceModel("submit.button.label", this, null));
		submitGroup.add(submitButton);
		form.add(submitGroup);
	}

	private UserManagement getUserManagement() {
		User anonymous = Activator.getDefault().getAuthenticationService().getAnonymousUser();
		EObject userManagement = anonymous.eContainer();
		return (UserManagement) userManagement;
	}

	@Override
	protected IModel<User> createModel(PageParameters params) {
		return new AttachableWritableModel<User>(UsersPackage.Literals.USER, new EObjectModel<UserManagement>(getUserManagement()), UsersPackage.Literals.USER_MANAGEMENT__USERS);
	}

	@Override
	public String getRequiredPermission() {
		return CommonPermissions.USER_REGISTER;
	}
	
	

}
