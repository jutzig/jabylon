/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.wicket.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.util.AttachablePreferences;
import org.jabylon.common.util.DelegatingPreferences;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.common.util.config.DynamicConfigUtil;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.AttachableWritableModel;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.LoginPage;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.components.ClientSideTabbedPanel;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;

/**
 * @author jutzig.dev@googlemail.com
 * 
 */
public class SettingsPanel<T extends CDOObject> extends GenericPanel<T> {

	public static final String QUERY_PARAM_NAMESPACE = "namespace";

	private static final long serialVersionUID = 1L;

	public static final String QUERY_PARAM_CREATE = "create";

	private static final Logger logger = LoggerFactory.getLogger(SettingsPage.class);

	private List<ConfigSection<?>> allSections;

	@Inject
	private transient URIResolver resolver;

	@SuppressWarnings("unchecked")
	public SettingsPanel(String id, IModel<T> model, PageParameters pageParameters) {
		super(id, model);
		EClass eclass = getEClassToCreate(pageParameters);
		if (eclass != null) {
			setModel(new AttachableWritableModel<T>(eclass, getModel()));
		}

		T modelObject = getModelObject();
		boolean isNew = modelObject.cdoState() == CDOState.NEW || modelObject.cdoState() == CDOState.TRANSIENT;
		final Preferences preferences = isNew ? new AttachablePreferences() : new DelegatingPreferences(PreferencesUtil.scopeFor(modelObject));

		final List<ITab> extensions = loadTabExtensions(preferences);

		// submit section

		@SuppressWarnings({ "rawtypes" })
		Form form = new Form("form", getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				IModel<T> model = SettingsPanel.this.getModel();
				CDOObject object = model.getObject();
				CDOView cdoView;
				if (model instanceof AttachableModel) {
					// it's a new object that needs attaching
					AttachableModel<CDOObject> attachable = (AttachableModel) model;
					attachable.attach();
					CDOObject parent = (CDOObject) attachable.getObject().eContainer();
					cdoView = parent.cdoView();
				} else
					cdoView = object.cdoView();
				if (cdoView instanceof CDOTransaction) {
					CDOTransaction transaction = (CDOTransaction) cdoView;

					Preferences prefs = preferences;
					if (preferences instanceof AttachablePreferences) {
						// the prefs are not in the tree yet
						Preferences targetPrefs = PreferencesUtil.scopeFor(object);
						try {
							PreferencesUtil.cloneNode(preferences, targetPrefs);
							prefs = targetPrefs;
						} catch (BackingStoreException e) {
							error("Some settings could not be saved: " + e.getMessage());
							logger.error("Failed to attach preferences to target path", e);
						}
					}

					commit(prefs, object, transaction);
					// model.detach();
				} else
					throw new IllegalStateException("not a transaction");
				super.onSubmit();
			}

			protected void commit(final Preferences preferences, CDOObject object, CDOTransaction transaction) {

				for (ConfigSection<?> section : allSections) {
					section.commit(getModel(), preferences);
				}
				try {
					transaction.commit();

					URI uri = resolver.getURI(object);
					setResponsePage(SettingsPage.class, WicketUtil.buildPageParametersFor(uri));

					preferences.flush();
					getSession().success(getString("save.success.feedback.message"));
				} catch (CommitException e) {
					getSession().error(e.getMessage());
					logger.error("failed to commit configuration for " + object, e);
				} catch (BackingStoreException e) {
					getSession().error(e.getMessage());
					logger.error("failed to commit configuration for " + object, e);
				} finally {
					// transaction.close();
				}
			}
		};

		ClientSideTabbedPanel<ITab> tabContainer = new ClientSideTabbedPanel<ITab>("tabs", extensions, false, "settings/"
				+ model.getObject().getClass().getSimpleName()) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				boolean visible = super.isVisible();
				List<ITab> tabContents = extensions;
				for (ITab component : tabContents) {
					if (component.isVisible())
						return visible;
				}
				CDOAuthenticatedSession session = (CDOAuthenticatedSession) CDOAuthenticatedSession.get();
				User user = session.getUser();
				if (user == null || CommonPermissions.USER_ANONYMOUS.equals(user.getName()))
					// user is not logged in, give him the chance
					throw new RestartResponseAtInterceptPageException(LoginPage.class);
				// if no tab is visible, the user has no permission to be here
				throw new UnauthorizedInstantiationException(SettingsPanel.class);
			}

		};
		form.add(tabContainer);
		// form.add(new CustomFeedbackPanel("feedback"));

		Button submitButton = new Button("submit", new StringResourceModel("submit.button.label", this, null));
		form.add(submitButton);
		// Button cancelButton = new Button("cancel-button",
		// Model.of("Cancel"));
		// form.add(cancelButton);

		add(form);

	}

	private EClass getEClassToCreate(PageParameters pageParameters) {
		StringValue value = pageParameters.get(QUERY_PARAM_CREATE);
		if (value != null && !value.isEmpty()) {
			String namespace = pageParameters.get(QUERY_PARAM_NAMESPACE).toString(PropertiesPackage.eNS_URI);
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(namespace);
			if (ePackage != null) {
				EClassifier eClassifier = ePackage.getEClassifier(value.toString());
				if (eClassifier instanceof EClass) {
					EClass eclass = (EClass) eClassifier;
					return eclass;
				}
			}
		}
		return null;

	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private List<ITab> loadTabExtensions(Preferences preferences) {

		List<IConfigurationElement> configurationElements = DynamicConfigUtil.getConfigTabs();
		ArrayListMultimap<String, ConfigSection<?>> sections = ArrayListMultimap.create(configurationElements.size(), 5);
		allSections = new ArrayList<ConfigSection<?>>();
		List<IConfigurationElement> elements = DynamicConfigUtil.getApplicableElements(getModelObject());
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("tab");
			ConfigSection<?> extension;
			try {
				extension = (ConfigSection<?>) element.createExecutableExtension("section");
				sections.put(id, extension);
			} catch (CoreException e) {
				logger.error("Failed to create executable extension: " + element, e);
			}
		}
		List<ITab> extensions = new ArrayList<ITab>();
		T modelObject = getModelObject();
		for (IConfigurationElement element : configurationElements) {
			String name = element.getAttribute("name");
			String id = element.getAttribute("tabID");
			List<ConfigSection<?>> tabSections = sections.removeAll(id);
			ConfigTab tab = new ConfigTab(name, tabSections, getModel(), preferences);
			allSections.addAll(tabSections);
			if (tab.isVisible())
				extensions.add(tab);
		}
		if (!sections.isEmpty()) {
			logger.warn("Unmapped config sections left {}", sections);
		}
		return extensions;
	}

}
