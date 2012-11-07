package de.jutzig.jabylon.properties.util;

import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ResourceFolder;

public class PropertyResourceUtil {

	public static void createMissingDescriptorEntries(ProjectVersion parent, IProgressMonitor monitor) {
		EList<ProjectLocale> children = parent.getChildren();
		monitor.beginTask("Adding missing localized resources", children.size()-1);
		ProjectLocale template = parent.getTemplate();
		for (ProjectLocale locale : children) {
			if (locale == template)
				continue;
			if(locale!=null && locale.getLocale()!=null)
				monitor.subTask("Add missing entries for "+locale.getLocale().getDisplayName());
			createMissingChildren(template, locale, locale);
			monitor.worked(1);
		}
		monitor.subTask("");
		monitor.done();

	}
	
	/**
	 * adds a new descriptor to the version (i.e. it creates any missing folders in all the locales)
	 * <strong>important:<strong> the feature {@link PropertyFileDescriptor#getLocation()} must be set for this to work 
	 * @param descriptor
	 * @param version
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addNewTemplateDescriptor(PropertyFileDescriptor descriptor, ProjectVersion version){
		if(!descriptor.eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION))
			throw new IllegalArgumentException("Property File Descriptor location is not set");
		for (ProjectLocale locale : version.getChildren()) {
			Resolvable parent = locale;
			String[] descriptorPart = descriptor.getLocation().segments();
			//add any missing folders
			for (int i = 0; i < descriptorPart.length-1; i++) {
				Resolvable child = parent.getChild(descriptorPart[i]);
				if(child==null)
				{
					child = PropertiesFactory.eINSTANCE.createResourceFolder();
					child.setName(descriptorPart[i]);
					parent.getChildren().add(child);
				}
				parent = child;
			}
			
			//if it is not the template language, create a new derived descriptor
			if(!locale.isMaster())
			{
				URI derivedLocation = computeLocaleResourceLocation(locale.getLocale(),descriptor.getLocation(),descriptor.getVariant());
				
				PropertyFileDescriptor translatedDescriptor = (PropertyFileDescriptor) parent.getChild(derivedLocation.lastSegment());
				if(translatedDescriptor==null)
				{
					translatedDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
					locale.getDescriptors().add(translatedDescriptor);
					parent.getChildren().add(translatedDescriptor);
					
				}
				translatedDescriptor.setVariant(locale.getLocale());
				translatedDescriptor.setLocation(derivedLocation);
				translatedDescriptor.setName(derivedLocation.lastSegment());
				translatedDescriptor.setMaster(descriptor);
			}
			//otherwise add it to the template language
			else
			{
				if(parent.getChild(descriptor.getName())==null)
				{
					parent.getChildren().add(descriptor);
					descriptor.setName(descriptor.getLocation().lastSegment());
					version.getTemplate().getDescriptors().add(descriptor);					
				}
			}
		}
		
	}

	
	
	public static void addNewLocale(ProjectLocale locale, ProjectVersion version){
		ProjectLocale template = version.getTemplate();
		version.getChildren().add(locale);
		createMissingChildren(template, locale, locale);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void createMissingChildren(Resolvable<?, ?> template, Resolvable locale, ProjectLocale variant) {
		//TODO: this algorithm isn't very efficient unfortunately
		for (Resolvable<?, ?> child : template.getChildren()) {
			String name = child.getName();
			if (child instanceof PropertyFileDescriptor) {
				// for properties we need the locale specific name
				PropertyFileDescriptor descriptor = (PropertyFileDescriptor)child;
				name = computeLocaleResourceLocation(variant.getLocale(), descriptor.getLocation(), descriptor.getVariant()).lastSegment();
			}
			Resolvable<?, ?> localeChild = locale.getChild(name);
			if (localeChild == null) {
				if (child instanceof PropertyFileDescriptor) {
					PropertyFileDescriptor templateDescriptor = (PropertyFileDescriptor) child;
					PropertyFileDescriptor localeDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
					localeDescriptor.setMaster(templateDescriptor);
					localeDescriptor.setVariant(variant.getLocale());
					localeDescriptor.computeLocation();
					localeDescriptor.setProjectLocale(variant);
					localeChild = localeDescriptor;

				} else if (child instanceof ResourceFolder) {
					ResourceFolder folder = (ResourceFolder) child;
					ResourceFolder localeFolder = PropertiesFactory.eINSTANCE.createResourceFolder();
					localeFolder.setName(folder.getName());
					localeChild = localeFolder;
				}
				locale.getChildren().add(localeChild);
			}
			createMissingChildren(child, localeChild, variant);
		}

	}

	private static URI computeLocaleResourceLocation(Locale locale, URI location, Locale masterLocale) {

		String filename = location.lastSegment();
		String extension = location.fileExtension();

		if (extension != null) {
			filename = filename.substring(0, filename.length() - extension.length() - 1);

			// if the master has a locale as well (i.e.
			// messages_en_EN.properties) we must remove the suffix
			if (masterLocale != null) {
				filename = filename.substring(0, filename.length() - (masterLocale.toString().length() + 1));
			}

			filename += "_";
			filename += locale.toString();
			filename += ".";
			filename += extension;
		}
		return location.trimSegments(1).appendSegment(filename);

	}
	
}
