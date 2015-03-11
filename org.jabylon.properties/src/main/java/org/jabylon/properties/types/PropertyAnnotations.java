/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PropertyAnnotations {

	private PropertyAnnotations(){};
	
	/**
	 * this annotation is used for bilingual files to mark the language of
	 * source, target, or both
	 */
	public static final String ANNOTATION_LANGUAGE = "language";	
	
	/**
	 * this annotation property is used for bilingual files to mark the language of
	 * the source language
	 */
	public static final String SOURCE_LANGUAGE = "source";
	
	/**
	 * this annotation property is used for bilingual files to mark the language of
	 * the source language
	 */
	public static final String TARGET_LANGUAGE = "target";
}
