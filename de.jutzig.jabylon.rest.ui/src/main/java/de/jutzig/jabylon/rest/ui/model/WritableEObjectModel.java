/**
 * 
 */
package de.jutzig.jabylon.rest.ui.model;

import java.io.Serializable;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

import com.google.common.base.Function;
import com.google.common.base.Suppliers;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.Activator;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class WritableEObjectModel<T extends Resolvable<?, ?>> extends EObjectModel<T> {


	private static final long serialVersionUID = 1L;
	
	private transient CDOTransaction transaction;

	public WritableEObjectModel(T model) {
		super(model);
	}

	@Override
	protected T getDomainObject() {
		T result = super.getDomainObject();
		if (result.cdoView() instanceof CDOTransaction) {
			transaction = (CDOTransaction) result.cdoView();
		}
		return result; 
	}

	@Override
	public void detach() {

		this.modelSupplier = Suppliers.memoize(Suppliers.compose(new LookupFunction<T>(), Suppliers.ofInstance(id)));
		if(transaction!=null)
			transaction.close();

	}

	private static final class LookupFunction<X> implements Serializable, Function<CDOID, X> {

		/** field <code>serialVersionUID</code> */
		private static final long serialVersionUID = -7243181664341900603L;

		@SuppressWarnings("unchecked")
		@Override
		public X apply(CDOID from) {
			CDOObject cdoObject = Activator.getDefault().getRepositoryLookup().lookupWithTransaction(from);
			return (X) cdoObject;
		}

	}
}
