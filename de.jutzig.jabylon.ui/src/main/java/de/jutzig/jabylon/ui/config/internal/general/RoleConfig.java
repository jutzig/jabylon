package de.jutzig.jabylon.ui.config.internal.general;

import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.users.Permission;
import de.jutzig.jabylon.users.Role;
import de.jutzig.jabylon.users.User;
import de.jutzig.jabylon.users.UserManagement;
import de.jutzig.jabylon.users.UsersFactory;
import de.jutzig.jabylon.users.UsersPackage;

public class RoleConfig extends AbstractConfigSection<Workspace> implements ConfigSection {
	Table roleTable = null;
	Role selectedRole = null;
	VerticalLayout roleConfig = new VerticalLayout();
	Section roleDetails = null;
	UserManagement userManagement = null;

	@SuppressWarnings("serial")
	@Override
	public Component createContents() {

		roleConfig.setMargin(true);
		roleTable = new Table("Roles");
		roleTable.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());

		roleTable.setSizeFull();
		roleTable.setSelectable(true);
		roleTable.addListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				removeUserDetails();

				selectedRole = (Role) event.getItemId();
				if (selectedRole != null)
					addRoleDetails();
			}
		});
		roleConfig.addComponent(roleTable);
		HorizontalLayout buttonLine = new HorizontalLayout();
		Button addRole = new Button("Add Role");
		addRole.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addRole();
			}
		});
		Button deleteRole = new Button("Delete Role");
		deleteRole.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				deleteRole();
			}
		});
		buttonLine.addComponent(addRole);
		buttonLine.addComponent(deleteRole);
		roleConfig.addComponent(buttonLine);
		return roleConfig;
	}

	private void removeUserDetails() {
		if (roleDetails == null)
			return;
		roleConfig.removeComponent(roleDetails);
		roleDetails = null;
	}

	private void addRoleDetails() {
		roleDetails = new Section();
		roleDetails.setCaption("User: " + selectedRole.getName());

		Select parentSelection = createParentSelection(new BeanItem<Role>(selectedRole));
		roleDetails.addComponent(parentSelection);

		TwinColSelect permissionSelect = new TwinColSelect("Permissions");
		permissionSelect.setMultiSelect(true);
		permissionSelect.setLeftColumnCaption("Available permissions");
		permissionSelect.setRightColumnCaption("Permissions currently assigned");
		permissionSelect.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				System.out.println(event.getProperty());
			}
		});
		GenericEObjectContainer<Permission> ds = new GenericEObjectContainer<Permission>(userManagement,
				UsersPackage.Literals.USER_MANAGEMENT__PERMISSIONS);
		permissionSelect.setContainerDataSource(ds);
		permissionSelect.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		permissionSelect.setItemCaptionPropertyId(UsersPackage.Literals.PERMISSION__DESCRIPTION);
		permissionSelect.setValue(new HashSet<Permission>(selectedRole.getAllPermissions()));
		roleDetails.addComponent(permissionSelect);
		roleConfig.addComponent(roleDetails);
	}

	@SuppressWarnings("serial")
	private void addRole() {
		final Window addRole = new Window("Add role");
		addRole.setModal(true);
		addRole.setHeight("180px");
		addRole.setWidth("280px");
		final Form addRoleForm = new Form();
		addRoleForm.setVisibleItemProperties(new Object[]{"name", "parent"});
		addRoleForm.setFormFieldFactory(new FormFieldFactory() {
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if(propertyId.equals("name"))
					return new TextField("Name:");
				else if(propertyId.equals("parent")) {
					Select parentSelection = createParentSelection((BeanItem<Role>)item);
					return parentSelection;
				}
				return null;
			}

		});

		Role role = UsersFactory.eINSTANCE.createRole();
		role.setName("<name>");
		addRoleForm.setItemDataSource(new BeanItem<Role>(role));

		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(addRoleForm);
		Button ok = new Button("Submit");
		ok.addListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				userManagement.getRoles().add(((BeanItem<Role>) addRoleForm.getItemDataSource()).getBean());
				MainDashboard.getCurrent().getMainWindow().removeWindow(addRole);
			}
		});
		layout.addComponent(ok);
		layout.setMargin(true);
		addRole.setContent(layout);
		MainDashboard.getCurrent().getMainWindow().addWindow(addRole);
	}

	private Select createParentSelection(BeanItem<Role> item) {
		Select parentSelection = new Select("Parent role", userManagement.getRoles());
		GenericEObjectContainer<Permission> ds = new GenericEObjectContainer<Permission>(userManagement, UsersPackage.Literals.USER_MANAGEMENT__ROLES);
		parentSelection.setContainerDataSource(ds);
		parentSelection.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
		parentSelection.setItemCaptionPropertyId(UsersPackage.Literals.ROLE__NAME);
		if(item.getBean().getParent()!=null)
			parentSelection.setValue(item.getBean().getParent());
		return parentSelection;
	}

	private void deleteRole() {
		if (selectedRole == null) {
			MainDashboard.getCurrent().getMainWindow()
					.showNotification("No role select", "Please select a role", Notification.TYPE_WARNING_MESSAGE);
			return;
		} else {
			userManagement.getRoles().remove(selectedRole);
			selectedRole = null;

		}
	}

	private Container getRoles() {
		return new GenericEObjectContainer<User>(userManagement, UsersPackage.Literals.USER_MANAGEMENT__ROLES);
	}

	@Override
	public void commit(Preferences config) {
	}

	@Override
	protected void init(Preferences config) {
		userManagement = (UserManagement)getDomainObject().cdoView().getResource(ServerConstants.USERS_RESOURCE).getContents().get(0);
		roleTable.setContainerDataSource(getRoles());
		roleTable.setVisibleColumns(new Object[] { UsersPackage.Literals.ROLE__NAME });
		roleTable.setColumnHeader(UsersPackage.Literals.ROLE__NAME, "Name");
		roleTable.addGeneratedColumn("Permissions", new ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return getPermissionsList(((Role) itemId).getAllPermissions());
			}

			public String getPermissionsList(EList<Permission> permissions) {
				StringBuffer sb = new StringBuffer();
				for (Permission permission : permissions)
					sb.append(permission.getName()).append(", ");
				return sb.toString();
			}
		});
	}

}
