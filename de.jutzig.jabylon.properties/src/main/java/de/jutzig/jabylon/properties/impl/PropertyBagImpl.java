/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import de.jutzig.jabylon.properties.ProjectVersion;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Bag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl#getDescriptors <em>Descriptors</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl#getPath <em>Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl#getFullPath <em>Full Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyBagImpl#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyBagImpl extends CDOObjectImpl implements PropertyBag {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final URI PATH_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getFullPath() <em>Full Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullPath()
	 * @generated
	 * @ordered
	 */
	protected static final URI FULL_PATH_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyBagImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROPERTY_BAG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<PropertyFileDescriptor> getDescriptors() {
		return (EList<PropertyFileDescriptor>)eDynamicGet(PropertiesPackage.PROPERTY_BAG__DESCRIPTORS, PropertiesPackage.Literals.PROPERTY_BAG__DESCRIPTORS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getPath() {
		return (URI)eDynamicGet(PropertiesPackage.PROPERTY_BAG__PATH, PropertiesPackage.Literals.PROPERTY_BAG__PATH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(URI newPath) {
		eDynamicSet(PropertiesPackage.PROPERTY_BAG__PATH, PropertiesPackage.Literals.PROPERTY_BAG__PATH, newPath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor getMaster() {
		return (PropertyFileDescriptor)eDynamicGet(PropertiesPackage.PROPERTY_BAG__MASTER, PropertiesPackage.Literals.PROPERTY_BAG__MASTER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyFileDescriptor basicGetMaster() {
		for (PropertyFileDescriptor descr : getDescriptors()) {
			if(descr.isMaster())
				return descr;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setMaster(PropertyFileDescriptor newMaster) {
		newMaster.setVariant(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI getFullPath() {
		return getProject().getBase().appendSegments(getPath().segments());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectVersion getProject() {
		return (ProjectVersion)eDynamicGet(PropertiesPackage.PROPERTY_BAG__PROJECT, PropertiesPackage.Literals.PROPERTY_BAG__PROJECT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProject(ProjectVersion newProject, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProject, PropertiesPackage.PROPERTY_BAG__PROJECT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(ProjectVersion newProject) {
		eDynamicSet(PropertiesPackage.PROPERTY_BAG__PROJECT, PropertiesPackage.Literals.PROPERTY_BAG__PROJECT, newProject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDescriptors()).basicAdd(otherEnd, msgs);
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProject((ProjectVersion)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				return ((InternalEList<?>)getDescriptors()).basicRemove(otherEnd, msgs);
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				return basicSetProject(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				return eInternalContainer().eInverseRemove(this, PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS, ProjectVersion.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				return getDescriptors();
			case PropertiesPackage.PROPERTY_BAG__PATH:
				return getPath();
			case PropertiesPackage.PROPERTY_BAG__MASTER:
				if (resolve) return getMaster();
				return basicGetMaster();
			case PropertiesPackage.PROPERTY_BAG__FULL_PATH:
				return getFullPath();
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				return getProject();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				getDescriptors().clear();
				getDescriptors().addAll((Collection<? extends PropertyFileDescriptor>)newValue);
				return;
			case PropertiesPackage.PROPERTY_BAG__PATH:
				setPath((URI)newValue);
				return;
			case PropertiesPackage.PROPERTY_BAG__MASTER:
				setMaster((PropertyFileDescriptor)newValue);
				return;
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				setProject((ProjectVersion)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				getDescriptors().clear();
				return;
			case PropertiesPackage.PROPERTY_BAG__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_BAG__MASTER:
				setMaster((PropertyFileDescriptor)null);
				return;
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				setProject((ProjectVersion)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_BAG__DESCRIPTORS:
				return !getDescriptors().isEmpty();
			case PropertiesPackage.PROPERTY_BAG__PATH:
				return PATH_EDEFAULT == null ? getPath() != null : !PATH_EDEFAULT.equals(getPath());
			case PropertiesPackage.PROPERTY_BAG__MASTER:
				return basicGetMaster() != null;
			case PropertiesPackage.PROPERTY_BAG__FULL_PATH:
				return FULL_PATH_EDEFAULT == null ? getFullPath() != null : !FULL_PATH_EDEFAULT.equals(getFullPath());
			case PropertiesPackage.PROPERTY_BAG__PROJECT:
				return getProject() != null;
		}
		return super.eIsSet(featureID);
	}

} //PropertyBagImpl
