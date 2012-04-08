/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyBag;

import java.util.Collection;
import java.util.Locale;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Locale</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectLocaleImpl#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectLocaleImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectLocaleImpl#getDescriptors <em>Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectLocaleImpl extends CDOObjectImpl implements ProjectLocale {
	/**
	 * The default value of the '{@link #getLocale() <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected static final Locale LOCALE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectLocaleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT_LOCALE;
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
	public ProjectVersion getProjectVersion() {
		return (ProjectVersion)eDynamicGet(PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION, PropertiesPackage.Literals.PROJECT_LOCALE__PROJECT_VERSION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjectVersion(ProjectVersion newProjectVersion, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProjectVersion, PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectVersion(ProjectVersion newProjectVersion) {
		eDynamicSet(PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION, PropertiesPackage.Literals.PROJECT_LOCALE__PROJECT_VERSION, newProjectVersion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Locale getLocale() {
		return (Locale)eDynamicGet(PropertiesPackage.PROJECT_LOCALE__LOCALE, PropertiesPackage.Literals.PROJECT_LOCALE__LOCALE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocale(Locale newLocale) {
		eDynamicSet(PropertiesPackage.PROJECT_LOCALE__LOCALE, PropertiesPackage.Literals.PROJECT_LOCALE__LOCALE, newLocale);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<PropertyFileDescriptor> getDescriptors() {
		return (EList<PropertyFileDescriptor>)eDynamicGet(PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS, PropertiesPackage.Literals.PROJECT_LOCALE__DESCRIPTORS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMaster() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int percentComplete() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProjectVersion((ProjectVersion)otherEnd, msgs);
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				return basicSetProjectVersion(null, msgs);
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				return eInternalContainer().eInverseRemove(this, PropertiesPackage.PROJECT_VERSION__LOCALES, ProjectVersion.class, msgs);
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				return getProjectVersion();
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				return getLocale();
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return getDescriptors();
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				setProjectVersion((ProjectVersion)newValue);
				return;
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				setLocale((Locale)newValue);
				return;
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				getDescriptors().clear();
				getDescriptors().addAll((Collection<? extends PropertyFileDescriptor>)newValue);
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				setProjectVersion((ProjectVersion)null);
				return;
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				setLocale(LOCALE_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				getDescriptors().clear();
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
			case PropertiesPackage.PROJECT_LOCALE__PROJECT_VERSION:
				return getProjectVersion() != null;
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				return LOCALE_EDEFAULT == null ? getLocale() != null : !LOCALE_EDEFAULT.equals(getLocale());
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return !getDescriptors().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProjectLocaleImpl
