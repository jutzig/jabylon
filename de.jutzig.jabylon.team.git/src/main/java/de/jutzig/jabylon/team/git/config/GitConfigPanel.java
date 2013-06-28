/**
 *
 */
package de.jutzig.jabylon.team.git.config;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.model.PreferencesPropertyModel;
import de.jutzig.jabylon.team.git.GitConstants;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConfigPanel extends GenericPanel<Project> {

    private static final long serialVersionUID = 1L;

    public GitConfigPanel(String id, IModel<Project> model, Preferences config) {
        super(id, model);
        EObjectPropertyModel<URI, Project> repositoryURI = new EObjectPropertyModel<URI, Project>(model, PropertiesPackage.Literals.PROJECT__REPOSITORY_URI);
        TextField<URI> uriField = new TextField<URI>("inputURI", repositoryURI);
        uriField.setType(URI.class);
        uriField.setConvertEmptyInputStringToNull(true);
        uriField.setRequired(true);
        add(uriField);

        PreferencesPropertyModel usernameModel = new PreferencesPropertyModel(config, GitConstants.KEY_USERNAME, "");
        add(new TextField<String>("inputUsername",usernameModel));
        PreferencesPropertyModel passwordModel = new PreferencesPropertyModel(config, GitConstants.KEY_PASSWORD, "");
        PasswordTextField passwordTextField = new PasswordTextField("inputPassword",passwordModel);
        passwordTextField.setResetPassword(false);
        passwordTextField.setRequired(false);
        add(passwordTextField);

        PreferencesPropertyModel emailModel = new PreferencesPropertyModel(config, GitConstants.KEY_EMAIL, "jabylon@example.org");
        TextField<String> emailField = new TextField<String>("inputEmail", emailModel);
        emailField.setRequired(true);
        emailField.add(EmailAddressValidator.getInstance());
        add(emailField);
    }

}
