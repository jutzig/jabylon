package de.jutzig.jabylon.rest.ui.model;

import org.apache.wicket.model.LoadableDetachableModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.rest.ui.Activator;

public class EObjectModel<T extends CDOObject> extends LoadableDetachableModel<T> {

	private static final long serialVersionUID = 1L;

	private transient EStructuralFeature feat;
	private String featContainerClassName;
	private String featName;
	private String featPackageNsURI;
	private CDOID cdoID;

	

	public EObjectModel(T target) {
		super(target);
//		this.feat = efeature;
		cdoID = target.cdoID();
//		this.featName = efeature.getName();
//		this.featContainerClassName = efeature.getContainerClass().getName();
//		this.featPackageNsURI = ((EPackage) efeature.eContainer().eContainer()).getNsURI();
	}


	public EStructuralFeature getFeature() {
		if (feat == null) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(featPackageNsURI);
			for (Object eClassifierItem : ePackage.getEClassifiers()) {
				EClassifier eClassifier = (EClassifier) eClassifierItem;
				if (eClassifier.getInstanceClass().getName().equals(featContainerClassName))
					feat = ((EClass) eClassifier).getEStructuralFeature(featName);
			}
		}
		return feat;
	}


	@SuppressWarnings("unchecked")
	public Class<T> getObjectClass() {
		return (Class<T>) getFeature().getEType().getInstanceClass();
	}


//	@Override
//	public String toString() {
//		return new StringBuilder("Model:classname=[").append(getClass().getName()).append("]").append(":target=[").append(target)
//				.append("]").toString();
//	}

	@SuppressWarnings("unchecked")
	@Override
	protected T load() {
		RepositoryConnector connector = Activator.getDefault().getRepositoryConnector();
		CDOView view = connector.openView();
		try{
			return (T) view.getObject(cdoID);			
		}finally{
			view.close();
		}
	}

}
