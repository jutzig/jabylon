/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
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
