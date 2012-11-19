/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;
import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;

import de.jutzig.jabylon.properties.DiffKind;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.PropertyFileDiff;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.types.PropertyScanner;
import de.jutzig.jabylon.properties.util.PropertyResourceUtil;
import de.jutzig.jabylon.properties.util.scanner.FullScanFileAcceptor;
import de.jutzig.jabylon.properties.util.scanner.PartialScanFileAcceptor;
import de.jutzig.jabylon.properties.util.scanner.WorkspaceScanner;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Project Version</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTemplate
 * <em>Template</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ProjectVersionImpl extends ResolvableImpl<Project, ProjectLocale> implements ProjectVersion {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProjectVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PropertiesPackage.Literals.PROJECT_VERSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProjectLocale getTemplate() {
		return (ProjectLocale) eDynamicGet(PropertiesPackage.PROJECT_VERSION__TEMPLATE,
				PropertiesPackage.Literals.PROJECT_VERSION__TEMPLATE, true, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProjectLocale basicGetTemplate() {
		return (ProjectLocale) eDynamicGet(PropertiesPackage.PROJECT_VERSION__TEMPLATE,
				PropertiesPackage.Literals.PROJECT_VERSION__TEMPLATE, false, true);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTemplate(ProjectLocale newTemplate) {
		eDynamicSet(PropertiesPackage.PROJECT_VERSION__TEMPLATE, PropertiesPackage.Literals.PROJECT_VERSION__TEMPLATE, newTemplate);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Project getProject() {
		return (Project) eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void fullScan(ScanConfiguration configuration) {
		fullScan(configuration, null);
	}

	public void fullScan(ScanConfiguration configuration, IProgressMonitor monitor) {
		getChildren().clear();
		setTemplate(null);
		WorkspaceScanner scanner = new WorkspaceScanner();
		File baseDir = new File(absolutPath().toFileString()).getAbsoluteFile();
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Scanning", 100);
		PropertyScanner propertyScanner = PropertyResourceUtil.createScanner(this);
		scanner.fullScan(new FullScanFileAcceptor(this, propertyScanner, configuration), baseDir, propertyScanner, configuration,
				subMonitor.newChild(50));

		for (ProjectLocale projectLocale : getChildren()) {
			for (PropertyFileDescriptor descriptor : projectLocale.getDescriptors()) {
				descriptor.updatePercentComplete();
			}
		}

		PropertyResourceUtil.createMissingDescriptorEntries(this, subMonitor.newChild(50));
	}

	public ProjectLocale getProjectLocale(Locale locale) {
		EList<ProjectLocale> locales = getChildren();
		for (ProjectLocale projectLocale : locales) {
			if (locale.equals(projectLocale.getLocale()))
				return projectLocale;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void partialScan(ScanConfiguration configuration, PropertyFileDiff fileDiff) {
		// TODO: MOVE and COPY can be handled better
		// TODO: this can only handle updates in master locale so far.
		// translated files won't make it through the scanner
		PropertyScanner propertyScanner = PropertyResourceUtil.createScanner(this);
		WorkspaceScanner scanner = new WorkspaceScanner();
		File baseDir = new File(absolutPath().toFileString()).getAbsoluteFile();
		// in case of a remove, the new path doesn't exist anymore
		String filePath = fileDiff.getKind() == DiffKind.REMOVE ? fileDiff.getOldPath() : fileDiff.getNewPath();
		File singleFile = new File(baseDir, filePath);
		if (!scanner.partialScan(baseDir, propertyScanner, configuration, singleFile))
			return; // no match -> no work
		switch (fileDiff.getKind()) {
		case MOVE: {
			deleteDescriptor(URI.createURI("/" + fileDiff.getOldPath()));
		}
		case COPY:
		case ADD: {

			scanner.partialScan(new PartialScanFileAcceptor(this, propertyScanner, configuration), baseDir, propertyScanner, configuration,
					singleFile);
			break;
		}
		case MODIFY: {
			PropertyFileDescriptor descriptor = findDescriptor(URI.createURI("/" + fileDiff.getNewPath()));
			if (descriptor != null) {
				PropertyFile properties = descriptor.loadProperties();
				descriptor.setKeys(properties.getProperties().size());
			}
			break;
		}
		case REMOVE: {
			deleteDescriptor(URI.createURI("/" + fileDiff.getOldPath()));
		}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
			if (resolve)
				return getTemplate();
			return basicGetTemplate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
			setTemplate((ProjectLocale) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
			setTemplate((ProjectLocale) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
			return basicGetTemplate() != null;
		}
		return super.eIsSet(featureID);
	}

	private void deleteDescriptor(URI uri) {

		PropertyFileDescriptor descriptor = findDescriptor(uri);
		if (descriptor != null)
			PropertyResourceUtil.removeDescriptor(descriptor);
	}

	@Override
	public Resolvable resolveChild(URI path) {
		if (path.segmentCount() == 0 || relativePath().equals(path))
			return this;
		String localeSegment = path.segment(0);
		ProjectLocale projectLocale = null;
		if (localeSegment.equals("template"))
			projectLocale = getTemplate();
		else {
			Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeSegment);
			if (locale == null)
				return null;
			projectLocale = getProjectLocale(locale);
		}
		if (path.segmentCount() == 1)
			return projectLocale;
		String[] segments = path.segments();
		String[] remainder = new String[segments.length - 1];
		System.arraycopy(segments, 1, remainder, 0, remainder.length);
		URI shorterURI = URI.createHierarchicalURI(remainder, null, null);
		return projectLocale.resolveChild(shorterURI);
	}

	protected PropertyFileDescriptor findDescriptor(URI path) {
		PropertyScanner scanner = PropertyResourceUtil.createScanner(this);
		Locale variant = scanner.getLocale(new File(path.toFileString()));

		ProjectLocale locale = variant == null ? getTemplate() : getProjectLocale(variant);
		if (locale == null)
			return null;
		EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
		for (PropertyFileDescriptor descriptor : descriptors) {
			if (path.equals(descriptor.getLocation())) {
				return descriptor;
			}
		}
		return null;
	}

	private Locale createVariant(String localeString) {
		return (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeString);
	}

} // ProjectVersionImpl
