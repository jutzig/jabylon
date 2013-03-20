/**
 * 
 */
package de.jutzig.jabylon.properties.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler.ByteOrderMark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;

/**
 * @author joe
 *
 */
public class PropertiesHelper {
	
	
	private boolean unicodeEscaping;
	private static final int MAX_BOM_LENGTH = 4;
	/** the license header at the beginning of the file (if any)*/
	private String licenseHeader;
	/** keeps track if we already checked for a license header*/
	private boolean checkedForHeader;
	/** to be able to log the path */
	private URI uri;
	
	final Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);
	
	public PropertiesHelper() {
		this(true);
		checkedForHeader = false;
	}
	
	public PropertiesHelper(boolean unicodeEscaping) {
		this(unicodeEscaping,null);
	}

	/**
	 * 
	 * @param unicodeEscaping
	 * @param uri can be used to log the path of invalid files if supplied
	 */
	public PropertiesHelper(boolean unicodeEscaping, URI uri) {
		this.unicodeEscaping = unicodeEscaping;
		if(uri==null)
			uri = URI.createURI("NONE SUPPLIED");
		this.uri = uri;
	}
	
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
			{
				if(!checkedForHeader)
				{
					licenseHeader = comment.toString();
					comment.setLength(0);
					checkedForHeader = true;
				}
				else {
					continue;					
				}
					
			}
			if(isComment(line))
			{
				if(comment.length()>0) //there's already a comment, so now we have a new line
					comment.append("\n");
				if(line.length()>1) //otherwise it's just an empty comment
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
				if(propertyValue.length()==0)
					continue;
				String[] parts = split(propertyValue.toString());
				if(parts == null || parts[0]==null) //invalid property
				{
					logger.error("Invalid line \"{}\" in property file \"{}\". Skipping", propertyValue, uri);
					continue;
				}
				property.setKey(parts[0]);
				property.setValue(parts[1]);
				checkedForHeader = true;
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
		if(unicodeEscaping)
			writer.write(NativeToAsciiConverter.convertUnicodeToEncoded(key,true));
		else
			writer.write(key);
		writer.write(" = ");
		String value = property.getValue();
		if(value!=null)
		{
			value = value.replaceAll("(\r?\n)", "\\\\$1");
			if(unicodeEscaping)
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

	
	/**
	 * returns the BOM if available. If no BOM was found the stream is reset to its original state 
	 * 
	 * @param inputStream must support mark/rest, or an IllegalArgumentException is thrown
	 * @return
	 * @throws IOException, IllegalArgumentException
	 */
	public ByteOrderMark checkForBom(InputStream inputStream) throws IOException {
		if(!inputStream.markSupported())
			throw new IllegalArgumentException("InputStream must support mark/rest: "+inputStream);
		inputStream.mark(MAX_BOM_LENGTH);
		ByteOrderMark bom = ByteOrderMark.read(inputStream);
		if(bom==null)
			inputStream.reset();
		return bom;
	}
	
	/**
	 * 
	 * @return the license header in the file or <code>null</code> if not available
	 */
	public String getLicenseHeader() {
		return licenseHeader;
	}
	
	public void writeLicenseHeader(Writer writer, String licenseHeader) throws IOException {
		if(licenseHeader==null || licenseHeader.isEmpty())
			return;
		writeComment(writer, licenseHeader);
		writer.write('\n');
	}
	
}
