package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOState;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.util.PropertyResourceUtil;
import de.jutzig.jabylon.rest.ui.model.AttachableWritableModel;
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
		// add(buildAddNewLink(getModel()));
		final WebMarkupContainer rowPanel = new WebMarkupContainer("rowPanel");
		rowPanel.setOutputMarkupId(true);
		add(rowPanel);
		final TextField<String> languageField = new TextField<String>("inputLanguage", Model.of(""));
		languageField.setOutputMarkupId(true);
		rowPanel.add(languageField);
		final TextField<String> countryField = new TextField<String>("inputCountry", Model.of(""));
		rowPanel.add(countryField);
		countryField.setOutputMarkupId(true);
		final TextField<String> variantField = new TextField<String>("inputVariant", Model.of(""));
		rowPanel.add(variantField);
		variantField.setOutputMarkupId(true);
		final ComplexEObjectListDataProvider<ProjectLocale> provider = new ComplexEObjectListDataProvider<ProjectLocale>(model,
				PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		final ListChoice<ProjectLocale> choice = new ListChoice<ProjectLocale>("children", provider);
		choice.setMaxRows(15);
		choice.setModel(new AttachableWritableModel<ProjectLocale>(PropertiesPackage.Literals.PROJECT_LOCALE, getModel()));
		 
		choice.add(new AjaxFormComponentUpdatingBehavior ("onchange") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(languageField);
				target.add(countryField);
				target.add(variantField);
				
				ProjectLocale locale = choice.getModelObject();
				if(locale==null || locale.getLocale()==null)
				{
					languageField.setModelObject("");
					countryField.setModelObject("");
					variantField.setModelObject("");	
				}
				else
				{
					languageField.setModelObject(locale.getLocale().getLanguage());
					countryField.setModelObject(locale.getLocale().getCountry());
					variantField.setModelObject(locale.getLocale().getVariant());					
				}
					
				
				
			}
		});
		choice.setChoiceRenderer(new IChoiceRenderer<ProjectLocale>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(ProjectLocale object) {
				Locale locale = object.getLocale();
				String displayName = locale == null ? "Template" : locale.getDisplayName(getSession().getLocale());
				return displayName;
			}

			@Override
			public String getIdValue(ProjectLocale object, int index) {
				Locale locale = object.getLocale();
				if(locale==null)
					return "template";
				return locale.toString();
			}
		
		});

		rowPanel.add(choice);

		AjaxSubmitLink addLink = new AjaxSubmitLink("addLocale") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				ProjectLocale newLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
				Locale locale = new Locale(languageField.getModelObject(),countryField.getModelObject(),variantField.getModelObject());
				newLocale.setLocale(locale);
				
				PropertyResourceUtil.addNewLocale(newLocale, getModelObject());
				if (target != null)
					target.add(rowPanel);
			}
		};
		addLink.setDefaultFormProcessing(false);
		rowPanel.add(addLink);
		
		
		AjaxSubmitLink removeLink = new AjaxSubmitLink("removeLocale") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				provider.getObject().remove(choice.getModelObject());
				if (target != null)
					target.add(rowPanel);
			}
		};
		removeLink.setDefaultFormProcessing(false);
		rowPanel.add(removeLink);
	}

	private Component buildAddNewLink(IModel<ProjectVersion> model) {
		if (model.getObject().cdoState() == CDOState.NEW || model.getObject().cdoState() == CDOState.TRANSIENT) {
			// it's a new object, we can't add anything yet
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
