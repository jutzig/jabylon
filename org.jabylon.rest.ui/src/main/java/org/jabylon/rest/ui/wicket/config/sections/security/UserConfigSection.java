/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import java.security.SecureRandom;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.BooleanPreferencesPropertyModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.components.UserImagePanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserConfigSection extends BasicPanel<User> {

    private static final String GENERATE_TOKEN_PREF = "generateToken";
	private static final long serialVersionUID = 1L;

    public UserConfigSection(String id, IModel<User> model, Preferences prefs) {
        super(id, model);

        boolean isLDAP = CommonPermissions.AUTH_TYPE_LDAP.equals(model.getObject().getType());

        add(new UserImagePanel("image", getModel(),true));
        UserManagement userManagement = getUserManagement();
        ControlGroup usernameGroup = new ControlGroup("username-group",nls("username.label"),nls("username.help.block"));
        RequiredTextField<String> userID = new RequiredTextField<String>("username",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__NAME));
        userID.setEnabled(!isLDAP);
        if(userManagement!=null)
        	userID.add(UniqueNameValidator.fromCollection(getUserManagement().getUsers(), UsersPackage.Literals.USER__NAME, getModel().getObject()));
        usernameGroup.add(userID);
        add(usernameGroup);
        
        ControlGroup passwordGroup = new ControlGroup("password-group",nls("userpassword.label"),nls("userpassword.help.block"));
        PasswordTextField passwordTextField = new PasswordTextField("userpassword",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__PASSWORD));
        passwordTextField.setRequired(!isLDAP);
        passwordTextField.setEnabled(!isLDAP);
        passwordTextField.setResetPassword(false);
        passwordGroup.add(passwordTextField);
        add(passwordGroup);

        ControlGroup emailGroup = new ControlGroup("email-group",nls("email.label"),nls("email.help.block"));
        TextField<String> emailField = new TextField<String>("email",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__EMAIL));
        emailField.setEnabled(!isLDAP);
        emailGroup.add(emailField);
        emailField.add(EmailAddressValidator.getInstance());
        add(emailGroup);
        
        ControlGroup displaynameGroup = new ControlGroup("displayname-group",nls("displayName.label"),nls("displayName.help.block"));
        TextField<String> displayName = new TextField<String>("displayName",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__DISPLAY_NAME));
        displayName.setEnabled(!isLDAP);
        displaynameGroup.add(displayName);
        add(displaynameGroup);
        
        ControlGroup typeGroup = new ControlGroup("type-group",nls("login.type"),nls("type.help.block"));
        TextField<String> type = new TextField<String>("type",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__TYPE));
        type.setEnabled(false);
        typeGroup.add(type);
        add(typeGroup);

        Label tokenLabel = new Label("token",new EObjectPropertyModel<String, User>(model, UsersPackage.Literals.USER__TOKEN));
        add(tokenLabel);
        CheckBox generateToken = new CheckBox("generate-token", new BooleanPreferencesPropertyModel(prefs, GENERATE_TOKEN_PREF, false));
//        CheckBox generateToken = new CheckBox("generate-token", Model.of(true));
		add(generateToken);
    }  

    @SuppressWarnings("rawtypes")
	public UserManagement getUserManagement() {
    	IModel<User> model = getModel();
    	if (model instanceof AttachableModel) {
			AttachableModel attachable = (AttachableModel) model;
			IModel<?> parent = attachable.getParent();
			if (parent.getObject() instanceof UserManagement) {
				return (UserManagement) parent.getObject();
			}	
		}
    	else if (model.getObject().eContainer() instanceof UserManagement) {
			return (UserManagement) model.getObject().eContainer();
		}
    	return null;
    }
    
    public static class UserConfigSectionContributor extends AbstractConfigSection<User> {

        private static final long serialVersionUID = 1L;

        private static Logger logger = LoggerFactory.getLogger(UserConfigSectionContributor.class);

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<User> input, Preferences config) {

            return new UserConfigSection(id, input,config);
        }

        @Override
        public void commit(IModel<User> input, Preferences config) {
            User user = input.getObject();

            if(input instanceof AttachableModel) {
                @SuppressWarnings("rawtypes")
                AttachableModel<?> model = (AttachableModel)input;
                Object container = model.getParent().getObject();
                if (container instanceof UserManagement) {
                    // only initialize defaults if it is an attachable model (new user)
                    UserManagement userManagement = (UserManagement) container;
                    CommonPermissions.addDefaultPermissions(userManagement,user);
                }
                else
                    logger.error("Failed to obtain usermanagement for "+user+". Default permissions will not be initialized");
            }
            if(config.getBoolean(GENERATE_TOKEN_PREF, false))
            	user.setToken(generateToken());
            config.putBoolean(GENERATE_TOKEN_PREF, false);
            //we don't really need to store this value, it's just to communicate the intention
            config.remove(GENERATE_TOKEN_PREF);
        }


        @Override
        public String getRequiredPermission() {
            String name = "null";
            if(getDomainObject()!=null && getDomainObject().getName()!=null)
                name = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.USER,name,CommonPermissions.ACTION_CONFIG);
        }
        
        protected String generateToken() {
        	SecureRandom random = new SecureRandom();
        	StringBuilder result = new StringBuilder();
        	//glues 2 random longs together
        	long number = random.nextLong();
        	result.append(Long.toHexString(number));
        	number = random.nextLong();
        	result.append(Long.toHexString(number));
        	return result.toString();
    	}           
    }
}

//unfortunately the ajax button seems to mess with the form processing
//class GenerateTokenButton extends AjaxButton{
//	
//	public GenerateTokenButton(String id) {
//		super(id);
//	}
//
//	private static final long serialVersionUID = 1L;
//	
//	@Override
//	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//		super.onSubmit(target, form);
////		target.add(token);
////		token.getModel().setObject(generateToken());
//	}
//}
