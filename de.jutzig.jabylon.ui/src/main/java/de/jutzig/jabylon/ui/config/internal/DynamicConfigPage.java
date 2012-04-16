package de.jutzig.jabylon.ui.config.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.ConfigSection;

public class DynamicConfigPage extends VerticalLayout implements CrumbTrail {

	private Map<String, ConfigSection> sections;
	private Preferences rootNode;

	public DynamicConfigPage(Object domainElement) {
		sections = new HashMap<String, ConfigSection>();
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		rootNode = initializePreferences();
		createContents(domainElement);
		initSections(domainElement);
	}

	private Preferences initializePreferences() {

		Preferences node = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		Collection<String> path = MainDashboard.getCurrent().getBreadcrumbs()
				.currentPath();
		for (String string : path) {
			node = node.node(string);

		}
		return node;
	}

	private void initSections(Object domainElement) {
		for (Entry<String, ConfigSection> entry : sections.entrySet()) {
			String id = entry.getKey();
			entry.getValue().init(domainElement, rootNode.node(id));
		}

	}

	private void createContents(Object domainElement) {

		List<IConfigurationElement> configSections = Activator.getDefault()
				.getConfigSections();
		for (IConfigurationElement child : configSections) {
			try {
				ConfigSection section = (ConfigSection) child
						.createExecutableExtension("section");
				if (section.appliesTo(domainElement)) {
					Section sectionWidget = new Section();
					sectionWidget.setTitle(child.getAttribute("title"));
					sectionWidget.getBody().addComponent(
							section.createContents());
					sections.put(child.getAttribute("id"), section);
					addComponent(sectionWidget);
				}
			} catch (CoreException e) {
				Activator.error(
						"Failed to initialze config extension "
								+ child.getAttribute("id"), e);
			}

		}
	}

	@Override
	public CrumbTrail walkTo(String path) {
		return null;
	}

	@Override
	public Component getComponent() {
		return this;
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
	public Object getDomainObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
