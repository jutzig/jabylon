/**
 *
 */
package de.jutzig.jabylon.index.properties.impl;

import java.io.IOException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.eclipse.emf.cdo.view.CDOView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.index.properties.IndexActivator;
import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class QueryServiceImpl implements QueryService {

	
	private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.jutzig.jabylon.index.properties.QueryService#search(java.lang.String)
	 */
	@Override
	public SearchResult search(String search, Object scope) {
		search = search.toLowerCase();
		Query q = constructQuery(scope, search);
		return search(q,1000);

	}

	private Query constructQuery(Object scope, String search) {
		BooleanQuery query = new BooleanQuery();
		if (query instanceof Workspace) {
			//nothing to do
		} else if (scope instanceof Project) {
			Project project = (Project) scope;
			query.add(createProjectQuery(project),Occur.MUST);
		} else if (scope instanceof ProjectVersion) {
			ProjectVersion version = (ProjectVersion) scope;
			query.add(createProjectQuery(version.getParent()), Occur.MUST);
			query.add(createVersionQuery(version), Occur.MUST);

		} else if (scope instanceof ProjectLocale) {
			ProjectLocale locale = (ProjectLocale) scope;
			query.add(createProjectQuery(locale.getParent().getParent()), Occur.MUST);
			query.add(createVersionQuery(locale.getParent()), Occur.MUST);
			query.add(createLocaleQuery(locale), Occur.MUST);
		}
		else if (scope instanceof PropertyFileDescriptor) {
			PropertyFileDescriptor descriptor = (PropertyFileDescriptor) scope;
			query.add(createProjectQuery(descriptor.getProjectLocale().getParent().getParent()), Occur.MUST);
			query.add(createVersionQuery(descriptor.getProjectLocale().getParent()), Occur.MUST);
			query.add(createLocaleQuery(descriptor.getProjectLocale()), Occur.MUST);
			query.add(createDescriptorQuery(descriptor), Occur.MUST);
		}
		BooleanQuery termQuery = new BooleanQuery();
		termQuery.add(new PrefixQuery(new Term(FIELD_COMMENT,search)),Occur.SHOULD);
		termQuery.add(new PrefixQuery(new Term(FIELD_KEY,search)),Occur.SHOULD);
		termQuery.add(new PrefixQuery(new Term(FIELD_VALUE,search)),Occur.SHOULD);
		query.add(termQuery, Occur.MUST);
		//TODO: should master files be searchable too?
		query.add(new TermQuery(new Term(QueryService.FIELD_LOCALE, QueryService.MASTER)), Occur.MUST_NOT); //exclude all masters from the search
		return query;
	}

	private TermQuery createLocaleQuery(ProjectLocale locale) {
		return new TermQuery(new Term(FIELD_LOCALE, locale.getLocale().toString()));
	}

	private TermQuery createVersionQuery(ProjectVersion version) {
		return new TermQuery(new Term(FIELD_VERSION, version.getName()));
	}

	private TermQuery createProjectQuery(Project project) {
		TermQuery query = new TermQuery(new Term(FIELD_PROJECT, project.getName()));
		return query;
	}

	private TermQuery createDescriptorQuery(PropertyFileDescriptor descriptor) {
		return new TermQuery(new Term(FIELD_URI, descriptor.fullPath().toString()));
	}

	@Override
	public SearchResult search(Query query, int maxHits) {

		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
		IndexSearcher searcher = null;
		try {
			searcher = new IndexSearcher(directory, true);
			TopDocs result = searcher.search(query, maxHits);

			return new SearchResult(searcher, result);

		} catch (CorruptIndexException e) {
			logger.error("Error during search "+query,e);
		} catch (IOException e) {
			logger.error("Error during search "+query,e);
		}
		return null;
	}

	@Override
	public PropertyFileDescriptor getDescriptor(Document doc, CDOView view) {
		String cdoID = doc.get(FIELD_CDO_ID);
		CDOID id = CDOIDUtil.read(cdoID);
		CDOObject object = null;
		try {
			object = view.getObject(id);
		} catch (ObjectNotFoundException e) {
			return null;
		}
		if (object instanceof PropertyFileDescriptor) {
			PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
			return descriptor;
		}
		return null;
	}


}
