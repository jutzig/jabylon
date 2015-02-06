/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.types.PropertyConverter;
import org.jabylon.properties.types.PropertyScanner;
import org.jabylon.properties.types.impl.JavaPropertyScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.jabylon.properties.util.PropertiesResourceFactoryImpl
 * @generated
 */
public class PropertiesResourceImpl extends ResourceImpl {

    //TODO: this is a dirty hack...
    private int savedProperties;

    public static final String OPTION_FILEMODE = "file.mode";
    public static final String OPTION_ENCODING = "encoding";
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesResourceImpl.class);

    private static final LoadingCache<String, ReentrantReadWriteLock> LOCKS = CacheBuilder.newBuilder().maximumSize(10000).build(new CacheLoader<String, ReentrantReadWriteLock>() {

		@Override
		public ReentrantReadWriteLock load(String key) throws Exception {
			return new ReentrantReadWriteLock();
		}
	});

    /**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
    public PropertiesResourceImpl(URI uri) {
		super(uri);

	}


	@Override
	public void load(Map<?, ?> options) throws IOException {

		ReentrantReadWriteLock lock = LOCKS
				.getUnchecked(getURI() == null ? "temp" : getURI()
						.toFileString());

		ReadLock readLock = lock.readLock();
		try {
			if (!readLock.tryLock(2, TimeUnit.MINUTES)) {
				LOG.warn("Failed to aquire read lock for {}", getURI());
				readLock = null;
				throw new IOException("Could not load " + getURI());
			}
			super.load(options);
		} catch (InterruptedException e) {
			LOG.warn("Interrupted while trying to aquire read lock for {}",
					getURI());
			throw new IOException("Could not load " + getURI());
		} finally {
			if (readLock != null)
				readLock.unlock();
		}
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		String type = getPropertyType(options);
		PropertyScanner scanner = PropertyResourceUtil.createScanner(type);
		PropertyConverter converter = scanner.createConverter(getURI());
		PropertyFile file = converter.load(inputStream, scanner.getEncoding());
		getContents().add(file);

	}

    private String getPropertyType(Map<?, ?> options) {
        if(options!=null && options.containsKey(OPTION_FILEMODE))
        {
            return (String) options.get(OPTION_FILEMODE);
        }
        return JavaPropertyScanner.TYPE;
    }


	@Override
	public void save(Map<?, ?> options) throws IOException {
		ReentrantReadWriteLock lock = LOCKS
				.getUnchecked(getURI() == null ? "temp" : getURI().toFileString());
		WriteLock writeLock = lock.writeLock();
		try {
			if (writeLock.tryLock(2, TimeUnit.MINUTES)) {
				super.save(options);
			}
			else {
				writeLock = null;
				throw new IOException("Could not save " + getURI()+ ". It was locked");
			}
		} catch (InterruptedException e) {
			LOG.warn("Interrupted while trying to aquire write lock for {}",
					getURI());
			throw new IOException("Could not save " + getURI());
		} finally {
			if (writeLock != null)
				writeLock.unlock();
		}
	}

    @Override
    protected void doSave(OutputStream outputStream, Map<?, ?> options)
            throws IOException {
        savedProperties = 0;
        String type = getPropertyType(options);
        PropertyScanner scanner = PropertyResourceUtil.createScanner(type);
        String encoding = scanner.getEncoding();
        PropertyConverter converter = scanner.createConverter(getURI());
        try{

        		PropertyFile file = (PropertyFile) getContents().get(0);
        		savedProperties = converter.write(outputStream, file,encoding);
        }
        finally{
				outputStream.close();
        }
    }

    public int getSavedProperties() {
        return savedProperties;
    }

} //PropertiesResourceImpl
