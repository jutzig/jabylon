/**
 * 
 */
package de.jutzig.jabylon.team.git.impl.config;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.team.git.impl.GitConstants;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.PreferencesItem;
import de.jutzig.jabylon.ui.util.WeakReferenceAdapter;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConfigSection extends AbstractConfigSection<Project> implements Adapter{

	
	private Form form;
	
	@Override
	public Component createContents() {
		form = new Form();
		form.setImmediate(true);
		form.setCaption("Git Settings");
		form.setWriteThrough(true);
		form.setFormFieldFactory(new DefaultFieldFactory() {
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if(propertyId.equals(GitConstants.KEY_PASSWORD))
				{
					PasswordField field = new PasswordField();
					field.setCaption("Password");
					field.setNullRepresentation("");
					return field;
				}
				Field field = super.createField(item, propertyId, uiContext);
				if(propertyId.equals(GitConstants.KEY_COMMIT))
				{
					field.setDescription("Check to have Jabylon automatically commit all changes to the local Git Repository");
					field.setCaption("Commit Automatically");
				}
				else if(propertyId.equals(GitConstants.KEY_PUSH))
				{
					field.setDescription("Check to have Jabylon automatically push all commits to a remote Git Repository");
					field.setCaption("Push Automatically");
				} 
				else if(propertyId.equals(GitConstants.KEY_USERNAME))
				{
					field.setCaption("Username");
					if (field instanceof TextField) {
						TextField text = (TextField) field;
						text.setNullRepresentation("");
						
					}
				}
				return field; 
			}
		});
		return form;
	}

	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void init(Preferences config) {
		getDomainObject().eAdapters().add(new WeakReferenceAdapter(this));
		form.setVisible(gitSelected());
		PreferencesItem item = new PreferencesItem(config);
		item.addProperty(GitConstants.KEY_USERNAME, String.class, null);
		item.addProperty(GitConstants.KEY_PASSWORD, String.class, null);
		item.addProperty(GitConstants.KEY_COMMIT, Boolean.class, false);
		item.addProperty(GitConstants.KEY_PUSH, Boolean.class, false);
		form.setItemDataSource(item);
		
		
	}

	@Override
	public void notifyChanged(Notification notification) {
		form.setVisible(gitSelected());
		
	}

	private boolean gitSelected() {
		return "Git".equals(getDomainObject().getTeamProvider());
	}

	@Override
	public Notifier getTarget() {
		return getDomainObject();
	}

	@Override
	public void setTarget(Notifier newTarget) {
		
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return false;
	}


}
