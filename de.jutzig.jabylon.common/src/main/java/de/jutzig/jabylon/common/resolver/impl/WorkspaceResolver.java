/**
 * 
 */
package de.jutzig.jabylon.common.resolver.impl;

import java.util.List;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.common.resolver.URIResolver;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.users.UserManagement;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@Component
@Service
public class WorkspaceResolver implements URIResolver {

	@Reference
	private RepositoryConnector repositoryConnector;
	private CDOSession session;
	private Workspace workspace;
	private UserManagement userManagement;

	@Activate
	public void activate() {
		session = repositoryConnector.createSession();
		CDOView view = session.openView();
		CDOResource workspaceResource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
		workspace = (Workspace) workspaceResource.getContents().get(0);
		CDOResource userResource = view.getResource(ServerConstants.USERS_RESOURCE);
		userManagement = (UserManagement) userResource.getContents().get(0);
	}

	@Deactivate
	public void deactivate() {
		session.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.jutzig.jabylon.common.resolver.URIResolver#resolve(org.eclipse.emf
	 * .common.util.URI)
	 */
	@Override
	public Object resolve(URI uri) {
		if (uri.isEmpty() || uri.segmentCount()==0)
			return workspace;
		String firstSegment = uri.segment(0);
		if ("workspace".equals(firstSegment))
		{
			List<String> list = uri.segmentsList().subList(1, uri.segmentCount());
			URI relativeURI = URI.createHierarchicalURI(list.toArray(new String[list.size()]), uri.query(), uri.fragment());
			return workspace.resolveChild(relativeURI);
		}
		//TODO: support additional URI schemes
		return null;
//		else if ("userManagment".equals(firstSegment))
//			return userManagment.resolveChild(uri.deresolve(URI.createURI("userManagment")));
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.jutzig.jabylon.common.resolver.URIResolver#resolve(java.lang.String)
	 */
	@Override
	public Object resolve(String path) {
		if(path==null)
			return workspace;
		URI uri = URI.createURI(path, true);
		return resolve(uri);
	}

	public void bindRepositoryConnector(RepositoryConnector connector) {
		this.repositoryConnector = connector;
	}

	public void unbindRepositoryConnector(RepositoryConnector connector) {
		this.repositoryConnector = null;
	}

	@Override
	public Object resolve(CDOID id) {
		return workspace.cdoView().getObject(id);
	}

}
