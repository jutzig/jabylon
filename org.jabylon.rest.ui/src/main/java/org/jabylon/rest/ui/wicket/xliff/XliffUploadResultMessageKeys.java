/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

/**
 * Keys for localized messages to be displayed after an XLIFF file upload.<br>
 * 
 * @author c.samulski (2016-02-14)
 */
public interface XliffUploadResultMessageKeys {
	String NO_FILE_MATCH = "xliff.upload.error.nofilematch";
	String PARSE_SAX = "xliff.upload.error.sax";
	String UNPARSABLE = "xliff.upload.error.unparsable";
	String INVALID_FILENAME = "xliff.error.upload.invalid.filename";
	String SUCCESS = "xliff.upload.success";
	String NO_PROPERTIES_UPDATED = "xliff.upload.no.properties.updated";
}
