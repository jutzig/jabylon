/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyConverter;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScanner;
import org.jabylon.properties.types.impl.PropertiesHelper;

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
        InputStream in = inputStream;
        if(!in.markSupported())
            in = new BufferedInputStream(in);
        //TODO: should we do anything with the bom? Set to Unicode?
        PropertiesHelper.checkForBom(in);

        BufferedReader reader = new BufferedReader(new InputStreamReader(in,scanner.getEncoding()));
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        try {
            Property p = null;
            while((p = converter.readProperty(reader))!=null)
            {
                file.getProperties().add(p);
            }
        } finally{
            if(reader!=null)
                reader.close();
        }
        file.setLicenseHeader(converter.getLicenseHeader());
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
        BufferedWriter writer;
        PropertyScanner scanner = PropertyResourceUtil.createScanner(type);
        String encoding = scanner.getEncoding();
        if("UTF-8".equals(encoding)) {
        	//see https://github.com/jutzig/jabylon/issues/5
            //write BOMs in unicode mode
            outputStream.write(ByteOrderMark.UTF_8.bytes());
        }
        PropertyConverter converter = scanner.createConverter(getURI());		
        writer = new BufferedWriter(new OutputStreamWriter(outputStream, encoding));
        try {
            PropertyFile file = (PropertyFile) getContents().get(0);
            converter.writeLicenseHeader(writer, file.getLicenseHeader());
            Iterator<Property> it = file.getProperties().iterator();
            while (it.hasNext()) {
                Property property = (Property) it.next();
                //eliminate all empty property entries
                if(isFilled(property))
                {
                	converter.writeProperty(writer, property);
                    savedProperties++;
                }

            }

        }
        finally{
            writer.close();
        }

    }


    private boolean isFilled(Property property) {
        if(property==null)
            return false;
        if(property.getKey()==null || property.getKey().length()==0)
            return false;
        return !(property.getValue()==null || property.getValue().length()==0);
    }

    public int getSavedProperties() {
        return savedProperties;
    }

} //PropertiesResourceImpl
