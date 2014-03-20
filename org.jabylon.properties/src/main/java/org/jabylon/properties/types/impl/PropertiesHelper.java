/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.properties.types.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyConverter;
import org.jabylon.properties.util.NativeToAsciiConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author joe
 *
 */
public class PropertiesHelper implements PropertyConverter {


    private boolean unicodeEscaping;
    private static final int MAX_BOM_LENGTH = 4;
    /** the license header at the beginning of the file (if any)*/
    private String licenseHeader;
    /** keeps track if we already checked for a license header*/
    private boolean checkedForHeader;
    /** to be able to log the path */
    private URI uri;
    /** current line number */
    private long lineNo = 0;

    final Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);

    public PropertiesHelper() {
        this(true);
        checkedForHeader = false;
    }

    public PropertiesHelper(boolean unicodeEscaping) {
        this(unicodeEscaping,null);
    }

    /**
     *
     * @param unicodeEscaping
     * @param uri can be used to log the path of invalid files if supplied
     */
    public PropertiesHelper(boolean unicodeEscaping, URI uri) {
        this.unicodeEscaping = unicodeEscaping;
        if(uri==null)
            uri = URI.createURI("NONE SUPPLIED");
        this.uri = uri;
    }

    
	public Property readProperty(BufferedReader reader) throws IOException
    {
        String line = null;
        Property property = null;
        StringBuilder comment = new StringBuilder();
        StringBuilder propertyValue = new StringBuilder();
        while((line = reader.readLine())!=null)
        {
            lineNo++;
            line=line.trim();
            if(line.length()==0)
            {
                if(!checkedForHeader)
                {
                    licenseHeader = comment.toString();
                    comment.setLength(0);
                    checkedForHeader = true;
                }
                else {
                    continue;
                }

            }
            if(isComment(line))
            {
                if(comment.length()>0) //there's already a comment, so now we have a new line
                    comment.append("\n");
                if(line.length()>1) //otherwise it's just an empty comment
                    comment.append(line.substring(1).trim());
            }
            else
            {
                propertyValue.append(NativeToAsciiConverter.convertEncodedToUnicode(line));
                if(line.endsWith("\\"))
                {
                    //if the line ends with a \ we need to continue reading in the next line
                    continue;
                }
                property = PropertiesFactory.eINSTANCE.createProperty();
                if(comment.length()>0)
                    property.setComment(comment.toString());
                if(propertyValue.length()==0)
                    continue;
                String[] parts = split(propertyValue.toString());
                if(parts == null || parts[0]==null) //invalid property
                {
                    logger.error("Invalid line {}: \"{}\" in property file \"{}\". Skipping", lineNo, propertyValue, uri);
                    propertyValue.setLength(0);
                    continue;
                }
                property.setKey(parts[0]);
                property.setValue(parts[1]);
                checkedForHeader = true;
                return property;
            }
        }
        //in some cases we already created an instance, but then never found a key
        //in that case, return null instead of an incomplete property
        //http://github.com/jutzig/jabylon/issues/issue/104
        if(property!=null && property.getKey()==null)
            return null;
        return property;
    }

	/**
	 * splits the property line into a key and value part
	 * @param propertyValue
	 * @return
	 */
    protected String[] split(String propertyValue) {
        boolean escape = false;
        StringBuilder buffer = new StringBuilder(propertyValue.length());
        String[] result = new String[2];
        for (char c : propertyValue.toCharArray()) {
            if(!escape)
            {
                if(result[0]==null && (c==':' || c=='='))
                {

                    String string = buffer.toString().trim();
                    if(string.length()>0)
                        result[0]=string;
                    else
                        return null;
                    buffer.setLength(0);
                    continue;
                }
                else if(c=='\\')
                {
                    escape = true;
                    continue;
                }
            }
            escape = false;
            buffer.append(c);
        }
        String string = buffer.toString();
        if(string.startsWith(" ")) //remove trailing space '= value'
            string = string.substring(1);

        if(string.length()>0)
            result[1] = string;
        return result;
    }

    protected boolean isComment(String line) {
        return (line.startsWith("#") || line.startsWith("!"));
    }

	public void writeProperty(Writer writer, Property property) throws IOException
    {
		writeCommentAndAnnotations(writer, property);
        String key = property.getKey();
        key = key.replaceAll("([ :=\n])", "\\\\$1");
        if(unicodeEscaping)
            writer.write(NativeToAsciiConverter.convertUnicodeToEncoded(key,true));
        else
            writer.write(key);
        writer.write(" = ");
        String value = property.getValue();
        if(value!=null)
        {
            //leading spaces need to be masked
            //see https://github.com/jutzig/jabylon/issues/186
            if(value.startsWith(" "))
                value = "\\"+value;
            value = value.replace("\r", "\\r");
            value = value.replace("\n", "\\n");
            if(unicodeEscaping)
                value = NativeToAsciiConverter.convertUnicodeToEncoded(value, true);
            writer.write(value);
        }
        writer.write('\n');

    }

	protected void writeCommentAndAnnotations(Writer writer, Property property) throws IOException {
        if(property.eIsSet(PropertiesPackage.Literals.PROPERTY__COMMENT) || property.getAnnotations().size()>0)
        {
        	StringBuilder builder = new StringBuilder();
        	for (PropertyAnnotation annotation : property.getAnnotations()) {
				builder.append(annotation);
			}
        	if(builder.length()>0)
        	{
        		builder.append("\n");
        	}
        	builder.append(property.getCommentWithoutAnnotations());
        	writeComment(writer,builder.toString());
        }
	}

    protected void writeComment(Writer writer, String comment) throws IOException {
        comment = comment.replace("\n", "\n#");
        writer.write("#");
        writer.write(comment);
        writer.write('\n');
    }


    /**
     * returns the BOM if available. If no BOM was found the stream is reset to its original state
     *
     * @param inputStream must support mark/rest, or an IllegalArgumentException is thrown
     * @return
     * @throws IOException, IllegalArgumentException
     */
    public static ByteOrderMark checkForBom(InputStream inputStream) throws IOException {
        if(!inputStream.markSupported())
            throw new IllegalArgumentException("InputStream must support mark/rest: "+inputStream);
        inputStream.mark(MAX_BOM_LENGTH);
        ByteOrderMark bom = ByteOrderMark.read(inputStream);
        if(bom==null)
            inputStream.reset();
        return bom;
    }

	public String getLicenseHeader() {
        return licenseHeader;
    }

	public void writeLicenseHeader(Writer writer, String licenseHeader) throws IOException {
        if(licenseHeader==null || licenseHeader.isEmpty())
            return;
        writeComment(writer, licenseHeader);
        writer.write('\n');
    }

    @Override
    public int write(OutputStream out, PropertyFile file, String encoding) throws IOException {
        int savedProperties = 0;
        BufferedWriter writer;
        if("UTF-8".equals(encoding)) {
        	//see https://github.com/jutzig/jabylon/issues/5
            //write BOMs in unicode mode
            out.write(ByteOrderMark.UTF_8.bytes());
        }	
        
        writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
        try {
                        
            writeLicenseHeader(writer, file.getLicenseHeader());
            Iterator<Property> it = file.getProperties().iterator();
            while (it.hasNext()) {
                Property property = (Property) it.next();
                //eliminate all empty property entries
                if(isFilled(property))
                {
                	writeProperty(writer, property);
                    savedProperties++;
                }

            }

        }
        finally{
            writer.close();
        }
    	return savedProperties;
    }

    protected boolean isFilled(Property property) {
        if(property==null)
            return false;
        if(property.getKey()==null || property.getKey().length()==0)
            return false;
        return !(property.getValue()==null || property.getValue().length()==0);
    }
 

	@Override
    public PropertyFile load(InputStream in, String encoding) throws IOException {
        if(!in.markSupported())
            in = new BufferedInputStream(in);
        ByteOrderMark bom = PropertiesHelper.checkForBom(in);
        String derivedEncoding = deriveEncoding(bom);
        if(derivedEncoding!=null){
        	if(encoding.equals("UTF-16") && derivedEncoding.startsWith("UTF-16"))
        		//the derived encoding will know if it's BE or LE
        		encoding = derivedEncoding;
        	else if(!encoding.equals(derivedEncoding)){
        		logger.warn("Encoding was specified as {} but according to the BOM it is {}. Using the value from the BOM instead",encoding,derivedEncoding);
        		encoding = derivedEncoding;
        	}
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in,encoding));
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        try {
            Property p = null;
            while((p = readProperty(reader))!=null)
            {
                file.getProperties().add(p);
            }
        } finally{
            if(reader!=null)
                reader.close();
        }
        file.setLicenseHeader(getLicenseHeader());
        return file;
    }
    
	
	protected String deriveEncoding(ByteOrderMark bom) {
		if(bom==null)
			return null;
		switch (bom) {
		case UTF_16BE:
			return "UTF-16BE";

		case UTF_16LE:
			return "UTF-16LE";
		case UTF_8:
			return "UTF-8";
		}
		return null;
	}

	public boolean isUnicodeEscaping() {
		return unicodeEscaping;
	}
    
}
