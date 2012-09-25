package de.jutzig.jabylon.rest.ui.model;

import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;


public class RepositoryLookupImpl
    implements RepositoryLookup
{

    private RepositoryConnector connector;
    private CDOView view;
    private Workspace workspace;



    public RepositoryLookupImpl(RepositoryConnector connector)
    {
        super();
        this.connector = connector;
        view = connector.openView();
        workspace = (Workspace)view.getResource(ServerConstants.WORKSPACE_RESOURCE).getContents().get(0);
    }



    @SuppressWarnings("unchecked")
    @Override
    public <T extends Resolvable< ? , ? >> T lookup(String path)
    {
        return lookup(URI.createURI(path));

    }


    public void dispose()
    {
        view.close();
    }



    @Override
    public <T extends CDOObject> T lookup(CDOID id)
    {
        return (T)view.getObject(id);
    }



    @Override
    public <T extends Resolvable< ? , ? >> T lookup(List<String> path)
    {
        return lookup(URI.createHierarchicalURI(path.toArray(new String[]{}), null, null));
    }

    @Override
    public <T extends Resolvable< ? , ? >> T lookup(URI uri)
    {
        return (T)workspace.resolveChild(uri);
    }
}



