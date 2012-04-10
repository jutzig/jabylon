package de.jutzig.jabylon.ui.pages;

import java.util.Locale;

import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
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
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.forms.NewLocaleForm;

public class ProjectDashboard extends Panel implements CrumbTrail,
		ClickListener {

	private static final String CREATE_LOCALE = "create locale";
	private Project project;
	private ProjectVersion version;

	public ProjectDashboard(String projectName, String versionName) {
		super(projectName);
		project = MainDashboard.getCurrent().getWorkspace()
				.getProject(projectName);
		version = getProjectVersion(project, versionName);
		initialize();

	}

	private void initialize() {
		GridLayout layout = new GridLayout(2, 1);
		setContent(layout);
		layout.setMargin(true);
		layout.setSpacing(true);

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

		final Table table = new Table();
		table.addContainerProperty("location", Button.class, null);
		table.addContainerProperty("progress", ResolvableProgressIndicator.class, null);
		table.setColumnWidth("progress", 110);

		 EList<ProjectLocale> locales = version.getLocales();
		
		 for (ProjectLocale locale : locales) {
				 Button projectName = new Button(locale.getLocale().getDisplayName());
				 projectName.setStyleName(Reindeer.BUTTON_LINK);
				 projectName.setData(locale);
				 projectName.addListener(this);
				 
				 StaticProgressIndicator progress = new ResolvableProgressIndicator(locale);
				 table.addItem(new Object[]{projectName,progress}, locale);
		 }
		 
		 parent.addComponent(table, 0,0,1,0);
		

		Button scanProject = new Button();
		scanProject.setCaption("Full Scan");
		scanProject.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				try {
					TransactionUtil.commit(version,
							new Modification<ProjectVersion, ProjectVersion>() {
								@Override
								public ProjectVersion apply(
										ProjectVersion object) {
									object.fullScan();
									return object;
								}
							});
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getWindow().showNotification("Scan complete");
				removeAllComponents();
				initialize();

			}

		});
		parent.addComponent(scanProject);
		
		
		Button addLocale = new Button();
		addLocale.setCaption("Add Locale");
		addLocale.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				MainDashboard.getCurrent().getBreadcrumbs().walkTo(CREATE_LOCALE);

			}

		});
		parent.addComponent(addLocale);

	}

	private void buildHeader(Layout parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public CrumbTrail walkTo(String path) {
		if(CREATE_LOCALE.equals(path))
		{
			return new NewLocaleForm(project);
		}
		Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(
				PropertiesPackage.Literals.LOCALE, path);
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
		MainDashboard.getCurrent().getBreadcrumbs()
				.walkTo(locale.getLocale().toString());

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

}
