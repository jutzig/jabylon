/**
 * 
 */
package de.jutzig.jabylon.rest.ui.model;

import java.util.List;

import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class EObjectListDataProvider<T extends CDOObject> extends ListDataProvider<IModel<T>> {

	private transient CDOObject object;
	private String featureName;
	
	@SuppressWarnings("unchecked")
	public EObjectListDataProvider(CDOObject object, EStructuralFeature feature) {
		super((List<T>) object.eGet(feature));
		featureName = feature.getName();
		this.object = object;
	}
	
	@Override
	public IModel<T> model(T object) {
		return new EObjectModel<T>(object);
	}
	
}
