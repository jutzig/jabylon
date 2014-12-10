/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util.scanner;

import java.io.File;
import java.util.Locale;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.PropertyScanner;

public abstract class AbstractScanFileAcceptor implements PropertyFileAcceptor{

    /**
     *
     */
    private final ProjectVersion projectVersion;
    private PropertyScanner scanner;
    private ScanConfiguration config;
    private URI versionPath;

    public AbstractScanFileAcceptor(ProjectVersion projectVersion, PropertyScanner scanner, ScanConfiguration config) {
        this.projectVersion = projectVersion;
        this.scanner = scanner;
        this.config = config;
        versionPath = projectVersion.absolutPath();
    }


    protected URI calculateLocation(File file) {
    	String versionPathString = versionPath.toFileString();
    	String absolutePath = file.getAbsolutePath();
    	//normalize
    	if(!(absolutePath.startsWith("/") ||  absolutePath.startsWith("\\")))
    			absolutePath = "/"+absolutePath;
    	if(!(versionPathString.startsWith("/") ||  versionPathString.startsWith("\\")))
    			versionPathString = "/"+versionPathString;
    	/*
    	 * we need to get rid of the base directory and version
    	 */
    	String relativePath = absolutePath.substring(versionPathString.length());
    	if(relativePath.startsWith("/") || relativePath.startsWith("\\"))
    		relativePath = relativePath.substring(1);
    	URI location = URI.createFileURI(relativePath);
        return location;
    }

    protected PropertyFileDescriptor createDescriptor(ProjectLocale projectLocale, URI childURI) {
        PropertyFileDescriptor fileDescriptor = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
        fileDescriptor.setLocation(childURI);
        fileDescriptor.setName(childURI.lastSegment());
        fileDescriptor.setVariant(projectLocale.getLocale());
        projectLocale.getDescriptors().add(fileDescriptor);
        Resolvable<?, Resolvable<?, ?>> parent = getOrCreateParent(projectLocale, childURI);
        parent.getChildren().add(fileDescriptor);
        return fileDescriptor;
    }

    private Resolvable<?, Resolvable<?, ?>> getOrCreateParent(ProjectLocale projectLocale, URI childURI) {
        Resolvable<?, Resolvable<?, ?>> currentParent = projectLocale;
        String[] segments = childURI.segments();
        for (int i = 0; i < segments.length - 1; i++) {
            currentParent = getOrCreate(currentParent, URI.decode(segments[i]));
        }
        return currentParent;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Resolvable<?, Resolvable<?, ?>> getOrCreate(Resolvable<?, Resolvable<?, ?>> currentParent, String child) {
        Resolvable<?, Resolvable<?, ?>> childObject = (Resolvable<?, Resolvable<?, ?>>) currentParent.getChild(child);
        if (childObject == null) {
            childObject = PropertiesFactory.eINSTANCE.createResourceFolder();
            childObject.setName(child);
            EList children = currentParent.getChildren();
            children.add(childObject);
        }
        return childObject;
    }

    public ProjectLocale getOrCreateProjectLocale(Locale locale) {
        ProjectLocale projectLocale = getProjectVersion().getProjectLocale(locale);
        if (projectLocale == null) {
            projectLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
            projectLocale.setLocale(locale);
            getProjectVersion().getChildren().add(projectLocale);
        }
        return projectLocale;
    }

    public ScanConfiguration getScanConfig() {
        return config;
    }

    public ProjectVersion getProjectVersion() {
        return projectVersion;
    }

    public PropertyScanner getPropertyScanner() {
        return scanner;
    }

}
