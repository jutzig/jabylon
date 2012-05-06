/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.review;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.jutzig.jabylon.review.ReviewFactory
 * @model kind="package"
 * @generated
 */
public interface ReviewPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "review";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://jutzig.de/jabylon/review";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "review";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReviewPackage eINSTANCE = de.jutzig.jabylon.review.impl.ReviewPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.review.impl.PropertyFileReviewImpl <em>Property File Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.review.impl.PropertyFileReviewImpl
	 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getPropertyFileReview()
	 * @generated
	 */
	int PROPERTY_FILE_REVIEW = 0;

	/**
	 * The feature id for the '<em><b>Property File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_REVIEW__PROPERTY_FILE = 0;

	/**
	 * The feature id for the '<em><b>Reviews</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_REVIEW__REVIEWS = 1;

	/**
	 * The number of structural features of the '<em>Property File Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FILE_REVIEW_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.review.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.review.impl.CommentImpl
	 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 1;

	/**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__USER = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__MESSAGE = 1;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.review.impl.ReviewImpl <em>Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.review.impl.ReviewImpl
	 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getReview()
	 * @generated
	 */
	int REVIEW = 2;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__MESSAGE = 0;

	/**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__USER = 1;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__COMMENTS = 2;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__STATE = 3;

	/**
	 * The feature id for the '<em><b>Review Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__REVIEW_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__KEY = 5;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__SEVERITY = 6;

	/**
	 * The number of structural features of the '<em>Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.review.ReviewState <em>State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.review.ReviewState
	 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getReviewState()
	 * @generated
	 */
	int REVIEW_STATE = 3;


	/**
	 * The meta object id for the '{@link de.jutzig.jabylon.review.Severity <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.jutzig.jabylon.review.Severity
	 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 4;


	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.review.PropertyFileReview <em>Property File Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property File Review</em>'.
	 * @see de.jutzig.jabylon.review.PropertyFileReview
	 * @generated
	 */
	EClass getPropertyFileReview();

	/**
	 * Returns the meta object for the reference '{@link de.jutzig.jabylon.review.PropertyFileReview#getPropertyFile <em>Property File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property File</em>'.
	 * @see de.jutzig.jabylon.review.PropertyFileReview#getPropertyFile()
	 * @see #getPropertyFileReview()
	 * @generated
	 */
	EReference getPropertyFileReview_PropertyFile();

	/**
	 * Returns the meta object for the containment reference list '{@link de.jutzig.jabylon.review.PropertyFileReview#getReviews <em>Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reviews</em>'.
	 * @see de.jutzig.jabylon.review.PropertyFileReview#getReviews()
	 * @see #getPropertyFileReview()
	 * @generated
	 */
	EReference getPropertyFileReview_Reviews();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.review.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see de.jutzig.jabylon.review.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Comment#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see de.jutzig.jabylon.review.Comment#getUser()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_User();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Comment#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.jutzig.jabylon.review.Comment#getMessage()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Message();

	/**
	 * Returns the meta object for class '{@link de.jutzig.jabylon.review.Review <em>Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review</em>'.
	 * @see de.jutzig.jabylon.review.Review
	 * @generated
	 */
	EClass getReview();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see de.jutzig.jabylon.review.Review#getMessage()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_Message();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see de.jutzig.jabylon.review.Review#getUser()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_User();

	/**
	 * Returns the meta object for the containment reference '{@link de.jutzig.jabylon.review.Review#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comments</em>'.
	 * @see de.jutzig.jabylon.review.Review#getComments()
	 * @see #getReview()
	 * @generated
	 */
	EReference getReview_Comments();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see de.jutzig.jabylon.review.Review#getState()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_State();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getReviewType <em>Review Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Review Type</em>'.
	 * @see de.jutzig.jabylon.review.Review#getReviewType()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_ReviewType();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see de.jutzig.jabylon.review.Review#getKey()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_Key();

	/**
	 * Returns the meta object for the attribute '{@link de.jutzig.jabylon.review.Review#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see de.jutzig.jabylon.review.Review#getSeverity()
	 * @see #getReview()
	 * @generated
	 */
	EAttribute getReview_Severity();

	/**
	 * Returns the meta object for enum '{@link de.jutzig.jabylon.review.ReviewState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>State</em>'.
	 * @see de.jutzig.jabylon.review.ReviewState
	 * @generated
	 */
	EEnum getReviewState();

	/**
	 * Returns the meta object for enum '{@link de.jutzig.jabylon.review.Severity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see de.jutzig.jabylon.review.Severity
	 * @generated
	 */
	EEnum getSeverity();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReviewFactory getReviewFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.review.impl.PropertyFileReviewImpl <em>Property File Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.review.impl.PropertyFileReviewImpl
		 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getPropertyFileReview()
		 * @generated
		 */
		EClass PROPERTY_FILE_REVIEW = eINSTANCE.getPropertyFileReview();

		/**
		 * The meta object literal for the '<em><b>Property File</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_FILE_REVIEW__PROPERTY_FILE = eINSTANCE.getPropertyFileReview_PropertyFile();

		/**
		 * The meta object literal for the '<em><b>Reviews</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_FILE_REVIEW__REVIEWS = eINSTANCE.getPropertyFileReview_Reviews();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.review.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.review.impl.CommentImpl
		 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__USER = eINSTANCE.getComment_User();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__MESSAGE = eINSTANCE.getComment_Message();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.review.impl.ReviewImpl <em>Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.review.impl.ReviewImpl
		 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getReview()
		 * @generated
		 */
		EClass REVIEW = eINSTANCE.getReview();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__MESSAGE = eINSTANCE.getReview_Message();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__USER = eINSTANCE.getReview_User();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW__COMMENTS = eINSTANCE.getReview_Comments();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__STATE = eINSTANCE.getReview_State();

		/**
		 * The meta object literal for the '<em><b>Review Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__REVIEW_TYPE = eINSTANCE.getReview_ReviewType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__KEY = eINSTANCE.getReview_Key();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW__SEVERITY = eINSTANCE.getReview_Severity();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.review.ReviewState <em>State</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.review.ReviewState
		 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getReviewState()
		 * @generated
		 */
		EEnum REVIEW_STATE = eINSTANCE.getReviewState();

		/**
		 * The meta object literal for the '{@link de.jutzig.jabylon.review.Severity <em>Severity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.jutzig.jabylon.review.Severity
		 * @see de.jutzig.jabylon.review.impl.ReviewPackageImpl#getSeverity()
		 * @generated
		 */
		EEnum SEVERITY = eINSTANCE.getSeverity();

	}

} //ReviewPackage
