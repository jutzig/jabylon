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
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jabylon.properties.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author c.samulski (2016-01-30)
 */
public final class XliffWriter implements XliffXMLConstants {

	private XliffWriter() {} // no instantiation necessary

	/**
	 * Basically Main() for this class. Writes an XLIFF 1.2 xml document filled with source/target
	 * properties passed via the corresponding {@link PropertyWrapper}s to the {@link OutputStream}.<br>
	 * 
	 */
	public static final void write(OutputStream out, PropertyWrapper filteredSource, PropertyWrapper filteredTarget,
			String encoding) throws IOException {

		Element rootElement = writeDocument(filteredSource, filteredTarget);
		Transformer transformer = getTransformer();

		StreamResult result = new StreamResult(out);
		DOMSource domSource = new DOMSource(rootElement);

		try {
			transformer.transform(domSource, result);
		} catch (TransformerException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Creates a {@link Document}, writes source/target {@link Property}s to document, adhering to
	 * XLIFF 1.2 schemas.<br>
	 * 
	 * @return a populated "xliff" root {@link Element}.<br>
	 */
	private static Element writeDocument(PropertyWrapper filteredSource, PropertyWrapper filteredTarget) throws IOException {
		Document doc = createDocument();
		Element rootElement = createRootElement(doc);
		Element fileElement = createFileElement(doc, rootElement, filteredSource.getLocale(), filteredTarget.getLocale());
		Element bodyElement = doc.createElement(TAG_BODY);

		Set<Map.Entry<String, Property>> targetEntries = filteredTarget.getPropertyFile().entrySet();
		Map<String, Property> sourceEntries = filteredSource.getPropertyFile();

		/* Retrieve source and target translation values. */
		for (Map.Entry<String, Property> targetEntry : targetEntries) {
			String key = targetEntry.getKey();
			String targetValue = getPropertyValueNullAsEmpty(targetEntry.getValue());
			String sourceValue = getPropertyValueNullAsEmpty(sourceEntries.get(key));

			/* Create populated unit element with the given input */
			bodyElement.appendChild(createTransUnitElement(doc, key, sourceValue, targetValue));
		}

		fileElement.appendChild(bodyElement);
		rootElement.appendChild(fileElement);
		return rootElement;
	}

	/**
	 * @return a "file" {@link Element}, with sourceLanguage and targetLanguage attributes set.<br>
	 */
	private static Element createFileElement(Document doc, Element rootElement, Locale source, Locale target) {
		Element fileElement = doc.createElement(TAG_FILE);
		fileElement.setAttribute(ATT_DATATYPE, "plaintext");
		fileElement.setAttribute(ATT_SOURCE_LANGUAGE, getLanguageCode(source));
		fileElement.setAttribute(ATT_TARGET_LANGUAGE, getLanguageCode(target));
		fileElement.setAttribute(ATT_ORIGINAL, "properties");
		return fileElement;
	}

	private static Element createRootElement(Document doc) {
		Element rootElement = doc.createElement(TAG_XLIFF);
		rootElement.setAttribute(ATT_XMLNS, "urn:oasis:names:tc:xliff:document:1.2");
		rootElement.setAttribute(ATT_VERSION, "1.2");
		return rootElement;
	}

	/**
	 * @return a populated "unit" {@link Element}, with children and text content.<br>
	 */
	protected static final Element createTransUnitElement(Document doc, String key, String sourceValue, String targetValue) {
		Element transUnitElement = doc.createElement(TAG_TRANS_UNIT);
		Element sourceElement = doc.createElement(TAG_SOURCE);
		Element targetElement = doc.createElement(TAG_TARGET);

		sourceElement.setTextContent(sourceValue);
		targetElement.setTextContent(targetValue);
		transUnitElement.appendChild(sourceElement);
		transUnitElement.appendChild(targetElement);
		transUnitElement.setAttribute(ATT_ID, key);

		return transUnitElement;
	}

	private static Document createDocument() throws IOException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
	}

	private static Transformer getTransformer() throws IOException {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			return transformer;
		} catch (TransformerConfigurationException e) {
			throw new IOException(e);
		}
	}

	private static String getPropertyValueNullAsEmpty(Property value) {
		return value == null ? "" : value.getValue() != null ? value.getValue() : "";
	}

	/**
	 * TODO: Come up with a better solution. Dirty.
	 */
	private static String getLanguageCode(Locale locale) {
		if (locale == null) {
			return "template";
		}
		/* e.g. de_DE or en_US => try to split at _ */
		String[] code = locale.getLanguage().split("_");
		/* e.g. de or en, ISO3 as last resort */
		return code.length > 0 ? code[0] : locale.getISO3Country();
	}
}
