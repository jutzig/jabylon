package de.jutzig.jabylon.rest.ui.model;

import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;

@Component
@Service(RepositoryLookup.class)
public class RepositoryLookupImpl implements RepositoryLookup {

	@Reference
	private RepositoryConnector connector;
	private CDOView view;
	private Workspace workspace;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Resolvable<?, ?>> T lookup(String path) {
		Resolvable<?, ?> resolvable = lookup(URI.createURI(path));
		return (T) resolvable;
	}

	public void dispose() {
		view.close();
	}

	@Override
	public <T extends CDOObject> T lookup(CDOID id) {
		return (T) view.getObject(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Resolvable<?, ?>> T lookup(List<String> path) {
		Resolvable<?, ?> lookup = lookup(URI.createHierarchicalURI(path.toArray(new String[] {}), null, null));
		return (T) lookup;
	}

	@Override
	public <T extends Resolvable<?, ?>> T lookup(URI uri) {
		return (T) workspace.resolveChild(uri);
	}

	@Override
	public <T extends CDOObject> T lookupWithTransaction(CDOID id) {
		CDOTransaction transaction = connector.openTransaction();
		return (T) transaction.getObject(id);
	}

	public void bindConnector(RepositoryConnector connector) {
		this.connector = connector;
		view = connector.openView();
		workspace = (Workspace) view.getResource(ServerConstants.WORKSPACE_RESOURCE).getContents().get(0);
	}
}
