/**
 * 
 */
package de.jutzig.jabylon.properties.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;

/**
 * @author joe
 *
 */
public class PropertiesHelper {
	
	public Property readProperty(BufferedReader reader) throws IOException
	{
		String line = null;
		Property property = null;
		StringBuilder comment = new StringBuilder();
		StringBuilder propertyValue = new StringBuilder();
		while((line = reader.readLine())!=null)
		{
			line=line.trim();
			if(line.length()==0)
				continue;
			if(isComment(line) && line.length()>1)
			{
				if(comment.length()>0) //there's already a comment, so now we have a new line
					comment.append("\n");
				comment.append(line.substring(1).trim());				
			}
			else
			{
				propertyValue.append(NativeToAsciiConverter.convertEncodedToUnicode(line));
				if(line.endsWith("\\"))
				{
					propertyValue.append("\n");
					continue;					
				}
				property = PropertiesFactory.eINSTANCE.createProperty();
				if(comment.length()>0)
					property.setComment(comment.toString());
				String[] parts = split(propertyValue.toString());
				if(parts[0]==null) //invalid property
					continue; //TODO: logging
				property.setKey(parts[0]);
				property.setValue(parts[1]);
				return property;
			}
		}
		return property;
	}

	private String[] split(String propertyValue) {
		boolean escape = false;
		StringBuilder buffer = new StringBuilder(propertyValue.length());
		String[] result = new String[2];
		for (char c : propertyValue.toCharArray()) {
			if(!escape)
			{
				if(result[0]==null && (c==':' || c=='='))
				{
					
					String string = buffer.toString().trim();
					if(string.length()>0)
						result[0]=string;
					else
						return null;
					buffer.setLength(0);
					continue;
				}
				else if(c=='\\')
				{
					escape = true;
					continue;
				}
			}
			escape = false;
			buffer.append(c);
		}
		String string = buffer.toString();
		if(string.startsWith(" ")) //remove trailing space '= value'
			string = string.substring(1);
		
		if(string.length()>0)
			result[1] = string;
		return result;
	}

	private boolean isComment(String line) {
		return (line.startsWith("#") || line.startsWith("!"));	
	}
	
	public void writeProperty(Writer writer, Property property) throws IOException
	{
		if(property.eIsSet(PropertiesPackage.Literals.PROPERTY__COMMENT))
			writeComment(writer,property.getComment());
		String key = property.getKey();
		key = key.replaceAll("([ :=\n])", "\\\\$1");
		writer.write(NativeToAsciiConverter.convertUnicodeToEncoded(key,true));
		writer.write(" = ");
		String value = property.getValue();
		if(value!=null)
		{
			value = value.replaceAll("(\r?\n)", "\\\\$1");
			value = NativeToAsciiConverter.convertUnicodeToEncoded(value, true);
			writer.write(value);
		}
		writer.write('\n');
		
	}



	private void writeComment(Writer writer, String comment) throws IOException {
		comment = comment.replace("\n", "\n#");
		writer.write("#");
		writer.write(comment);
		writer.write('\n');
	}

}
