/**
 *
 */
package org.jabylon.index.properties;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchResult {
    private IndexSearcher searcher;
    private TopDocs topDocs;

    public SearchResult(IndexSearcher searcher, TopDocs topDocs) {
        super();
        this.searcher = searcher;
        this.topDocs = topDocs;
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    public TopDocs getTopDocs() {
        return topDocs;
    }
}
