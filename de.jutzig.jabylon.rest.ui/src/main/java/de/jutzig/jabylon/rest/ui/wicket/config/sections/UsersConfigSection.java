package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOState;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPanel;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersPackage;

public class UsersConfigSection extends GenericPanel<UserManagement> {

	private static final long serialVersionUID = 1L;

	public UsersConfigSection(String id, IModel<UserManagement> model) {
		super(id, model);
		ComplexEObjectListDataProvider<User> provider = new ComplexEObjectListDataProvider<User>(model, UsersPackage.Literals.USER_MANAGEMENT__USERS);
		ListView<User> userList = new ListView<User>("user.row",provider) {

			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<User> item) {
				
				IModel<String> nameProperty = new EObjectPropertyModel<String, User>(item.getModel(), UsersPackage.Literals.USER__NAME); 	
				item.add(new Label("name", nameProperty));
				item.add(new Label("username", nameProperty));
				item.add(new Label("roles",buildRoles(item.getModelObject())));
				item.add(new ExternalLink("edit","#"));
				item.add(new ExternalLink("delete","#"));
			}
		};
		add(userList);
	}

	protected String buildRoles(User modelObject) {
		StringBuilder builder = new StringBuilder();
		//TODO: shouldn't there be an "allRoles"?
		for (Role role : modelObject.getRoles()) {
			builder.append(role.getName());
			builder.append(", ");
		}
		if(builder.length()>2)
			builder.setLength(builder.length()-2);
		return builder.toString();
	}
	
	private Component buildAddNewLink(IModel<UserManagement> model) {
		PageParameters params = new PageParameters();
		UserManagement project = model.getObject();
		if (project.cdoState() == CDOState.NEW || project.cdoState() == CDOState.TRANSIENT) {
			// it's a new project, we can't add anything yet
			Button link = new Button("addNew");
			link.setEnabled(false);
			return link;
		}
		params.set(0, "security");
		params.add(SettingsPanel.QUERY_PARAM_CREATE, UsersPackage.Literals.USER.getName());
		return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, params);
	}

	public static class UsersConfigSectionContributor extends AbstractConfigSection<UserManagement> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer createContents(String id, IModel<UserManagement> input, Preferences config) {

			return new UsersConfigSection(id, input);
		}

		@Override
		public void commit(IModel<UserManagement> input, Preferences config) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}