/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.connector;

import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

public interface RepositoryConnector {

    CDOView openView();

    CDOTransaction openTransaction();

//	CDOSession getSession();

    CDONet4jSession createSession();

    void close();


    /**
     * opens a view for the session and configures the default options
     * @param session
     */
    public CDOView openView(CDONet4jSession session);

    /**
     * configures the default options for a view
     * @param view
     */
    public <T extends CDOView> T configureView(T view);

}
