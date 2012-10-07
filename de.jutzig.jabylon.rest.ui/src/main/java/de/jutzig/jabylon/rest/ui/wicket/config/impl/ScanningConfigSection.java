package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.PreferencesPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

public class ScanningConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ScanningConfigSection(String id, IModel<Project> model, Preferences config) {
		super(id, model);
		PreferencesPropertyModel includeModel = new PreferencesPropertyModel(config, PreferencesUtil.SCAN_CONFIG_INCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__INCLUDE.getDefaultValueLiteral());
		add(new TextArea<String>("inputIncludes", includeModel));
		PreferencesPropertyModel excludeModel = new PreferencesPropertyModel(config, PreferencesUtil.SCAN_CONFIG_EXCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__EXCLUDE.getDefaultValueLiteral());
		add(new TextArea<String>("inputExcludes",excludeModel));
		PreferencesPropertyModel templateLocaleModel = new PreferencesPropertyModel(config, PreferencesUtil.SCAN_CONFIG_MASTER_LOCALE, PropertiesPackage.Literals.SCAN_CONFIGURATION__MASTER_LOCALE.getDefaultValueLiteral());
		add(new TextField<String>("inputTemplateLocale",templateLocaleModel));
	}
	
//	@Override
//	protected void init(Preferences config) {
//		PreferencesItem item = new PreferencesItem(config);
//		item.addProperty(PreferencesUtil.SCAN_CONFIG_INCLUDE, String.class, PropertiesPackage.Literals.SCAN_CONFIGURATION__INCLUDE.getDefaultValueLiteral());
//		item.addProperty(PreferencesUtil.SCAN_CONFIG_EXCLUDE, String.class, PropertiesPackage.Literals.SCAN_CONFIGURATION__EXCLUDE.getDefaultValueLiteral());
//		item.addProperty(PreferencesUtil.SCAN_CONFIG_MASTER_LOCALE, String.class, PropertiesPackage.Literals.SCAN_CONFIGURATION__MASTER_LOCALE.getDefaultValueLiteral());
//		form.setItemDataSource(item);
//
//	}

	public static class ScanningConfig extends AbstractConfigSection<Project> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer createContents(String id, IModel<Project> input, Preferences prefs) {
			return new ScanningConfigSection(id, input, prefs);
		}

		@Override
		public void commit(IModel<Project> input, Preferences config) {
			// TODO Auto-generated method stub
			
		}

	}

	
}