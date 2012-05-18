/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.Comment;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.ReviewState;
import de.jutzig.jabylon.properties.Severity;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Review</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getUser <em>User</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getComments <em>Comments</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getState <em>State</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getReviewType <em>Review Type</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getKey <em>Key</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ReviewImpl#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReviewImpl extends CDOObjectImpl implements Review {
	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final ReviewState STATE_EDEFAULT = ReviewState.OPEN;

	/**
	 * The default value of the '{@link #getReviewType() <em>Review Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewType()
	 * @generated
	 * @ordered
	 */
	protected static final String REVIEW_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final Severity SEVERITY_EDEFAULT = Severity.INFO;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.REVIEW;
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
	public String getMessage() {
		return (String)eDynamicGet(PropertiesPackage.REVIEW__MESSAGE, PropertiesPackage.Literals.REVIEW__MESSAGE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		eDynamicSet(PropertiesPackage.REVIEW__MESSAGE, PropertiesPackage.Literals.REVIEW__MESSAGE, newMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUser() {
		return (String)eDynamicGet(PropertiesPackage.REVIEW__USER, PropertiesPackage.Literals.REVIEW__USER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUser(String newUser) {
		eDynamicSet(PropertiesPackage.REVIEW__USER, PropertiesPackage.Literals.REVIEW__USER, newUser);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Comment> getComments() {
		return (EList<Comment>)eDynamicGet(PropertiesPackage.REVIEW__COMMENTS, PropertiesPackage.Literals.REVIEW__COMMENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewState getState() {
		return (ReviewState)eDynamicGet(PropertiesPackage.REVIEW__STATE, PropertiesPackage.Literals.REVIEW__STATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(ReviewState newState) {
		eDynamicSet(PropertiesPackage.REVIEW__STATE, PropertiesPackage.Literals.REVIEW__STATE, newState);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReviewType() {
		return (String)eDynamicGet(PropertiesPackage.REVIEW__REVIEW_TYPE, PropertiesPackage.Literals.REVIEW__REVIEW_TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewType(String newReviewType) {
		eDynamicSet(PropertiesPackage.REVIEW__REVIEW_TYPE, PropertiesPackage.Literals.REVIEW__REVIEW_TYPE, newReviewType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return (String)eDynamicGet(PropertiesPackage.REVIEW__KEY, PropertiesPackage.Literals.REVIEW__KEY, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		eDynamicSet(PropertiesPackage.REVIEW__KEY, PropertiesPackage.Literals.REVIEW__KEY, newKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Severity getSeverity() {
		return (Severity)eDynamicGet(PropertiesPackage.REVIEW__SEVERITY, PropertiesPackage.Literals.REVIEW__SEVERITY, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity(Severity newSeverity) {
		eDynamicSet(PropertiesPackage.REVIEW__SEVERITY, PropertiesPackage.Literals.REVIEW__SEVERITY, newSeverity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.REVIEW__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
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
			case PropertiesPackage.REVIEW__MESSAGE:
				return getMessage();
			case PropertiesPackage.REVIEW__USER:
				return getUser();
			case PropertiesPackage.REVIEW__COMMENTS:
				return getComments();
			case PropertiesPackage.REVIEW__STATE:
				return getState();
			case PropertiesPackage.REVIEW__REVIEW_TYPE:
				return getReviewType();
			case PropertiesPackage.REVIEW__KEY:
				return getKey();
			case PropertiesPackage.REVIEW__SEVERITY:
				return getSeverity();
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
			case PropertiesPackage.REVIEW__MESSAGE:
				setMessage((String)newValue);
				return;
			case PropertiesPackage.REVIEW__USER:
				setUser((String)newValue);
				return;
			case PropertiesPackage.REVIEW__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case PropertiesPackage.REVIEW__STATE:
				setState((ReviewState)newValue);
				return;
			case PropertiesPackage.REVIEW__REVIEW_TYPE:
				setReviewType((String)newValue);
				return;
			case PropertiesPackage.REVIEW__KEY:
				setKey((String)newValue);
				return;
			case PropertiesPackage.REVIEW__SEVERITY:
				setSeverity((Severity)newValue);
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
			case PropertiesPackage.REVIEW__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case PropertiesPackage.REVIEW__USER:
				setUser(USER_EDEFAULT);
				return;
			case PropertiesPackage.REVIEW__COMMENTS:
				getComments().clear();
				return;
			case PropertiesPackage.REVIEW__STATE:
				setState(STATE_EDEFAULT);
				return;
			case PropertiesPackage.REVIEW__REVIEW_TYPE:
				setReviewType(REVIEW_TYPE_EDEFAULT);
				return;
			case PropertiesPackage.REVIEW__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case PropertiesPackage.REVIEW__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
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
			case PropertiesPackage.REVIEW__MESSAGE:
				return MESSAGE_EDEFAULT == null ? getMessage() != null : !MESSAGE_EDEFAULT.equals(getMessage());
			case PropertiesPackage.REVIEW__USER:
				return USER_EDEFAULT == null ? getUser() != null : !USER_EDEFAULT.equals(getUser());
			case PropertiesPackage.REVIEW__COMMENTS:
				return !getComments().isEmpty();
			case PropertiesPackage.REVIEW__STATE:
				return getState() != STATE_EDEFAULT;
			case PropertiesPackage.REVIEW__REVIEW_TYPE:
				return REVIEW_TYPE_EDEFAULT == null ? getReviewType() != null : !REVIEW_TYPE_EDEFAULT.equals(getReviewType());
			case PropertiesPackage.REVIEW__KEY:
				return KEY_EDEFAULT == null ? getKey() != null : !KEY_EDEFAULT.equals(getKey());
			case PropertiesPackage.REVIEW__SEVERITY:
				return getSeverity() != SEVERITY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //ReviewImpl
