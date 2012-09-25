package de.jutzig.jabylon.rest.ui.model;

import org.eclipse.emf.cdo.CDOObject;

public class EObjectModel<T extends CDOObject> extends AbstractEMFModel<T, T> {

	public EObjectModel(T model)
    {
        super(model);
    }

    private static final long serialVersionUID = 1L;

    @Override
    public T getObject()
    {
        return getDomainObject();
    }

    @Override
    public void setObject(T object)
    {
        setDomainObject(object);
    }

}
