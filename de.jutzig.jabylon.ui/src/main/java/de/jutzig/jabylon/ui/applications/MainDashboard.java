package de.jutzig.jabylon.ui.applications;

import java.io.File;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumb;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumbImpl;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.pages.ProjectDashboard;
import de.jutzig.jabylon.ui.panels.ProjectListPanel;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

public class MainDashboard extends Application implements TransactionListener, CrumbTrail {

	private RepositoryConnector repositoryConnector;
	Button addProject;
	private CDOView currentView;
	private static final ThreadLocal<MainDashboard> application = new ThreadLocal<MainDashboard>();
	private BreadCrumb breadcrumbs;
	private Workspace workspace;
	private Component mainComponent;
	private VerticalLayout mainLayout;
	private GridLayout contentArea;

	@Override
	public void init() {
		// mainWindow initialization omitted
		setTheme("jabylon");
		workspace = getOrInitializeWorkspace();
		getContext().addTransactionListener(this);
		application.set(this);
		buildMainLayout();
	}

	private void buildMainLayout() {
		setMainWindow(new Window("Jabylon"));

		mainLayout = new VerticalLayout();
		contentArea = new GridLayout();
		contentArea.setSizeUndefined();
		
		Component header = createHeader();
		
		mainLayout.addComponent(header);
		mainLayout.setComponentAlignment(header, Alignment.TOP_LEFT);
		mainLayout.setExpandRatio(header, 0f);
		mainLayout.addComponent(contentArea);
		
		getMainWindow().setContent(mainLayout);
		

	}

	private Component createHeader() {
		Label title = new Label();
		title.setCaption("Jabylon");
		title.setStyleName(Reindeer.LABEL_H1);
//		title.addStyleName(JabylonStyle.APPLICATION_TITLE.getCSSName());
		
		HorizontalLayout header = new HorizontalLayout();
		header.setMargin(true);
		header.setSpacing(true);

		
		header.addComponent(title);
		BreadCrumbImpl crumbs = new BreadCrumbImpl();
		breadcrumbs = crumbs;
		header.addComponent(crumbs);
		return header;
	}

	public HorizontalLayout createToolbar() {

		HorizontalLayout lo = new HorizontalLayout();


		return lo;

	}

	public void setMainComponent(Component c) {
		if(mainComponent!=null)
		{
			contentArea.removeComponent(mainComponent);
		}
		mainComponent = c;
		contentArea.addComponent(mainComponent);
		
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

	private CDOView getView() {
		if (currentView == null)
			currentView = getRepositoryConnector().openView();
		return currentView;
	}

	private void closeView() {
		if (currentView != null) {
			currentView.close();
			currentView = null;
		}
	}



	public void transactionStart(Application application, Object transactionData) {
		if (application == this)
			MainDashboard.application.set(this);
	}

	public static MainDashboard getCurrent() {
		return application.get();
	}

	public BreadCrumb getBreadcrumbs() {
		return breadcrumbs;
	}

	public void transactionEnd(Application application, Object transactionData) {
	}

	private Workspace getOrInitializeWorkspace() {
		CDOView view = getView();
		Workspace workspace = null;
		if (view.hasResource(ServerConstants.WORKSPACE_RESOURCE)) {

			CDOResource resource = view
					.getResource(ServerConstants.WORKSPACE_RESOURCE);
			workspace = (Workspace) resource.getContents().get(0);
		} else {
			CDOTransaction transaction = getRepositoryConnector()
					.openTransaction();
			CDOResource resource = transaction
					.createResource(ServerConstants.WORKSPACE_RESOURCE);
			workspace = PropertiesFactory.eINSTANCE.createWorkspace();
			URI uri = URI.createFileURI(ServerConstants.WORKSPACE_DIR);
			File root = new File(ServerConstants.WORKSPACE_DIR);
			if (!root.exists())
				root.mkdirs();
			workspace.setRoot(uri);
			resource.getContents().add(workspace);
			try {
				transaction.commit();
				workspace = view.getObject(workspace);
			} catch (final CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				transaction.close();
			}
		}
		return workspace;
	}

	@Override
	public CrumbTrail walkTo(String path) {
		ProjectDashboard dashboard = new ProjectDashboard(path);
		setMainComponent(dashboard);
		return dashboard;
	}

	@Override
	public Component getComponent() {
		return new ProjectListPanel();
	}

	@Override
	public String getTrailCaption() {
		return "Jabylon";
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}

}

