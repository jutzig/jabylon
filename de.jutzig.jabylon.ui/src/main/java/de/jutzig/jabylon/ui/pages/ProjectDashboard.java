package de.jutzig.jabylon.ui.pages;

import java.util.Locale;
import java.util.Random;

import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.CompletableProgressIndicator;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.forms.NewProjectForm;

public class ProjectDashboard extends Panel implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectVersion version;

	public ProjectDashboard(String projectName, String versionName) {
		super(projectName);
		GridLayout layout = new GridLayout(2, 1);
		setContent(layout);
		layout.setMargin(true);
		layout.setSpacing(true);
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
		EList<ProjectLocale> locales = version.getLocales();
		
		for (ProjectLocale locale : locales) {
			Button projectName = new Button(locale.getLocale().getDisplayName());
			projectName.setStyleName(Reindeer.BUTTON_LINK);
			addComponent(projectName);
			projectName.setData(locale);
			projectName.addListener(this);

			StaticProgressIndicator progress = new CompletableProgressIndicator(locale);
			addComponent(progress);
		}

		Button addProject = new Button();
		addProject.setCaption("Full Scan");
		addProject.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				try {
					TransactionUtil.commit(version, new Modification<ProjectVersion ,ProjectVersion>() {
						@Override
						public ProjectVersion apply(ProjectVersion object) {
							object.fullScan();
							return object;
						}
					});
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		addComponent(addProject);

	}

	private void buildHeader(GridLayout parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrumbTrail walkTo(String path) {
		Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, path);
		ProjectLocale projectLocale = version.getProjectLocale(locale);
		return new ProjectLocaleDashboard(projectLocale);
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
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(locale.getLocale().toString());
		
	}

}
