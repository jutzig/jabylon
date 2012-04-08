/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.util.Locale;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getPropertyFile <em>Property File</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getMaster <em>Master</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyFileDescriptorImpl extends ResolvableImpl implements PropertyFileDescriptor {
	/**
	 * The default value of the '{@link #getVariant() <em>Variant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected static final Locale VARIANT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final URI LOCATION_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyFileDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Locale getVariant() {
		return (Locale)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__VARIANT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariant(Locale newVariant) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__VARIANT, newVariant);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getLocation() {
		return (URI)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(URI newLocation) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isMaster() {
		return !eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__VARIANT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFile getPropertyFile() {
		return (PropertyFile)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFile basicGetPropertyFile() {
		return (PropertyFile)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor getMaster() {
		return (PropertyFileDescriptor)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__MASTER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor basicGetMaster() {
		return (PropertyFileDescriptor)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__MASTER, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaster(PropertyFileDescriptor newMaster) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__MASTER, newMaster);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				return getVariant();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION:
				return getLocation();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE:
				if (resolve) return getPropertyFile();
				return basicGetPropertyFile();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				if (resolve) return getMaster();
				return basicGetMaster();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				setVariant((Locale)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION:
				setLocation((URI)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				setMaster((PropertyFileDescriptor)newValue);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				setVariant(VARIANT_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				setMaster((PropertyFileDescriptor)null);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				return VARIANT_EDEFAULT == null ? getVariant() != null : !VARIANT_EDEFAULT.equals(getVariant());
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION:
				return LOCATION_EDEFAULT == null ? getLocation() != null : !LOCATION_EDEFAULT.equals(getLocation());
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE:
				return basicGetPropertyFile() != null;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return basicGetMaster() != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public URI relativePath() {
		return getLocation();
	}

} //PropertyFileDescriptorImpl
