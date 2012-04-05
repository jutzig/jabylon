/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Stats</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectStats#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.ProjectStats#getTotal <em>Total</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectStats()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProjectStats extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Translated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translated</em>' attribute.
	 * @see #setTranslated(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectStats_Translated()
	 * @model
	 * @generated
	 */
	int getTranslated();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectStats#getTranslated <em>Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translated</em>' attribute.
	 * @see #getTranslated()
	 * @generated
	 */
	void setTranslated(int value);

	/**
	 * Returns the value of the '<em><b>Total</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total</em>' attribute.
	 * @see #setTotal(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getProjectStats_Total()
	 * @model
	 * @generated
	 */
	int getTotal();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.ProjectStats#getTotal <em>Total</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total</em>' attribute.
	 * @see #getTotal()
	 * @generated
	 */
	void setTotal(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	int getPercentComplete();

} // ProjectStats
