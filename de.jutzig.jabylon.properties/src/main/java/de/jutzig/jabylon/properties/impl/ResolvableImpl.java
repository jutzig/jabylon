/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resolvable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class ResolvableImpl extends CDOObjectImpl implements Resolvable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResolvableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.RESOLVABLE;
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
	public URI fullPath() {
		EObject parent = eContainer();
		while(parent!=null)
		{
			if (parent instanceof Resolvable) {
				Resolvable resolvable = (Resolvable) parent;
				return resolvable.fullPath().appendSegments(relativePath().segments());
			}
		}
		return URI.createURI("");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract URI relativePath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI absolutPath() {
		EObject object = this;
		while((object!=null) && !(object instanceof Workspace))
			object = object.eContainer();
		
		if (object instanceof Workspace) {
			Workspace workspace = (Workspace) object;
			URI root = workspace.getRoot();
			if(root==null)
				return null;
			if(root.isRelative())
			{
				File f = new File("");
				URI workingDir = URI.createFileURI(f.getAbsolutePath());
				root = workingDir.appendSegments(root.segments());
			}
			return root.appendSegments(relativePath().segments());
		}
		return null;
		
	}

} //ResolvableImpl
