package de.jutzig.jabylon.cdo.connector.internal;

import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.net4j.CDOSessionConfiguration;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.users.UsersPackage;

public class RepositoryConnectorImpl implements RepositoryConnector {

	private IConnector connector = null;
	private CDOSession session = null;
	/**
	 * The repository name
	 */
	private static final String REPOSITORY_NAME = "jabylon";

	@Override
	public void close() {

		if (session != null)
			LifecycleUtil.deactivate(session);

		if (connector != null)
			LifecycleUtil.deactivate(connector);

	}

	/**
	 * Getter for session including lazy initialization
	 *
	 * @return the CDO session
	 */
	public CDOSession getSession() {
		if (session == null) {
			IManagedContainer container = IPluginContainer.INSTANCE;

			if (connector == null)
				connector = JVMUtil.getConnector(container, "default");

			CDOSessionConfiguration config = CDONet4jUtil.createSessionConfiguration();
			config.setConnector(connector);
			config.setRepositoryName(REPOSITORY_NAME);
			// config.setLegacySupportEnabled(false);

			// see explanation below
			// config.setLazyPopulatingPackageRegistry();
			session = config.openSession();
			session.options().setCollectionLoadingPolicy (CDOUtil.createCollectionLoadingPolicy(0, 300));
			session.getPackageRegistry().putEPackage(PropertiesPackage.eINSTANCE);
			session.getPackageRegistry().putEPackage(UsersPackage.eINSTANCE);
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

}
