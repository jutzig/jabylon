/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties.impl;

import java.util.Arrays;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.ScanConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scan Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.jabylon.properties.impl.ScanConfigurationImpl#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ScanConfigurationImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ScanConfigurationImpl#getMasterLocale <em>Master Locale</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ScanConfigurationImpl#getInclude <em>Include</em>}</li>
 *   <li>{@link org.jabylon.properties.impl.ScanConfigurationImpl#getExclude <em>Exclude</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScanConfigurationImpl extends CDOObjectImpl implements ScanConfiguration {
    /**
     * The default value of the '{@link #getMasterLocale() <em>Master Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMasterLocale()
     * @generated
     * @ordered
     */
    protected static final String MASTER_LOCALE_EDEFAULT = null;

    /**
     * The default value of the '{@link #getInclude() <em>Include</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInclude()
     * @generated NOT
     * @ordered
     */
    protected static final String INCLUDE_EDEFAULT = "**/*.properties";

    /**
     * The default value of the '{@link #getExclude() <em>Exclude</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExclude()
     * @generated NOT
     * @ordered
     */
    protected static final String EXCLUDE_EDEFAULT = "**/build.properties\n**/.git";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScanConfigurationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PropertiesPackage.Literals.SCAN_CONFIGURATION;
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
     * @generated NOT
     */
    public EList<String> getExcludes() {
        EList<String> excludes = new BasicEList<String>();
        if(getExclude()!=null && getExclude().length()>0)
        {
            excludes = splitString(getExclude());
        }
        return excludes;

    }

    private EList<String> splitString(String input) {
        EList<String> splittedList = new BasicEList<String>();
        String[] split;
        if(input.indexOf("\n")!=-1)
            split = input.split("\n");
        else
            split = input.split("\r");

        splittedList.addAll(Lists.transform(Arrays.asList(split), new Function<String, String>() {
            public String apply(String input) {
                return input.trim();
            }
        }));

        return splittedList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public EList<String> getIncludes() {
        EList<String> includes = new BasicEList<String>();
        if(getInclude()!=null)
        {
            includes = splitString(getInclude());
        }
        return includes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMasterLocale() {
        return (String)eDynamicGet(PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE, PropertiesPackage.Literals.SCAN_CONFIGURATION__MASTER_LOCALE, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMasterLocale(String newMasterLocale) {
        eDynamicSet(PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE, PropertiesPackage.Literals.SCAN_CONFIGURATION__MASTER_LOCALE, newMasterLocale);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getInclude() {
        return (String)eDynamicGet(PropertiesPackage.SCAN_CONFIGURATION__INCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__INCLUDE, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInclude(String newInclude) {
        eDynamicSet(PropertiesPackage.SCAN_CONFIGURATION__INCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__INCLUDE, newInclude);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExclude() {
        return (String)eDynamicGet(PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__EXCLUDE, true, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExclude(String newExclude) {
        eDynamicSet(PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE, PropertiesPackage.Literals.SCAN_CONFIGURATION__EXCLUDE, newExclude);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDES:
                return getExcludes();
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDES:
                return getIncludes();
            case PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE:
                return getMasterLocale();
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDE:
                return getInclude();
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE:
                return getExclude();
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
            case PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE:
                setMasterLocale((String)newValue);
                return;
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDE:
                setInclude((String)newValue);
                return;
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE:
                setExclude((String)newValue);
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
            case PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE:
                setMasterLocale(MASTER_LOCALE_EDEFAULT);
                return;
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDE:
                setInclude(INCLUDE_EDEFAULT);
                return;
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE:
                setExclude(EXCLUDE_EDEFAULT);
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
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDES:
                return !getExcludes().isEmpty();
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDES:
                return !getIncludes().isEmpty();
            case PropertiesPackage.SCAN_CONFIGURATION__MASTER_LOCALE:
                return MASTER_LOCALE_EDEFAULT == null ? getMasterLocale() != null : !MASTER_LOCALE_EDEFAULT.equals(getMasterLocale());
            case PropertiesPackage.SCAN_CONFIGURATION__INCLUDE:
                return INCLUDE_EDEFAULT == null ? getInclude() != null : !INCLUDE_EDEFAULT.equals(getInclude());
            case PropertiesPackage.SCAN_CONFIGURATION__EXCLUDE:
                return EXCLUDE_EDEFAULT == null ? getExclude() != null : !EXCLUDE_EDEFAULT.equals(getExclude());
        }
        return super.eIsSet(featureID);
    }

} //ScanConfigurationImpl
