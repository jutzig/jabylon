/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.xliff;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Converts a single XLIFF 1.2 Document (read from an {@link InputStream}) into {@link Property}s.<br>
 * 
 * @author c.samulski (2016-02-08)
 */
public final class XliffReader implements XliffXMLConstants {

	private XliffReader() {
	} // no instantiation necessary

	/**
	 * Converts the incoming XLIFF document into a {@link PropertyFile} POJO.<br>
	 */
	public static final PropertyWrapper read(InputStream in, String encoding) throws IOException, SAXException {
		/*
		 * 1. Create Document, validate and parse.
		 */
		DocumentBuilder builder = getDocumentBuilder();
		Element root = builder.parse(in).getDocumentElement();
		Element fileElement = (Element) getChildNodeByTagName(root, TAG_FILE);

		/*
		 * 2. Validate fileElement. If it's null, we throw a SAXException to produce a friendly UI
		 * error (instead of throwing NPEs up the stack). Frankly we should be XSD-validating here, but
		 * the official XLIFF 1.2 XSD's license terms are not exactly transparent.
		 */
		if (fileElement == null) {
			throw new SAXException("Element <file> null.");
		}

		/*
		 * 2. Retrieve targetLocale.
		 */
		Locale locale = getTargetLocale(fileElement);
		/*
		 * 3. Retrieve Properties, return.
		 */
		Element bodyElement = (Element) getChildNodeByTagName(fileElement, TAG_BODY);
		Map<String, Property> properties = readProperties(bodyElement);
		return new PropertyWrapper(locale, properties);
	}

	/**
	 * Parses "TransUnit" {@link Element}s and Populates a {@link Map} of {@link Property}s. <br>
	 * Sadly we have to perform "manual" validation here as the XLIFF 1.2 XSD's license terms are
	 * ambiguous and we cannot simply do a schema validation when calling
	 * {@link DocumentBuilder#parse(InputStream)} .<br>
	 */
	private static Map<String, Property> readProperties(Element bodyElement) {
		NodeList transUnits = bodyElement.getElementsByTagName(TAG_TRANS_UNIT);
		Map<String, Property> ret = new HashMap<>();

		/* TransUnit sequence is minOccurs=0 */
		if (transUnits == null) {
			return ret;
		}

		for (int i = 0; i < transUnits.getLength(); i++) {
			Element transUnit = (Element) transUnits.item(i);
			/*
			 * 1. Retrieve target element, it's the only one we will import.
			 */
			Node targetElement = getChildNodeByTagName(transUnit, TAG_TARGET);
			if (targetElement == null) {
				continue;
			}
			/*
			 * 2. Check if ID (NLS key) is set.
			 */
			String key = transUnit.getAttribute(ATT_ID);
			if (!hasValue(key)) {
				continue;
			}
			/**
			 * 3. Check if our TransUnit element has text content.
			 */
			String value = getTranslationFromTargetElement(targetElement);
			if (!hasValue(value)) {
				continue;
			}
			/*
			 * 4. Add the parsed property.
			 */
			Property property = newProperty(key, value);
			ret.put(property.getKey(), property);
		}

		return ret;
	}

	/**
	 * Translation values may be found either under:<br>
	 * trans-unit > target > "value" OR<br>
	 * trans-unit > target > mrk > "value"<br>
	 */
	private static String getTranslationFromTargetElement(Node targetElement) {
		/* Check if target element itself has text content. */
		String value = getNodeValue(targetElement);
		if (hasValue(value)) {
			return value;
		}

		/* Check if a direct "mrl" child exists, retrieve text content from that. */
		Node mrkElement = getChildNodeByTagName(targetElement, TAG_MRK);
		if (mrkElement == null) {
			return null;
		}

		return getNodeValue(mrkElement);
	}

	/**
	 * Creates and returns a new {@link Property} based on key and value inputs.<br>
	 */
	private static Property newProperty(String key, String value) {
		Property property = PropertiesFactory.eINSTANCE.createProperty();
		property.setKey(key);
		property.setValue(value);
		return property;
	}

	/**
	 * Retrieve {@link Locale} from this {@link Element}s
	 * {@link XliffXMLConstants#ATT_TARGET_LANGUAGE} attribute.<br>
	 */
	private static Locale getTargetLocale(Element fileElement) throws IOException {
		String targetLanguage = fileElement.getAttribute(ATT_TARGET_LANGUAGE);

		if (targetLanguage.indexOf("_") != -1) {
			return parseLocale(targetLanguage, "_");
		}

		if (targetLanguage.indexOf("-") != -1) {
			return parseLocale(targetLanguage, "-");
		}

		return new Locale(targetLanguage);
	}

	/**
	 * Return new {@link Locale} for an arbitrary language and country code string.<br>
	 * TODO: Might want to use org.apache.commons.lang.LocaleUtils for this. Kind of dirty, but
	 * covers most cases.<br>
	 */
	private static Locale parseLocale(String targetLanguage, String split) {
		String[] locale = targetLanguage.split(split);
		if (locale.length == 3) { // language, country, variant.
			return new Locale(locale[0], locale[1], locale[3]);
		} else if (locale.length == 2) { // language, country.
			return new Locale(locale[0], locale[1]);
		} else {
			return new Locale(locale[0]);
		}
	}

	/**
	 * Helper to retrieve *first* child {@link Node} by specified tag name.<br>
	 * Explicitly not calling {@link Element#getElementsByTagName(String)} as this would traverse
	 * children until any {@link Element} is found with the given name.<br>
	 */
	private static Node getChildNodeByTagName(Node node, String tagName) {
		Node child = node.getFirstChild();
		while (child != null) {
			if (tagName.equals(child.getNodeName())) {
				return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	/**
	 * Helper to retrieve *this* {@link Node}s text content.<br>
	 * Explicitly not calling {@link Element#getTextContent()} as this would traverse children until
	 * any is found with text content.<br>
	 */
	private static String getNodeValue(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeType() == Node.TEXT_NODE) {
				return child.getNodeValue().trim();
			}
			child = child.getNextSibling();
		}
		return "";
	}

	private static boolean hasValue(String value) {
		return value != null && !"".equals(value);
	}

	private static DocumentBuilder getDocumentBuilder() throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(false);
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
	}
}