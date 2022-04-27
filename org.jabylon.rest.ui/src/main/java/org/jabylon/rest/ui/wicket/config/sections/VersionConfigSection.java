/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.util.PropertyResourceUtil;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.EObjectPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.UniqueNameValidator;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionConfigSection extends BasicPanel<ProjectVersion> {

    private static final String TEMPLATE_ID = "template";

	private static final long serialVersionUID = 1L;

	private ListModel<String> choiceModel;

    public VersionConfigSection(String id, IModel<ProjectVersion> model, ListModel<String> locales, Preferences prefs) {
        super(id, model);
        IModel<String> nameProperty = new EObjectPropertyModel<String, ProjectVersion>(model, PropertiesPackage.Literals.RESOLVABLE__NAME);
        TextField<String> field = new RequiredTextField<String>("inputName", nameProperty);
        field.add(UniqueNameValidator.fromCollection(getBranches(model),PropertiesPackage.Literals.RESOLVABLE__NAME,model.getObject()));
        field.add(new PatternValidator("[A-Za-z0-9.\\-+#]+"));
        add(field);

        ControlGroup readOnlyGroup = new ControlGroup("readonly-group",null, nls("readonly.help"));
        CheckBox readonly = new CheckBox("readonly", new EObjectPropertyModel<Boolean, ProjectVersion>(model, PropertiesPackage.Literals.PROJECT_VERSION__READ_ONLY));
        readOnlyGroup.add(readonly);
        add(readOnlyGroup);

        // add(buildAddNewLink(getModel()));
        final WebMarkupContainer rowPanel = new WebMarkupContainer("rowPanel");
        rowPanel.setOutputMarkupId(true);
        add(rowPanel);
        Form<?> form = new Form<Object>("locale-form");
        form.setOutputMarkupId(true);
        rowPanel.add(form);
        final TextField<String> languageField = new TextField<String>("inputLanguage", Model.of(""));
        languageField.setOutputMarkupId(true);
        form.add(languageField);
        final TextField<String> countryField = new TextField<String>("inputCountry", Model.of(""));
        countryField.setOutputMarkupId(true);
        form.add(countryField);
        final TextField<String> variantField = new TextField<String>("inputVariant", Model.of(""));
        variantField.setOutputMarkupId(true);
        form.add(variantField);
        choiceModel = locales;
        final ListChoice<String> choice = new ListChoice<String>("children", choiceModel);
        choice.setModel(choiceModel.getObject().isEmpty() ? Model.of("") : Model.of(choiceModel.getObject().get(0)));
        choice.setMaxRows(15);

        choice.add(new AjaxFormComponentUpdatingBehavior ("onchange") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(languageField);
                target.add(countryField);
                target.add(variantField);

                String localeString = choice.getModelObject();
                if(localeString==null || TEMPLATE_ID.equals(localeString))
                {
                    languageField.setModelObject("");
                    countryField.setModelObject("");
                    variantField.setModelObject("");
                }
                else
                {
                	Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeString);
                    languageField.setModelObject(locale.getLanguage());
                    countryField.setModelObject(locale.getCountry());
                    variantField.setModelObject(locale.getVariant());
                }



            }
        });
        choice.setChoiceRenderer(new IChoiceRenderer<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Object getDisplayValue(String object) {
            	if(TEMPLATE_ID.equals(object))
            		return "Template";

                Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, object);
                String displayName = locale == null ? "Template" : locale.getDisplayName(getSession().getLocale());
                return displayName;
            }

            @Override
            public String getIdValue(String locale, int index) {
                if(locale==null)
                    return TEMPLATE_ID;
                return locale.toString();
            }

        });

        rowPanel.add(choice);

        AjaxSubmitLink addLink = new AjaxSubmitLink("addLocale", form) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit(AjaxRequestTarget target) {
//                ProjectLocale newLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
                Locale locale = new Locale(safeGet(languageField.getModelObject()),safeGet(countryField.getModelObject()),safeGet(variantField.getModelObject()));
                //make sure to avoid duplicates
                if(!choiceModel.getObject().contains(locale.toString()))
                	choiceModel.getObject().add(locale.toString());
                choice.setModelObject(locale.toString());
//                newLocale.setLocale(locale);
//                PropertyResourceUtil.addNewLocale(newLocale, getModelObject());
                if (target != null)
                    target.add(rowPanel);
            }
        };
//        addLink.setDefaultFormProcessing(false);
        form.add(addLink);


        AjaxSubmitLink removeLink = new AjaxSubmitLink("removeLocale", form) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit(AjaxRequestTarget target) {
            	choiceModel.getObject().remove(choice.getModelObject());
//                provider.getObject().remove(choice.getModelObject());
                if (target != null)
                    target.add(rowPanel);
            }
        };
//        removeLink.setDefaultFormProcessing(false);
        form.add(removeLink);
    }


    public Collection<ProjectVersion> getBranches(IModel<ProjectVersion> current) {
    	if(current==null)
    		return Collections.emptyList();
    	Project parent = current.getObject().getParent();
    	if(parent==null && current instanceof AttachableModel) {
    		AttachableModel<ProjectVersion> model = (AttachableModel<ProjectVersion>)current;
    		parent = (Project) model.getParent().getObject();
    	}
    	if(parent==null)
    		return Collections.emptyList();
    	return parent.getChildren();
    }

    public static class VersionConfig extends AbstractConfigSection<ProjectVersion> {

        private static final long serialVersionUID = 1L;

        private static final Logger LOGGER = LoggerFactory.getLogger(VersionConfig.class);

        private ListModel<String> locales;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<ProjectVersion> input, Preferences prefs) {
        	locales = createListModel(input);
            return new VersionConfigSection(id, input, locales,prefs);
        }

        @Override
        public void commit(IModel<ProjectVersion> input, Preferences config) {
        	applyLocaleList(locales.getObject(), input);

        }


        /**
         * calculates a diff between existing locales and selected ones.
         * Deletes and adds ProjectLocales as needed
         * @param object
         * @param input
         */
        protected void applyLocaleList(List<String> object, IModel<ProjectVersion> input) {
        	Set<String> locales = new HashSet<String>(object);
        	ProjectVersion version = input.getObject();
        	List<ProjectLocale> projectLocales = version.getChildren();
        	List<ProjectLocale> toDelete = new ArrayList<>();
        	for (Iterator<ProjectLocale> iterator = projectLocales.iterator(); iterator.hasNext();) {
				ProjectLocale projectLocale = iterator.next();
				if(projectLocale.isMaster())
        			continue;
        		if(!locales.remove(projectLocale.getName())) {
        			toDelete.add(projectLocale);
        		}
			}
        	for (ProjectLocale projectLocale : toDelete) {
        		LOGGER.info("Deleting ProjectLocale {} from {}",projectLocale.getName(), version.fullPath());
        		EcoreUtil.delete(projectLocale, true);
			}
        	for (String locale : locales) {
        		ProjectLocale projectLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
        		projectLocale.setLocale((Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, locale));
        		LOGGER.info("Adding ProjectLocale {} to {}",projectLocale.getName(), version.fullPath());
				PropertyResourceUtil.addNewLocale(projectLocale, input.getObject());
			}

		}

		private ListModel<String> createListModel(IModel<ProjectVersion> parent) {
        	ProjectVersion projectVersion = parent.getObject();
        	EList<ProjectLocale> children = projectVersion.getChildren();

        	List<String> list = new ArrayList<String>(children.size());
        	for (ProjectLocale projectLocale : children) {
        		if(projectLocale.isMaster())
        			//don't allow to delete the template
        			continue;
    			list.add(projectLocale.getName());
    		}
    		return new ListModel<String>(list);
    	}


        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if(getDomainObject()!=null && getDomainObject().getParent()!=null)
                projectName = getDomainObject().getParent().getName();
            return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_CONFIG);
        }
    }

    private String safeGet(String s)
    {
    	if(s!=null)
    		return s;
    	return "";
    }

}
