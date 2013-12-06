/**
 *
 */
package org.jabylon.rest.ui.model;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class SimpleEObjectListDataProvider<T extends Serializable>
    extends AbstractEMFModel<CDOObject, T>
    implements IDataProvider<T>
{

    private String featureName;


    public SimpleEObjectListDataProvider(CDOObject object, EStructuralFeature feature)
    {
        super(object);
        featureName = feature.getName();
    }


    @Override
    public IModel<T> model(T object)
    {
        return Model.of(object);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Iterator< ? extends T> iterator(long arg0, long arg1)
    {
        CDOObject model = getDomainObject();
        EStructuralFeature feature = model.eClass().getEStructuralFeature(featureName);
        List<T> result = (List<T>)model.eGet(feature);
        return result.subList((int)arg0, (int)arg1).iterator();
    }


    @SuppressWarnings("unchecked")
    @Override
    public long size()
    {
        CDOObject model = getDomainObject();
        EStructuralFeature feature = model.eClass().getEStructuralFeature(featureName);
        List<T> result = (List<T>)model.eGet(feature);
        return result.size();
    }


    @Override
    public T getObject()
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setObject(T object)
    {
        // TODO Auto-generated method stub

    }

}
