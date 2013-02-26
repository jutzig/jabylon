package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.model.AttachableModel;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersPackage;

public class UserConfigSection extends GenericPanel<User> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserConfigSection(String id, IModel<User> model) {
		super(id, model);
		EObject container = model.getObject().eContainer();
		ComplexEObjectListDataProvider<Permission> userPermissions = new ComplexEObjectListDataProvider<Permission>(model, UsersPackage.Literals.USER__PERMISSIONS);
		ComplexEObjectListDataProvider<Permission> availablePermissions = null;
	
		if (container instanceof UserManagement) {
			UserManagement management = (UserManagement) container;
			availablePermissions = new FilteringListDataProvider(new EObjectModel<UserManagement>(management), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
		}
		else if(model instanceof AttachableModel) {
			availablePermissions = new FilteringListDataProvider(((AttachableModel)model).getParent(), UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
		}
		
		Palette<Permission> palette = new Palette<Permission>("palette", userPermissions, availablePermissions, new PermissionRenderer(), 10, false);
		add(palette);

		add(new RequiredTextField<String>("name",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__NAME)));
		PasswordTextField passwordTextField = new PasswordTextField("password",new EObjectPropertyModel<String,User>(getModel(), UsersPackage.Literals.USER__PASSWORD));
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
	
	private static class PermissionRenderer implements IChoiceRenderer<Permission> {

		private static final long serialVersionUID = 1L;

		@Override
		public Object getDisplayValue(Permission object) {
			return object.getName();
		}

		@Override
		public String getIdValue(Permission object, int index) {
			return object.getName();
		}
		
	}
	
	private class FilteringListDataProvider extends ComplexEObjectListDataProvider<Permission> {

		private static final long serialVersionUID = 1L;

		public FilteringListDataProvider(IModel<? extends CDOObject> model, EStructuralFeature feature) {
			super(model, feature);
		}
		
		@Override
		public List<Permission> getObject() {
			List<Permission> available = super.getObject();
			//filter out the inherited ones
			List<Permission> reduced = new ArrayList<Permission>(available);
			reduced.removeAll(getModel().getObject().getAllPermissions());
			reduced.addAll(getModel().getObject().getPermissions());
			return reduced;
		}
	}
}
