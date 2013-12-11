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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.PropertyScanner;

public class FullScanFileAcceptor extends AbstractScanFileAcceptor {

    public FullScanFileAcceptor(ProjectVersion projectVersion, PropertyScanner scanner, ScanConfiguration config) {
        super(projectVersion, scanner, config);
    }

    @Override
    public void newMatch(File file) {

        URI location = calculateLocation(file);
        if (getProjectVersion().getTemplate() == null) {
            getProjectVersion().setTemplate(PropertiesFactory.eINSTANCE.createProjectLocale());
            getProjectVersion().getTemplate().setName("template");
            getProjectVersion().getChildren().add(getProjectVersion().getTemplate());
        }
        PropertyFileDescriptor descriptor = createDescriptor(getProjectVersion().getTemplate(), location);
        getProjectVersion().getTemplate().getDescriptors().add(descriptor);

        // load file to initialize statistics;
        PropertyFile propertyFile = descriptor.loadProperties();
        descriptor.setKeys(propertyFile.getProperties().size());
        descriptor.updatePercentComplete();

        Locale locale = getPropertyScanner().getLocale(file);
        if (locale!=null) {
            descriptor.setVariant(locale);
        }

        Map<Locale, File> translations = getPropertyScanner().findTranslations(file, getScanConfig());
        Set<Entry<Locale, File>> set = translations.entrySet();
        for (Entry<Locale, File> entry : set) {
            ProjectLocale projectLocale = getOrCreateProjectLocale(entry.getKey());
            URI childURI = calculateLocation(entry.getValue());
            PropertyFileDescriptor fileDescriptor = createDescriptor(projectLocale, childURI);
            fileDescriptor.setMaster(descriptor);

            // load file to initialize statistics;
            PropertyFile translatedFile = fileDescriptor.loadProperties();
            int size = translatedFile.getProperties().size();
            fileDescriptor.setKeys(size);
        }

    }



}
