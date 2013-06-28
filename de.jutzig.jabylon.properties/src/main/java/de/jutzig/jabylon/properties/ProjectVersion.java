/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectVersion#getTemplate <em>Template</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion()
 * @model
 * @generated
 */
public interface ProjectVersion extends Resolvable<Project, ProjectLocale> {
    /**
     * Returns the value of the '<em><b>Template</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Template</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Template</em>' reference.
     * @see #setTemplate(ProjectLocale)
     * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectVersion_Template()
     * @model
     * @generated
     */
    ProjectLocale getTemplate();

    /**
     * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectVersion#getTemplate <em>Template</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Template</em>' reference.
     * @see #getTemplate()
     * @generated
     */
    void setTemplate(ProjectLocale value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    void fullScan(ScanConfiguration configuration);


    void fullScan(ScanConfiguration configuration, IProgressMonitor monitor);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model localeDataType="de.jutzig.jabylon.properties.Locale"
     * @generated
     */
    ProjectLocale getProjectLocale(Locale locale);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    void partialScan(ScanConfiguration configuration, PropertyFileDiff fileDiff);

} // ProjectVersion
