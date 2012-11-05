package de.jutzig.jabylon.rest.api;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

@Component
public class APIComponent {
	private static final String SERVLET_ALIAS = "/jabylon/api";
	
	@Reference
	private HttpService httpService;
	
	@Reference
	private RepositoryConnector repositoryConnector;
	
	@Reference
	private PropertyPersistenceService persistenceService;
	private CDOView view;
	private static final Logger logger = LoggerFactory.getLogger(APIComponent.class); 
	
	public void bindHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

	public void unbindHttpService(HttpService httpService) {
		logger.info("Http Service deactivated. Shutting down Jabylon REST API");
		this.httpService = null;
	}
	
	@Activate
	protected void startup() {
		try {

			view = getRepositoryConnector().openView();
			CDOResource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);

			logger.info("Starting up Jabylon REST API servlet at " + SERVLET_ALIAS);
			ApiServlet servlet = new ApiServlet((Workspace) resource.getContents().get(0), persistenceService);
			httpService.registerServlet(SERVLET_ALIAS, servlet, null, null);
		} catch (ServletException e) {
			logger.error("Failed to register Jabylon REST API servlet",e);
		} catch (NamespaceException e) {
			logger.error("Failed to register Jabylon REST API servlet",e);
		}
	}

	@Deactivate
	protected void shutdown() {
		httpService.unregister(SERVLET_ALIAS);
		logger.info("Shutting down Jabylon REST API");
		view.close();
		getRepositoryConnector().close();
	}

	public void bindRepositoryConnector(RepositoryConnector repositoryConnector) {
		this.repositoryConnector = repositoryConnector;
	}

	public void unbindRepositoryConnector(RepositoryConnector repositoryConnector) {
		this.repositoryConnector = null;
	}

	public RepositoryConnector getRepositoryConnector() {
		return repositoryConnector;
	}

	public PropertyPersistenceService getPersistenceService() {
		return persistenceService;
	}

	public void bindPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}
	
	public void unbindPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = null;
	}
}
