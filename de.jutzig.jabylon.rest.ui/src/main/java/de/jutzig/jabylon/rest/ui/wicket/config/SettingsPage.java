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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
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
import de.jutzig.jabylon.rest.ui.model.AttachableWritableModel;
import de.jutzig.jabylon.rest.ui.model.IEObjectModel;
import de.jutzig.jabylon.rest.ui.model.WritableEObjectModel;
import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.wicket.JabylonApplication;
import de.jutzig.jabylon.rest.ui.wicket.components.BootstrapTabbedPanel;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericPage;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericResolvablePage;
import de.jutzig.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import de.jutzig.jabylon.users.User;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@AuthorizeInstantiation("ACCESS_CONFIG")
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
		List<ITab> extensions = loadTabExtensions();
		
		BootstrapTabbedPanel<ITab> tabContainer = new BootstrapTabbedPanel<ITab>("tabs", extensions);
		add(tabContainer);
		tabContainer.setOutputMarkupId(true);
		BreadcrumbPanel breadcrumbPanel = new BreadcrumbPanel("breadcrumb-panel", getModel(),getPageParameters());
		breadcrumbPanel.setRootLabel("Settings");
		breadcrumbPanel.setRootURL("/"+JabylonApplication.CONTEXT+"/settings");
		add(breadcrumbPanel);
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

	private List<ITab> loadTabExtensions() {
	
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
		boolean isNew = modelObject.cdoState()==CDOState.NEW || modelObject.cdoState()==CDOState.TRANSIENT;
		Preferences preferences = isNew ? new AttachablePreferences() : new DelegatingPreferences(PreferencesUtil.scopeFor(modelObject));
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
