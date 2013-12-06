package org.jabylon.security;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class SubjectAttribute {

    private EStructuralFeature feature;
    private Object value;

    public SubjectAttribute(EStructuralFeature feature, Object value) {
        super();
        this.feature = feature;
        this.value = value;
    };

    public Object getValue() {
        return value;
    }

    public EStructuralFeature getFeature() {
        return feature;
    }

    public void applyTo(EObject eobject) {
        eobject.eSet(getFeature(), getValue());
    }

}
