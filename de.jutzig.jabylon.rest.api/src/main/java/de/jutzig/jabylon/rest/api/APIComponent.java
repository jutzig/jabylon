package de.jutzig.jabylon.rest.api;

import javax.servlet.ServletException;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

public class APIComponent {
	private static final String SERVLET_ALIAS = "/jabylon/api";
	private HttpService httpService;
	private RepositoryConnector repositoryConnector;
	private CDOView view;
	private PropertyPersistenceService persistenceService;

	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

	protected void startup() {
		try {

			view = getRepositoryConnector().openView();
			CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);

			System.out.println("Staring up sevlet at " + SERVLET_ALIAS);
			ApiServlet servlet = new ApiServlet((Workspace) resource.getContents().get(0), persistenceService);
			httpService.registerServlet(SERVLET_ALIAS, servlet, null, null);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (NamespaceException e) {
			e.printStackTrace();
		}
	}

	protected void shutdown() {
		httpService.unregister(SERVLET_ALIAS);
		view.close();
		getRepositoryConnector().close();
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

	public PropertyPersistenceService getPersistenceService() {
		return persistenceService;
	}

	public void setPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	
	public void unsetPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = null;
	}
}
