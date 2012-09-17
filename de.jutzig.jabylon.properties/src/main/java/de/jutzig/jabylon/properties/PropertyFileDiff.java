/**
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property File Diff</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDiff#getNewPath <em>New Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDiff#getOldPath <em>Old Path</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.PropertyFileDiff#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDiff()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PropertyFileDiff extends CDOObject {
	/**
	 * Returns the value of the '<em><b>New Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Path</em>' attribute.
	 * @see #setNewPath(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDiff_NewPath()
	 * @model
	 * @generated
	 */
	String getNewPath();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDiff#getNewPath <em>New Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Path</em>' attribute.
	 * @see #getNewPath()
	 * @generated
	 */
	void setNewPath(String value);

	/**
	 * Returns the value of the '<em><b>Old Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Path</em>' attribute.
	 * @see #setOldPath(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDiff_OldPath()
	 * @model
	 * @generated
	 */
	String getOldPath();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDiff#getOldPath <em>Old Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Path</em>' attribute.
	 * @see #getOldPath()
	 * @generated
	 */
	void setOldPath(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link de.jutzig.jabylon.properties.DiffKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see de.jutzig.jabylon.properties.DiffKind
	 * @see #setKind(DiffKind)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getPropertyFileDiff_Kind()
	 * @model
	 * @generated
	 */
	DiffKind getKind();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.PropertyFileDiff#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see de.jutzig.jabylon.properties.DiffKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(DiffKind value);

} // PropertyFileDiff
