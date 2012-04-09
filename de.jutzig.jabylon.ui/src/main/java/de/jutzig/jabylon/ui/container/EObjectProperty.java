package de.jutzig.jabylon.ui.container;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractProperty;

public class EObjectProperty extends AbstractProperty implements Adapter{

	
	private EObject object;
	private EStructuralFeature feature;
	
	
	
	public EObjectProperty(EObject object, EStructuralFeature feature) {
		super();
		this.object = object;
		this.feature = feature;
	}

	@Override
	public Object getValue() {
		if(object==null)
			return null;
		return object.eGet(feature);
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		object.eSet(feature, newValue);
	}

	@Override
	public Class<?> getType() {
		return feature.getEType().getInstanceClass();
	}

	
	@Override
	public void addListener(ValueChangeListener listener) {
		if(getListeners(ValueChangeListener.class).isEmpty() && object!=null)
		{
			object.eAdapters().add(this);
		}
		super.addListener(listener);
	}

	@Override
	public void notifyChanged(Notification notification) {
		if(feature==notification.getFeature())
			fireValueChange();
	}

	@Override
	public Notifier getTarget() {
		return object;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		if (newTarget != object)
		{
			if(newTarget instanceof EObject) 
				object = (EObject) newTarget;
			else
				object = null;		
		}
		
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == Property.class;
	}
	
}
