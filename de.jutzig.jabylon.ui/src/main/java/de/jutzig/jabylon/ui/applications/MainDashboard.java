package de.jutzig.jabylon.ui.applications;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import com.vaadin.Application;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.URIHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.forms.NewProjectForm;

public class MainDashboard extends Application {

	
	private static final String WORKSPACE = "workspace";
	private RepositoryConnector repositoryConnector;
	Button addProject;
	private HorizontalSplitPanel horizontalSplit;
	private CDOView currentView;
	
	@Override
	public void init() {
		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow(new Window("Jabylon"));
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		horizontalSplit = new HorizontalSplitPanel();
		layout.addComponent(createToolbar());
		layout.addComponent(horizontalSplit);

		/* Allocate all available extra space to the horizontal split panel */

		layout.setExpandRatio(horizontalSplit, 1);
		/*
		 * Set the initial split position so we can have a 200 pixel menu to the
		 * left
		 */

		horizontalSplit.setSplitPosition(200, Sizeable.UNITS_PIXELS);
		horizontalSplit.setCaption("A caption");
		horizontalSplit.setDescription("The description");

		getMainWindow().setContent(layout);
		addProject = new Button();
		addProject.setCaption("Create Project");
		addProject.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Workspace workspace = getOrInitializeWorkspace();
				setMainComponent(new NewProjectForm(workspace, MainDashboard.this));
				
			}


		});
		horizontalSplit.addComponent(addProject);

	}

	public HorizontalLayout createToolbar() {

		HorizontalLayout lo = new HorizontalLayout();
		// lo.addComponent(newContact);
		// lo.addComponent(search);
		// lo.addComponent(share);
		// lo.addComponent(help);

		return lo;

	}
	
	public void setMainComponent(Component c) {
        horizontalSplit.setSecondComponent(c);
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
	
	private CDOView getView()
	{
		if(currentView==null)
			currentView = getRepositoryConnector().openView();
		return currentView;
	}
	
	private void closeView()
	{
		if(currentView!=null)
		{
			currentView.close();
			currentView = null;
		}
	}
	
	private Workspace getOrInitializeWorkspace() {
		CDOView view = getView();
		Workspace workspace = null;
		if(view.hasResource(WORKSPACE))
		{
			
			CDOResource resource = view.getResource(WORKSPACE);
			workspace = (Workspace) resource.getContents().get(0);
		}
		else
		{
			CDOTransaction transaction = getRepositoryConnector().openTransaction();
			CDOResource resource = transaction.createResource(WORKSPACE);
			workspace = PropertiesFactory.eINSTANCE.createWorkspace();
			resource.getContents().add(workspace);
			try {
				transaction.commit();
			} catch (final CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				transaction.close();				
			}
		}
		return workspace;
	}
	
	
}
