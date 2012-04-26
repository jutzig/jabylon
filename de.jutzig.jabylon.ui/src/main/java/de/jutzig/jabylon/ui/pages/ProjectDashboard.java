package de.jutzig.jabylon.ui.pages;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.cdo.CDOObject;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
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

public class ProjectDashboard implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectVersion version;
	private VerticalLayout mainLayout;
	private Table table;

	public ProjectDashboard(String projectName, String versionName) {
		project = MainDashboard.getCurrent().getWorkspace().getProject(projectName);
		version = getProjectVersion(project, versionName);

	}

	private void initialize() {
		mainLayout = new VerticalLayout();

		mainLayout.addComponent(createVersionSelection());

		Section section = new Section();
		section.setTitle("Available Locales");
		GridLayout layout = new GridLayout(2, 1);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		section.getBody().addComponent(layout);
		createContents(layout);
		mainLayout.setSizeFull();
		mainLayout.addComponent(section);

	}

	private Component createVersionSelection() {
		ComboBox versionSelector = new ComboBox("Version");
		versionSelector.setTextInputAllowed(false);
		versionSelector.setNewItemsAllowed(false);
		versionSelector.setImmediate(true);
		versionSelector.setItemCaptionPropertyId("branch");
		BeanItemContainer<ProjectVersion> container = new BeanItemContainer<ProjectVersion>(ProjectVersion.class);
		container.addItem(project.getMaster());
		for (ProjectVersion version : project.getVersions()) {
			container.addItem(version);
		}
		versionSelector.setContainerDataSource(container);
		versionSelector.select(version);
		versionSelector.setNullSelectionAllowed(false);
		versionSelector.setVisible(container.size() > 1);

		versionSelector.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (value instanceof ProjectVersion) {
					version = (ProjectVersion) value;
					//TODO: this isn't so great really
					if("master".equals(version.getBranch()))
						MainDashboard.getCurrent().getBreadcrumbs().goBack();
					else	
						MainDashboard.getCurrent().getBreadcrumbs().walkTo("?" + version.getBranch());
				}

			}
		});
		return versionSelector;
	}

	private ProjectVersion getProjectVersion(Project project, String version) {
		if (version == null) {
			return project.getMaster();
		}
		for (ProjectVersion projectVersion : project.getVersions()) {
			if (version.equals(projectVersion.getBranch()))
				return projectVersion;

		}
		return null;
	}

	public ProjectDashboard(String projectName) {
		this(projectName, null);

	}

	private void createContents(GridLayout parent) {
		buildHeader(parent);

		table = new Table();

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

				TeamProvider teamProvider = MainDashboard.getCurrent().getTeamProviderForURI(project.getRepositoryURI());
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
		Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, path);
		if (path.startsWith("?")) {
			version = getProjectVersion(project, path.substring(1));
			if(mainLayout!=null)
				mainLayout.removeAllComponents();
			return this;
		}
		//TODO: this is a hack
		
		ProjectLocale projectLocale = version.getProjectLocale(locale);
		return new ProjectLocaleDashboard(projectLocale);
	}

	@Override
	public String getTrailCaption() {
		if("master".equals(version.getBranch()))
			return project.getName();
		return version.getBranch();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		ProjectLocale locale = (ProjectLocale) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(locale.getLocale().toString());

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
