package de.jutzig.jabylon.ui.applications;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumb;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumbImpl;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.ApplicationTitleBar;
import de.jutzig.jabylon.ui.components.LabeledContainer;
import de.jutzig.jabylon.ui.pages.ProjectDashboard;
import de.jutzig.jabylon.ui.panels.ProjectListPanel;
import de.jutzig.jabylon.ui.team.TeamProvider;

public class MainDashboard extends Application implements TransactionListener, CrumbTrail {

	private RepositoryConnector repositoryConnector;
	Button addProject;
	private CDOView currentView;
	private static final ThreadLocal<MainDashboard> application = new ThreadLocal<MainDashboard>();
	private BreadCrumb breadcrumbs;
	private Workspace workspace;
	private VerticalLayout mainLayout;
	private LabeledContainer contentArea;
	private Map<String, TeamProvider> teamProvider;
	private PropertyPersistenceService propertyPersistence;

	public MainDashboard() {
		teamProvider = new HashMap<String, TeamProvider>();
		
	}

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

		ApplicationTitleBar titleBar = new ApplicationTitleBar();
		mainLayout.addComponent(titleBar);
		addListener(titleBar); //user change listener

		contentArea = new LabeledContainer();
		contentArea.setHeadClient(createHeader());
		contentArea.setSizeFull();
		breadcrumbs.addCrumbListener(titleBar);

//		Component header = createHeader();
//
//		mainLayout.addComponent(header);
//		mainLayout.setComponentAlignment(header, Alignment.TOP_LEFT);
//		mainLayout.setExpandRatio(header, 0f);
		mainLayout.addComponent(contentArea);

		getMainWindow().setContent(mainLayout);


	}

	private Component createHeader() {
//		Label title = new Label();
//		title.setCaption("Jabylon");
//		title.setStyleName(Reindeer.LABEL_H1);
//
//		HorizontalLayout header = new HorizontalLayout();
//
//		header.setSpacing(true);
//		header.addComponent(title);
//
//		header.addStyleName(JabylonStyle.BREADCRUMB_PANEL.getCSSName());
//		BreadCrumbImpl crumbs = new BreadCrumbImpl();
//		breadcrumbs = crumbs;
//		header.addComponent(crumbs);
//		return header;


        HorizontalLayout nav = new HorizontalLayout();
        nav.setHeight("30px");
        nav.setWidth("100%");
//        nav.setStyleName(JabylonStyle.BREADCRUMB_PANEL.getCSSName());
        nav.setSpacing(true);
//        nav.setMargin(true, true, false, true);


        // Breadcrumbs
		BreadCrumbImpl crumbs = new BreadCrumbImpl();
		breadcrumbs = crumbs;
		nav.addComponent(crumbs);
        nav.setExpandRatio(crumbs, 1);
        nav.setComponentAlignment(crumbs, Alignment.MIDDLE_LEFT);

        return nav;
	}

	public HorizontalLayout createToolbar() {

		HorizontalLayout lo = new HorizontalLayout();


		return lo;

	}

	public void setMainComponent(Component c) {
		contentArea.setBody(c);
	}

	public void setPropertyPersistence(PropertyPersistenceService propertyPersistence) {
		this.propertyPersistence = propertyPersistence;
	}
	
	
	public PropertyPersistenceService getPropertyPersistence() {
		return propertyPersistence;
	}
	
	public void unsetPropertyPersistence(PropertyPersistenceService propertyPersistence) {
		this.propertyPersistence = null;
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

			CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
			workspace = (Workspace) resource.getContents().get(0);
		} else {
			CDOTransaction transaction = getRepositoryConnector().openTransaction();
			CDOResource resource = transaction.createResource(ServerConstants.WORKSPACE_RESOURCE);
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
//		setMainComponent(dashboard);
		return dashboard;
	}

	@Override
	public Component createContents() {
		return new ProjectListPanel();
	}

	@Override
	public String getTrailCaption() {
		return "Home";
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	@Override
	public boolean isDirty() {
		return false;
	}


	public void addTeamProvider(TeamProvider provider, Map properties)
	{
		//TODO: enable properties
		teamProvider.put((String) properties.get("scheme"), provider);
//		teamProvider.put("git", provider);
	}

	public void addTeamProvider(TeamProvider provider)
	{
		//TODO: enable properties
//		teamProvider.put((String) properties.get("scheme"), provider);
		teamProvider.put("git", provider);
	}



	public void removeTeamProvider(TeamProvider provider)
	{
		//TODO: enable properties
//		teamProvider.remove(properties.get("schema"));
		teamProvider.remove("git");
	}

	public TeamProvider getTeamProviderForURI(URI uri)
	{
		if(uri.lastSegment().endsWith(".git"))
			return teamProvider.get("git");
		return teamProvider.get(uri.scheme());
	}

	@Override
	public CDOObject getDomainObject() {
		return workspace;
	}

}
