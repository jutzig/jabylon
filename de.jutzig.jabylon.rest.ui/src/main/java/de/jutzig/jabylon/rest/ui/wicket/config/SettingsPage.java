/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import de.jutzig.jabylon.common.util.AttachablePreferences;
import de.jutzig.jabylon.common.util.DelegatingPreferences;
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.common.util.config.DynamicConfigUtil;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.model.AttachableModel;
import de.jutzig.jabylon.rest.ui.model.AttachableWritableModel;
import de.jutzig.jabylon.rest.ui.model.IEObjectModel;
import de.jutzig.jabylon.rest.ui.model.WritableEObjectModel;
import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.util.WicketUtil;
import de.jutzig.jabylon.rest.ui.wicket.components.ClientSideTabbedPanel;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericResolvablePage;
import de.jutzig.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import de.jutzig.jabylon.security.CommonPermissions;
import de.jutzig.jabylon.users.User;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@AuthorizeInstantiation(CommonPermissions.SYSTEM_GLOBAL_CONFIG)
public class SettingsPage extends GenericResolvablePage<Resolvable<?, ?>> {

	private static final long serialVersionUID = 1L;
	
	public static final String QUERY_PARAM_CREATE = "create";
	
	private static final Logger logger = LoggerFactory.getLogger(SettingsPage.class);

	public SettingsPage(PageParameters parameters) {
		super(parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void construct() {
		super.construct();
		StringValue value = getPageParameters().get(QUERY_PARAM_CREATE);
		if(value!=null && !value.isEmpty())
		{
			EClassifier eClassifier = PropertiesPackage.eINSTANCE.getEClassifier(value.toString());
			if (eClassifier instanceof EClass) {
				EClass eclass = (EClass) eClassifier;
				setModel(new AttachableWritableModel(eclass, getModel()));
			}
		}
		
		Resolvable<?, ?> modelObject = getModelObject();
		boolean isNew = modelObject.cdoState()==CDOState.NEW || modelObject.cdoState()==CDOState.TRANSIENT;
		final Preferences preferences = isNew ? new AttachablePreferences() : new DelegatingPreferences(PreferencesUtil.scopeFor(modelObject));

		List<ITab> extensions = loadTabExtensions(preferences);
		

		BreadcrumbPanel breadcrumbPanel = new BreadcrumbPanel("breadcrumb-panel", getModel(),getPageParameters());
		breadcrumbPanel.setRootLabel("Settings");
		
		breadcrumbPanel.setRootURL(WicketUtil.getContextPath()+"/settings");
		add(breadcrumbPanel);
		
		// submit section
		
		Form form = new StatelessForm("form", getModel()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				IEObjectModel<Resolvable<?, ?>> model = SettingsPage.this.getModel();
				CDOObject object = model.getObject();
				CDOView cdoView;
				if (model instanceof AttachableModel) {
					//it's a new object that needs attaching
					AttachableModel<Resolvable<?, ?>> attachable = (AttachableModel) model;
					attachable.attach();
					CDOObject parent = (CDOObject) attachable.getObject().eContainer();
					cdoView = parent.cdoView();
				}
				else
					cdoView = object.cdoView();
				if (cdoView instanceof CDOTransaction) {
					CDOTransaction transaction = (CDOTransaction) cdoView;
					commit(preferences, object, transaction);
					model.detach();
				}
				else
					throw new IllegalStateException("not a transaction");
				

				super.onSubmit();
			}

			protected void commit(final Preferences preferences, CDOObject object, CDOTransaction transaction) {
				try {
					transaction.commit();
					if (object instanceof Resolvable) {
						@SuppressWarnings("rawtypes")
						Resolvable r = (Resolvable) object;
						if(!r.getName().equals(preferences.name()))
						{
							//FIXME: must rename preferences properly
//								preferences = PreferencesUtil.renamePreferenceNode(preferences,r.getName());
						}
						setResponsePage(SettingsPage.class, WicketUtil.buildPageParametersFor(r));
					}
					preferences.flush();
					getSession().success("Saved successfully");
				} catch (CommitException e) {
					getSession().error(e.getMessage());
					logger.error("failed to commit configuration for "+object,e);
				} catch (BackingStoreException e) {
					getSession().error(e.getMessage());
					logger.error("failed to commit configuration for "+object,e);
				}
				finally{
					transaction.close();
				}
			}


		};
		
		ClientSideTabbedPanel<ITab> tabContainer = new ClientSideTabbedPanel<ITab>("tabs", extensions);
		form.add(tabContainer);

	
//		Button submitButton = new Button("submit-button", Model.of("Submit"));
//		form.add(submitButton);
//		Button cancelButton = new Button("cancel-button", Model.of("Cancel"));
//		form.add(cancelButton);
		
		add(form);
	}

	@Override
	protected IEObjectModel<Resolvable<?, ?>> createModel(Resolvable<?, ?> object) {
		return new WritableEObjectModel<Resolvable<?, ?>>(object);
	}
	

	private User getUser() {
		User user = null;
		if (getSession() instanceof CDOAuthenticatedSession) {
			CDOAuthenticatedSession session = (CDOAuthenticatedSession) getSession();
			user = session.getUser();
		}
		return user;
	}

	private List<ITab> loadTabExtensions(Preferences preferences) {
	
		List<IConfigurationElement> configurationElements = DynamicConfigUtil.getConfigTabs();
		ListMultimap<String, ConfigSection<?>> sections = ArrayListMultimap.create(configurationElements.size(), 5);		
		
		List<IConfigurationElement> elements = DynamicConfigUtil.getApplicableElements(getModelObject(), getUser());
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("tab");
			ConfigSection<?> extension;
			try {
				extension = (ConfigSection<?>) element.createExecutableExtension("section");
				sections.put(id, extension);
			} catch (CoreException e) {
				logger.error("Failed to create executable extension: "+element, e);
			}
		}
		List<ITab> extensions = new ArrayList<ITab>();
		Resolvable<?, ?> modelObject = getModelObject();
		for (IConfigurationElement element : configurationElements) {
			String name = element.getAttribute("name");
			String id = element.getAttribute("tabID");
			ConfigTab tab = new ConfigTab(name, sections.removeAll(id),getModel(), preferences);
			if(tab.isVisible())
				extensions.add(tab);
		}
		if(!sections.isEmpty())
		{
			logger.warn("Unmapped config sections left {}",sections);
		}
		return extensions;
	}

	private Map<String, IConfigurationElement> computeVisibleTabs(List<IConfigurationElement> configSections) {
		// linked hashmap to retain the precendence order
		Map<String, IConfigurationElement> tabs = new LinkedHashMap<String, IConfigurationElement>();
		List<IConfigurationElement> tabList = DynamicConfigUtil.getConfigTabs();
		for (IConfigurationElement tab : tabList) {
			tabs.put(tab.getAttribute("tabID"), tab);
		}
		Set<String> neededTabs = new HashSet<String>();
		for (IConfigurationElement element : configSections) {
			neededTabs.add(element.getAttribute("tab"));
		}
		tabs.keySet().retainAll(neededTabs);

		return tabs;

	}
	
}
