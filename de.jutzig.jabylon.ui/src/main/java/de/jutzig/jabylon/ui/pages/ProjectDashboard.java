package de.jutzig.jabylon.ui.pages;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.cdo.CDOObject;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.team.TeamProvider;

public class ProjectDashboard implements CrumbTrail,
		ClickListener {

	private Project project;
	private ProjectVersion version;
	private VerticalLayout mainLayout;

	public ProjectDashboard(String projectName, String versionName) {
		project = MainDashboard.getCurrent().getWorkspace()
				.getProject(projectName);
		version = getProjectVersion(project, versionName);
		

	}

	private void initialize() {
		mainLayout = new VerticalLayout();
		GridLayout layout = new GridLayout(2, 1);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		Section section = new Section();
		section.setTitle("Available Locales");
		section.getBody().addComponent(layout);
		createContents(layout);
		mainLayout.setSizeFull();
		mainLayout.addComponent(section);


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

		final Table table = new Table();

		table.setColumnWidth(ProjectLocaleTableContainer.LocaleProperty.PROGRESS, 110);
		table.setColumnExpandRatio(ProjectLocaleTableContainer.LocaleProperty.SUMMARY, 3f);
		table.setSizeFull();
		table.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		
		table.setContainerDataSource(new ProjectLocaleTableContainer(version));
		table.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE, LocaleProperty.SUMMARY, LocaleProperty.PROGRESS).toArray());
		table.setItemIconPropertyId(LocaleProperty.FLAG);
		parent.addComponent(table, 0, 0, 1, 0);

		Button commit = new Button();
		commit.setCaption("Commit Changes");
		commit.setIcon(ImageConstants.IMAGE_PROJECT_COMMIT);
		commit.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				TeamProvider teamProvider = MainDashboard.getCurrent()
						.getTeamProviderForURI(project.getRepositoryURI());
				if (teamProvider != null) {
					try {
						teamProvider.commit(version, new NullProgressMonitor());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
		parent.addComponent(commit);

	}



	private void buildHeader(Layout parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public CrumbTrail walkTo(String path) {
		Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(
				PropertiesPackage.Literals.LOCALE, path);
		ProjectLocale projectLocale = version.getProjectLocale(locale);
		return new ProjectLocaleDashboard(projectLocale);
	}

	@Override
	public String getTrailCaption() {
		return project.getName();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectLocale locale = (ProjectLocale) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs()
				.walkTo(locale.getLocale().toString());

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CDOObject getDomainObject() {
		return project;
	}

	@Override
	public Component createContents() {
		initialize();
		return mainLayout;
		
	}

}
