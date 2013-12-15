/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.connector.internal;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.net4j.acceptor.IAcceptor;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.users.UsersPackage;

@Component
@Service(RepositoryConnector.class)
public class RepositoryConnectorImpl implements RepositoryConnector {

    private IConnector connector = null;
    private CDONet4jSession session = null;

    @Reference
    private IAcceptor acceptor;
    /**
     * The repository name
     */
    private static final String REPOSITORY_NAME = "jabylon";

    @Deactivate
    @Override
    public void close() {

        if (session != null)
            LifecycleUtil.deactivate(session);

        if (connector != null)
            LifecycleUtil.deactivate(connector);

    }


    public void bindAcceptor(IAcceptor acceptor)
    {
        this.acceptor = acceptor;
    }

    public void unbindAcceptor(IAcceptor acceptor)
    {
        this.acceptor = null;
    }

    /**
     * Getter for session including lazy initialization
     *
     * @return the CDO session
     */
    public CDONet4jSession getSession() {
        if (session == null) {
            session = createSession();
        }
        return session;
    }

    /**
     * Getter for transaction including lazy initialization
     *
     * @return the transaction
     */
    public CDOTransaction openTransaction() {
        return getSession().openTransaction();

    }

    @Override
    public CDOView openView() {
        return getSession().openView();
    }

    @Override
    public CDONet4jSession createSession() {
        IManagedContainer container = IPluginContainer.INSTANCE;

        if (connector == null)
            connector = JVMUtil.getConnector(container, "default");

        CDONet4jSessionConfiguration config = CDONet4jUtil.createNet4jSessionConfiguration();
        config.setConnector(connector);
        config.setRepositoryName(REPOSITORY_NAME);
        CDONet4jSession theSession = config.openNet4jSession();
        theSession.options().setCollectionLoadingPolicy (CDOUtil.createCollectionLoadingPolicy(0, 300));
        theSession.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);
        theSession.getPackageRegistry().putEPackage(UsersPackage.eINSTANCE);
        return theSession;
    }

}
