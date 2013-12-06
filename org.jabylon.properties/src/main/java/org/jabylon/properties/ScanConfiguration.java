/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scan Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getMasterLocale <em>Master Locale</em>}</li>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getInclude <em>Include</em>}</li>
 *   <li>{@link org.jabylon.properties.ScanConfiguration#getExclude <em>Exclude</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ScanConfiguration extends CDOObject {
    /**
     * Returns the value of the '<em><b>Excludes</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Excludes</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Excludes</em>' attribute list.
     * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration_Excludes()
     * @model default="** /.git\\n** /build.properties" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<String> getExcludes();

    /**
     * Returns the value of the '<em><b>Includes</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Includes</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Includes</em>' attribute list.
     * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration_Includes()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    EList<String> getIncludes();

    /**
     * Returns the value of the '<em><b>Master Locale</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Master Locale</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Master Locale</em>' attribute.
     * @see #setMasterLocale(String)
     * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration_MasterLocale()
     * @model
     * @generated
     */
    String getMasterLocale();

    /**
     * Sets the value of the '{@link org.jabylon.properties.ScanConfiguration#getMasterLocale <em>Master Locale</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Master Locale</em>' attribute.
     * @see #getMasterLocale()
     * @generated
     */
    void setMasterLocale(String value);

    /**
     * Returns the value of the '<em><b>Include</b></em>' attribute.
     * The default value is <code>"** / *.properties"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Include</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Include</em>' attribute.
     * @see #setInclude(String)
     * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration_Include()
     * @model default="** / *.properties"
     * @generated
     */
    String getInclude();

    /**
     * Sets the value of the '{@link org.jabylon.properties.ScanConfiguration#getInclude <em>Include</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Include</em>' attribute.
     * @see #getInclude()
     * @generated
     */
    void setInclude(String value);

    /**
     * Returns the value of the '<em><b>Exclude</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Exclude</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Exclude</em>' attribute.
     * @see #setExclude(String)
     * @see org.jabylon.properties.PropertiesPackage#getScanConfiguration_Exclude()
     * @model default=""
     * @generated
     */
    String getExclude();

    /**
     * Sets the value of the '{@link org.jabylon.properties.ScanConfiguration#getExclude <em>Exclude</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Exclude</em>' attribute.
     * @see #getExclude()
     * @generated
     */
    void setExclude(String value);

} // ScanConfiguration
