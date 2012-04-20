/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.util.EnumSet;
import java.util.Set;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.PreferencesItem;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.forms.NewLocaleForm;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	private static final String EXCLUDE_FILTER = "excludeFilter";
	private static final String INCLUDE_FILTER = "includeFilter";
	private static final String MASTER_LOCALE = "masterLocale";
	private Form form;
	private ProjectVersion version;
	private Table table;

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		form = new Form();
		form.setWriteThrough(false);
		form.setImmediate(true);
		layout.addComponent(form);
		
		Component table = createTable();
		layout.addComponent(table);
		
		
		return layout;
		
	}

	private Component createTable() {
		Section section = new Section();
		section.setTitle("Locales");
		section.setWidth(400, Section.UNITS_PIXELS);
		GridLayout layout = section.getBody();
		layout.setColumns(2);
		layout.setRows(21);
		layout.setSpacing(true);
		table = new Table();
		table.setWidth(300, Table.UNITS_PIXELS);
		table.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		table.setMultiSelect(true);
		table.setSelectable(true);
		layout.addComponent(table,0,0,0,20);
		
		Button add = new Button("Add");
		add.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				Window window = new Window();
				window.setContent(new NewLocaleForm(version));
				window.setModal(true);
				window.setWidth(300, Window.UNITS_PIXELS);
				window.setHeight(300, Window.UNITS_PIXELS);
				window.setCaption("Add new Locale");
				window.setName("Add new Locale");
				event.getButton().getWindow().addWindow(window);
			}
		});
		layout.addComponent(add);
		layout.setComponentAlignment(add, Alignment.TOP_LEFT);
		
		Button remove = new Button("Remove");
		remove.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				Object value = table.getValue();
				if (value instanceof Set) {
					Set selection = (Set)value;
					version.getLocales().removeAll(selection);
				}
				else if (value instanceof ProjectLocale) {
					ProjectLocale locale = (ProjectLocale) value;
					version.getLocales().remove(locale);
					
				}
			}
		});
		layout.addComponent(remove);
		layout.setComponentAlignment(remove, Alignment.TOP_LEFT);
		return section;
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
		Project object = getDomainObject();
		version = object.getMaster();
		PreferencesItem item = new PreferencesItem(config);
		item.addProperty(MASTER_LOCALE, String.class, "");
		item.addProperty(INCLUDE_FILTER, String.class, "[:\\w/.\\\\&&[^_]]+.properties");
		item.addProperty(EXCLUDE_FILTER, String.class, ".*build.properties");
		form.setItemDataSource(item);
			
		
		
		
//		form.setFormFieldFactory(new DefaultFieldFactory() {
//			
//			@Override
//			public Field createField(Item item, Object propertyId, Component uiContext) {
//				if(propertyId.equals(INCLUDE_FILTER))
//				{
//					TextArea area = new TextArea();
//					area.setCaption(createCaptionByPropertyId(propertyId));
//					area.setRows(1);
//					area.setRequired(true);
//					return area;
//				}
//				else if(propertyId.equals(EXCLUDE_FILTER))
//				{
//					TextArea area = new TextArea();
//					area.setCaption(createCaptionByPropertyId(propertyId));
//					area.setRows(1);
//					return area;
//				}
//				return super.createField(item, propertyId, uiContext);
//			}
//		});
		form.setVisibleItemProperties(new Object[]{MASTER_LOCALE,INCLUDE_FILTER,EXCLUDE_FILTER});
		
		table.setContainerDataSource(new ProjectLocaleTableContainer(version));
		table.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE).toArray());
		table.setItemIconPropertyId(LocaleProperty.FLAG);
	}

	
}


