/**
 */
package de.jutzig.jabylon.properties.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ResourceFolder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ResourceFolderImpl extends ResolvableImpl<Resolvable<?, ?>, Resolvable<?, ?>> implements ResourceFolder {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceFolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.RESOURCE_FOLDER;
	}

	@Override
	public URI relativePath() {
		return URI.createURI(getName());
	}

} //ResourceFolderImpl
