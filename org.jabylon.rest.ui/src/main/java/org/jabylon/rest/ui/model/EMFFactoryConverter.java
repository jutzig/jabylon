/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;

public class EMFFactoryConverter<C> implements IConverter<C> {


    private static final long serialVersionUID = 1L;
    private String dataType;

    public EMFFactoryConverter(String dataType) {
        super();
        this.dataType = dataType;
    }


    @SuppressWarnings("unchecked")
    @Override
    public C convertToObject(String value, Locale locale) {
        EClassifier classifier = PropertiesPackage.eINSTANCE.getEClassifier(dataType);
        if (classifier instanceof EDataType) {
            EDataType dataType = (EDataType) classifier;
            return (C) PropertiesFactory.eINSTANCE.createFromString(dataType, value);
        }
        return null;
    }

    @Override
    public String convertToString(C value, Locale locale) {
        EClassifier classifier = PropertiesPackage.eINSTANCE.getEClassifier(dataType);
        if (classifier instanceof EDataType) {
            EDataType dataType = (EDataType) classifier;
            return PropertiesFactory.eINSTANCE.convertToString(dataType, value);
        }
        return null;
    }

}
