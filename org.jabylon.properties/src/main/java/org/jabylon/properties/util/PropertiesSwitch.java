/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.jabylon.properties.*;
import org.jabylon.properties.Comment;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyFileDiff;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.Review;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.Workspace;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage
 * @generated
 */
public class PropertiesSwitch<T> extends Switch<T> {
    /**
	 * The cached model package
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static PropertiesPackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PropertiesSwitch() {
		if (modelPackage == null) {
			modelPackage = PropertiesPackage.eINSTANCE;
		}
	}

    /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PropertiesPackage.PROPERTY_FILE: {
				PropertyFile propertyFile = (PropertyFile)theEObject;
				T result = casePropertyFile(propertyFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROPERTY: {
				Property property = (Property)theEObject;
				T result = caseProperty(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROPERTY_FILE_DESCRIPTOR: {
				PropertyFileDescriptor propertyFileDescriptor = (PropertyFileDescriptor)theEObject;
				T result = casePropertyFileDescriptor(propertyFileDescriptor);
				if (result == null) result = caseResolvable(propertyFileDescriptor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROJECT: {
				Project project = (Project)theEObject;
				T result = caseProject(project);
				if (result == null) result = caseResolvable(project);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROJECT_VERSION: {
				ProjectVersion projectVersion = (ProjectVersion)theEObject;
				T result = caseProjectVersion(projectVersion);
				if (result == null) result = caseResolvable(projectVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROJECT_LOCALE: {
				ProjectLocale projectLocale = (ProjectLocale)theEObject;
				T result = caseProjectLocale(projectLocale);
				if (result == null) result = caseResolvable(projectLocale);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.WORKSPACE: {
				Workspace workspace = (Workspace)theEObject;
				T result = caseWorkspace(workspace);
				if (result == null) result = caseResolvable(workspace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.RESOLVABLE: {
				Resolvable<?, ?> resolvable = (Resolvable<?, ?>)theEObject;
				T result = caseResolvable(resolvable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.SCAN_CONFIGURATION: {
				ScanConfiguration scanConfiguration = (ScanConfiguration)theEObject;
				T result = caseScanConfiguration(scanConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.REVIEW: {
				Review review = (Review)theEObject;
				T result = caseReview(review);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.PROPERTY_FILE_DIFF: {
				PropertyFileDiff propertyFileDiff = (PropertyFileDiff)theEObject;
				T result = casePropertyFileDiff(propertyFileDiff);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PropertiesPackage.RESOURCE_FOLDER: {
				ResourceFolder resourceFolder = (ResourceFolder)theEObject;
				T result = caseResourceFolder(resourceFolder);
				if (result == null) result = caseResolvable(resourceFolder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Property File</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePropertyFile(PropertyFile object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseProperty(Property object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Property File Descriptor</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property File Descriptor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePropertyFileDescriptor(PropertyFileDescriptor object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Project</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseProject(Project object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Project Version</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseProjectVersion(ProjectVersion object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Project Locale</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Project Locale</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseProjectLocale(ProjectLocale object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Workspace</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Workspace</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseWorkspace(Workspace object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Resolvable</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resolvable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> T caseResolvable(Resolvable<P, C> object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Scan Configuration</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scan Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseScanConfiguration(ScanConfiguration object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Review</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseReview(Review object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseComment(Comment object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Property File Diff</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property File Diff</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePropertyFileDiff(PropertyFileDiff object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Resource Folder</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseResourceFolder(ResourceFolder object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    @Override
    public T defaultCase(EObject object) {
		return null;
	}

} //PropertiesSwitch
