/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.util.EnumSet;
import java.util.Set;

import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.ui.components.EditableTable;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.forms.NewLocaleForm;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralLocaleConfig extends AbstractConfigSection<Project> {

	private Table localeTable;
	private ProjectVersion version;


	/**
	 * 
	 */
	public GeneralLocaleConfig() {
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		return createLocalesTable();
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub

	}


	private Component createLocalesTable() {
		final Section section = new Section();
		section.setTitle("Locales");
		section.setWidth(400, Section.UNITS_PIXELS);
		GridLayout layout = section.getBody();
		layout.setSpacing(true);


		
		final EditableTable table = new EditableTable() {
			@Override
			protected void addPressed() {
				Window window = new Window();
				window.setContent(new NewLocaleForm(version));
				window.setModal(true);
				window.setWidth(300, Window.UNITS_PIXELS);
				window.setHeight(300, Window.UNITS_PIXELS);
				window.setCaption("Add new Locale");
				window.setName("Add new Locale");
				section.getWindow().addWindow(window);
			}
			
			@Override
			protected void removePressed() {
				Object value = localeTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set)value;
					version.getLocales().removeAll(selection);
				}
				else if (value instanceof ProjectLocale) {
					ProjectLocale locale = (ProjectLocale) value;
					version.getLocales().remove(locale);
					
				}
			}
		};
		
		localeTable = table.getTable();
		localeTable.setWidth(300, Table.UNITS_PIXELS);
		localeTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		localeTable.setMultiSelect(true);
		localeTable.setSelectable(true);
		layout.addComponent(table);
		
		return section;
	}
	
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		version = getDomainObject().getMaster();
		localeTable.setContainerDataSource(new ProjectLocaleTableContainer(version));
		localeTable.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE).toArray());
		localeTable.setItemIconPropertyId(LocaleProperty.FLAG);

	}

}
