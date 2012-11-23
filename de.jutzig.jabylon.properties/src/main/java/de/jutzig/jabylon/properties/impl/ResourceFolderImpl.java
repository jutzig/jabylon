/**
 */
package de.jutzig.jabylon.properties.impl;

import org.eclipse.emf.ecore.EClass;

import de.jutzig.jabylon.properties.ProjectLocale;
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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ProjectLocale getProjectLocale() {
		@SuppressWarnings("rawtypes")
		Resolvable parent = this;
		while(parent!=null)
		{
			if (parent instanceof ProjectLocale) {
				ProjectLocale locale = (ProjectLocale) parent;
				return locale;
				
			}
			parent = parent.getParent();
		}
		return null;
	}


} //ResourceFolderImpl
