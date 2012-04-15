package de.jutzig.jabylon.ui.util;

import java.util.Locale;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.ui.resources.ImageConstants;

public class LocaleUtil {

	private LocaleUtil() {
		//hide utility constructor
	}
	
	public static Resource getIconForLocale(ProjectLocale locale)
	{
		if(locale==null)
			return null;
		return getIconForLocale(locale.getLocale());
	}
	
	public static Resource getIconForLocale(Locale locale)
	{
		if(locale==null)
			return null;
		String iconName = "";
		if(locale.getCountry()!=null && locale.getCountry().length()>0)
		{
			iconName = locale.getCountry().toLowerCase();
		}
		else
		{
			iconName = derriveCountry(locale);
		}
		return new ThemeResource(ImageConstants.FOLDER_FLAGS+iconName+".gif");
	}

	private static String derriveCountry(Locale locale) {
		String language = locale.getLanguage();
		if("da".equals(language)) //denmark
			return "dk";
		
		else if("ja".equals(language)) //japanese
			return "jp";
		else if("uk".equals(language)) //ukraine
			return "ua";
		else if("eu".equals(language)) //basque
			return null; //don't have this one yet
		else if("he".equals(language)) //hebrew
			return "il"; //isreal
		else if("iw".equals(language)) //old hebrew code, still used in java
			return "il"; //isreal		
		else if("el".equals(language)) //greek
			return "gr"; 
		else if("ko".equals(language)) //korean
			return "kr";
		else if("te".equals(language)) //telegu
			return "in"; //india
		else if("ca".equals(language)) //catalan
			return "catalonia"; //official language in andorra (AD), but also other places. Use catalonia for now 
		
		
		return language.toLowerCase(); //this works in many cases, but is wrong in some
	}
	
}
