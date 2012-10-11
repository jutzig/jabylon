package de.jutzig.jabylon.rest.ui.model;

import org.apache.wicket.model.IModel;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;

public class AttachableWritableModel<T extends Resolvable<?, ?>> implements IEObjectModel<T>, AttachableModel<T>{

	
	private static final long serialVersionUID = 1L;
	private String eClass;
	private IModel<?  extends Resolvable<?, T>> parent;
	private transient T model;


	public AttachableWritableModel(EClass eClass, IModel<? extends Resolvable<?, T>> parent) {
		super();
		this.eClass = eClass.getName();
		this.parent = parent;
	}

	@Override
	public void detach() {
		parent.detach();
		
	}
	
	@Override
	public T getObject() {
		if(model==null)
			model = createModel();
		return model;
	}

	@SuppressWarnings("unchecked")
	private T createModel() {
		EClassifier classifier = PropertiesPackage.eINSTANCE.getEClassifier(eClass);
		T object = (T) PropertiesFactory.eINSTANCE.create((EClass) classifier);
		return object;
	}

	@Override
	public void setObject(T object) {
		model = object;

		
	}

    @Override
	public <X> IModel<X> forProperty(EStructuralFeature feature)
    {
    	return new EObjectPropertyModel<X, T>(this, feature);
    }

	@Override
	public void attach() {
		if(parent.getObject()!=null)
			parent.getObject().getChildren().add(getObject());
		
	}


}
