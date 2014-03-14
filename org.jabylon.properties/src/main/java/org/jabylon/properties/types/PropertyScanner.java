/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.jabylon.properties.ScanConfiguration;

/**
 * contributes a new kind of translatable resource
 * @author jutzig.dev@googlemail.com
 *
 */
public interface PropertyScanner {

	/**
	 * the unique id of the type of resources that this scanner is responsible for
	 */
	String TYPE = "TYPE";
	
	/**
	 * checks if the given file is a translation template
	 * @param propertyFile
	 * @param config
	 * @return
	 */
    boolean isTemplate(File propertyFile, String masterLocale);

    /**
     * checks if the given file is a translation file
     * @param propertyFile
     * @param config
     * @return
     */
    boolean isTranslation(File propertyFile, ScanConfiguration config);

    /**
     * retrieves the translation template for the given translation (used for mono lingual resources)
     * @param propertyFile
     * @param config
     * @return the template file or <code>null</code> if not available
     */
    File findTemplate(File propertyFile, ScanConfiguration config);

    /**
     * finds all existing translation files for the given template 
     * @param template
     * @param config
     * @return
     */
    Map<Locale, File> findTranslations(File template, ScanConfiguration config);

    /**
     * computes the location of the translation file for the given template and locale
     * @param template
     * @param templateLocale
     * @param translationLocale
     * @return
     */
    File computeTranslationPath(File template, Locale templateLocale, Locale translationLocale);

    /**
     * extracts the locale from the given file
     * @param propertyFile
     * @return
     */
    Locale getLocale(File propertyFile);
    
    /**
     * 
     * @return <code>true</code> if this scanner is responsible for bi-lingual files (like XLFIFF) and <code>false</code> if is responsible for mono-lingual
     * files like java properties
     */
    boolean isBilingual();
    
    /**
     * creates a new PropertyConverter for the given file
     * @param file
     * @return
     */
    PropertyConverter createConverter(URI resource);
    
    /**
     * returns the default includes for the file scan
     * @return
     */
    String[] getDefaultIncludes();
    
    /**
     * returns the default excludes for the file scan
     * @return
     */
    String[] getDefaultExcludes();

    /**
     * determines which encoding should be used to write and write resources
     * @return
     */
    String getEncoding();
    
}
