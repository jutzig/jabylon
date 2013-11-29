/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getMessage <em>Message</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getUser <em>User</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getComments <em>Comments</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getState <em>State</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getReviewType <em>Review Type</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getKey <em>Key</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getSeverity <em>Severity</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.Review#getCreated <em>Created</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Review extends CDOObject {
    /**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_Message()
	 * @model
	 * @generated
	 */
    String getMessage();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
    void setMessage(String value);

    /**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_User()
	 * @model
	 * @generated
	 */
    String getUser();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
    void setUser(String value);

    /**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.properties.Comment}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Comments</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_Comments()
	 * @model containment="true"
	 * @generated
	 */
    EList<Comment> getComments();

    /**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link de.jutzig.jabylon.properties.ReviewState}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>State</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see de.jutzig.jabylon.properties.ReviewState
	 * @see #setState(ReviewState)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_State()
	 * @model
	 * @generated
	 */
    ReviewState getState();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see de.jutzig.jabylon.properties.ReviewState
	 * @see #getState()
	 * @generated
	 */
    void setState(ReviewState value);

    /**
	 * Returns the value of the '<em><b>Review Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Review Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Type</em>' attribute.
	 * @see #setReviewType(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_ReviewType()
	 * @model
	 * @generated
	 */
    String getReviewType();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getReviewType <em>Review Type</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Type</em>' attribute.
	 * @see #getReviewType()
	 * @generated
	 */
    void setReviewType(String value);

    /**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_Key()
	 * @model
	 * @generated
	 */
    String getKey();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
    void setKey(String value);

    /**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link de.jutzig.jabylon.properties.Severity}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Severity</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see de.jutzig.jabylon.properties.Severity
	 * @see #setSeverity(Severity)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_Severity()
	 * @model
	 * @generated
	 */
    Severity getSeverity();

    /**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see de.jutzig.jabylon.properties.Severity
	 * @see #getSeverity()
	 * @generated
	 */
    void setSeverity(Severity value);

				/**
	 * Returns the value of the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created</em>' attribute.
	 * @see #setCreated(long)
	 * @see de.jutzig.jabylon.properties.PropertiesPackage#getReview_Created()
	 * @model
	 * @generated
	 */
	long getCreated();

				/**
	 * Sets the value of the '{@link de.jutzig.jabylon.properties.Review#getCreated <em>Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created</em>' attribute.
	 * @see #getCreated()
	 * @generated
	 */
	void setCreated(long value);

} // Review
