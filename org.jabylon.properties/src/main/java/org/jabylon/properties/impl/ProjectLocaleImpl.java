/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.impl;

import java.util.Collection;
import java.util.Locale;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Locale</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.ProjectLocaleImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ProjectLocaleImpl#getDescriptors <em>Descriptors</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ProjectLocaleImpl#getPropertyCount <em>Property Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectLocaleImpl extends ResolvableImpl<ProjectVersion, Resolvable<?, ?>> implements ProjectLocale {
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
	 * The default value of the '{@link #getPropertyCount() <em>Property Count</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPropertyCount()
	 * @generated
	 * @ordered
	 */
    protected static final int PROPERTY_COUNT_EDEFAULT = 0;

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
	 * This is specialized for the more specific type known in this context.
	 * @generated
	 */
	@Override
	public NotificationChain basicSetParent(ProjectVersion newParent, NotificationChain msgs) {
		return super.basicSetParent(newParent, msgs);
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
    public int getPropertyCount() {
		return (Integer)eDynamicGet(PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT, PropertiesPackage.Literals.PROJECT_LOCALE__PROPERTY_COUNT, true, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPropertyCount(int newPropertyCount) {
		eDynamicSet(PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT, PropertiesPackage.Literals.PROJECT_LOCALE__PROPERTY_COUNT, newPropertyCount);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public boolean isMaster() {
        return getParent()!=null && getParent().getTemplate()==this;
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
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDescriptors()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    @Override
    public int internalUpdatePercentComplete() {
        int translatedProps = 0;
        for (PropertyFileDescriptor descriptor : getDescriptors()) {
            translatedProps += descriptor.getKeys();
        }
        setPropertyCount(translatedProps);
        if(!isMaster())
        {
            int totalProps = getParent().getTemplate().getPropertyCount();
            return  Math.min(100, (int) Math.floor(((translatedProps/(double)totalProps)*100)));
        }
        else
            return 100;

    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return ((InternalEList<?>)getDescriptors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				return getLocale();
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return getDescriptors();
			case PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT:
				return getPropertyCount();
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
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				setLocale((Locale)newValue);
				return;
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				getDescriptors().clear();
				getDescriptors().addAll((Collection<? extends PropertyFileDescriptor>)newValue);
				return;
			case PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT:
				setPropertyCount((Integer)newValue);
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
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				setLocale(LOCALE_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				getDescriptors().clear();
				return;
			case PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT:
				setPropertyCount(PROPERTY_COUNT_EDEFAULT);
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
			case PropertiesPackage.PROJECT_LOCALE__LOCALE:
				return LOCALE_EDEFAULT == null ? getLocale() != null : !LOCALE_EDEFAULT.equals(getLocale());
			case PropertiesPackage.PROJECT_LOCALE__DESCRIPTORS:
				return !getDescriptors().isEmpty();
			case PropertiesPackage.PROJECT_LOCALE__PROPERTY_COUNT:
				return getPropertyCount() != PROPERTY_COUNT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

    @Override
    public URI relativePath() {
        return URI.createURI(""); //locales are just virtual,  not in the FS
    }


    @Override
    public String getName() {
        Locale locale = getLocale();
        if(locale==null)
            return "template";
        return locale.toString();
    }
} //ProjectLocaleImpl
