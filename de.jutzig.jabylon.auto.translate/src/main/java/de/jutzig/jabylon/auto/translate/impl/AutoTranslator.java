/**
 *
 */
package de.jutzig.jabylon.auto.translate.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.changes.PropertiesListener;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class AutoTranslator implements PropertiesListener {

	private PropertyPersistenceService persistenceService;
	private QueryService queryService;

	@Override
	public void propertyFileAdded(PropertyFileDescriptor descriptor, boolean autoSync) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyFileDeleted(PropertyFileDescriptor descriptor, boolean autoSync) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyFileModified(PropertyFileDescriptor descriptor, List<Notification> changes, boolean autoSync) {
		if(autoSync)
			return;
		if(descriptor.isMaster())
			return;
		PropertyFileDescriptor master = descriptor.getMaster();
		PropertyFile masterProperties = master.loadProperties();
		for (Notification notification : changes) {
			if(notification.getFeature()!=PropertiesPackage.Literals.PROPERTY__VALUE)
				continue;
			Object notifier = notification.getNotifier();
			if (notifier instanceof Property) {
				Property property = (Property) notifier;
				String key = property.getKey();
				Property masterProperty = masterProperties.getProperty(key);
				if(masterProperty==null)
					continue;
				String newValue = notification.getNewStringValue();
				if(newValue==null)
					continue;
				SearchResult result = searchIdenticalValues(masterProperty.getValue());
				if(result==null)
					continue;
				try {
					ScoreDoc[] docs = result.getTopDocs().scoreDocs;
					for (ScoreDoc doc : docs) {
						Document document = result.getSearcher().doc(doc.doc);
						if(!document.get(QueryService.FIELD_VALUE).equals(masterProperty.getValue()))
							continue;
						PropertyFileDescriptor relatedMasterDescriptor = queryService.getDescriptor(document);
						PropertyFileDescriptor relatedDescriptor = getMatchingLocale(relatedMasterDescriptor,descriptor.getProjectLocale());
						if(relatedDescriptor==null)
							continue;
						PropertyFile relatedProperties = relatedDescriptor.loadProperties();
						Property relatedproperty = relatedProperties.getProperty(document.get(QueryService.FIELD_KEY));
						if(relatedproperty==null)
						{
							relatedproperty = PropertiesFactory.eINSTANCE.createProperty();
							relatedproperty.setKey(document.get(QueryService.FIELD_KEY));
							relatedProperties.getProperties().add(relatedproperty);
						}
						String currentValue = relatedproperty.getValue();
						if(newValue.equals(currentValue))
							continue;
						relatedproperty.setValue(newValue);
						String comment = relatedproperty.getComment();
						if(comment==null || comment.length()==0)
							relatedproperty.setComment("Jabylon auto sync-up");
						else if(!comment.endsWith("Jabylon auto sync-up"))
						{
							comment += "\n Jabylon auto sync-up";
							relatedproperty.setComment(comment);
						}
						System.out.println("Auto sync up at "+relatedDescriptor.relativePath());
						persistenceService.saveProperties(relatedDescriptor, relatedProperties, true);

					}
				} catch (CorruptIndexException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					try {
						result.getSearcher().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

	}


	private PropertyFileDescriptor getMatchingLocale(PropertyFileDescriptor master, ProjectLocale targetLocale) {
		if(master==null)
			return null;
		ProjectVersion version = master.getProjectLocale().getParent();
		ProjectLocale projectLocale = version.getProjectLocale(targetLocale.getLocale());
		if(projectLocale==null)
			return null;
		EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
		for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
			if(propertyFileDescriptor.getMaster()==master)
				return propertyFileDescriptor;
		}
		return null;
	}

	private SearchResult searchIdenticalValues(String value) {
		if(value ==null)
			return null;
		Query query = constructQuery(value);
		return queryService.search(query,10000);
	}

	private Query constructQuery(String value) {
		BooleanQuery query = new BooleanQuery();
		query.add(new TermQuery(new Term(QueryService.FIELD_LOCALE, QueryService.MASTER)),Occur.MUST);
		QueryParser parser = new QueryParser(Version.LUCENE_35,QueryService.FIELD_VALUE,new StandardAnalyzer(Version.LUCENE_29));
		Query mainQuery = null;
		try {
			mainQuery = parser.parse("\""+QueryParser.escape(value)+"\"");
		} catch (ParseException e) {
			// should never happen
			e.printStackTrace();
		}
		query.add(mainQuery,Occur.MUST);
		return query;
	}

	public void setPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public void unsetPersistenceService(PropertyPersistenceService persistenceService) {
		this.persistenceService = null;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

	public void unsetQueryService(QueryService queryService) {
		this.queryService = null;
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		Properties props = new Properties();
		for(int i=0;i<10000;i++)
		{
			props.put("property"+i, "test"+i);
		}
		props.save(new FileOutputStream(new File("/home/joe/workspaces/jabylon/work/workspace/Jenkins/master/core/src/main/resources/jenkins/mvn/Messages.properties")), null);
	}
}
