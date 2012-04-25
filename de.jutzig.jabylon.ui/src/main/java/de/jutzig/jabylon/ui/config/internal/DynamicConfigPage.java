package de.jutzig.jabylon.ui.config.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.resources.ApplicationConstants;
import de.jutzig.jabylon.ui.util.PreferencesUtil;

public class DynamicConfigPage implements CrumbTrail {

	private Map<String, ConfigSection> sections;
	private Preferences rootNode;
	private CDOTransaction transaction;
	private CDOObject domainElement;
	private VerticalLayout layout;

	public DynamicConfigPage(CDOObject domainElement) {

		this.domainElement = domainElement;
		sections = new HashMap<String, ConfigSection>();
		rootNode = initializePreferences(domainElement);

	}

	private Preferences initializePreferences(CDOObject domainElement2) {

		return PreferencesUtil.scopeFor(domainElement2);
	}

	private void initSections(Object domainElement) {
		for (Entry<String, ConfigSection> entry : sections.entrySet()) {
			String id = entry.getKey();
			entry.getValue().init(domainElement, rootNode.node(id));
		}

	}

	private void createContents(Object domainElement) {

		layout = new VerticalLayout() {
			@Override
			public void detach() {
				super.detach();
				transaction.close();
			}
		};
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		List<IConfigurationElement> configSections = DynamicConfigUtil.getApplicableElements(domainElement);
		Map<String, IConfigurationElement> visibleTabs = computeVisibleTabs(configSections);

		TabSheet sheet = new TabSheet();
		Map<String, VerticalLayout> tabs = fillTabSheet(visibleTabs, sheet);
		layout.addComponent(sheet);
		for (IConfigurationElement child : configSections) {
			try {

				ConfigSection section = (ConfigSection) child.createExecutableExtension("section");
				String title = child.getAttribute("title");
				VerticalLayout parent = tabs.get(child.getAttribute("tab"));
				parent.setSpacing(true);
				parent.setMargin(true);
				if (title != null && title.length() > 0) {
					Section sectionWidget = new Section();
					sectionWidget.setTitle(title);
					sectionWidget.getBody().addComponent(section.createContents());
					parent.addComponent(sectionWidget);
				} else {
					parent.addComponent(section.createContents());
				}
				sections.put(child.getAttribute("id"), section);

			} catch (CoreException e) {
				Activator.error("Failed to initialze config extension " + child.getAttribute("id"), e);
			}

		}

		Button safe = new Button();
		safe.setCaption("OK");
		safe.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				for (Entry<String, ConfigSection> entry : sections.entrySet()) {
					String id = entry.getKey();
					entry.getValue().commit(rootNode.node(id));
				}
				try {
					rootNode.flush();
					transaction.commit();
					MainDashboard.getCurrent().getBreadcrumbs().goBack();
//					layout.getWindow().showNotification("Saved");
				} catch (BackingStoreException e) {
					Activator.error("Failed to persist settings of " + MainDashboard.getCurrent().getBreadcrumbs().currentPath(), e);
					layout.getWindow().showNotification("Failed to persist changes", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);

				} catch (CommitException e) {
					Activator.error("Commit failed", e);
				}

			}
		});
		layout.addComponent(safe);
	}

	private Map<String, VerticalLayout> fillTabSheet(final Map<String, IConfigurationElement> visibleTabs, TabSheet sheet) {
		Map<String, VerticalLayout> result = new HashMap<String, VerticalLayout>();

		for (Entry<String, IConfigurationElement> entry : visibleTabs.entrySet()) {
			IConfigurationElement element = entry.getValue();
			VerticalLayout layout = new VerticalLayout();
			sheet.addTab(layout, element.getAttribute("name"));
			result.put(entry.getKey(), layout);
		}
		return result;
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

	@Override
	public CrumbTrail walkTo(String path) {
		return null;
	}

	@Override
	public String getTrailCaption() {
		return "Settings";
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CDOObject getDomainObject() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Component createContents() {
		transaction = (CDOTransaction) domainElement.cdoView().getSession().openTransaction();
		CDOObject writable = transaction.getObject(domainElement);
		createContents(writable);
		initSections(writable);
		return layout;

	}

}
