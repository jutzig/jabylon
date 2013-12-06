/**
 *
 */
package org.jabylon.scheduler;

import java.util.Map;

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.scheduler.internal.JabylonJob;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JobContextUtil {


    public static CDOSession openSession(Map<String, Object> jobContext)
    {
        RepositoryConnector connector = getRepositoryConnector(jobContext);
        return connector.createSession();
    }

    public static CDOTransaction openTransaction(Map<String, Object> jobContext)
    {
        RepositoryConnector connector = getRepositoryConnector(jobContext);
        return connector.openTransaction();
    }

    public static CDOView openView(Map<String, Object> jobContext)
    {
        RepositoryConnector connector = getRepositoryConnector(jobContext);
        return connector.openView();
    }

    public static RepositoryConnector getRepositoryConnector(Map<String, Object> jobContext)
    {
        return (RepositoryConnector) jobContext.get(JabylonJob.CONNECTOR_KEY);
    }

}
