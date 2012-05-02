/**
 * 
 */
package de.jutzig.jabylon.index.properties.impl;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import de.jutzig.jabylon.index.properties.IndexActivator;
import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class QueryServiceImpl implements QueryService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.jutzig.jabylon.index.properties.QueryService#search(java.lang.String)
	 */
	@Override
	public SearchResult search(String search, Object scope) {
		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
		try {
			IndexSearcher searcher = new IndexSearcher(directory, true);
			String query = search += "*";
			Query q = new QueryParser(Version.LUCENE_29, QueryService.FIELD_VALUE, new StandardAnalyzer(Version.LUCENE_29))
					.parse(query);
			TopDocs result = searcher.search(q, 1000);
			return new SearchResult(searcher, result);

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

}
