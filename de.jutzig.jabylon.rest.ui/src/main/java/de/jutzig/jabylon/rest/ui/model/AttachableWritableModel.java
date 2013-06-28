package de.jutzig.jabylon.rest.ui.model;

import java.util.Collection;

import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class AttachableWritableModel<T extends CDOObject> implements IEObjectModel<T>, AttachableModel<T>{


    private static final long serialVersionUID = 1L;
    private String eClass;
    private String namespace;
    private String containmentFeature;
    private IModel<? extends CDOObject> parent;
    private transient T model;


    public AttachableWritableModel(EClass eClass, IModel<? extends CDOObject> parent, EStructuralFeature containmentFeature) {
        super();
        this.eClass = eClass.getName();
        this.namespace = eClass.getEPackage().getNsURI();
        this.parent = parent;
        this.containmentFeature = containmentFeature.getName();
    }

    public AttachableWritableModel(EClass eClass, IModel<? extends CDOObject> parent) {
        this(eClass, parent, computeContainmentFeature(eClass,parent));
    }

    private static EStructuralFeature computeContainmentFeature(EClass eClass, IModel<? extends CDOObject> parent) {
        EList<EReference> containments = parent.getObject().eClass().getEAllContainments();
        for (EReference eReference : containments) {
            if(eReference.getEType().getInstanceClass().isAssignableFrom(eClass.getInstanceClass()))
                return eReference;
        }
        throw new IllegalArgumentException("Could not compute the correct containment feature for the given eclass");
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
        EClassifier classifier = EPackage.Registry.INSTANCE.getEPackage(namespace).getEClassifier(eClass);
        T object = (T) EcoreUtil.create((EClass) classifier);
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void attach() {
        CDOObject container = parent.getObject();
        EStructuralFeature feature = container.eClass().getEStructuralFeature(containmentFeature);
        if(feature.isMany())
        {
            Collection c = (Collection) container.eGet(feature);
            c.add(getObject());
        }
        else
            container.eSet(feature, getObject());
    }

    @Override
    public IModel<?> getParent() {
        return parent;
    }


}
