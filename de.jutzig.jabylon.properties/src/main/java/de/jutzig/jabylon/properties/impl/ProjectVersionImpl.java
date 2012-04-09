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
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyBag;
import de.jutzig.jabylon.properties.PropertyFile;
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
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTranslated <em>Translated</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTotal <em>Total</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getProject <em>Project</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getBranch <em>Branch</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getLocales <em>Locales</em>}</li>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getMaster <em>Master</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectVersionImpl extends ResolvableImpl implements ProjectVersion {
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
	 * The default value of the '{@link #getBranch() <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANCH_EDEFAULT = "master";

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
	 * @generated NOT
	 */
	public Project getProject() {
		return (Project)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBranch() {
		return (String)eDynamicGet(PropertiesPackage.PROJECT_VERSION__BRANCH, PropertiesPackage.Literals.PROJECT_VERSION__BRANCH, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBranch(String newBranch) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__BRANCH, PropertiesPackage.Literals.PROJECT_VERSION__BRANCH, newBranch);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProjectLocale> getLocales() {
		return (EList<ProjectLocale>)eDynamicGet(PropertiesPackage.PROJECT_VERSION__LOCALES, PropertiesPackage.Literals.PROJECT_VERSION__LOCALES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectLocale getMaster() {
		return (ProjectLocale)eDynamicGet(PropertiesPackage.PROJECT_VERSION__MASTER, PropertiesPackage.Literals.PROJECT_VERSION__MASTER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaster(ProjectLocale newMaster, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newMaster, PropertiesPackage.PROJECT_VERSION__MASTER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaster(ProjectLocale newMaster) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__MASTER, PropertiesPackage.Literals.PROJECT_VERSION__MASTER, newMaster);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void fullScan() {
		EList<ProjectLocale> locales = getLocales();
		for (ProjectLocale projectLocale : locales) {
			projectLocale.getDescriptors().clear();
		}
		getMaster().getDescriptors().clear();
		WorkspaceScanner scanner = new WorkspaceScanner();
		scanner.fullScan(new FileAcceptor(), this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int percentComplete() {
		int totalComplete = 0;
		for (ProjectLocale locale : getLocales()) {
			totalComplete += locale.percentComplete();
		}
		return totalComplete / getLocales().size();
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
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLocales()).basicAdd(otherEnd, msgs);
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
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				return ((InternalEList<?>)getLocales()).basicRemove(otherEnd, msgs);
			case PropertiesPackage.PROJECT_VERSION__MASTER:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				return getTranslated();
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				return getTotal();
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				return getProject();
			case PropertiesPackage.PROJECT_VERSION__BRANCH:
				return getBranch();
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				return getLocales();
			case PropertiesPackage.PROJECT_VERSION__MASTER:
				return getMaster();
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
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				setTranslated((Integer)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				setTotal((Integer)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__BRANCH:
				setBranch((String)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				getLocales().clear();
				getLocales().addAll((Collection<? extends ProjectLocale>)newValue);
				return;
			case PropertiesPackage.PROJECT_VERSION__MASTER:
				setMaster((ProjectLocale)newValue);
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
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				setTranslated(TRANSLATED_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				setTotal(TOTAL_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_VERSION__BRANCH:
				setBranch(BRANCH_EDEFAULT);
				return;
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				getLocales().clear();
				return;
			case PropertiesPackage.PROJECT_VERSION__MASTER:
				setMaster((ProjectLocale)null);
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
			case PropertiesPackage.PROJECT_VERSION__TRANSLATED:
				return getTranslated() != TRANSLATED_EDEFAULT;
			case PropertiesPackage.PROJECT_VERSION__TOTAL:
				return getTotal() != TOTAL_EDEFAULT;
			case PropertiesPackage.PROJECT_VERSION__PROJECT:
				return getProject() != null;
			case PropertiesPackage.PROJECT_VERSION__BRANCH:
				return BRANCH_EDEFAULT == null ? getBranch() != null : !BRANCH_EDEFAULT.equals(getBranch());
			case PropertiesPackage.PROJECT_VERSION__LOCALES:
				return !getLocales().isEmpty();
			case PropertiesPackage.PROJECT_VERSION__MASTER:
				return getMaster() != null;
		}
		return super.eIsSet(featureID);
	}

	
	class FileAcceptor implements PropertyFileAcceptor
	{

		@Override
		public void newMatch(File file) {
			PropertyFileDescriptor descriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
			URI location = URI.createFileURI(file.getAbsolutePath());
			location = location.deresolve(absolutPath()); //get rid of the version
			location = URI.createHierarchicalURI(location.scheme(),location.authority(),location.device(),location.segmentsList().subList(1, location.segmentCount()).toArray(new String[location.segmentCount()-1]),location.query(),location.fragment());
			descriptor.setLocation(location);
			if(getMaster()==null)
				setMaster(PropertiesFactory.eINSTANCE.createProjectLocale());
			getMaster().getDescriptors().add(descriptor);
			String absolutePath = file.getParentFile().getAbsolutePath();
			
			//load file to initialize statistics;
			PropertyFile propertyFile = descriptor.loadProperties();
			descriptor.setKeys(propertyFile.getProperties().size());
			
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
					URI childURI = location.trimSegments(1).appendSegment(child);
					fileDescriptor.setLocation(childURI);
					Locale locale = createVariant(matcher.group(1).substring(1));
					fileDescriptor.setVariant(locale);
					fileDescriptor.setMaster(descriptor);
					ProjectLocale projectLocale = getOrCreateProjectLocale(locale);
					projectLocale.getDescriptors().add(fileDescriptor);
					
					//load file to initialize statistics;
					PropertyFile translatedFile = fileDescriptor.loadProperties();
					fileDescriptor.setKeys(translatedFile.getProperties().size());
				}
			}	
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
	
	public ProjectLocale getProjectLocale(Locale locale)
	{
		EList<ProjectLocale> locales = getLocales();
		for (ProjectLocale projectLocale : locales) {
			if(locale.equals(projectLocale.getLocale()))
				return projectLocale;
		}
		return null;
	}
	
	public ProjectLocale getOrCreateProjectLocale(Locale locale)
	{
		ProjectLocale projectLocale = getProjectLocale(locale);
		if(projectLocale==null)
		{
			projectLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
			projectLocale.setLocale(locale);
			getLocales().add(projectLocale);
		}
		return projectLocale;
	}

	@Override
	public URI relativePath() {
		return URI.createHierarchicalURI(new String[] {getBranch()}, null, null);
	}
	
} //ProjectVersionImpl
