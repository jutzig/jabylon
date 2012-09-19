/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.impl;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import de.jutzig.jabylon.properties.util.scanner.PropertyFileAcceptor;
import de.jutzig.jabylon.properties.util.scanner.WorkspaceScanner;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Project Version</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.jutzig.jabylon.properties.impl.ProjectVersionImpl#getTemplate <em>Template</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectVersionImpl extends ResolvableImpl<Project, ProjectLocale> implements ProjectVersion {
	private static final Pattern LOCALE_PATTERN = Pattern.compile(".+?((_\\w\\w){1,3})\\..+");

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	public ProjectLocale getTemplate() {
		return (ProjectLocale)eDynamicGet(PropertiesPackage.PROJECT_VERSION__TEMPLATE, PropertiesPackage.Literals.PROJECT_VERSION__TEMPLATE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectLocale basicGetTemplate() {
		return (ProjectLocale)eDynamicGet(PropertiesPackage.PROJECT_VERSION__TEMPLATE, PropertiesPackage.Literals.PROJECT_VERSION__TEMPLATE, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		getChildren().clear();
		setTemplate(null);
		WorkspaceScanner scanner = new WorkspaceScanner();
		File baseDir = new File(absolutPath().toFileString()).getAbsoluteFile();
		scanner.fullScan(new FileAcceptor(), baseDir, configuration);

		for (ProjectLocale projectLocale : getChildren()) {
			for (PropertyFileDescriptor descriptor : projectLocale.getDescriptors()) {
				descriptor.updatePercentComplete();
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public int internalUpdatePercentComplete() {
		int totalComplete = 0;
		for (ProjectLocale locale : getChildren()) {
			totalComplete += locale.getPercentComplete();
		}
		if (getChildren().size() == 0)
			return 100;
		return (int) Math.floor(totalComplete / getChildren().size());
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
		//TODO: MOVE and COPY can be handled better
		//TODO: this can only handle updates in master locale so far. translated files won't make it through the scanner
		WorkspaceScanner scanner = new WorkspaceScanner();
		File baseDir = new File(absolutPath().toFileString()).getAbsoluteFile();
		File singleFile = new File(baseDir, fileDiff.getNewPath());
		if(fileDiff.getKind()==DiffKind.REMOVE) 
			//in case of a remove, the new path doesn't exist anymore
			singleFile = new File(baseDir, fileDiff.getOldPath());
		if(!scanner.partialScan(baseDir, configuration, singleFile))
			return; //no match -> no work
		switch (fileDiff.getKind()) {
		case MOVE: {
			deleteDescriptor(URI.createURI("/"+fileDiff.getOldPath()));
		}
		case COPY:
		case ADD: {

			scanner.partialScan(new FileAcceptor(), baseDir, configuration, singleFile);

			for (ProjectLocale projectLocale : getChildren()) {
				for (PropertyFileDescriptor descriptor : projectLocale.getDescriptors()) {
					descriptor.updatePercentComplete();
				}
			}
			break;
		}
		case MODIFY: {
			PropertyFileDescriptor descriptor = findDescriptor(URI.createURI("/"+fileDiff.getNewPath()));
			if(descriptor!=null)
			{
				PropertyFile properties = descriptor.loadProperties();
				descriptor.setKeys(properties.getProperties().size());
			}
			break;
		}
		case REMOVE: {
			deleteDescriptor(URI.createURI("/"+fileDiff.getOldPath()));
		}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
				if (resolve) return getTemplate();
				return basicGetTemplate();
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
			case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
				setTemplate((ProjectLocale)newValue);
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
			case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
				setTemplate((ProjectLocale)null);
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
			case PropertiesPackage.PROJECT_VERSION__TEMPLATE:
				return basicGetTemplate() != null;
		}
		return super.eIsSet(featureID);
	}

	private void deleteDescriptor(URI uri) {

		PropertyFileDescriptor descriptor = findDescriptor(uri);
		if (descriptor != null)
		{
			if(descriptor.isMaster())
			{
				EList<ProjectLocale> locales = getChildren();
				OUTER: for (ProjectLocale projectLocale : locales) {
					EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
					for (PropertyFileDescriptor variant : descriptors) {
						if(variant.getMaster()==descriptor)
						{
							projectLocale.getDescriptors().remove(variant);
							continue OUTER;
						}
					}
				}
			}
			descriptor.getProjectLocale().getDescriptors().remove(descriptor);
		}
	}

	public ProjectLocale getOrCreateProjectLocale(Locale locale) {
		ProjectLocale projectLocale = getProjectLocale(locale);
		if (projectLocale == null) {
			projectLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
			projectLocale.setLocale(locale);
			getChildren().add(projectLocale);
		}
		return projectLocale;
	}

	@Override
	public URI relativePath() {
		return URI.createHierarchicalURI(new String[] { getName() }, null, null);
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
		String localeString = getLocaleString(path.lastSegment());
		ProjectLocale locale = localeString.isEmpty() ? getTemplate() : getProjectLocale(createVariant(localeString));
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

	/**
	 * example: "messages_de_DE.properties" returns "de_DE"
	 * 
	 * @param fileName
	 * @return the locale string in the filename or an empty string if there is
	 *         non
	 */
	private String getLocaleString(String fileName) {
		Matcher matcher = LOCALE_PATTERN.matcher(fileName);
		if (matcher.matches())
			return matcher.group(1);
		return "";
	}

	private Locale createVariant(String localeString) {
		return (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeString);
	}

	class FileAcceptor implements PropertyFileAcceptor {

		public FileAcceptor() {
		}

		@Override
		public void newMatch(File file) {

			URI location = URI.createFileURI(file.getAbsolutePath());
			location = location.deresolve(absolutPath()); // get rid of the
															// version
			location = URI.createHierarchicalURI(location.scheme(), location.authority(), location.device(), location.segmentsList()
					.subList(1, location.segmentCount()).toArray(new String[location.segmentCount() - 1]), location.query(),
					location.fragment());
			if (getTemplate() == null) {
				setTemplate(PropertiesFactory.eINSTANCE.createProjectLocale());
				getTemplate().setName("template");
				getChildren().add(getTemplate());
			}
			PropertyFileDescriptor descriptor = createDescriptor(getTemplate(), location);
			getTemplate().getDescriptors().add(descriptor);
			//TODO: implement folder hierarchy!
			getTemplate().getChildren().add(descriptor);

			// load file to initialize statistics;
			PropertyFile propertyFile = descriptor.loadProperties();
			descriptor.setKeys(propertyFile.getProperties().size());
			descriptor.updatePercentComplete();

			String localeString = getLocaleString(file.getName());
			if (!localeString.isEmpty()) {
				Locale locale = createVariant(localeString.substring(1));
				descriptor.setVariant(locale);
			}

			Pattern pattern = buildPatternFrom(file.getName().replace(localeString, ""));
			File folder = file.getParentFile();
			String[] childNames = folder.list();
			for (String child : childNames) {
				if (child.equals(file.getName()))
					continue;
				Matcher matcher = pattern.matcher(child);
				if (matcher.matches()) {
					Locale locale = createVariant(matcher.group(1).substring(1));
					ProjectLocale projectLocale = getOrCreateProjectLocale(locale);
					URI childURI = location.trimSegments(1).appendSegment(child);
					PropertyFileDescriptor fileDescriptor = createDescriptor(projectLocale, childURI);
					fileDescriptor.setMaster(descriptor);

					// load file to initialize statistics;
					PropertyFile translatedFile = fileDescriptor.loadProperties();
					fileDescriptor.setKeys(translatedFile.getProperties().size());

					// fileDescriptor.updatePercentComplete();
				}
			}
		}

		private PropertyFileDescriptor createDescriptor(ProjectLocale projectLocale, URI childURI) {
			PropertyFileDescriptor fileDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
			fileDescriptor.setLocation(childURI);
			fileDescriptor.setVariant(projectLocale.getLocale());
			//TODO: implement folder structure
			projectLocale.getDescriptors().add(fileDescriptor);
			projectLocale.getChildren().add(fileDescriptor);
			return fileDescriptor;
		}

		private Pattern buildPatternFrom(String fileName) {
			int separator = fileName.lastIndexOf(".");
			String prefix = fileName.substring(0, separator);
			String suffix = fileName.substring(separator);
			return Pattern.compile(Pattern.quote(prefix) + "((_\\w\\w){1,3})" + Pattern.quote(suffix)); // messages.properties
																										// =>
																										// messages_de_DE.properties
		}

	}

} // ProjectVersionImpl
