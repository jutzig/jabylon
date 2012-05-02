package de.jutzig.jabylon.cdo.connector;

import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

public interface RepositoryConnector {
	
	CDOView openView();
	
	CDOTransaction openTransaction();
	
//	CDOSession getSession();
	
	CDOSession createSession();
	
	void close();
	
}
