package de.jutzig.jabylon.rest.ui.model;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EObjectPropertyModel<T extends CDOObject,R> extends AbstractEMFModel<T, R> {

    private String featureName;

    public EObjectPropertyModel(T model, EStructuralFeature feature)
    {
        super(model);
        this.featureName = feature.getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public R getObject()
    {
        T object = getDomainObject();
        return (R)object.eGet(object.eClass().getEStructuralFeature(featureName));
    }

    @Override
    public void setObject(R object)
    {
        T domainObject = getDomainObject();
        domainObject.eSet(domainObject.eClass().getEStructuralFeature(featureName), object);
    }

}
