/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.wicket.model.IModel;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Review;
import org.jabylon.properties.xliff.PropertyWrapper;
import org.jabylon.properties.xliff.XliffResourceImpl;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.wicket.panels.PropertyListMode;

/**
 * Exports a project as an archive containing xliff files
 *
 * @author c.samulski (29.01.2016)
 */
public final class XliffDownloadHelper {

	/**
	 * The {@link ProjectVersion} we are preparing the XLIFF conversion and download for.<br>
	 */
	private transient final ProjectVersion projectVersion;
	/**
	 * The set {@link Map} of language tuples for which we will be retrieving and converting
	 * {@link Property}s.<br>
	 */
	private transient final Map<Language, Language> languageTuples;
	/**
	 * The {@link PropertyListMode} a user may have selected, e.g. {@link PropertyListMode#ALL},
	 * {@link PropertyListMode#FUZZY}, or {@link PropertyListMode#MISSING}.<br>
	 */
	private transient final PropertyListMode filter;

	/**
	 * The response {@link OutputStream} which the ZIP file will be written to.<br>
	 */
	private transient final ZipOutputStream zipOut;

	/**
	 * The only Constructor of this class.<br>
	 * Note that the {@link Resolvable} passed here must be of type {@link ProjectVersion}.<br>
	 * Currently, aggregated XLIFF conversions/downloads are only allowed on {@link ProjectVersion}
	 * level.<br>
	 */
	public XliffDownloadHelper(IModel<Resolvable<?, ?>> projectVersion, Map<Language, Language> languageTuples,
			PropertyListMode filter, OutputStream out) {
		/**
		 * Create ZipStream to write ZIP file to the response OutputStream.<br>
		 */
		this.zipOut = new ZipOutputStream(new BufferedOutputStream(out));
		/**
		 * Do the cast to ProjectVersion here.<br>
		 */
		this.projectVersion = ((ProjectVersion) projectVersion.getObject());

		this.languageTuples = languageTuples;
		this.filter = filter;
	}

	public PropertyPersistenceService getPersistenceService() {
		return Activator.getDefault().getPersistenceService();
	}

	/**
	 * Basically Main() for this class. The only public method.<br>
	 * Calls "Properties to XLIFF Conversion" for every source/target language tuple, write to ZIP
	 * stream.<br>
	 */
	public final void handleXliffDownload() throws IOException {
		try {
			for (Map.Entry<Language, Language> tuple : languageTuples.entrySet()) {
				convertAndZip(tuple);
			}
			zipOut.finish();
		} catch (ExecutionException e) {
			if(e.getCause() instanceof IOException)
				throw (IOException)e.getCause();
			throw new IOException("Failed to load property values",e);
		} finally {
			zipOut.close();
		}
	}

	/**
	 * Retrieves {@link Property}s for the passed {@link Language} tuples, matches source and target
	 * language module files, passes them to
	 * {@link #writeZipEntry(PropertyFileDescriptor, PropertyFileDescriptor, String, PropertyListMode, ZipOutputStream)}
	 * for XLIFF conversion and the writing of a ZIP entry to the stream.<br>
	 * @throws ExecutionException
	 */
	private void convertAndZip(Map.Entry<Language, Language> tuple) throws IOException, ExecutionException {
		Locale targetLocale = getLocale(tuple.getKey());
		Locale sourceLocale = getLocale(tuple.getValue());

		Map<String, PropertyFileDescriptor> targetDescriptors = loadDescriptorsForLocale(targetLocale);
		Map<String, PropertyFileDescriptor> sourceDescriptors = loadDescriptorsForLocale(sourceLocale);

		/* Iterate over target descriptors. */
		for (Map.Entry<String, PropertyFileDescriptor> target : targetDescriptors.entrySet()) {
			String templatePath = target.getKey();
			PropertyFileDescriptor targetDescriptor = target.getValue();
			PropertyFileDescriptor sourceDescriptor = sourceDescriptors.get(templatePath);
			writeZipEntry(targetDescriptor, sourceDescriptor, targetLocale, sourceLocale);
		}
	}


	/**
	 * Write a single XLIFF file as {@link ZipEntry} to our {@link ZipOutputStream}.<br>
	 * Conversion from source/target {@link PropertyFileDescriptor}s to XLIFF is done on the fly.<br>
	 * @throws ExecutionException
	 */
	private void writeZipEntry(PropertyFileDescriptor targetDescriptor, PropertyFileDescriptor sourceDescriptor,
			Locale targetLocale, Locale sourceLocale) throws IOException, ExecutionException {
		/*
		 * 1. Target language: Retrieve filtered list of properties.<br>
		 * 2. Source language: Retrieve filtered list based on filtered target language keys.<br>
		 */
		try {
			PropertyWrapper target = new PropertyWrapper(targetLocale, filterTarget(targetDescriptor));
			PropertyWrapper source = new PropertyWrapper(sourceLocale, filterSource(sourceDescriptor, target.getProperties().keySet()));
			/*
			 * 3. Retrieve properly formatted filename. (module_srcLang_trgLang.xml) <br>
			 * 4. Create new ZipEntry.<br>
			 */
			String fileNameWithPath = getXliffFileName(sourceDescriptor, targetDescriptor);
			ZipEntry entry = new ZipEntry(fileNameWithPath);
			zipOut.putNextEntry(entry);
			/*
			 * 5. Call Property to XLIFF converter (will write to stream).<br>
			 */
			new XliffResourceImpl().write(source, target, zipOut);
			zipOut.closeEntry();
		} catch (ExecutionException e) {
			if(e.getCause() instanceof IOException)
				throw (IOException)e.getCause();
			throw new IOException("Failed to load property values",e);
		}
	}

	/**
	 * Helper to reduce the Map of {@link Property}s retrieved from the passed
	 * {@link PropertyFileDescriptor} to match the passed keys.<br>
	 *
	 * (Could frankly be omitted for now, as we only get via keys later on anyway. (just for
	 * clarity).<br>
	 *
	 * 1. Load complete Map of source language properties.<br>
	 * 2. Filter them according to the keySet parameter and return the reduced map.<br>
	 * @throws ExecutionException
	 */
	private Map<String, Property> filterSource(PropertyFileDescriptor sourceDescriptor, Set<String> keySet) throws ExecutionException {
		Map<String, Property> filtered = new LinkedHashMap<String, Property>(keySet.size(),1f);
		Map<String, Property> sourceProperties = getPersistenceService().loadProperties(sourceDescriptor).asMap();

		for (String key : keySet) {
			Property value = sourceProperties.get(key);
			filtered.put(key, value);
		}

		return filtered;
	}

	/**
	 * Filters the {@link Property}s retrieved via the passed {@link PropertyFileDescriptor}
	 * according to the passed {@link PropertyListMode}.<br>
	 *
	 * @return {@link Map} of filtered {@link Property}s.
	 * @throws ExecutionException
	 */
	private Map<String, Property> filterTarget(PropertyFileDescriptor descriptor) throws ExecutionException {
		/*
		 * 1. Instantiate the return collection. Retain order.<br>
		 */
		Map<String, Property> filtered = new LinkedHashMap<String, Property>();
		/*
		 * 2. Get master properties and this locale descriptor's reviews.<br>
		 */
		Map<String, List<Review>> reviews = reviewsAsMap(descriptor.getReviews());
		PropertyFileDescriptor master = descriptor.getMaster();
		List<Property> templateProperties = getPersistenceService().loadProperties(master).getProperties();
		/*
		 * 3. Load translated properties.<br>
		 */
		Map<String, Property> translated = getPersistenceService().loadProperties(descriptor).asMap();
		/*
		 * 4. Iterate over template properties, check if the given filter applies, add to our return
		 * collection if true.<br>
		 */
		for (Property property : templateProperties) {
			Property translatedProperty = translated.get(property.getKey());
			PropertyPair pair = new PropertyPair(property, translatedProperty, descriptor.getVariant(), descriptor.cdoID());
			if (filter.apply(pair, reviews.get(pair.getKey()))) {
				filtered.put(property.getKey(), translated.get(property.getKey()));
			}
		}

		return filtered;
	}

	/**
	 * Helper method. Create Map with K=Template Name, V=Descriptor.<br>
	 *
	 * This associates a property descriptor with the path of its respective template for faster lookups.<br>
	 */
	private Map<String, PropertyFileDescriptor> loadDescriptorsForLocale(Locale locale) {
		Map<String, PropertyFileDescriptor> ret = new LinkedHashMap<String, PropertyFileDescriptor>();
		List<PropertyFileDescriptor> descriptors = null;

		/*
		 * Load the List of descriptors available for this locale.<br>
		 * Note that a null locale implies that either no source language was selected, or the
		 * selected source language is the template language.<br>
		 */
		if (locale == null) {
			descriptors = projectVersion.getTemplate().getDescriptors();
		} else {
			descriptors = projectVersion.getProjectLocale(locale).getDescriptors();
		}

		for (PropertyFileDescriptor descriptor : descriptors) {
			ret.put(descriptor.isMaster() ? descriptor.getLocation().toString() : descriptor.getMaster().getLocation().toString(), descriptor);
		}
		return ret;
	}

	/**
	 * Helper method. Construct a filename for *this* particular XLIFF translation file.<br>
	 * (Not the fileName of the ZIP to be served later).<br>
	 * Format will be [moduleName]_[targetLangISO].properties.xml
	 */
	private static String getXliffFileName(PropertyFileDescriptor sourceDescriptor, PropertyFileDescriptor targetDescriptor) {
		return targetDescriptor.getLocation().path() + ".xml";
	}

	/**
	 * Helper method. Converts {@link List} to {@link Map} to be consumed by
	 * {@link PropertyListMode#apply(PropertyPair, java.util.Collection)}
	 */
	private static Map<String, List<Review>> reviewsAsMap(List<Review> reviews) {
		Map<String, List<Review>> ret = new HashMap<String, List<Review>>();
		for (Review r : reviews) {
			List<Review> value = new ArrayList<Review>();
			value.add(r);
			ret.put(r.getKey(), value);
		}
		return ret;
	}

	/**
	 * Helper method. Return {@link Locale} from {@link Language} or null if {@link Language} is
	 * null.<br>
	 */
	private static Locale getLocale(Language language) {
		return language == null ? null : language.getLocale();
	}
}
