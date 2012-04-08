package de.jutzig.jabylon.ui.pages;

import java.util.Locale;
import java.util.Random;

import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.forms.NewProjectForm;

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
		Random random = new Random();
		
		for (PropertyFileDescriptor descriptor : locale.getDescriptors()) {
			Button fileName = new Button(descriptor.getLocation().toString());
			fileName.setStyleName(Reindeer.BUTTON_LINK);
			addComponent(fileName);
			fileName.setData(descriptor);
			fileName.addListener(this);

			StaticProgressIndicator progress = new StaticProgressIndicator();
			progress.setPercentage(random.nextInt(100));
			addComponent(progress);
		}


	}

	private void buildHeader(GridLayout parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrumbTrail walkTo(String path) {
		// TODO Auto-generated method stub
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
//		ProjectLocale locale = (ProjectLocale) event.getButton().getData();
//		MainDashboard.getCurrent().getBreadcrumbs().walkTo(locale.toString());
		
	}

}
