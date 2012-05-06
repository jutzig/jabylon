/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.review.impl;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;

import de.jutzig.jabylon.review.PropertyFileReview;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.review.ReviewPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property File Review</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.review.impl.PropertyFileReviewImpl#getPropertyFile <em>Property File</em>}</li>
 *   <li>{@link de.jutzig.jabylon.review.impl.PropertyFileReviewImpl#getReviews <em>Reviews</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyFileReviewImpl extends CDOObjectImpl implements PropertyFileReview {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyFileReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReviewPackage.Literals.PROPERTY_FILE_REVIEW;
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
	 * @generated
	 */
	public PropertyFileDescriptor getPropertyFile() {
		return (PropertyFileDescriptor)eDynamicGet(ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE, ReviewPackage.Literals.PROPERTY_FILE_REVIEW__PROPERTY_FILE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyFileDescriptor basicGetPropertyFile() {
		return (PropertyFileDescriptor)eDynamicGet(ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE, ReviewPackage.Literals.PROPERTY_FILE_REVIEW__PROPERTY_FILE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyFile(PropertyFileDescriptor newPropertyFile) {
		eDynamicSet(ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE, ReviewPackage.Literals.PROPERTY_FILE_REVIEW__PROPERTY_FILE, newPropertyFile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Review> getReviews() {
		return (EList<Review>)eDynamicGet(ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS, ReviewPackage.Literals.PROPERTY_FILE_REVIEW__REVIEWS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS:
				return ((InternalEList<?>)getReviews()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE:
				if (resolve) return getPropertyFile();
				return basicGetPropertyFile();
			case ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS:
				return getReviews();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE:
				setPropertyFile((PropertyFileDescriptor)newValue);
				return;
			case ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS:
				getReviews().clear();
				getReviews().addAll((Collection<? extends Review>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE:
				setPropertyFile((PropertyFileDescriptor)null);
				return;
			case ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS:
				getReviews().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ReviewPackage.PROPERTY_FILE_REVIEW__PROPERTY_FILE:
				return basicGetPropertyFile() != null;
			case ReviewPackage.PROPERTY_FILE_REVIEW__REVIEWS:
				return !getReviews().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PropertyFileReviewImpl
