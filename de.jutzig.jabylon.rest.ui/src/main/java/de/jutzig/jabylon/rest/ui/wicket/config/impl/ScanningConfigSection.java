package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

public class ScanningConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ScanningConfigSection(String id, IModel<Project> model, Preferences config) {
		super(id, model);
		add(new TextArea<String>("inputIncludes"));
		add(new TextArea<String>("inputExcludes"));
		add(new TextField<String>("inputTemplateLocale"));
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