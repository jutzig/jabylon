/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import de.jutzig.jabylon.properties.ProjectStats;
import de.jutzig.jabylon.properties.PropertiesPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Stats</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectStatsImpl#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectStatsImpl#getTotal <em>Total</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectStatsImpl extends CDOObjectImpl implements ProjectStats {
	/**
	 * The default value of the '{@link #getTranslated() <em>Translated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTranslated()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSLATED_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getTotal() <em>Total</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotal()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectStatsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT_STATS;
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
	public int getTranslated() {
		return (Integer)eDynamicGet(PropertiesPackage.PROJECT_STATS__TRANSLATED, PropertiesPackage.Literals.PROJECT_STATS__TRANSLATED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslated(int newTranslated) {
		eDynamicSet(PropertiesPackage.PROJECT_STATS__TRANSLATED, PropertiesPackage.Literals.PROJECT_STATS__TRANSLATED, newTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotal() {
		return (Integer)eDynamicGet(PropertiesPackage.PROJECT_STATS__TOTAL, PropertiesPackage.Literals.PROJECT_STATS__TOTAL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotal(int newTotal) {
		eDynamicSet(PropertiesPackage.PROJECT_STATS__TOTAL, PropertiesPackage.Literals.PROJECT_STATS__TOTAL, newTotal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPercentComplete() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_STATS__TRANSLATED:
				return getTranslated();
			case PropertiesPackage.PROJECT_STATS__TOTAL:
				return getTotal();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_STATS__TRANSLATED:
				setTranslated((Integer)newValue);
				return;
			case PropertiesPackage.PROJECT_STATS__TOTAL:
				setTotal((Integer)newValue);
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
			case PropertiesPackage.PROJECT_STATS__TRANSLATED:
				setTranslated(TRANSLATED_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_STATS__TOTAL:
				setTotal(TOTAL_EDEFAULT);
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
			case PropertiesPackage.PROJECT_STATS__TRANSLATED:
				return getTranslated() != TRANSLATED_EDEFAULT;
			case PropertiesPackage.PROJECT_STATS__TOTAL:
				return getTotal() != TOTAL_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //ProjectStatsImpl
