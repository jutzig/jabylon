/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;

import de.jutzig.jabylon.properties.Comment;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.util.PropertiesResourceImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property File Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getProjectLocale <em>Project Locale</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getKeys <em>Keys</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getReviews <em>Reviews</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getLastModification <em>Last Modification</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.PropertyFileDescriptorImpl#getDerivedDescriptors <em>Derived Descriptors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyFileDescriptorImpl extends ResolvableImpl<Resolvable<?, ?>, PropertyFileDescriptor> implements PropertyFileDescriptor {
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
	 * The default value of the '{@link #getKeys() <em>Keys</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeys()
	 * @generated
	 * @ordered
	 */
	protected static final int KEYS_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected static final long LAST_MODIFIED_EDEFAULT = 0L;

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
		ProjectLocale locale = getProjectLocale();
		if(locale==null)
			return false;
		return locale.isMaster();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyFile loadProperties() {
		URI path = absolutPath();

		PropertiesResourceImpl resource = new PropertiesResourceImpl(path);

		Map<String, Object> options = new HashMap<String, Object>();
		if(getProjectLocale()!=null && getProjectLocale().getParent()!=null &&
			getProjectLocale().getParent().getParent()!=null) {
			options.put(PropertiesResourceImpl.OPTION_FILEMODE, getProjectLocale().getParent().getParent().getPropertyType());
		}

		try {
			resource.load(options);
		} catch (FileNotFoundException e)
		{
			//The file does not exist, create a new one.
			//TODO: log this
			return PropertiesFactory.eINSTANCE.createPropertyFile();
		}
		 catch (IOException e) {
			throw new RuntimeException(e);
		}
		return (PropertyFile) resource.getContents().get(0);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyFile loadProperties(InputStream in) {
		URI path = absolutPath();

		PropertiesResourceImpl resource = new PropertiesResourceImpl(path);

		Map<String, Object> options = new HashMap<String, Object>();
		if(getProjectLocale()!=null && getProjectLocale().getParent()!=null &&
			getProjectLocale().getParent().getParent()!=null) {
			options.put(PropertiesResourceImpl.OPTION_FILEMODE, getProjectLocale().getParent().getParent().getPropertyType());
		}

		try {
			resource.load(in,options);
		} catch (FileNotFoundException e)
		{
			//The file does not exist, create a new one.
			//TODO: log this
			return PropertiesFactory.eINSTANCE.createPropertyFile();
		}
		 catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (PropertyFile) resource.getContents().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				PropertyFileDescriptor master = getMaster();
				if (master != null)
					msgs = ((InternalEObject)master).eInverseRemove(this, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, PropertyFileDescriptor.class, msgs);
				return basicSetMaster((PropertyFileDescriptor)otherEnd, msgs);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				ProjectLocale projectLocale = getProjectLocale();
				if (projectLocale != null)
					msgs = ((InternalEObject)projectLocale).eInverseRemove(this, PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS, ProjectLocale.class, msgs);
				return basicSetProjectLocale((ProjectLocale)otherEnd, msgs);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				PropertyFileDescriptor derivedDescriptors = basicGetDerivedDescriptors();
				if (derivedDescriptors != null)
					msgs = ((InternalEObject)derivedDescriptors).eInverseRemove(this, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER, PropertyFileDescriptor.class, msgs);
				return basicSetDerivedDescriptors((PropertyFileDescriptor)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void computeLocation() {
		if(isMaster() || getMaster()==null)
			return;
		Locale locale = getVariant();
		URI location = getMaster().getLocation();
		String filename = location.lastSegment();
		String extension = location.fileExtension();

		if(extension!=null)
		{
			filename = filename.substring(0,filename.length()-extension.length()-1);
			
			//if the master has a locale as well (i.e. messages_en_EN.properties) we must remove the suffix
			Locale masterLocale = getMaster().getVariant();
			if(masterLocale!=null)
			{
				filename = filename.substring(0, filename.length() - (masterLocale.toString().length()+1));
			}
			
			filename += "_";
			filename += locale.toString();
			filename += ".";
			filename += extension;
		}
		setLocation(location.trimSegments(1).appendSegment(filename));
	}



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return basicSetMaster(null, msgs);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				return basicSetProjectLocale(null, msgs);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS:
				return ((InternalEList<?>)getReviews()).basicRemove(otherEnd, msgs);
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				return basicSetDerivedDescriptors(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
	public NotificationChain basicSetMaster(PropertyFileDescriptor newMaster, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newMaster, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER, msgs);
		return msgs;
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
	public ProjectLocale getProjectLocale() {
		return (ProjectLocale)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjectLocale(ProjectLocale newProjectLocale, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newProjectLocale, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectLocale(ProjectLocale newProjectLocale) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE, newProjectLocale);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getKeys() {
		return (Integer)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__KEYS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeys(int newKeys) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__KEYS, newKeys);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Review> getReviews() {
		return (EList<Review>)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__REVIEWS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLastModified() {
		return (Long)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModified(long newLastModified) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED, newLastModified);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment getLastModification() {
		return (Comment)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModification(Comment newLastModification) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION, newLastModification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor getDerivedDescriptors() {
		return (PropertyFileDescriptor)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor basicGetDerivedDescriptors() {
		return (PropertyFileDescriptor)eDynamicGet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDerivedDescriptors(PropertyFileDescriptor newDerivedDescriptors, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newDerivedDescriptors, PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDerivedDescriptors(PropertyFileDescriptor newDerivedDescriptors) {
		eDynamicSet(PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS, newDerivedDescriptors);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public int internalUpdatePercentComplete() {
		if(isMaster())
			return 100;
		PropertyFileDescriptor master = getMaster();
		int keys = master.getKeys();
		int translated = getKeys();
		return (int) Math.min(100, Math.floor(((translated/(double)keys)*100)));
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return getMaster();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				return getProjectLocale();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS:
				return getKeys();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS:
				return getReviews();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED:
				return getLastModified();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION:
				return getLastModification();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				if (resolve) return getDerivedDescriptors();
				return basicGetDerivedDescriptors();
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__VARIANT:
				setVariant((Locale)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LOCATION:
				setLocation((URI)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				setMaster((PropertyFileDescriptor)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				setProjectLocale((ProjectLocale)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS:
				setKeys((Integer)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS:
				getReviews().clear();
				getReviews().addAll((Collection<? extends Review>)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED:
				setLastModified((Long)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION:
				setLastModification((Comment)newValue);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				setDerivedDescriptors((PropertyFileDescriptor)newValue);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				setProjectLocale((ProjectLocale)null);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS:
				setKeys(KEYS_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS:
				getReviews().clear();
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED:
				setLastModified(LAST_MODIFIED_EDEFAULT);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION:
				setLastModification((Comment)null);
				return;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				setDerivedDescriptors((PropertyFileDescriptor)null);
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
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__MASTER:
				return getMaster() != null;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__PROJECT_LOCALE:
				return getProjectLocale() != null;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__KEYS:
				return getKeys() != KEYS_EDEFAULT;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__REVIEWS:
				return !getReviews().isEmpty();
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFIED:
				return getLastModified() != LAST_MODIFIED_EDEFAULT;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__LAST_MODIFICATION:
				return getLastModification() != null;
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR__DERIVED_DESCRIPTORS:
				return basicGetDerivedDescriptors() != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public URI relativePath() {
		return getLocation();
	}

} //PropertyFileDescriptorImpl
