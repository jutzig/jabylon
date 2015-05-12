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

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.eclipse.core.runtime.Plugin;
import org.jabylon.cdo.server.ServerConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class IndexActivator extends Plugin implements BundleActivator {

    private static IndexActivator INSTANCE;
    private FSDirectory directory;
    public static final String PLUGIN_ID = "org.jabylon.index";
    private static final Logger logger = LoggerFactory.getLogger(IndexActivator.class);
	private IndexWriter indexWriter;
	private ReentrantLock lock = new ReentrantLock();

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        INSTANCE = this;

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        if(directory!=null)
            directory.close();
        INSTANCE = null;
        directory = null;
        logger.info("Stopping Lucene Index");
    }

    public Directory getOrCreateDirectory()
    {
        if(directory==null)
        {
            try {
                logger.info("Opening Lucene Index");
                File file = new File(ServerConstants.WORKING_DIR,"lucene");
                if(!file.exists())
                    file.mkdirs();
                directory = FSDirectory.open(new File(ServerConstants.WORKING_DIR,"lucene"));
            } catch (IOException e) {
                logger.error("Failed to open index directory",e);
            }
        }
        return directory;
    }

    /**
     * use this method to obtain an index writer. It is mandatory to call {@link #returnIndexWriter(IndexWriter)} once you are done.
     * be aware that other threads might be using the same instance simultaneously
     * @return
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    public IndexWriter obtainIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
    	lock.lock();
    	if(indexWriter==null) {
    		IndexWriterConfig c = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));
    		indexWriter = new IndexWriter(getOrCreateDirectory(), c);
    	}
    	return indexWriter;
    }

    /**
     * call this method once you are done with the index writer.
     * Once all consumers have returned their instance, the writer will be closed and disposed
     * @param writer
     * @throws CorruptIndexException
     * @throws IOException
     */
    public void returnIndexWriter(IndexWriter writer) throws CorruptIndexException, IOException {
    	if(!lock.isHeldByCurrentThread())
    		throw new IllegalStateException("The calling thread isn't the one that obtained the writer initially");
    	if(writer!=indexWriter)
    		throw new IllegalStateException("The given index writer is not the current index writer");
    	if(indexWriter!=null)
    	{
			try {
				indexWriter.close();
			} finally {
				indexWriter = null;
				try {
					if (IndexWriter.isLocked(directory)) {
						IndexWriter.unlock(directory);
					}
				} finally {
					lock.unlock();
				}
			}
    	}
    }

    public static IndexActivator getDefault() {
        return INSTANCE;
    }
}
