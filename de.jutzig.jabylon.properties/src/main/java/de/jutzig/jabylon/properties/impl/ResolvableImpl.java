/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Resolvable</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ResolvableImpl#getPercentComplete <em>Percent Complete</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ResolvableImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ResolvableImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ResolvableImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ResolvableImpl<P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> extends CDOObjectImpl implements
		Resolvable<P, C> {
	/**
	 * The default value of the '{@link #getPercentComplete() <em>Percent Complete</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPercentComplete()
	 * @generated
	 * @ordered
	 */
	protected static final int PERCENT_COMPLETE_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResolvableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.RESOLVABLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getPercentComplete() {
		return (Integer)eDynamicGet(PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE, PropertiesPackage.Literals.RESOLVABLE__PERCENT_COMPLETE, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPercentComplete(int newPercentComplete) {
		eDynamicSet(PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE, PropertiesPackage.Literals.RESOLVABLE__PERCENT_COMPLETE, newPercentComplete);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<C> getChildren() {
		return (EList<C>)eDynamicGet(PropertiesPackage.RESOLVABLE__CHILDREN, PropertiesPackage.Literals.RESOLVABLE__CHILDREN, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public P getParent() {
		return (P)eDynamicGet(PropertiesPackage.RESOLVABLE__PARENT, PropertiesPackage.Literals.RESOLVABLE__PARENT, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(P newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, PropertiesPackage.RESOLVABLE__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(P newParent) {
		eDynamicSet(PropertiesPackage.RESOLVABLE__PARENT, PropertiesPackage.Literals.RESOLVABLE__PARENT, newParent);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(PropertiesPackage.RESOLVABLE__NAME, PropertiesPackage.Literals.RESOLVABLE__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(PropertiesPackage.RESOLVABLE__NAME, PropertiesPackage.Literals.RESOLVABLE__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public URI fullPath() {
		EObject parent = eContainer();
		while (parent != null) {
			if (parent instanceof Resolvable) {
				Resolvable resolvable = (Resolvable) parent;
				URI path = relativePath();
				if(path!=null)
				{
					URI fullParentPath = resolvable.fullPath();
					if(fullParentPath!=null)
						return fullParentPath.appendSegments(path.segments());
					return relativePath();
				}
				return resolvable.fullPath();
			}
		}
		return relativePath();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public abstract URI relativePath();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public URI absolutPath() {
		EObject object = this;
		while ((object != null) && !(object instanceof Workspace))
			object = object.eContainer();

		if (object instanceof Workspace) {
			Workspace workspace = (Workspace) object;
			URI root = workspace.getRoot();
			if (root == null)
				return null;
			if (root.isRelative()) {
				File f = new File("");
				URI workingDir = URI.createFileURI(f.getAbsolutePath());
				root = workingDir.appendSegments(root.segments());
			}
			return root.appendSegments(fullPath().segments());
		}
		return null;

	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public int updatePercentComplete() {
		Resolvable<?, ?> parent = getParent();
		int percentComplete = internalUpdatePercentComplete();
		if (percentComplete != getPercentComplete()) {
			setPercentComplete(percentComplete);
			while (parent != null) {
				parent.updatePercentComplete();
				parent = parent.getParent();
			}
		}
		return percentComplete;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Resolvable<?, ?> resolveChild(URI path) {
		return resolveChild(path.segmentsList());
	}

	@Override
	public Resolvable<?, ?> resolveChild(List<String> pathSegments) {
		if (pathSegments.isEmpty())
			return this;
		for (C child : getChildren()) {
			if (child.getName().equals(pathSegments.get(0)))
				return child.resolveChild(pathSegments.subList(1, pathSegments.size()));
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI absoluteFilePath() {
		return absolutPath();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public C getChild(String name) {
		for (C child : getChildren()) {
			if (child.getName().equals(name))
				return child;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case PropertiesPackage.RESOLVABLE__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((P)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case PropertiesPackage.RESOLVABLE__PARENT:
				return basicSetParent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PropertiesPackage.RESOLVABLE__PARENT:
				return eInternalContainer().eInverseRemove(this, PropertiesPackage.RESOLVABLE__CHILDREN, Resolvable.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	
	public int internalUpdatePercentComplete()
	{
		if(getChildren().isEmpty())
			return 100;
		int complete = 0;
		for (C child : getChildren()) {
			complete += child.getPercentComplete();
		}
		return (int) Math.floor(complete / getChildren().size());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
				return getPercentComplete();
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				return getChildren();
			case PropertiesPackage.RESOLVABLE__PARENT:
				return getParent();
			case PropertiesPackage.RESOLVABLE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
				setPercentComplete((Integer)newValue);
				return;
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends C>)newValue);
				return;
			case PropertiesPackage.RESOLVABLE__PARENT:
				setParent((P)newValue);
				return;
			case PropertiesPackage.RESOLVABLE__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
				setPercentComplete(PERCENT_COMPLETE_EDEFAULT);
				return;
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				getChildren().clear();
				return;
			case PropertiesPackage.RESOLVABLE__PARENT:
				setParent((P)null);
				return;
			case PropertiesPackage.RESOLVABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PropertiesPackage.RESOLVABLE__PERCENT_COMPLETE:
				return getPercentComplete() != PERCENT_COMPLETE_EDEFAULT;
			case PropertiesPackage.RESOLVABLE__CHILDREN:
				return !getChildren().isEmpty();
			case PropertiesPackage.RESOLVABLE__PARENT:
				return getParent() != null;
			case PropertiesPackage.RESOLVABLE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
		}
		return super.eIsSet(featureID);
	}

} // ResolvableImpl
