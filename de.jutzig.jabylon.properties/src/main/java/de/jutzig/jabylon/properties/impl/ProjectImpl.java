/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.ProjectStats;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.scanner.PropertyFileAcceptor;
import de.jutzig.jabylon.properties.util.scanner.WorkspaceScanner;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectImpl#getWorkspace <em>Workspace</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectImpl#getVersions <em>Versions</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectImpl#getMaster <em>Master</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectImpl#getBase <em>Base</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectImpl extends ResolvableImpl implements Project {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	

	/**
	 * The default value of the '{@link #getBase() <em>Base</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected static final URI BASE_EDEFAULT = null;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(PropertiesPackage.PROJECT__NAME, PropertiesPackage.Literals.PROJECT__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(PropertiesPackage.PROJECT__NAME, PropertiesPackage.Literals.PROJECT__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Workspace getWorkspace() {
		return (Workspace)eDynamicGet(PropertiesPackage.PROJECT__WORKSPACE, PropertiesPackage.Literals.PROJECT__WORKSPACE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorkspace(Workspace newWorkspace, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newWorkspace, PropertiesPackage.PROJECT__WORKSPACE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkspace(Workspace newWorkspace) {
		eDynamicSet(PropertiesPackage.PROJECT__WORKSPACE, PropertiesPackage.Literals.PROJECT__WORKSPACE, newWorkspace);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProjectVersion> getVersions() {
		return (EList<ProjectVersion>)eDynamicGet(PropertiesPackage.PROJECT__VERSIONS, PropertiesPackage.Literals.PROJECT__VERSIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectVersion getMaster() {
		return (ProjectVersion)eDynamicGet(PropertiesPackage.PROJECT__MASTER, PropertiesPackage.Literals.PROJECT__MASTER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaster(ProjectVersion newMaster, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newMaster, PropertiesPackage.PROJECT__MASTER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaster(ProjectVersion newMaster) {
		eDynamicSet(PropertiesPackage.PROJECT__MASTER, PropertiesPackage.Literals.PROJECT__MASTER, newMaster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public URI getBase() {
		if(getWorkspace()==null)
			return null;
		if(getWorkspace().getRoot()==null)
			return null;
		return getWorkspace().getRoot().appendSegment(getName());
	}



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int percentComplete() {
		return getMaster().percentComplete();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROJECT__WORKSPACE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetWorkspace((Workspace)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROJECT__WORKSPACE:
				return basicSetWorkspace(null, msgs);
			case PropertiesPackage.PROJECT__VERSIONS:
				return ((InternalEList<?>)getVersions()).basicRemove(otherEnd, msgs);
			case PropertiesPackage.PROJECT__MASTER:
				return basicSetMaster(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case PropertiesPackage.PROJECT__WORKSPACE:
				return eInternalContainer().eInverseRemove(this, PropertiesPackage.WORKSPACE__PROJECTS, Workspace.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT__NAME:
				return getName();
			case PropertiesPackage.PROJECT__WORKSPACE:
				return getWorkspace();
			case PropertiesPackage.PROJECT__VERSIONS:
				return getVersions();
			case PropertiesPackage.PROJECT__MASTER:
				return getMaster();
			case PropertiesPackage.PROJECT__BASE:
				return getBase();
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
			case PropertiesPackage.PROJECT__NAME:
				setName((String)newValue);
				return;
			case PropertiesPackage.PROJECT__WORKSPACE:
				setWorkspace((Workspace)newValue);
				return;
			case PropertiesPackage.PROJECT__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends ProjectVersion>)newValue);
				return;
			case PropertiesPackage.PROJECT__MASTER:
				setMaster((ProjectVersion)newValue);
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
			case PropertiesPackage.PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT__WORKSPACE:
				setWorkspace((Workspace)null);
				return;
			case PropertiesPackage.PROJECT__VERSIONS:
				getVersions().clear();
				return;
			case PropertiesPackage.PROJECT__MASTER:
				setMaster((ProjectVersion)null);
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
			case PropertiesPackage.PROJECT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case PropertiesPackage.PROJECT__WORKSPACE:
				return getWorkspace() != null;
			case PropertiesPackage.PROJECT__VERSIONS:
				return !getVersions().isEmpty();
			case PropertiesPackage.PROJECT__MASTER:
				return getMaster() != null;
			case PropertiesPackage.PROJECT__BASE:
				return BASE_EDEFAULT == null ? getBase() != null : !BASE_EDEFAULT.equals(getBase());
		}
		return super.eIsSet(featureID);
	}

	@Override
	public URI relativePath() {
		return URI.createHierarchicalURI(new String[] {getName()}, null, null);
	}

} //ProjectImpl
