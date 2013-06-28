package de.jutzig.jabylon.rest.ui.model;

import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public interface IEObjectModel<T extends CDOObject> extends IModel<T>{

    public abstract <X> IModel<X> forProperty(EStructuralFeature feature);

}
