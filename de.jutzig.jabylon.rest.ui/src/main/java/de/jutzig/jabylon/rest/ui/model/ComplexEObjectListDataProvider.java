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
    implements IDataProvider<R>, IModel<List<R>>
{

	private static final long serialVersionUID = 1L;
	private String featureName;
	private IModel<? extends CDOObject> model;


    public ComplexEObjectListDataProvider(IModel<? extends CDOObject> model, EStructuralFeature feature)
    {
    	this.model = model;
        featureName = feature.getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator< ? extends R> iterator(long arg0, long arg1)
    {
        CDOObject parent = model.getObject();
        EStructuralFeature feature = parent.eClass().getEStructuralFeature(featureName);
        List<R> result = (List<R>)parent.eGet(feature);
        return result.subList((int)arg0, (int)arg1).iterator();
    }


    @SuppressWarnings("unchecked")
    @Override
    public long size()
    {
    	CDOObject parent = model.getObject();;
        EStructuralFeature feature = parent.eClass().getEStructuralFeature(featureName);
        List<R> result = (List<R>)parent.eGet(feature);
        return result.size();
    }



    @Override
    public IModel<R> model(R object)
    {
        return new EObjectModel<R>(object);
    }

	@Override
	public void detach() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<R> getObject() {
        CDOObject parent = model.getObject();
        EStructuralFeature feature = parent.eClass().getEStructuralFeature(featureName);
        List<R> result = (List<R>)parent.eGet(feature);
        return result;
	}

	@Override
	public void setObject(List<R> object) {
        CDOObject parent = model.getObject();
        EStructuralFeature feature = parent.eClass().getEStructuralFeature(featureName);
        parent.eSet(feature, object);
	}

}
