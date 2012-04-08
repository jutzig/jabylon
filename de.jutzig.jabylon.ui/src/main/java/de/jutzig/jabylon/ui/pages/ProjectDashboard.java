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
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.forms.NewProjectForm;

public class ProjectDashboard extends Panel implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectVersion version;

	public ProjectDashboard(String projectName, String versionName) {
		super(projectName);
		GridLayout layout = new GridLayout(2, 1);
		setContent(layout);
		project = MainDashboard.getCurrent().getWorkspace()
				.getProject(projectName);
		version = getProjectVersion(project, versionName);
		createContents(layout);

	}

	private ProjectVersion getProjectVersion(Project project, String version) {
		if (version == null) {
			return project.getMaster();
		}
		// TODO: get actual version
		return null;
	}

	public ProjectDashboard(String projectName) {
		this(projectName, null);

	}

	private void createContents(GridLayout parent) {
		buildHeader(parent);
		Random random = new Random();
		EList<ProjectLocale> locales = version.getLocales();
		
		for (ProjectLocale locale : locales) {
			Button projectName = new Button(locale.getLocale().getDisplayName());
			projectName.setStyleName(Reindeer.BUTTON_LINK);
			addComponent(projectName);
			projectName.setData(locale);
			projectName.addListener(this);

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
		return project.getName();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectLocale locale = (ProjectLocale) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(locale.toString());
		
	}

}
