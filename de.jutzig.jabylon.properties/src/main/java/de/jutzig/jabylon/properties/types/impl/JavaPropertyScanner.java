package de.jutzig.jabylon.properties.types.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.properties.types.PropertyScanner;

public class JavaPropertyScanner implements PropertyScanner {

	
	private static final Pattern LOCALE_PATTERN = Pattern.compile("(.+?)((?:_\\w\\w){0,3})(\\..+)");
	
	@Override
	public boolean isTemplate(File propertyFile, ScanConfiguration config) {
		return matchesLocale(propertyFile.getName(), config.getMasterLocale());
	}

	@Override
	public boolean isTranslation(File propertyFile, ScanConfiguration config) {
		Locale locale = getLocale(propertyFile);
		return locale!=null && !locale.toString().equals(config.getMasterLocale());
	}

	@Override
	public File findTemplate(File propertyFile, ScanConfiguration config) {
		Matcher matcher = LOCALE_PATTERN.matcher(propertyFile.getName());
		if(!matcher.matches())
			return null;
		String prefix = matcher.group(1);
		String suffix = matcher.group(3);
		StringBuilder filename = new StringBuilder(prefix);
		if(config.getMasterLocale()!=null)
		{
			filename.append("_");
			filename.append(config.getMasterLocale());
		}
		filename.append(suffix);
		return new File(propertyFile.getParentFile(),filename.toString());
	}



	private boolean matchesLocale(String f, String desiredLocale) {

		if (desiredLocale == null)
		{
			Matcher matcher = LOCALE_PATTERN.matcher(f);
			return matcher.matches() && matcher.group(2).isEmpty();
		}

		Matcher matcher = LOCALE_PATTERN.matcher(f);
		if (matcher.matches()) {
			String actualLocale = matcher.group(1);
			actualLocale = actualLocale.substring(1);
			return actualLocale.equals(desiredLocale);
		}
		return false;
	}

	@Override
	public Locale getLocale(File propertyFile) {
		Matcher matcher = LOCALE_PATTERN.matcher(propertyFile.getName());
		if (matcher.matches()) {
			String actualLocale = matcher.group(2);
			if(actualLocale==null || actualLocale.isEmpty())
				return null;
			actualLocale = actualLocale.substring(1);
			return (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, actualLocale);
		}
		return null;
	}

	private Pattern buildPatternFrom(String fileName) {
		Matcher matcher = LOCALE_PATTERN.matcher(fileName);
		if (!matcher.matches()) 
			return null;
		return Pattern.compile(Pattern.quote(matcher.group(1)) + "((?:_\\w\\w){1,3})" + Pattern.quote(matcher.group(3)));
	}
	
	@Override
	public Map<Locale, File> findTranslations(File template, ScanConfiguration config) {
		Map<Locale, File> results = new HashMap<Locale, File>();
		Pattern filePattern = buildPatternFrom(template.getName());
		File folder = template.getParentFile();
		File[] files = folder.listFiles();
		for (File file : files) {
			if(file.equals(template))
				continue;
			Matcher matcher = filePattern.matcher(file.getName());
			if(matcher.matches())
			{
				String localeString = matcher.group(1).substring(1);
				Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, localeString);
				results.put(locale, file);
			}
		}
		return results;
	}

}
