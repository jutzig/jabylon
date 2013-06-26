/**
 *
 */
package de.jutzig.jabylon.index.properties;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface QueryService {

	String FIELD_VALUE = "value";
	String FIELD_KEY = "key";
	String FIELD_URI = "uri";
	String FIELD_FULL_PATH = "path";
	String FIELD_COMMENT = "comment";
	String FIELD_LOCALE = "locale";
	String FIELD_VERSION = "version";
	String FIELD_PROJECT = "project";
	String FIELD_CDO_ID = "cdoID";
	/**
	 * location of the template file, or empty string if there is none
	 * @see de.jutzig.jabylon.properties.PropertyFileDescriptor#getLocation()
	 */
	String FIELD_TEMPLATE_LOCATION = "templatePath";
	String MASTER = "master";


	SearchResult search(String search, String scopeURI);

	SearchResult search(Query query, int maxHits);

	PropertyFileDescriptor getDescriptor(Document doc);

}
