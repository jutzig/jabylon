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
package org.jabylon.properties.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class POHelper {


    private static final Pattern ID_PATTERN = Pattern.compile("msgid \"(.*)\"");
    private static final Pattern VALUE_PATTERN = Pattern.compile("msgstr \"(.*)\"");

    public Property readProperty(BufferedReader reader) throws IOException
    {
        String line = null;
        Property property = null;
        StringBuilder comment = new StringBuilder();
        StringBuilder propertyValue = new StringBuilder();
        while((line = reader.readLine())!=null)
        {
            line=line.trim();
            if(line.startsWith("\""))
                continue;
            if(line.length()==0)
                continue;
            if(isComment(line))
            {
                if(comment.length()>0) //there's already a comment, so now we have a new line
                    comment.append("\n");
                if(line.length()>1) //otherwise it's just an empty comment
                    comment.append(line.substring(1).trim());
            }
            else if(property==null)
            {
                Matcher matcher = ID_PATTERN.matcher(line);
                if(matcher.matches())
                {
                    property = PropertiesFactory.eINSTANCE.createProperty();
                    property.setKey(matcher.group(1).replace("\\n", "\n"));
                    if(comment.length()>0)
                    property.setComment(comment.toString());
                }
            }
            else
            {
                Matcher matcher = VALUE_PATTERN.matcher(line);
                if(matcher.matches())
                {
                    property.setValue(matcher.group(1).replace("\\n", "\n"));
                    return property;
                }
            }
        }
        return property;
    }

    private boolean isComment(String line) {
        return (line.startsWith("#") || line.startsWith("!"));
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        boolean template = false;
        URL url = new URL("http://pootle.locamotion.org/export/terminology/de/essential.po");
        InputStream openStream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(openStream, "UTF-8"));
        Property property = null;
        POHelper helper = new POHelper();
        PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
        while((property=helper.readProperty(reader))!=null)
        {
            if(template)
                property.setValue(property.getKey());
            file.getProperties().add(property);
        }
        PropertiesResourceImpl resource = new PropertiesResourceImpl(org.eclipse.emf.common.util.URI.createFileURI("messages.properties"));
        resource.getContents().add(file);
        resource.save(new HashMap<Object, Object>());

    }

}
