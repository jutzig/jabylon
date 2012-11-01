/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resolvable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Resolvable#getPercentComplete <em>Percent Complete</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Resolvable#getChildren <em>Children</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Resolvable#getParent <em>Parent</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Resolvable#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface Resolvable<P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Percent Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Percent Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Percent Complete</em>' attribute.
	 * @see #setPercentComplete(int)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable_PercentComplete()
	 * @model
	 * @generated
	 */
	int getPercentComplete();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Resolvable#getPercentComplete <em>Percent Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Percent Complete</em>' attribute.
	 * @see #getPercentComplete()
	 * @generated
	 */
	void setPercentComplete(int value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link C}.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.Resolvable#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable_Children()
	 * @see de.jutzig.jabylon.properties.Resolvable#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<C> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jutzig.jabylon.properties.Resolvable#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Resolvable)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable_Parent()
	 * @see de.jutzig.jabylon.properties.Resolvable#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
	P getParent();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Resolvable#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(P value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getResolvable_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Resolvable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	int updatePercentComplete();

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @model pathDataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
    Resolvable<?, ?> resolveChild(URI path);
    
    Resolvable<?, ?> resolveChild(List<String> path);

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="de.jutzig.jabylon.properties.URI"
	 * @generated
	 */
	URI absoluteFilePath();

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	C getChild(String name);

} // Resolvable
