package de.jutzig.jabylon.ui.pages;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.EObjectProperty;

public class ProjectDashboard extends Panel implements CrumbTrail{

	private Project project;
	
	public ProjectDashboard(String projectName) {
		super(projectName);
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		project = MainDashboard.getCurrent().getWorkspace().getProject(projectName);
		Label label = new Label(new EObjectProperty(project, PropertiesPackage.Literals.PROJECT__BASE));
		layout.addComponent(label);
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
	
	
	
}
