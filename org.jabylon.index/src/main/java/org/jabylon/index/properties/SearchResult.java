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
