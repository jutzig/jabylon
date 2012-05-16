package de.jutzig.jabylon.ui.panels;

import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

public class ProjectListPanel extends GridLayout implements ClickListener {

	public ProjectListPanel() {
		
		createContents();
		setMargin(true, true, true, true);
		setSpacing(true);
		setSizeFull();
//		setSizeFull();
		
	}

	private void createContents() {
		Workspace workspace = MainDashboard.getCurrent().getWorkspace();
		EList<Project> projects = workspace.getProjects();
		setColumns(1);
		setRows(projects.size()+1);
		buildHeader();
		
		Section section = new Section();
		section.setHeight(500, UNITS_PIXELS);
		section.setTitle("Active Projects");
		section.setWidth(100, UNITS_PERCENTAGE);
//		section.setWidth(800, UNITS_PIXELS);
		VerticalLayout layout = section.getBody();
		
		final Table table = new Table();
		table.setSizeFull();
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
//		table.setWidth(800, UNITS_PIXELS);
		table.addContainerProperty("location", Button.class, null);
		table.addContainerProperty("progress", ResolvableProgressIndicator.class, null);
		table.setColumnWidth("progress", 110);
		
		for (Project project : projects) {
			Button projectName = new Button(project.getName());
			projectName.setStyleName(Reindeer.BUTTON_LINK);
			projectName.setData(project);
			projectName.addListener(this);
			projectName.setIcon(ImageConstants.IMAGE_PROJECT);
			
			StaticProgressIndicator progress = new ResolvableProgressIndicator(project);
			
			table.addItem(new Object[] {projectName,progress},project.cdoID());
		}
		
		layout.addComponent(table);
		addComponent(section);		
	}

	private void buildHeader() {

		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Project project = (Project) event.getButton().getData();
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(project.getName());
		
	}
	

}
