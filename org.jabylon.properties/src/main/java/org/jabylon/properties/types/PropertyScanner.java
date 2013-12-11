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

import org.jabylon.properties.ScanConfiguration;

public interface PropertyScanner {

    boolean isTemplate(File propertyFile, ScanConfiguration config);

    boolean isTranslation(File propertyFile, ScanConfiguration config);

    File findTemplate(File propertyFile, ScanConfiguration config);

    Map<Locale, File> findTranslations(File template, ScanConfiguration config);

    File computeTranslationPath(File template, Locale templateLocale, Locale translationLocale);

    Locale getLocale(File propertyFile);

}
