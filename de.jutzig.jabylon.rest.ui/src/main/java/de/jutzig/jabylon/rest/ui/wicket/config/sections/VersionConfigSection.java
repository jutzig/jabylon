package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOState;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.model.EObjectPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;

public class VersionConfigSection extends GenericPanel<ProjectVersion> {

	private static final long serialVersionUID = 1L;

	public VersionConfigSection(String id, IModel<ProjectVersion> model) {
		super(id, model);
		IModel<String> nameProperty = new EObjectPropertyModel<String, ProjectVersion>(model, PropertiesPackage.Literals.RESOLVABLE__NAME); 	
		TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
		add(field);
		add(buildAddNewLink(getModel()));
		ComplexEObjectListDataProvider<ProjectLocale> provider = new ComplexEObjectListDataProvider<ProjectLocale>(model.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<ProjectLocale> locale = new ListView<ProjectLocale>("children",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ProjectLocale> item) {
				
				Locale locale = item.getModelObject().getLocale();
				String displayName = locale == null ? "Template" : locale.getDisplayName(getSession().getLocale());
				item.add(new Label("name",displayName));
			}
		};
		add(locale);
	}

	private Component buildAddNewLink(IModel<ProjectVersion> model) {
		if(model.getObject().cdoState()==CDOState.NEW || model.getObject().cdoState()==CDOState.TRANSIENT)
		{
//			it's a new object, we can't add anything yet
			Button link = new Button("addNew");
			link.setEnabled(false);
			return link;
		}
		PageParameters params = new PageParameters();
		params.set(0, model.getObject().getParent().getName());
		params.set(1, model.getObject().getName());
		params.add(SettingsPage.QUERY_PARAM_CREATE, PropertiesPackage.Literals.PROJECT_LOCALE.getName());
		return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, params);
	}

	public static class VersionConfig extends AbstractConfigSection<ProjectVersion> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer createContents(String id, IModel<ProjectVersion> input, Preferences prefs) {
			return new VersionConfigSection(id, input);
		}

		@Override
		public void commit(IModel<ProjectVersion> input, Preferences config) {
			// TODO Auto-generated method stub
			// TODO rename on filesystem 
			
		}
		

	}

}
