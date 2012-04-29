package de.jutzig.jabylon.index.properties.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.eclipse.emf.common.util.EList;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;


public class PropertyFileAnalyzer {

	public static final String FIELD_VALUE = "value";
	public static final String FIELD_KEY = "key";
	public static final String FIELD_URI = "uri";
	public static final String FIELD_COMMENT = "comment";
	public static final String FIELD_LOCALE = "locale";
	public static final String FIELD_VERSION = "version";
	public static final String FIELD_PROJECT = "project";

	public Document createDocument(PropertyFileDescriptor descriptor) {
		PropertyFile file = descriptor.loadProperties();
		Document doc = new Document();
		ProjectLocale locale = descriptor.getProjectLocale();
		ProjectVersion version = locale.getProjectVersion();
		Project project = version.getProject();
		
		Field projectField = new Field(FIELD_PROJECT,project.getName(),Store.YES,Index.NOT_ANALYZED);
		doc.add(projectField);
		Field versionField = new Field(FIELD_VERSION,version.getBranch(),Store.YES,Index.NOT_ANALYZED);
		doc.add(versionField);
		Field localeField = new Field(FIELD_LOCALE,locale.getLocale().toString(),Store.YES,Index.NOT_ANALYZED);
		doc.add(localeField);
		Field uriField = new Field(FIELD_URI,descriptor.fullPath().toString(),Store.YES,Index.NOT_ANALYZED);
		doc.add(uriField);
		
		EList<Property> properties = file.getProperties();
		for (Property property : properties) {
			Field comment = new Field(FIELD_COMMENT,nullSafe(property.getComment()),Store.YES,Index.ANALYZED);
			doc.add(comment);
			Field key = new Field(FIELD_KEY,nullSafe(property.getKey()),Store.YES,Index.ANALYZED);
			doc.add(key);
			Field value = new Field(FIELD_VALUE,nullSafe(property.getValue()),Store.YES,Index.ANALYZED);
			doc.add(value);
		}
		return doc;
	}
	
	private String nullSafe(String s)
	{
		return s == null ? "" : s;
	}
	
}
