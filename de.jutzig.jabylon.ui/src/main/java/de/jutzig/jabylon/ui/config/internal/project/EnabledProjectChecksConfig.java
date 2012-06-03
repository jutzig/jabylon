/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import org.eclipse.core.runtime.IConfigurationElement;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.container.PreferencesItem;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class EnabledProjectChecksConfig extends AbstractConfigSection<Project> implements ConfigSection {


	private Form form;
	private IConfigurationElement[] reviewParticipants;
	
	public EnabledProjectChecksConfig() {
		reviewParticipants = Activator.getDefault().getReviewParticipants();
		
	}
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		form = new Form();
		form.setWriteThrough(true);
		form.setImmediate(true);
		form.setFormFieldFactory(new DefaultFieldFactory() {
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				Field field = new CheckBox();
				for (IConfigurationElement participants : reviewParticipants) {
					String id = participants.getAttribute("id");
					if(propertyId.equals(id))
					{
						field.setCaption(participants.getAttribute("name"));
						field.setDescription(participants.getAttribute("description"));
						break;
					}
					
				}
				return field;
			}
		});
		return form;
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		PreferencesItem item = new PreferencesItem(config.node(PreferencesUtil.NODE_CHECKS));
		for (IConfigurationElement participants : reviewParticipants) {
			String id = participants.getAttribute("id");
			item.addProperty(id, Boolean.TYPE, false);
		}
		form.setItemDataSource(item);

	}

}
