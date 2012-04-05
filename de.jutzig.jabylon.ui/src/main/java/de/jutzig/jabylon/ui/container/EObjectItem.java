package de.jutzig.jabylon.ui.container;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class EObjectItem implements Item {

	private EObject eObject;
	
	
	public EObjectItem(EObject eObject) {
		this.eObject = eObject;
	}
	
	@Override
	public Property getItemProperty(Object id) {
		return new EObjectProperty(eObject, (EStructuralFeature) id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		
		return eObject.eClass().getEAllStructuralFeatures();
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public EObject getEObject() {
		return eObject;
	};

}
