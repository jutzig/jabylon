/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.zip.ZipEntry;

import org.apache.wicket.model.IModel;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.xliff.PropertyWrapper;
import org.jabylon.properties.xliff.XliffReader;
import org.jabylon.properties.xliff.XliffZipInputStream;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.wicket.panels.PropertyListPanel;
import org.jabylon.rest.ui.wicket.xliff.XliffUploadResult.Level;
import org.xml.sax.SAXException;

/**
 * Processes the upload of a ZIP archive containing multiple XLIFF documents.<br>
 * {@link #handleUpload()} is basically Main() and will return a {@link List} of
 * {@link XliffUploadResult}s which can be used for displaying appropriate per {@link ZipEntry}
 * status messages.<br>
 *
 * @author c.samulski (2016-02-09)
 */
public final class XliffUploadHelper implements XliffUploadResultMessageKeys {
	/**
	 * The {@link ProjectVersion} we are are parsing the uploaded XLIFF file for.<br>
	 */
	private transient final ProjectVersion version;
	/**
	 * The request {@link InputStream} we will be reading the ZIP archive (containing the XLIFF
	 * translation files) from. We will *not* be closing this stream here.<br>
	 */
	private transient final XliffZipInputStream in;

	/**
	 * Filled during {@link #readFromStream()} and {@link #handleImport(Entry)} processing, used to
	 * notify UI about {@link ZipEntry}s which could not be parsed or matched successfully,
	 * respectively.<br>
	 */
	private transient final List<XliffUploadResult> uploadResult = new ArrayList<>();

	/**
	 * The only Constructor of this class.<br>
	 * Note that the {@link Resolvable} passed here must be of type {@link ProjectVersion}.<br>
	 * Currently, aggregated XLIFF uploads (and thus conversions into {@link Property}s) are only
	 * allowed on {@link ProjectVersion} level.<br>
	 */
	public XliffUploadHelper(IModel<Resolvable<?, ?>> projectVersion, InputStream in) {
		this.in = new XliffZipInputStream(new BufferedInputStream(in));
		this.version = (ProjectVersion) projectVersion.getObject();
	}
	
	/**
	 * Basically Main() for this class. Work order:<br>
	 * 
	 * 1. Consume request {@link InputStream}, read the ZIP contents into {@link PropertyWrapper}s.<br>
	 * 
	 * 2. Load matching {@link PropertyFileDescriptor}s.<br>
	 * 
	 * 3. Merge retrieved {@link Property}s into existing {@link PropertyFileDescriptor}.<br>
	 * 
	 * 4. Persist via {@link PropertyPersistenceService}.<br>
	 * 
	 * @return a {@link Map} of file names for {@link ZipEntry}s which could not be parsed or
	 *         matched to existing {@link PropertyFileDescriptor}s.<br>
	 */
	public final List<XliffUploadResult> handleUpload() throws IOException {
		try {
			Map<String, PropertyWrapper> wrappers = readFromStream();
			/* Here be per XLIFF file processing. */
			for (Map.Entry<String, PropertyWrapper> entry : wrappers.entrySet()) {
				handleImport(entry);
			}
		} catch (ExecutionException e) {
			if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			}
			throw new IOException("Failed to load property values", e);
		} finally {
			in.doClose();
		}

		return XliffUploadResult.sort(uploadResult);
	}

	/**
	 * Read files from ZIP, call {@link XliffReader} for each {@link ZipEntry}.<br>
	 */
	private Map<String, PropertyWrapper> readFromStream() throws IOException {
		Map<String, PropertyWrapper> ret = new HashMap<String, PropertyWrapper>();

		ZipEntry entry = null;
		while ((entry = in.getNextEntry()) != null) {

			if (entry.isDirectory()) {
				continue; // we only pass files to the parser.
			}

			String key = entry.getName(); 
			try {
				ret.put(key, XliffReader.read(in, StandardCharsets.UTF_8.displayName()));
			} catch (IOException e) {
				uploadResult.add(new XliffUploadResult(UNPARSABLE, Level.ERROR, key));
			} catch (SAXException e) {
				uploadResult.add(new XliffUploadResult(PARSE_SAX, Level.ERROR, key, e.getLocalizedMessage()));
			}
		}

		return ret;
	}

	/**
	 * Handles the per {@link ZipEntry} import of new {@link Property}s.<br>
	 */
	private void handleImport(Entry<String, PropertyWrapper> entry) throws ExecutionException {
		String fileName = getFileName(entry.getKey());
		/*
		 * Validate FileName first. Return to caller if not conform (i.e. ends in .xlf).
		 */
		if (fileName == null) {
			uploadResult.add(new XliffUploadResult(INVALID_FILENAME, Level.ERROR, entry.getKey()));
			return;
		}
		
		Locale locale = entry.getValue().getLocale();
		Map<String, Property> properties = entry.getValue().getProperties();
		/*
		 * Retrieve PropertyFileDescriptors for this locale.
		 */
		List<PropertyFileDescriptor> descriptors = version.getProjectLocale(locale).getDescriptors();
		/*
		 * Find the corresponding descriptor, match by fileName.
		 */
		for (PropertyFileDescriptor descriptor : descriptors) {
			if (fileName.equals(descriptor.getLocation().path())) {
				int updated = merge(properties, descriptor);

				if (updated == 0) { // no properties were updated!
					uploadResult.add(new XliffUploadResult(NO_PROPERTIES_UPDATED, Level.WARNING, fileName));
				} else { // we had matches!
					uploadResult.add(new XliffUploadResult(SUCCESS, Level.INFO, fileName, String.valueOf(updated)));
				}
				return;
			}
		}
		/*
		 * This ZipEntry could not be matched to any existing PropertyFile.
		 */
		uploadResult.add(new XliffUploadResult(NO_FILE_MATCH, Level.WARNING, fileName));
	}

	/**
	 * @return null if the filename does not end with ".xlf". Caller has to handle that.<br>
	 */
	private String getFileName(String key) {
		if (key.indexOf(".xlf") == -1) {
			return null;
		}
		return key.split(".xlf")[0];
	}

	/**
	 * Merges a {@link Map} of {@link Property}s into the existing {@link PropertyFileDescriptor}.<br>
	 * TODO: Possibly merge with persist logic in {@link PropertyListPanel}.<br>
	 * 
	 * @return count of updated properties.<br>
	 */
	private static int merge(Map<String, Property> newProperties, PropertyFileDescriptor descriptor)
			throws ExecutionException {
		PropertyFile existingFile = getPersistenceService().loadProperties(descriptor);
		Map<String, Property> existingProperties = existingFile.asMap();
		Map<String, Property> master = descriptor.getMaster().loadProperties().asMap();

		int updateCount = 0;

		for (Property newProperty : newProperties.values()) {
			Property existingProperty = existingProperties.get(newProperty.getKey());

			if (!master.containsKey(newProperty.getKey())) {
				continue; // we're not creating new K/V pairs, only updating.
			}

			if (!updatePropertyValue(newProperty, existingProperty)) {
				continue; // not setting null, but we allow empty (value deletion).
			}

			updateExistingProperty(existingFile, existingProperty, newProperty);
			updateCount++; // trigger persist.
		}

		if (updateCount > 0) {
			getPersistenceService().saveProperties(descriptor, existingFile);
		}

		return updateCount;
	}

	/**
	 * If we make it here, we know that the {@link Property} exists in the master
	 * {@link PropertyFileDescriptor}, but we don't know if a corresponding {@link Property} exists
	 * for the existing {@link PropertyFile}. If it does not, we create a new {@link Property}.<br>
	 * 
	 * Hence perform a quick check, and create a new {@link Property} which we add to this
	 * {@link PropertyFileDescriptor} if it does not yet exist.<br>
	 */
	private static void updateExistingProperty(PropertyFile existingFile, Property existingProperty, Property newProperty) {
		if (existingProperty == null) {
			existingProperty = PropertiesFactory.eINSTANCE.createProperty();
			existingProperty.setKey(newProperty.getKey());
			existingFile.getProperties().add(existingProperty);
		}

		existingProperty.setValue(newProperty.getValue());
		existingProperty.setComment(newProperty.getComment());
	}

	/**
	 * Decide if we need to update the old {@link Property} with the value of the new one.<br>
	 */
	private static boolean updatePropertyValue(Property newProperty, Property oldProperty) {
		String oldValue = oldProperty == null ? null : oldProperty.getValue();
		String newValue = newProperty == null ? null : newProperty.getValue();

		/* no translation value was sent. */
		if (newProperty == null) {
			return false;
		}

		/* if new is empty, but old is not, we will want to update */
		if (!hasValue(oldValue) && !hasValue(newValue)) {
			return false;
		}

		/* only update if values differ */
		return !newValue.equals(oldValue);
	}

	private static boolean hasValue(String value) {
		return value != null && !"".equals(value);
	}

	private static PropertyPersistenceService getPersistenceService() {
		return Activator.getDefault().getPersistenceService();
	}
}
