package de.jutzig.jabylon.ui.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.PropertiesEditor;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;

public class ProjectLocaleDashboard extends Panel implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectLocale locale;

	public ProjectLocaleDashboard(ProjectLocale locale) {
		this.locale = locale;
		GridLayout layout = new GridLayout(2, 1);
		setContent(layout);
		layout.setMargin(true);
		layout.setSpacing(true);
		createContents(layout);

	}


	private void createContents(GridLayout parent) {
		buildHeader(parent);
		Map<PropertyFileDescriptor, PropertyFileDescriptor> masterToTransation = associate(locale);
		
//		ProjectLocale master = locale.getProjectVersion().getMaster();
		
		for (Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry : masterToTransation.entrySet()) {
			Button fileName = new Button(entry.getKey().getLocation().toString());
			fileName.setStyleName(Reindeer.BUTTON_LINK);
			addComponent(fileName);
			fileName.setData(entry);
			fileName.addListener(this);

			StaticProgressIndicator progress = new StaticProgressIndicator();
			PropertyFileDescriptor translation = entry.getValue();
			if(translation!=null)
				progress.setPercentage(translation.percentComplete());
			else
				progress.setPercentage(0);
			addComponent(progress);
		}

	}

	private Map<PropertyFileDescriptor, PropertyFileDescriptor> associate(
			ProjectLocale locale) {
		ProjectLocale master = locale.getProjectVersion().getMaster();
		Map<PropertyFileDescriptor, PropertyFileDescriptor> result = new HashMap<PropertyFileDescriptor, PropertyFileDescriptor>();
		for (PropertyFileDescriptor descriptor : master.getDescriptors()) {
			result.put(descriptor, null);
		}
		
		for (PropertyFileDescriptor descriptor : locale.getDescriptors()) {
			result.put(descriptor.getMaster(), descriptor);
		}
		return result;
	}


	private void buildHeader(GridLayout parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrumbTrail walkTo(String path) {
		return new PropertiesEditor(getDescriptor(path));
	}

	private PropertyFileDescriptor getDescriptor(String path) {
		EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
		for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
			if(path.equals(propertyFileDescriptor.getLocation().toString()))
				return propertyFileDescriptor;
		}
		return null;
	}


	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTrailCaption() {
		return locale.isMaster() ? "Master" : locale.getLocale().getDisplayName();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry = (Entry<PropertyFileDescriptor, PropertyFileDescriptor>) event.getButton().getData();
		PropertyFileDescriptor target = entry.getValue();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(target.getLocation().toString());
		
	}

}
