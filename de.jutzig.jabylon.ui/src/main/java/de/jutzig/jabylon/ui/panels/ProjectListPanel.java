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
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;

public class ProjectListPanel extends GridLayout implements ClickListener {

	public ProjectListPanel() {
		
		createContents();
		setMargin(true, true, true, true);
		setSpacing(true);
		
	}

	private void createContents() {
		Workspace workspace = MainDashboard.getCurrent().getWorkspace();
		EList<Project> projects = workspace.getProjects();
		setColumns(2);
		setRows(projects.size());
		buildHeader();
		Random random = new Random();
		for (Project project : projects) {
			Button projectName = new Button(project.getName());
			projectName.setStyleName(Reindeer.BUTTON_LINK);
//			label.setWidth(300, UNITS_PIXELS);
//			projectName.setWidth(200, UNITS_PIXELS);
			addComponent(projectName);
			projectName.setData(project);
			projectName.addListener(this);
			
			StaticProgressIndicator progress = new StaticProgressIndicator();
			progress.setPercentage(random.nextInt(100));
			addComponent(progress);
		}
		
	}

	private void buildHeader() {
//		Label label = new Label();
//		label.setCaption("")
//		addComponent(c, column, row)
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Project project = (Project) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(project.getName());
		
	}
	

}
