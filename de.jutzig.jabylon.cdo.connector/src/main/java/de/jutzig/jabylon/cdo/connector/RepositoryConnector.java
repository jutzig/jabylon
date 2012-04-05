package de.jutzig.jabylon.cdo.connector;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

public interface RepositoryConnector {
	
	CDOView openView();
	
	CDOTransaction openTransaction();
	
//	CDOSession getSession();
	
	void close();
	
}
