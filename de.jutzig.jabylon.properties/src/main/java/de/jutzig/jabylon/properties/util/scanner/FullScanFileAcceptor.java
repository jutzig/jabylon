package de.jutzig.jabylon.properties.util.scanner;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.types.PropertyScanner;

public class FullScanFileAcceptor extends AbstractScanFileAcceptor {

	public FullScanFileAcceptor(ProjectVersion projectVersion, PropertyScanner scanner, ScanConfiguration config) {
		super(projectVersion, scanner, config);
	}

	@Override
	public void newMatch(File file) {

		URI location = calculateLocation(file);
		if (getProjectVersion().getTemplate() == null) {
			getProjectVersion().setTemplate(PropertiesFactory.eINSTANCE.createProjectLocale());
			getProjectVersion().getTemplate().setName("template");
			getProjectVersion().getChildren().add(getProjectVersion().getTemplate());
		}
		PropertyFileDescriptor descriptor = createDescriptor(getProjectVersion().getTemplate(), location);
		getProjectVersion().getTemplate().getDescriptors().add(descriptor);

		// load file to initialize statistics;
		PropertyFile propertyFile = descriptor.loadProperties();
		descriptor.setKeys(propertyFile.getProperties().size());
		descriptor.updatePercentComplete();

		Locale locale = getPropertyScanner().getLocale(file);
		if (locale!=null) {
			descriptor.setVariant(locale);
		}

		Map<Locale, File> translations = getPropertyScanner().findTranslations(file, getScanConfig());
		Set<Entry<Locale, File>> set = translations.entrySet();
		for (Entry<Locale, File> entry : set) {
			ProjectLocale projectLocale = getOrCreateProjectLocale(entry.getKey());
			URI childURI = calculateLocation(entry.getValue());
			PropertyFileDescriptor fileDescriptor = createDescriptor(projectLocale, childURI);
			fileDescriptor.setMaster(descriptor);

			// load file to initialize statistics;
			PropertyFile translatedFile = fileDescriptor.loadProperties();
			int size = translatedFile.getProperties().size();
			fileDescriptor.setKeys(size);
		}

	}



}