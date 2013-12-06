package org.jabylon.rest.ui.wicket.config.sections.security;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.components.UserImagePanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;

public class UserConfigSection extends GenericPanel<User> {

    private static final long serialVersionUID = 1L;

    public UserConfigSection(String id, IModel<User> model) {
        super(id, model);

        boolean isLDAP = CommonPermissions.AUTH_TYPE_LDAP.equals(model.getObject().getType());

        add(new UserImagePanel("image", getModel(),true));
        RequiredTextField<String> userID = new RequiredTextField<String>("username",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__NAME));
        userID.setEnabled(!isLDAP);
        add(userID);
        PasswordTextField passwordTextField = new PasswordTextField("userpassword",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__PASSWORD));
        passwordTextField.setRequired(!isLDAP);
        passwordTextField.setEnabled(!isLDAP);
        passwordTextField.setResetPassword(false);
        add(passwordTextField);

        TextField<String> emailField = new TextField<String>("email",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__EMAIL));
        emailField.setEnabled(!isLDAP);
        add(emailField);
        emailField.add(EmailAddressValidator.getInstance());
        TextField<String> displayName = new TextField<String>("displayName",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__DISPLAY_NAME));
        displayName.setEnabled(!isLDAP);
        add(displayName);

        TextField<String> type = new TextField<String>("type",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__TYPE));
        type.setEnabled(false);
        add(type);
    }

    public static class UserConfigSectionContributor extends AbstractConfigSection<User> {

        private static final long serialVersionUID = 1L;

        private static Logger logger = LoggerFactory.getLogger(UserConfigSectionContributor.class);

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<User> input, Preferences config) {

            return new UserConfigSection(id, input);
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
        }


        @Override
        public String getRequiredPermission() {
            String name = "null";
            if(getDomainObject()!=null && getDomainObject().getName()!=null)
                name = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.USER,name,CommonPermissions.ACTION_CONFIG);
        }
    }
}
