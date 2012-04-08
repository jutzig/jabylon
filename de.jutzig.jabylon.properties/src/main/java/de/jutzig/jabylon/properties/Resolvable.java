/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resolvable</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface Resolvable extends CDOObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI fullPath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI relativePath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI absolutPath();

} // Resolvable
