package de.jutzig.jabylon.ui.applications;

import com.vaadin.Application;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;

public class MainDashboard extends Application {

	
	private RepositoryConnector repositoryConnector;
	
	@Override
	public void init() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow(new Window("Jabylon"));
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		HorizontalSplitPanel horizontalSplit = new HorizontalSplitPanel();
		layout.addComponent(createToolbar());
		layout.addComponent(horizontalSplit);

		/* Allocate all available extra space to the horizontal split panel */

		layout.setExpandRatio(horizontalSplit, 1);
		/*
		 * Set the initial split position so we can have a 200 pixel menu to the
		 * left
		 */

		horizontalSplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);

		getMainWindow().setContent(layout);

	}

	public HorizontalLayout createToolbar() {

		HorizontalLayout lo = new HorizontalLayout();
		// lo.addComponent(newContact);
		// lo.addComponent(search);
		// lo.addComponent(share);
		// lo.addComponent(help);

		return lo;

	}

	
	public void setRepositoryConnector(RepositoryConnector repositoryConnector) {
		this.repositoryConnector = repositoryConnector;
	}
	
	public void unsetRepositoryConnector(RepositoryConnector repositoryConnector) {
		this.repositoryConnector = null;
	}
	
	public RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}
	
	
}
