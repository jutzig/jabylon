/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyConverter;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScanner;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.util.PropertiesResourceFactoryImpl
 * @generated
 */
public class PropertiesResourceImpl extends ResourceImpl {

    //TODO: this is a dirty hack...
    private int savedProperties;

    public static final String OPTION_FILEMODE = "file.mode";
    public static final String OPTION_ENCODING = "encoding";

    /**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
    public PropertiesResourceImpl(URI uri) {
		super(uri);
	}


    @Override
    protected void doLoad(InputStream inputStream, Map<?, ?> options)
            throws IOException {
        String type = getPropertyType(options);
        PropertyScanner scanner = PropertyResourceUtil.createScanner(type);
        PropertyConverter converter = scanner.createConverter(getURI());
        PropertyFile file = converter.load(inputStream, scanner.getEncoding());
        getContents().add(file);
    }

    private String getPropertyType(Map<?, ?> options) {
        if(options!=null && options.containsKey(OPTION_FILEMODE))
        {
            return (String) options.get(OPTION_FILEMODE);
        }
        return JavaPropertyScanner.TYPE;
    }


    @Override
    protected void doSave(OutputStream outputStream, Map<?, ?> options)
            throws IOException {
        savedProperties = 0;
        String type = getPropertyType(options);
        PropertyScanner scanner = PropertyResourceUtil.createScanner(type);
        String encoding = scanner.getEncoding();
        PropertyConverter converter = scanner.createConverter(getURI());	
        
        try {
            PropertyFile file = (PropertyFile) getContents().get(0);    
            savedProperties = converter.write(outputStream, file,encoding);
        }
        finally{
            outputStream.close();
        }

    }

    public int getSavedProperties() {
        return savedProperties;
    }

} //PropertiesResourceImpl
