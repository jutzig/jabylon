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

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.RegistryFactory;

import de.jutzig.jabylon.common.util.config.DynamicConfigUtil;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.GenericPage;
import de.jutzig.jabylon.rest.ui.wicket.components.BootstrapTabbedPanel;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class SettingsPage<T extends Resolvable<?, ?>> extends GenericPage<T> {

	private static final long serialVersionUID = 1L;

	public SettingsPage(PageParameters parameters) {
		super(parameters);
		
		List<ITab> extensions = loadTabExtensions();
		
		BootstrapTabbedPanel<ITab> tabContainer = new BootstrapTabbedPanel<ITab>("tabs", extensions);
		add(tabContainer);
		tabContainer.setOutputMarkupId(true);
	}

	private List<ITab> loadTabExtensions() {
		List<ITab> extensions = new ArrayList<ITab>();
		IConfigurationElement[] configurationElements = RegistryFactory.getRegistry().getConfigurationElementsFor(
				"de.jutzig.jabylon.rest.ui.configTab");

		for (IConfigurationElement element : configurationElements) {

			String name = element.getAttribute("name");
			extensions.add(new ConfigTab(name, new ArrayList<ConfigSection<?>>()));

		}

		return extensions;
	}

	//
	// private Map<String, ConfigSection<T>> sections;
	// private DelegatingPreferences rootNode;
	// private CDOTransaction transaction;
	// private CDOObject domainElement;
	//
	//
	//
	//
	// private DelegatingPreferences initializePreferences(CDOObject
	// domainElement2) {
	//
	// return new
	// DelegatingPreferences(PreferencesUtil.scopeFor(domainElement2));
	// }
	//
	// private void initSections(IModel<T> model) {
	// for (Entry<String, ConfigSection<T>> entry : sections.entrySet()) {
	// String id = entry.getKey();
	// entry.getValue().init(model, rootNode);
	// }
	//
	// }
	//
	// private void createContents(Object domainElement) {
	//
	// layout = new VerticalLayout() {
	// @Override
	// public void detach() {
	// super.detach();
	// transaction.close();
	// }
	// };
	// layout.setMargin(true);
	// layout.setSpacing(true);
	// // layout.setSizeFull();
	// List<IConfigurationElement> configSections =
	// DynamicConfigUtil.getApplicableElements(domainElement);
	// Map<String, IConfigurationElement> visibleTabs =
	// computeVisibleTabs(configSections);
	//
	// TabSheet sheet = new TabSheet();
	// Map<String, VerticalLayout> tabs = fillTabSheet(visibleTabs, sheet);
	// layout.addComponent(sheet);
	// layout.setExpandRatio(sheet, 0);
	// for(int i=configSections.size()-1;i>=0;i--)
	// {
	// //go in reverse order, because they are computed in reverse order
	// IConfigurationElement child = configSections.get(i);
	// try {
	//
	// ConfigSection section = (ConfigSection)
	// child.createExecutableExtension("section");
	// String title = child.getAttribute("title");
	// VerticalLayout parent = tabs.get(child.getAttribute("tab"));
	// parent.setSpacing(true);
	// parent.setMargin(true);
	// if (title != null && title.length() > 0) {
	// Section sectionWidget = new Section();
	// sectionWidget.setCaption(title);
	// sectionWidget.addComponent(section.createContents());
	// parent.addComponent(sectionWidget);
	// } else {
	// parent.addComponent(section.createContents());
	// }
	// sections.put(child.getAttribute("id"), section);
	//
	// } catch (CoreException e) {
	// Activator.error("Failed to initialze config extension " +
	// child.getAttribute("id"), e);
	// }
	//
	// }
	//
	// Button safe = new Button();
	// safe.setCaption("OK");
	// safe.addListener(new ClickListener() {
	//
	// @Override
	// public void buttonClick(ClickEvent event) {
	// for (Entry<String, ConfigSection> entry : sections.entrySet()) {
	// entry.getValue().apply(rootNode);
	// }
	// try {
	// //flush once, so clients using the preferences during 'commit' see the
	// changes
	// rootNode.flush();
	// for (Entry<String, ConfigSection> entry : sections.entrySet()) {
	// entry.getValue().commit(rootNode);
	// }
	// //flush twice if commit changed something
	// rootNode.flush();
	// transaction.commit();
	// MainDashboard.getCurrent().getBreadcrumbs().goBack();
	// // layout.getWindow().showNotification("Saved");
	// } catch (BackingStoreException e) {
	// Activator.error("Failed to persist settings of " +
	// MainDashboard.getCurrent().getBreadcrumbs().currentPath(), e);
	// layout.getWindow().showNotification("Failed to persist changes",
	// e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
	//
	// } catch (CommitException e) {
	// Activator.error("Commit failed", e);
	// }
	//
	// }
	// });
	// layout.addComponent(safe);
	// Label spacer = new Label();
	// layout.addComponent(spacer);
	// layout.setExpandRatio(spacer, 1);
	// }
	//
	// private Map<String, VerticalLayout> fillTabSheet(final Map<String,
	// IConfigurationElement> visibleTabs, TabSheet sheet) {
	// Map<String, VerticalLayout> result = new HashMap<String,
	// VerticalLayout>();
	//
	// for (Entry<String, IConfigurationElement> entry : visibleTabs.entrySet())
	// {
	// IConfigurationElement element = entry.getValue();
	// VerticalLayout layout = new VerticalLayout();
	// sheet.addTab(layout, element.getAttribute("name"));
	// result.put(entry.getKey(), layout);
	// }
	// return result;
	// }
	//
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
	//
	// @Override
	// public boolean isDirty() {
	// return transaction.isDirty() || rootNode.isDirty();
	// }
	//
	// @Override
	// public Component createContents() {
	// transaction = (CDOTransaction)
	// domainElement.cdoView().getSession().openTransaction();
	// CDOObject writable = transaction.getObject(domainElement);
	// createContents(writable);
	// initSections(writable);
	// return layout;
	//
	// }

}
