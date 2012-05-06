/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.review;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property File Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.review.PropertyFileReview#getPropertyFile <em>Property File</em>}</li>
 *   <li>{@link de.jutzig.jabylon.review.PropertyFileReview#getReviews <em>Reviews</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.jutzig.jabylon.review.ReviewPackage#getPropertyFileReview()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PropertyFileReview extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Property File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property File</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property File</em>' reference.
	 * @see #setPropertyFile(PropertyFileDescriptor)
	 * @see de.jutzig.jabylon.review.ReviewPackage#getPropertyFileReview_PropertyFile()
	 * @model
	 * @generated
	 */
	PropertyFileDescriptor getPropertyFile();

	/**
	 * Sets the value of the '{@link de.jutzig.jabylon.review.PropertyFileReview#getPropertyFile <em>Property File</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property File</em>' reference.
	 * @see #getPropertyFile()
	 * @generated
	 */
	void setPropertyFile(PropertyFileDescriptor value);

	/**
	 * Returns the value of the '<em><b>Reviews</b></em>' containment reference list.
	 * The list contents are of type {@link de.jutzig.jabylon.review.Review}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviews</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviews</em>' containment reference list.
	 * @see de.jutzig.jabylon.review.ReviewPackage#getPropertyFileReview_Reviews()
	 * @model containment="true"
	 * @generated
	 */
	EList<Review> getReviews();

} // PropertyFileReview
