/**
 *
 */
package de.jutzig.jabylon.rest.ui.model;


import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class ComplexEObjectListDataProvider<R extends CDOObject>
    extends AbstractEMFModel<CDOObject, R>
    implements IDataProvider<R>
{

	private static final long serialVersionUID = 1L;
	private String featureName;


    public ComplexEObjectListDataProvider(CDOObject object, EStructuralFeature feature)
    {
        super(object);
        featureName = feature.getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator< ? extends R> iterator(long arg0, long arg1)
    {
        CDOObject model = getDomainObject();
        EStructuralFeature feature = model.eClass().getEStructuralFeature(featureName);
        List<R> result = (List<R>)model.eGet(feature);
        return result.subList((int)arg0, (int)arg1).iterator();
    }


    @SuppressWarnings("unchecked")
    @Override
    public long size()
    {
        CDOObject model = getDomainObject();
        EStructuralFeature feature = model.eClass().getEStructuralFeature(featureName);
        List<R> result = (List<R>)model.eGet(feature);
        return result.size();
    }


    @Override
    public R getObject()
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setObject(R object)
    {
        // TODO Auto-generated method stub

    }


    @Override
    public IModel<R> model(R object)
    {
        return new EObjectModel<R>(object);
    }

}
