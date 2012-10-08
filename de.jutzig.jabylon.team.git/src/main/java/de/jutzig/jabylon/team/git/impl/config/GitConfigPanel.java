/**
 * 
 */
package de.jutzig.jabylon.team.git.impl.config;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.model.PreferencesPropertyModel;
import de.jutzig.jabylon.team.git.impl.GitConstants;

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
		add(uriField);
		
		PreferencesPropertyModel usernameModel = new PreferencesPropertyModel(config, GitConstants.KEY_USERNAME, "");
		add(new TextField<String>("inputUsername",usernameModel));
		PreferencesPropertyModel passwordModel = new PreferencesPropertyModel(config, GitConstants.KEY_PASSWORD, "");
		PasswordTextField passwordTextField = new PasswordTextField("inputPassword",passwordModel);
		passwordTextField.setResetPassword(false);
		passwordTextField.setRequired(false);
		add(passwordTextField);
	}

	
//	
//	@Override
//	public Component createContents() {
//		form = new Form();
//		form.setImmediate(true);
//		form.setCaption("Git Settings");
//		form.setWriteThrough(true);
//		form.setFormFieldFactory(new DefaultFieldFactory() {
//			@Override
//			public Field createField(Item item, Object propertyId, Component uiContext) {
//				if(propertyId.equals(GitConstants.KEY_PASSWORD))
//				{
//					PasswordField field = new PasswordField();
//					field.setCaption("Password");
//					field.setNullRepresentation("");
//					return field;
//				}
//				Field field = super.createField(item, propertyId, uiContext);
//				if(propertyId.equals(GitConstants.KEY_COMMIT))
//				{
//					field.setDescription("Check to have Jabylon automatically commit all changes to the local Git Repository");
//					field.setCaption("Commit Automatically");
//				}
//				else if(propertyId.equals(GitConstants.KEY_PUSH))
//				{
//					field.setDescription("Check to have Jabylon automatically push all commits to a remote Git Repository");
//					field.setCaption("Push Automatically");
//				} 
//				else if(propertyId.equals(GitConstants.KEY_USERNAME))
//				{
//					field.setCaption("Username");
//					if (field instanceof TextField) {
//						TextField text = (TextField) field;
//						text.setNullRepresentation("");
//						
//					}
//				}
//				return field; 
//			}
//		});
//		return form;
//	}
//
	
}
