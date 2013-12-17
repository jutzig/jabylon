/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.index.properties.impl;

import java.io.IOException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.index.properties.IndexActivator;
import org.jabylon.index.properties.QueryService;
import org.jabylon.index.properties.SearchResult;
import org.jabylon.index.properties.jobs.impl.ReorgIndexJob;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class QueryServiceImpl implements QueryService {


    private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Reference
    private URIResolver uriResolver;
    
    private volatile IndexSearcher searcher;
    private volatile IndexReader reader;

    @Reference
    private RepositoryConnector RepositoryConnector;
    
    @Deactivate
    public void deactivate() {
			try {
				if(reader!=null)
					reader.close();
				if(searcher!=null)
					searcher.close();
			} catch (IOException e) {
				logger.error("Failed to close the index",e);
			}
    }

    public void bindUriResolver(URIResolver uriResolver) {
        this.uriResolver = uriResolver;
    }

    public void unbindUriResolver(URIResolver uriResolver) {
        this.uriResolver = null;
    }

    public void bindRepositoryConnector(RepositoryConnector repositoryConnector) {
		RepositoryConnector = repositoryConnector;
	}

    public void unbindRepositoryConnector(RepositoryConnector repositoryConnector) {
		RepositoryConnector = repositoryConnector;
	}

    /*
     * (non-Javadoc)
     *
     * @see
     * org.jabylon.index.properties.QueryService#search(java.lang.String)
     */
    @Override
    public SearchResult search(String search, String scopeURI) {
        search = search.toLowerCase();
        Query q = constructQuery(uriResolver.resolve(scopeURI), search);
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
        else if (scope instanceof ResourceFolder) {
            ResourceFolder folder = (ResourceFolder) scope;
            ProjectLocale locale = folder.getProjectLocale();
            query.add(createProjectQuery(locale.getParent().getParent()), Occur.MUST);
            query.add(createVersionQuery(locale.getParent()), Occur.MUST);
            query.add(createLocaleQuery(locale), Occur.MUST);
            query.add(new PrefixQuery(new Term(QueryService.FIELD_FULL_PATH,folder.fullPath().path())), Occur.MUST);
        }
        else if (scope instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) scope;
            query.add(createProjectQuery(descriptor.getProjectLocale().getParent().getParent()), Occur.MUST);
            query.add(createVersionQuery(descriptor.getProjectLocale().getParent()), Occur.MUST);
            query.add(createLocaleQuery(descriptor.getProjectLocale()), Occur.MUST);
            query.add(createDescriptorQuery(descriptor), Occur.MUST);
        }
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_35, new String[] {FIELD_COMMENT, FIELD_KEY, FIELD_ANALYZED_KEY, FIELD_VALUE, FIELD_MASTER_VALUE, FIELD_MASTER_COMMENT}, new StandardAnalyzer(Version.LUCENE_35));
        try {
            Query userQuery = queryParser.parse(search);
            query.add(userQuery, Occur.MUST);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
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
        return new TermQuery(new Term(FIELD_FULL_PATH, descriptor.fullPath().toString()));
    }

    @Override
    public SearchResult search(Query query, int maxHits) {

        Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
        try {
        	if(reader==null)
        	{
        		synchronized (this) {
        			//double checked locking
					if(reader==null)
						reader = IndexReader.open(directory);
				}
        	}
        	else
        	{
        		IndexReader newReader = IndexReader.openIfChanged(reader,true);
        		if(newReader!=reader && newReader!=null)
        		{
        			reader.close();
        			reader = newReader;
        			if(searcher!=null)
        				searcher.close();
        			searcher = null;
        		}
        	}
        	if(searcher==null)
        	{
        		synchronized (this) {
        			//double checked locking
					if(searcher==null)
						searcher = new IndexSearcher(reader);
				}
        	}
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
    public PropertyFileDescriptor getDescriptor(Document doc) {
        String cdoID = doc.get(FIELD_CDO_ID);
        CDOID id = CDOIDUtil.read(cdoID);
        Object object = null;
        try {
            object = uriResolver.resolve(id);
        } catch (ObjectNotFoundException e) {
            return null;
        }
        if (object instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
            return descriptor;
        }
        return null;
    }

	@Override
	public void rebuildIndex(IProgressMonitor monitor) throws CorruptIndexException, IOException {
		ReorgIndexJob.indexWorkspace(RepositoryConnector, monitor);

	}


}
