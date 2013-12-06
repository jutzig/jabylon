package org.jabylon.cdo.connector;

import org.eclipse.emf.ecore.EObject;

public interface Modification <T extends EObject, R extends EObject>{

    R apply(T object);


//	public <T> T modify(IUnitOfWork<T,P> work);
}
