/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.jabylon.properties.*;
import org.jabylon.properties.Comment;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.PropertiesPackage
 * @generated
 */
public class PropertiesAdapterFactory extends AdapterFactoryImpl {
    /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static PropertiesPackage modelPackage;

    /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public PropertiesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PropertiesPackage.eINSTANCE;
		}
	}

    /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
    @Override
    public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

    /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected PropertiesSwitch<Adapter> modelSwitch =
        new PropertiesSwitch<Adapter>() {
			@Override
			public Adapter casePropertyFile(PropertyFile object) {
				return createPropertyFileAdapter();
			}
			@Override
			public Adapter caseProperty(Property object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter casePropertyFileDescriptor(PropertyFileDescriptor object) {
				return createPropertyFileDescriptorAdapter();
			}
			@Override
			public Adapter caseProject(Project object) {
				return createProjectAdapter();
			}
			@Override
			public Adapter caseProjectVersion(ProjectVersion object) {
				return createProjectVersionAdapter();
			}
			@Override
			public Adapter caseProjectLocale(ProjectLocale object) {
				return createProjectLocaleAdapter();
			}
			@Override
			public Adapter caseWorkspace(Workspace object) {
				return createWorkspaceAdapter();
			}
			@Override
			public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> Adapter caseResolvable(Resolvable<P, C> object) {
				return createResolvableAdapter();
			}
			@Override
			public Adapter caseScanConfiguration(ScanConfiguration object) {
				return createScanConfigurationAdapter();
			}
			@Override
			public Adapter caseReview(Review object) {
				return createReviewAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter casePropertyFileDiff(PropertyFileDiff object) {
				return createPropertyFileDiffAdapter();
			}
			@Override
			public Adapter caseResourceFolder(ResourceFolder object) {
				return createResourceFolderAdapter();
			}
			@Override
			public Adapter casePropertyAnnotation(PropertyAnnotation object) {
				return createPropertyAnnotationAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

    /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
    @Override
    public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.PropertyFile <em>Property File</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.PropertyFile
	 * @generated
	 */
    public Adapter createPropertyFileAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Property
	 * @generated
	 */
    public Adapter createPropertyAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.PropertyFileDescriptor <em>Property File Descriptor</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.PropertyFileDescriptor
	 * @generated
	 */
    public Adapter createPropertyFileDescriptorAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Project
	 * @generated
	 */
    public Adapter createProjectAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.ProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.ProjectVersion
	 * @generated
	 */
    public Adapter createProjectVersionAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.ProjectLocale <em>Project Locale</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.ProjectLocale
	 * @generated
	 */
    public Adapter createProjectLocaleAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Workspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Workspace
	 * @generated
	 */
    public Adapter createWorkspaceAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Resolvable <em>Resolvable</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Resolvable
	 * @generated
	 */
    public Adapter createResolvableAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.ScanConfiguration <em>Scan Configuration</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.ScanConfiguration
	 * @generated
	 */
    public Adapter createScanConfigurationAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Review <em>Review</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Review
	 * @generated
	 */
    public Adapter createReviewAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.Comment
	 * @generated
	 */
    public Adapter createCommentAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.PropertyFileDiff <em>Property File Diff</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.PropertyFileDiff
	 * @generated
	 */
    public Adapter createPropertyFileDiffAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.ResourceFolder <em>Resource Folder</em>}'.
	 * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.ResourceFolder
	 * @generated
	 */
    public Adapter createResourceFolderAdapter() {
		return null;
	}

    /**
	 * Creates a new adapter for an object of class '{@link org.jabylon.properties.PropertyAnnotation <em>Property Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.jabylon.properties.PropertyAnnotation
	 * @generated
	 */
	public Adapter createPropertyAnnotationAdapter() {
		return null;
	}

				/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
    public Adapter createEObjectAdapter() {
		return null;
	}

} //PropertiesAdapterFactory
