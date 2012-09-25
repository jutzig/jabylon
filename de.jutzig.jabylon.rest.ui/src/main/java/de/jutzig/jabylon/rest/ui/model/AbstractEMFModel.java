package de.jutzig.jabylon.rest.ui.model;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.jutzig.jabylon.rest.ui.Activator;


public abstract class AbstractEMFModel<T extends CDOObject, R>
    implements IModel<R>
{

    private Supplier<T> modelSupplier;
    private CDOID id;

    public AbstractEMFModel(T model)
    {
        super();
        id=model.cdoID();
        this.modelSupplier = Suppliers.memoize(Suppliers.ofInstance(model));
    }

    protected T getDomainObject()
    {
        return modelSupplier.get();
    }

    protected void setDomainObject(T domainObject) {
        this.modelSupplier = Suppliers.memoize(Suppliers.ofInstance(domainObject));
    }


    @Override
    public void detach()
    {
        this.modelSupplier = Suppliers.memoize(Suppliers.compose(new LookupFunction<T>(), Suppliers.ofInstance(id)));

    }

    private static final class LookupFunction<T> implements Serializable, Function<CDOID, T>
    {

        /** field <code>serialVersionUID</code> */
        private static final long serialVersionUID = -7243181664341900603L;

        @Override
        public T apply(CDOID from)
        {
            return Activator.getDefault().getRepositoryLookup().lookup(from);
        }

    }
}



