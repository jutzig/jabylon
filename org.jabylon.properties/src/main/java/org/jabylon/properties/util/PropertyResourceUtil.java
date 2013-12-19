/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import java.io.File;
import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.PropertyType;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScanner;

public class PropertyResourceUtil {


    public static PropertyScanner createScanner(ProjectVersion version)
    {
        //TODO: make this more dynamic to support property types as a service
        Project project = version.getParent();
        PropertyType propertyType = project.getPropertyType();
        switch (propertyType) {
        case ENCODED_ISO:
            return new JavaPropertyScanner();
        case UNICODE:
            return new JavaPropertyScanner();
        default:
            throw new UnsupportedOperationException("unsupported property type: "+propertyType);
        }
    }

    public static void createMissingDescriptorEntries(ProjectVersion parent, IProgressMonitor monitor) {
        EList<ProjectLocale> children = parent.getChildren();
        monitor.beginTask("Adding missing localized resources", children.size() - 1);
        ProjectLocale template = parent.getTemplate();
        for (ProjectLocale locale : children) {
            if (locale == template)
                continue;
            if (locale != null && locale.getLocale() != null)
                monitor.subTask("Add missing entries for " + locale.getLocale().getDisplayName());
            createMissingChildren(template, locale, locale);
            monitor.worked(1);
        }
        monitor.subTask("");
        monitor.done();

    }

    /**
     * adds a new descriptor to the version (i.e. it creates any missing folders
     * in all the locales) <strong>important:<strong> the feature
     * {@link PropertyFileDescriptor#getLocation()} must be set for this to work
     *
     * @param descriptor
     * @param version
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void addNewTemplateDescriptor(PropertyFileDescriptor descriptor, ProjectVersion version) {
        if (!descriptor.eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION))
            throw new IllegalArgumentException("Property File Descriptor location is not set");
        for (ProjectLocale locale : version.getChildren()) {
            Resolvable parent = locale;
            String[] descriptorPart = descriptor.getLocation().segments();
            // add any missing folders
            for (int i = 0; i < descriptorPart.length - 1; i++) {
                Resolvable child = parent.getChild(descriptorPart[i]);
                if (child == null) {
                    child = PropertiesFactory.eINSTANCE.createResourceFolder();
                    //get rid of encodings like %20 for space
                    String childName = URI.decode(descriptorPart[i]);
                    child.setName(childName);
                    parent.getChildren().add(child);
                }
                parent = child;
            }

            // if it is not the template language, create a new derived
            // descriptor
            if (!locale.isMaster()) {
                URI derivedLocation = computeLocaleResourceLocation(locale.getLocale(), version, descriptor.getLocation());

                PropertyFileDescriptor translatedDescriptor = (PropertyFileDescriptor) parent.getChild(derivedLocation.lastSegment());
                if (translatedDescriptor == null) {
                    translatedDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
                    locale.getDescriptors().add(translatedDescriptor);
                    parent.getChildren().add(translatedDescriptor);

                }
                translatedDescriptor.setVariant(locale.getLocale());
                translatedDescriptor.setLocation(derivedLocation);
                translatedDescriptor.setName(derivedLocation.lastSegment());
                translatedDescriptor.setMaster(descriptor);
                if(new File(translatedDescriptor.absoluteFilePath().path()).isFile())
                {
                    // load file to initialize statistics;
                    PropertyFile translatedFile = translatedDescriptor.loadProperties();
                    int size = translatedFile.getProperties().size();
                    translatedDescriptor.setKeys(size);
                    translatedDescriptor.updatePercentComplete();
                }
                else
                {
                	/*
                	 * the file wasn't already there, but we still
                	 * must inform the parent  that there is more children now
                	 */
                	parent.updatePercentComplete();
                }
            }
            // otherwise add it to the template language
            else {
                if (parent.getChild(descriptor.getName()) == null) {
                    parent.getChildren().add(descriptor);
                    descriptor.setName(descriptor.getLocation().lastSegment());
                    version.getTemplate().getDescriptors().add(descriptor);
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void addNewLocalizedDescriptor(PropertyFileDescriptor descriptor, ProjectLocale locale) {
        if (!descriptor.eIsSet(PropertiesPackage.Literals.PROPERTY_FILE_DESCRIPTOR__LOCATION))
            throw new IllegalArgumentException("Property File Descriptor location is not set");
        ProjectVersion version = locale.getParent();
        ProjectLocale templateLocale = version.getTemplate();
        URI templateResourceLocation = computeTemplateResourceLocation(locale.getLocale(), descriptor.getLocation(),
                templateLocale.getLocale());
        Resolvable<?, ?> resolved = templateLocale.resolveChild(templateResourceLocation);
        PropertyFileDescriptor template = null;
        if (resolved instanceof PropertyFileDescriptor) {
            template = (PropertyFileDescriptor) resolved;
        }
        if (template == null)
            throw new IllegalArgumentException("Template property " + templateResourceLocation + " doesn't exist");
        Resolvable container = getOrCreateFolder(locale, descriptor.getLocation().trimSegments(1).segments());
        if (container.getChild(descriptor.getLocation().lastSegment()) != null) {
            PropertyFileDescriptor child = (PropertyFileDescriptor) container.getChild(descriptor.getLocation().lastSegment());
            child.setMaster(null);
            container.getChildren().set(container.getChildren().indexOf(child), descriptor);
            locale.getDescriptors().remove(child);
        } else {
            container.getChildren().add(descriptor);
        }
        descriptor.setMaster(template);
        locale.getDescriptors().add(descriptor);

    }

    public static void addNewLocale(ProjectLocale locale, ProjectVersion version) {
        ProjectLocale template = version.getTemplate();
        version.getChildren().add(locale);
        if(template==null) {
        	//we always need a template
        	template = PropertiesFactory.eINSTANCE.createProjectLocale();
            version.setTemplate(template);
            template.setName("template");
            version.getChildren().add(template);
        }
        createMissingChildren(template, locale, locale);
    }    

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void createMissingChildren(Resolvable<?, ?> template, Resolvable locale, ProjectLocale variant) {
        // TODO: this algorithm isn't very efficient unfortunately
        for (Resolvable<?, ?> child : template.getChildren()) {
            String name = child.getName();
            if (child instanceof PropertyFileDescriptor) {
                // for properties we need the locale specific name
                PropertyFileDescriptor descriptor = (PropertyFileDescriptor) child;
                URI derivedLocation = computeLocaleResourceLocation(variant.getLocale(), variant.getParent(), descriptor.getLocation());
                name = URI.decode(derivedLocation.lastSegment());
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Resolvable<?, ?> getOrCreateFolder(Resolvable<?, ?> parent, String... segments) {

        Resolvable currentParent = parent;
        if (segments == null)
            return parent;
        for (String segment : segments) {
            Resolvable child = currentParent.getChild(segment);
            if (child == null) {
                child = PropertiesFactory.eINSTANCE.createResourceFolder();
                currentParent.getChildren().add(child);
            }
            currentParent = child;
        }
        return (ResourceFolder) currentParent;

    }

    public static URI computeTemplateResourceLocation(Locale locale, URI location, Locale masterLocale) {

        String filename = location.lastSegment();
        String extension = location.fileExtension();

        if (extension != null) {
            filename = filename.substring(0, filename.length() - extension.length() - 1);

            filename = filename.substring(0, filename.length() - (locale.toString().length() + 1));
            // if the master has a locale as well (i.e.
            // messages_en_EN.properties) we must add that portion
            if (masterLocale != null) {
                filename += "_";
                filename += masterLocale.toString();
            }
            filename += ".";
            filename += extension;
        }
        return location.trimSegments(1).appendSegment(filename);

    }

    public static URI computeLocaleResourceLocation(Locale locale, ProjectVersion version, URI templateLocation) {

        PropertyScanner scanner = createScanner(version);
        URI parentPath = version.absoluteFilePath();
        File path = scanner.computeTranslationPath(new File(parentPath.path()+templateLocation.toString()), version.getTemplate().getLocale(), locale);

        URI location = URI.createFileURI(path.getAbsolutePath());
        URI trimmedLocation = URI.createURI(location.segment(parentPath.segmentCount()));
        for (int i = parentPath.segmentCount()+1; i < location.segmentCount(); i++) {
			//append the other segments
        	trimmedLocation = trimmedLocation.appendSegment(location.segment(i));
		}
        return trimmedLocation;

    }

    public static void removeDescriptor(PropertyFileDescriptor descriptor) {

        if (descriptor.isMaster()) {
            EList<PropertyFileDescriptor> derivedDescriptors = descriptor.getDerivedDescriptors();
            for (PropertyFileDescriptor derived : derivedDescriptors) {
                derived.setProjectLocale(null);
                Resolvable<?, ?> parent = derived.getParent();
                if(parent!=null)
                {
                	//update the percentage of all derived resources
                	parent.updatePercentComplete();
                	if(parent.getChildren().size()==1 && parent instanceof ResourceFolder)
                	{
                		
                		deleteFolder((ResourceFolder) parent);
                	}                	
                }

                EcoreUtil.remove(derived);

            }
        }
        else
        {
            descriptor.setMaster(null);
        }
        Resolvable<?, ?> parent = descriptor.getParent();
        if(parent!=null && parent.getChildren().size()==1 && parent instanceof ResourceFolder)
            deleteFolder((ResourceFolder) parent);
        EcoreUtil.remove(descriptor);
        descriptor.getProjectLocale().getDescriptors().remove(descriptor);
    }

    /**
     * deletes a folder (and recursively all parents above) if there's only one child left in it
     * <p>
     * this is to clean up no longer needed folders when a descriptor is removed
     * @param parent
     */
    @SuppressWarnings("rawtypes")
    private static void deleteFolder(ResourceFolder folder) {
        Resolvable currentParent = folder;
        Resolvable lastParent = folder;
        while(currentParent instanceof ResourceFolder)
        {
            if(currentParent.getChildren().size()<=1)
            {
                lastParent = currentParent;
                currentParent = currentParent.getParent();
            }
            else
                break;
        }
        EcoreUtil.remove(lastParent);
    }

}
