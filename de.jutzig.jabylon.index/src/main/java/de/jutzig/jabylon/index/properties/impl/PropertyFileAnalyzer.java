package de.jutzig.jabylon.index.properties.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.common.util.EList;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

public class PropertyFileAnalyzer {

	public List<Document> createDocuments(PropertyFileDescriptor descriptor) {
		PropertyFile file = descriptor.loadProperties();
		List<Document> documents = new ArrayList<Document>(file.getProperties().size());

		EList<Property> properties = file.getProperties();
		for (Property property : properties) {

			Document doc = new Document();
			ProjectLocale locale = descriptor.getProjectLocale();
			ProjectVersion version = locale.getProjectVersion();
			Project project = version.getProject();

			Field projectField = new Field(QueryService.FIELD_PROJECT, project.getName(), Store.YES, Index.NOT_ANALYZED);
			doc.add(projectField);
			Field versionField = new Field(QueryService.FIELD_VERSION, version.getBranch(), Store.YES, Index.NOT_ANALYZED);
			doc.add(versionField);
			if(locale.getLocale()!=null)
			{
				Field localeField = new Field(QueryService.FIELD_LOCALE, locale.getLocale().toString(), Store.YES, Index.NOT_ANALYZED);				
				doc.add(localeField);
			}
			Field uriField = new Field(QueryService.FIELD_URI, descriptor.fullPath().toString(), Store.YES, Index.NOT_ANALYZED);
			doc.add(uriField);
			CDOID cdoID = descriptor.cdoID();
			StringBuilder builder = new StringBuilder();
			CDOIDUtil.write(builder, cdoID);

			Field idField = new Field(QueryService.FIELD_CDO_ID, builder.toString(), Store.YES, Index.NO);
			doc.add(idField);

			Field comment = new Field(QueryService.FIELD_COMMENT, nullSafe(property.getComment()), Store.YES, Index.ANALYZED);
			doc.add(comment);
			Field key = new Field(QueryService.FIELD_KEY, nullSafe(property.getKey()), Store.YES, Index.ANALYZED);
			doc.add(key);
			Field value = new Field(QueryService.FIELD_VALUE, nullSafe(property.getValue()), Store.YES, Index.ANALYZED);
			doc.add(value);
			documents.add(doc);
		}
		return documents;
	}

	private String nullSafe(String s) {
		return s == null ? "" : s;
	}

}
