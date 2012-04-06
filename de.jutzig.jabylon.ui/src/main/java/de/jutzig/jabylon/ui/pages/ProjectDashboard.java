package de.jutzig.jabylon.ui.pages;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.container.EObjectProperty;

public class ProjectDashboard extends Panel {

	private Project project;
	
	public ProjectDashboard(Project project) {
		super(project.getName());
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		Label label = new Label(new EObjectProperty(project, PropertiesPackage.Literals.PROJECT__BASE));
		layout.addComponent(label);
	}
	
	
	
}
