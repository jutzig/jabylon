/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

import java.util.Locale;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getBag <em>Bag</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#isMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getPropertyFile <em>Property File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyFileDescriptorImpl extends EObjectImpl implements PropertyFileDescriptor {
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
	 * The cached value of the '{@link #getVariant() <em>Variant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected Locale variant = VARIANT_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isMaster() <em>Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMaster()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MASTER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getPropertyFile() <em>Property File</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyFile()
	 * @generated
	 * @ordered
	 */
	protected PropertyFile propertyFile;

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
		return variant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariant(Locale newVariant) {
		Locale oldVariant = variant;
		variant = newVariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT, oldVariant, variant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyBag getBag() {
		if (eContainerFeatureID() != PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG) return null;
		return (PropertyBag)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBag(PropertyBag newBag, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newBag, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBag(PropertyBag newBag) {
		if (newBag != eInternalContainer() || (eContainerFeatureID() != PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG && newBag != null)) {
			if (EcoreUtil.isAncestor(this, newBag))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newBag != null)
				msgs = ((InternalEObject)newBag).eInverseAdd(this, PropertiesPackage.PROPERTY_BAG__DESCRIPTORS, PropertyBag.class, msgs);
			msgs = basicSetBag(newBag, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG, newBag, newBag));
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
	 * @generated NOT
	 */
	public PropertyFile getPropertyFile() {
		propertyFile = basicGetPropertyFile();
		if (propertyFile != null && propertyFile.eIsProxy()) {
			InternalEObject oldPropertyFile = (InternalEObject)propertyFile;
			propertyFile = (PropertyFile)eResolveProxy(oldPropertyFile);
			if (propertyFile != oldPropertyFile) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE, oldPropertyFile, propertyFile));
			}
		}
		return propertyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyFile basicGetPropertyFile() {
		if(propertyFile==null && getBag()!=null)
		{
			URI path = getBag().getFullPath();
			path = path.appendSegment(getName());
			Resource resource = eResource().getResourceSet().getResource(path, true);
			if(resource!=null && resource.getContents().size()>0)
				propertyFile = (PropertyFile) resource.getContents().get(0);
		}
		return propertyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetBag((PropertyBag)otherEnd, msgs);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				return basicSetBag(null, msgs);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				return eInternalContainer().eInverseRemove(this, PropertiesPackage.PROPERTY_BAG__DESCRIPTORS, PropertyBag.class, msgs);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				return getVariant();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__NAME:
				return getName();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				return getBag();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return isMaster();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE:
				if (resolve) return getPropertyFile();
				return basicGetPropertyFile();
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__NAME:
				setName((String)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				setBag((PropertyBag)newValue);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				setBag((PropertyBag)null);
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
				return VARIANT_EDEFAULT == null ? variant != null : !VARIANT_EDEFAULT.equals(variant);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__BAG:
				return getBag() != null;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return isMaster() != MASTER_EDEFAULT;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROPERTY_FILE:
				return propertyFile != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (variant: ");
		result.append(variant);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PropertyFileDescriptorImpl
