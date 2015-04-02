/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.types.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyAnnotation;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyAnnotations;
import org.jabylon.properties.types.PropertyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class TMXConverter implements PropertyConverter {

	
	private static final Logger LOG = LoggerFactory.getLogger(TMXConverter.class);
	
	/* (non-Javadoc)
	 * @see org.jabylon.properties.types.PropertyConverter#load(java.io.InputStream, java.lang.String)
	 */
	@Override
	public PropertyFile load(InputStream in, String encoding) throws IOException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
			TMXHandler handler = new TMXHandler(file);
			parser.getXMLReader().setEntityResolver(handler);
			parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			parser.parse(in, handler);
			return file;
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.jabylon.properties.types.PropertyConverter#write(java.io.OutputStream, org.jabylon.properties.PropertyFile, java.lang.String)
	 */
	@Override
	public int write(OutputStream out, PropertyFile file, String encoding) throws IOException {
		
		throw new UnsupportedOperationException("not yet implemented");
	}

	static class TMXHandler extends DefaultHandler
	{
		private PropertyFile file;
		private String srcLanguage;
		private Property current;
		private String currentLanguage;
		private String targetLanguage;
		private StringBuilder currentValue;

		public TMXHandler(PropertyFile file) {
			this.file = file;
		}
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if(is("header",uri,qName,localName))
			{
				srcLanguage = attributes.getValue("", "srclang");
			}
			else if(is("tu",uri,qName,localName))
			{
				current = PropertiesFactory.eINSTANCE.createProperty();
			}
			else if(is("tuv",uri,qName,localName)) 
			{
				currentLanguage = attributes.getValue("xml:lang");
			}
			else if(is("seg",uri,qName,localName)) 
			{
				currentValue = new StringBuilder();
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if(currentValue!=null)
				currentValue.append(ch, start, length);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if(is("tu",uri,qName,localName))
			{
				if(current!=null)
				{
					if(current.getValue()==null || current.getKey()==null)
					{
						LOG.error("Invalid property in TMX file: "+current);
					}
					else
					{
						PropertyAnnotation annotation = PropertiesFactory.eINSTANCE.createPropertyAnnotation();
						annotation.setName(PropertyAnnotations.ANNOTATION_LANGUAGE);
						annotation.getValues().put(PropertyAnnotations.SOURCE_LANGUAGE, normalizeLanguage(srcLanguage));
						annotation.getValues().put(PropertyAnnotations.TARGET_LANGUAGE, normalizeLanguage(targetLanguage));
						current.getAnnotations().add(annotation);
						file.getProperties().add(current);	
					}
				}
				current = null;
			}
			else if(is("seg",uri,qName,localName)) 
			{
				if(currentValue!=null && current!=null)
				{
					if(currentLanguage!=null && currentLanguage.equals(srcLanguage))
					{
						current.setKey(currentValue.toString());
					}
					else
					{
						targetLanguage = currentLanguage;
						current.setValue(currentValue.toString());
					}
					currentValue = null;
				}
			}
		}
		
		private String normalizeLanguage(String xmlLang) {
			if(xmlLang==null || xmlLang.isEmpty())
				return "en";
			return Locale.forLanguageTag(xmlLang).toString();
		}

		protected boolean is(String expextedElementName, String uri, String qname, String localname)
		{
			return expextedElementName.equals(localname) || expextedElementName.equals(qname);
				
		}
		
		
		@Override
		public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
			return null;
		}
	}
	
}


