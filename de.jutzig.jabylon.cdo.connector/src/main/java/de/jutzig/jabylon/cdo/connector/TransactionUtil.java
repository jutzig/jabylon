package de.jutzig.jabylon.cdo.connector;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.net4j.util.transaction.TransactionException;

public class TransactionUtil {

	public static <T extends EObject, R extends EObject> R commit(T parent, Modification<T,R> op) throws CommitException {
		if (parent instanceof CDOObject) {
			CDOObject cdoObject = (CDOObject) parent;
			CDOView view = cdoObject.cdoView();
			CDOTransaction transaction = getTransaction(cdoObject);
			T writableObject = transaction.getObject(parent);
			R returnValue = null;
			try{
				returnValue = op.apply(writableObject);
				transaction.commit();
				return returnValue;
			}
			finally
			{
				if(view!=transaction)
				{
					transaction.close();
					if(returnValue!=null)
						return view.getObject(returnValue);
				}
			}
			

		}
		throw new TransactionException("could not obtain a transaction");
	}

	private static CDOTransaction getTransaction(CDOObject object) {
		CDOView view = object.cdoView();
		if (view instanceof CDOTransaction) {
			CDOTransaction transaction = (CDOTransaction) view;
			return transaction;
		} else if (view != null) {
			return view.getSession().openTransaction();
		}
		throw new TransactionException("could not obtain a transaction");
	}
}
