package de.jutzig.jabylon.cdo.connector;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

	
	/**
	 * deletes the given eobject as well as all cross references pointing to it
	 * @param object
	 * @param features the features to consider for cross references, or null if all features are relevant
	 * @throws CommitException 
	 */
	public static void deleteWithCrossRefs(CDOObject object, final EReference... features) throws CommitException {
		commit(object, new Modification<CDOObject, CDOObject>() {

			@Override
			public CDOObject apply(CDOObject object) {
				List<CDOObjectReference> refs = object.cdoView().queryXRefs(object, features);
				for (CDOObjectReference reference : refs) {
					EStructuralFeature sourceFeature = reference.getSourceFeature();
					if(sourceFeature.isMany()) {
						Object result = reference.getSourceObject().eGet(sourceFeature);
						if (result instanceof Collection) {
							Collection collection = (Collection) result;
							collection.remove(object);
						}
					}
					else
					{
						reference.getSourceObject().eUnset(sourceFeature);
					}
				}
				EcoreUtil.remove(object);
				return null;
			}
		});
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
