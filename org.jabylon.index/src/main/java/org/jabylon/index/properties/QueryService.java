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
package org.jabylon.index.properties;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Query;
import org.eclipse.core.runtime.IProgressMonitor;

import org.jabylon.properties.PropertyFileDescriptor;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface QueryService {

    String FIELD_VALUE = "value";
    String FIELD_MASTER_VALUE = "masterValue";
    String FIELD_KEY = "key";
    String FIELD_ANALYZED_KEY = "analyzedkey";
    String FIELD_URI = "uri";
    String FIELD_FULL_PATH = "path";
    String FIELD_COMMENT = "comment";
    String FIELD_MASTER_COMMENT = "masterComment";
    String FIELD_LOCALE = "locale";
    
    String FIELD_VERSION = "version";
    String FIELD_PROJECT = "project";
    String FIELD_CDO_ID = "cdoID";

    /**
     * location of the template file, or empty string if there is none
     * @see org.jabylon.properties.PropertyFileDescriptor#getLocation()
     */
    String FIELD_TEMPLATE_LOCATION = "templatePath";
    String MASTER = "master";
    /**
     * if the value is true, marks that the document was produced by a tmx file and not a normal project
     */
    String FIELD_TMX = "tmx";
    /**
     * when dealing with tmx we have src and target language in one file.
     * The locale is "template" and the other locale is stored in this field
     */
    String FIELD_TMX_LOCALE = "target.locale";
    /**
     * when dealing with tmx we have src and target language in one file.
     * The normal value represents the template value and the translated value is stored here
     */
    String FIELD_TMX_VALUE = "tmx.locale";
    
    SearchResult search(String search, String scopeURI);

    SearchResult search(Query query, int maxHits);

    PropertyFileDescriptor getDescriptor(Document doc);

    /**
     * recreates the complete search index
     * @param monitor
     * @throws CorruptIndexException
     * @throws IOException
     */
	void rebuildIndex(IProgressMonitor monitor) throws CorruptIndexException, IOException;

}
