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

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jabylon.cdo.server.ServerConstants;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class IndexActivator extends Plugin implements BundleActivator {

    private static IndexActivator INSTANCE;
    private FSDirectory directory;
    public static final String PLUGIN_ID = "org.jabylon.index";
    private static final Logger logger = LoggerFactory.getLogger(IndexActivator.class);

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

    public static IndexActivator getDefault() {
        return INSTANCE;
    }
}
