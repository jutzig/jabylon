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

    private static final long serialVersionUID = 1L;

    protected Supplier<T> modelSupplier;
    protected CDOID id;

    public AbstractEMFModel(T model)
    {
        super();
        id=model.cdoID();
        this.modelSupplier = Suppliers.memoize(new LoadingSupplier<T>(model));
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
     //nothing to do

    }

    private static final class LookupFunction<X> implements Serializable, Function<CDOID, X>
    {

        /** field <code>serialVersionUID</code> */
        private static final long serialVersionUID = -7243181664341900603L;

        @SuppressWarnings("unchecked")
        @Override
        public X apply(CDOID from)
        {
            CDOObject cdoObject = Activator.getDefault().getRepositoryLookup().resolve(from);
            return (X)cdoObject;
        }
    }


    private static final class LoadingSupplier<T> implements Supplier<T>, Serializable {

        private static final long serialVersionUID = 1L;
        private transient T instance;
        private LookupFunction<T> function;
        private CDOID id;



        public LoadingSupplier(T instance) {
            super();
            this.instance = instance;
            function = new LookupFunction<T>();
        }



        @Override
        public T get() {
            if(instance==null)
                instance = function.apply(id);
            return instance;
        }

    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractEMFModel) {
            AbstractEMFModel other = (AbstractEMFModel) obj;
            return other.id.equals(id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
