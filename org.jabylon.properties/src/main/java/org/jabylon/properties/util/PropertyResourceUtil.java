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
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScannerUTF8;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyResourceUtil {

	private static ConcurrentMap<String, PropertyScanner> PROPERTY_SCANNERS;
	private static final Logger LOG = LoggerFactory.getLogger(PropertyResourceUtil.class);

	static {
		PROPERTY_SCANNERS = new ConcurrentHashMap<String, PropertyScanner>();
		Bundle bundle = FrameworkUtil.getBundle(PropertyResourceUtil.class);
		if(bundle==null){
			//fallback for unit tests
			PROPERTY_SCANNERS.put(JavaPropertyScanner.TYPE, new JavaPropertyScanner());
			PROPERTY_SCANNERS.put(JavaPropertyScannerUTF8.TYPE, new JavaPropertyScannerUTF8());
		}
		else {
			final BundleContext context = FrameworkUtil.getBundle(PropertyResourceUtil.class).getBundleContext();
			ServiceTracker<PropertyScanner, PropertyScanner> tracker = new ServiceTracker<PropertyScanner, PropertyScanner>(context, PropertyScanner.class, new ServiceTrackerCustomizer<PropertyScanner, PropertyScanner>() {

				@Override
				public PropertyScanner addingService(ServiceReference<PropertyScanner> reference){

					Object value = reference.getProperty(PropertyScanner.TYPE);
					PropertyScanner service = context.getService(reference);
					if (value instanceof String) {
						String type = (String) value;
						PROPERTY_SCANNERS.put(type, service);
						LOG.debug("Added property type {} {}",type,service);
					}
					else
						LOG.error("PropertyScanner has no valid type {}",service);
					return service;
				}

				@Override
				public void removedService(ServiceReference<PropertyScanner> reference, PropertyScanner service) {
					Object value = reference.getProperty(PropertyScanner.TYPE);
					context.ungetService(reference);
					if(value!=null)
						PROPERTY_SCANNERS.remove(value);
					LOG.debug("Removed property type {} {}",value);
				}

				@Override
				public void modifiedService(ServiceReference<PropertyScanner> reference, PropertyScanner service) {
					// nothing to do

				}

			});
			tracker.open(true);
		}
	}

    public static PropertyScanner createScanner(ProjectVersion version)
    {
    	Project project = version.getParent();
    	String propertyType = project.getPropertyType();
    	return createScanner(propertyType);
    }

    public static PropertyScanner createScanner(String propertyType)
    {
    	PropertyScanner scanner = PROPERTY_SCANNERS.get(propertyType);
    	if(scanner==null)
    		throw new UnsupportedOperationException("unsupported property type: "+propertyType);
    	return scanner;

    }

    public static Map<String,PropertyScanner> getPropertyScanners()
    {
    	TreeMap<String, PropertyScanner> sortedMap = new TreeMap<String, PropertyScanner>(PROPERTY_SCANNERS);
    	return Collections.unmodifiableMap(sortedMap);
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
            createMissingChildren(template, locale);
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
			else if (parent.getChild(descriptor.getName()) == null) {
				parent.getChildren().add(descriptor);
				descriptor.setName(descriptor.getLocation().lastSegment());
				version.getTemplate().getDescriptors().add(descriptor);
				descriptor.updatePercentComplete();
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
                templateLocale.getLocale(),version);
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
        createMissingChildren(template, locale);
    }

    private static void createMissingChildren(ProjectLocale template, ProjectLocale other) {

    	EList<PropertyFileDescriptor> descriptors = template.getDescriptors();
    	for (PropertyFileDescriptor descriptor : descriptors) {
    		URI derivedLocation = computeLocaleResourceLocation(other.getLocale(), other.getParent(), descriptor.getLocation());
    		Resolvable<?, ?> child = other.resolveChild(derivedLocation);
    		if(child==null){
    			Resolvable<?, ?> folder = getOrCreateFolder(other, derivedLocation.trimSegments(1).segments());
                PropertyFileDescriptor localeDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
                localeDescriptor.setMaster(descriptor);
                localeDescriptor.setVariant(other.getLocale());
                localeDescriptor.setLocation(derivedLocation);
                localeDescriptor.setProjectLocale(other);
                localeDescriptor.setParent(folder);
                localeDescriptor.setName(URI.decode(derivedLocation.lastSegment()));
    		}
		}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Resolvable<?, ?> getOrCreateFolder(Resolvable<?, ?> parent, String... segments) {

        Resolvable currentParent = parent;
        if (segments == null || segments.length==0)
            return parent;
        for (String segment : segments) {
        	//in case there was encoded characters in the segment
        	//see https://github.com/jutzig/jabylon/issues/195
        	segment = URI.decode(segment);
            Resolvable child = currentParent.getChild(segment);
            if (child == null) {
                child = PropertiesFactory.eINSTANCE.createResourceFolder();
                child.setName(segment);
                currentParent.getChildren().add(child);
            }
            currentParent = child;
        }
        return (ResourceFolder) currentParent;

    }

    public static URI computeTemplateResourceLocation(Locale locale, URI translationLocation, Locale masterLocale, ProjectVersion version) {

        PropertyScanner scanner = createScanner(version);
        URI parentPath = version.absoluteFilePath();
        File path = scanner.findTemplate(new File(parentPath.path()+translationLocation.toString()), null);

        URI location = URI.createFileURI(path.getAbsolutePath());
        URI trimmedLocation = URI.createURI(location.segment(parentPath.segmentCount()));
        for (int i = parentPath.segmentCount()+1; i < location.segmentCount(); i++) {
			//append the other segments
        	trimmedLocation = trimmedLocation.appendSegment(location.segment(i));
		}
        return trimmedLocation;

    }

	public static URI computeLocaleResourceLocation(Locale locale,
			ProjectVersion version, URI templateLocation) {

		PropertyScanner scanner = createScanner(version);
		URI parentPath = version.absoluteFilePath();
		String childPath = templateLocation.path();
		if (childPath != null && !childPath.startsWith("/"))
			childPath = "/" + childPath;
		File path = scanner.computeTranslationPath(new File(URI.decode(parentPath.path()) + URI.decode(childPath)), version.getTemplate().getLocale(), locale);

		/*
		 * workaround for https://github.com/jutzig/jabylon/issues/238 certain
		 * issues seem to trigger a bug in EMFs createFileURI
		 */
		URI location = URI.createURI(path.getAbsolutePath().replace('\\','/'));
		URI trimmedLocation = URI.createURI(location.segment(parentPath.segmentCount()));
		for (int i = parentPath.segmentCount() + 1; i < location.segmentCount(); i++) {
			// append the other segments
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
