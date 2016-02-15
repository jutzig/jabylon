/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.xliff;

/**
 * Holds finals for XML tag names and attributes.<br>
 * 
 * @author c.samulski (2016-01-21)
 */
public interface XliffXMLConstants {
	String TAG_XLIFF = "xliff";
	String TAG_FILE = "file";
	String TAG_TRANS_UNIT = "trans-unit";
	String TAG_BODY = "body";
	String TAG_SOURCE = "source";
	String TAG_TARGET = "target";
	String TAG_MRK = "mrk";
	
	String ATT_XMLNS = "xmlns";
	String ATT_SOURCE_LANGUAGE = "source-language";
	String ATT_TARGET_LANGUAGE = "target-language";
	String ATT_VERSION = "version";
	String ATT_DATATYPE = "datatype";
	String ATT_ORIGINAL = "original";
	String ATT_ID = "id";
}