/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal;

import java.text.MessageFormat;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.PreferencesItem;
import de.jutzig.jabylon.ui.team.TeamProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	private static final String EXCLUDE_FILTER = "excludeFilter";
	private static final String INCLUDE_FILTER = "includeFilter";
	private static final String MASTER_LOCALE = "masterLocale";
	private static final String TEAM_PROVIDER = "teamProvider";
	private Form form;

	/**
	 * @param domainClass
	 */
	public GeneralProjectConfig() {
		super(Project.class);
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		form = new Form();
		form.setWriteThrough(false);
		form.setImmediate(true);
		return form;
		
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		form.commit();

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		PreferencesItem item = new PreferencesItem(config);
		item.addProperty(TEAM_PROVIDER, String.class, "");
		item.addProperty(MASTER_LOCALE, String.class, "");
		item.addProperty(INCLUDE_FILTER, String.class, "[:\\w/.\\\\&&[^_]]+.properties");
		item.addProperty(EXCLUDE_FILTER, String.class, ".*build.properties");
		form.setItemDataSource(item);
		form.setVisibleItemProperties(new Object[]{MASTER_LOCALE,INCLUDE_FILTER,EXCLUDE_FILTER,TEAM_PROVIDER});
		Field field = form.getField(TEAM_PROVIDER);
		field.addValidator(new Validator() {
			
			@Override
			public void validate(Object value) throws InvalidValueException {
				if(!isValid(value))
				{
					String message = "Team provider {0} could not be found"; 					
					throw new InvalidValueException(MessageFormat.format(message, value));
				}
				
			}
			
			@Override
			public boolean isValid(Object value) {
				TeamProvider provider = MainDashboard.getCurrent().getTeamProviderForURI(URI.createURI("http://example.org/."+value)); //FIXME: need to rework this
				return provider!=null;
			}
		});
	}

}
