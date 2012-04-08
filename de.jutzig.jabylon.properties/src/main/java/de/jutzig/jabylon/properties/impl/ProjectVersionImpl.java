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
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.util.scanner.PropertyFileAcceptor;
import de.jutzig.jabylon.properties.util.scanner.WorkspaceScanner;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getPropertyBags <em>Property Bags</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getBase <em>Base</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTotal <em>Total</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectVersionImpl extends CDOObjectImpl implements ProjectVersion {
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
	protected ProjectVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT_VERSION;
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
	@SuppressWarnings("unchecked")
	public EList<PropertyBag> getPropertyBags() {
		return (EList<PropertyBag>)eDynamicGet(PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS, PropertiesPackage.Literals.PROJECT_VERSION__PROPERTY_BAGS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URI getBase() {
		return (URI)eDynamicGet(PropertiesPackage.PROJECT_VERSION__BASE, PropertiesPackage.Literals.PROJECT_VERSION__BASE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTranslated() {
		return (Integer)eDynamicGet(PropertiesPackage.PROJECT_VERSION__TRANSLATED, PropertiesPackage.Literals.PROJECT_VERSION__TRANSLATED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTranslated(int newTranslated) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__TRANSLATED, PropertiesPackage.Literals.PROJECT_VERSION__TRANSLATED, newTranslated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTotal() {
		return (Integer)eDynamicGet(PropertiesPackage.PROJECT_VERSION__TOTAL, PropertiesPackage.Literals.PROJECT_VERSION__TOTAL, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotal(int newTotal) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__TOTAL, PropertiesPackage.Literals.PROJECT_VERSION__TOTAL, newTotal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project getProject() {
		return (Project)eDynamicGet(PropertiesPackage.PROJECT_VERSION__PROJECT, PropertiesPackage.Literals.PROJECT_VERSION__PROJECT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(Project newProject) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__PROJECT, PropertiesPackage.Literals.PROJECT_VERSION__PROJECT, newProject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void fullScan() {
		getPropertyBags().clear();
		WorkspaceScanner scanner = new WorkspaceScanner();
		scanner.fullScan(new FileAcceptor(), this);
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPropertyBags()).basicAdd(otherEnd, msgs);
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
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				return ((InternalEList<?>)getPropertyBags()).basicRemove(otherEnd, msgs);
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
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				return getPropertyBags();
			case PropertiesPackage.PROJECT_VERSION__BASE:
				return getBase();
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				return getTranslated();
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				return getTotal();
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				return getProject();
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
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				getPropertyBags().clear();
				getPropertyBags().addAll((Collection<? extends PropertyBag>)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				setTranslated((Integer)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				setTotal((Integer)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				setProject((Project)newValue);
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
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				getPropertyBags().clear();
				return;
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				setTranslated(TRANSLATED_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				setTotal(TOTAL_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				setProject((Project)null);
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
			case PropertiesPackage.PROJECT_VERSION__PROPERTY_BAGS:
				return !getPropertyBags().isEmpty();
			case PropertiesPackage.PROJECT_VERSION__BASE:
				return BASE_EDEFAULT == null ? getBase() != null : !BASE_EDEFAULT.equals(getBase());
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				return getTranslated() != TRANSLATED_EDEFAULT;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				return getTotal() != TOTAL_EDEFAULT;
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				return getProject() != null;
		}
		return super.eIsSet(featureID);
	}

	
	class FileAcceptor implements PropertyFileAcceptor
	{

		@Override
		public void newMatch(File file) {
			PropertyBag propertyBag = PropertiesFactory.eINSTANCE.createPropertyBag();
			PropertyFileDescriptor descriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
			descriptor.setName(file.getName());
			propertyBag.getDescriptors().add(descriptor);
			String absolutePath = file.getParentFile().getAbsolutePath();
			URI bagURI = URI.createFileURI(absolutePath);
			bagURI = bagURI.deresolve(getBase());
			propertyBag.setPath(bagURI);
			Pattern pattern = buildPatternFrom(file);
			File folder = file.getParentFile();
			String[] childNames = folder.list();
			for (String child : childNames) {
				if(child.equals(file.getName()))
					continue;
				Matcher matcher = pattern.matcher(child);
				if(matcher.matches())
				{
					PropertyFileDescriptor fileDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
					fileDescriptor.setBag(propertyBag);
					fileDescriptor.setName(child);
					Locale locale = createVariant(matcher.group(1).substring(1));
					fileDescriptor.setVariant(locale);
				}
			}
			getPropertyBags().add(propertyBag);			
		}

		private Locale createVariant(String localeString) {
			return (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeString);
		}

		private Pattern buildPatternFrom(File file) {
			int separator = file.getName().lastIndexOf(".");
			String prefix = file.getName().substring(0,separator);
			String suffix = file.getName().substring(separator);
			return Pattern.compile(Pattern.quote(prefix) + "((_\\w\\w){1,3})"+Pattern.quote(suffix)); //messages.properties => messages_de_DE.properties
		}
		
	}
	
} //ProjectVersionImpl
