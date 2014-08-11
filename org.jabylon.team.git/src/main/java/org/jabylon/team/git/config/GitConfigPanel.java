/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.team.git.config;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.rest.ui.model.BooleanPreferencesPropertyModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.model.PreferencesPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.validators.UriValidator;
import org.jabylon.team.git.GitConstants;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConfigPanel extends BasicPanel<Project> {

    private static final long serialVersionUID = 1L;
	private Preferences config;

    public GitConfigPanel(String id, IModel<Project> model, Preferences config) {
        super(id, model, new PageParameters());
        this.config = config;
    }
    
    @Override
    protected void construct() {
    	super.construct();
        EObjectPropertyModel<URI, Project> repositoryURI = new EObjectPropertyModel<URI, Project>(getModel(), PropertiesPackage.Literals.PROJECT__REPOSITORY_URI);
        ControlGroup uriGroup = new ControlGroup("uri-group", nls("repository.uri.label"), nls("repository.uri.help"));
        TextField<URI> uriField = new TextField<URI>("gitURI", repositoryURI);
        uriField.setType(URI.class);
        uriField.add(new UriValidator());
        uriField.setConvertEmptyInputStringToNull(true);
        uriField.setRequired(true);
        uriGroup.add(uriField);
        add(uriGroup);

        PreferencesPropertyModel usernameModel = new PreferencesPropertyModel(config, GitConstants.KEY_USERNAME, "");
        ControlGroup usernameGroup = new ControlGroup("username-group", nls("username.label"));
        usernameGroup.add(new TextField<String>("gitUsername",usernameModel));
        add(usernameGroup);
        
        PreferencesPropertyModel passwordModel = new PreferencesPropertyModel(config, GitConstants.KEY_PASSWORD, "");
        ControlGroup passwordGroup = new ControlGroup("password-group", nls("password.label"));
        PasswordTextField passwordTextField = new PasswordTextField("gitPassword",passwordModel);
        passwordTextField.setResetPassword(false);
        passwordTextField.setRequired(false);
        passwordGroup.add(passwordTextField);
        add(passwordGroup);

        PreferencesPropertyModel emailModel = new PreferencesPropertyModel(config, GitConstants.KEY_EMAIL, "jabylon@example.org");
        ControlGroup emailGroup = new ControlGroup("email-group", nls("email.label"), nls("email.help"));
        TextField<String> emailField = new TextField<String>("gitEmail", emailModel);
        emailField.setRequired(true);
        emailField.add(EmailAddressValidator.getInstance());
        emailGroup.add(emailField);
        add(emailGroup);
        
        PreferencesPropertyModel messageModel = new PreferencesPropertyModel(config, GitConstants.KEY_MESSAGE, "");
        ControlGroup messageGroup = new ControlGroup("message-group", nls("message.label"), nls("message.help"));
        TextArea<String> messageField = new TextArea<String>("gitCommitMessage", messageModel);
        messageField.setRequired(false);
        messageGroup.add(messageField);
        add(messageGroup);
        
        BooleanPreferencesPropertyModel changeIdModel = new BooleanPreferencesPropertyModel(config, GitConstants.KEY_INSERT_CHANGE_ID, false);
        ControlGroup changeIdGroup = new ControlGroup("changeId-group", nls("changeId.label"), nls("changeId.help"));
        CheckBox changeIdCheckbox = new CheckBox("changeId", changeIdModel);
        changeIdGroup.add(changeIdCheckbox);
        add(changeIdGroup);
    }

}
