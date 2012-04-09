package de.jutzig.jabylon.ui.panels;

import java.util.Random;

import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.CompletableProgressIndicator;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.forms.NewProjectForm;

public class ProjectListPanel extends GridLayout implements ClickListener {

	public ProjectListPanel() {
		
		createContents();
		setMargin(true, true, true, true);
		setSpacing(true);
//		setSizeFull();
		
	}

	private void createContents() {
		Workspace workspace = MainDashboard.getCurrent().getWorkspace();
		EList<Project> projects = workspace.getProjects();
		setColumns(2);
		setRows(projects.size()+1);
		buildHeader();
		for (Project project : projects) {
			Button projectName = new Button(project.getName());
			projectName.setStyleName(Reindeer.BUTTON_LINK);
			addComponent(projectName);
			projectName.setData(project);
			projectName.addListener(this);
			
			StaticProgressIndicator progress = new CompletableProgressIndicator(project);
			addComponent(progress);
		}
		
		Button addProject = new Button();
		addProject.setCaption("Create Project");
		addProject.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				MainDashboard dashboard = MainDashboard.getCurrent();
				dashboard.setMainComponent(new NewProjectForm(dashboard));
			}

		});
		addComponent(addProject);
		
	}

	private void buildHeader() {

		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Project project = (Project) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(project.getName());
		
	}
	

}
