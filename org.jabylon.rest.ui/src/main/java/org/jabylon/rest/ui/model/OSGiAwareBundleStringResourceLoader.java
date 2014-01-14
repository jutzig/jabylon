/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.resource.loader.IStringResourceLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class OSGiAwareBundleStringResourceLoader implements IStringResourceLoader
{

	private final BundleContext context;
	private LoadingCache<Integer, ClassLoader> cache;
	private Logger logger = LoggerFactory.getLogger(OSGiAwareBundleStringResourceLoader.class);
	
	public OSGiAwareBundleStringResourceLoader() {
		context = FrameworkUtil.getBundle(getClass()).getBundleContext();
		cache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, ClassLoader>() {

			@Override
			public ClassLoader load(Integer key) throws Exception {
				Bundle bundle = context.getBundle(key);
				String bundleName = bundle.getHeaders().get("Bundle-Localisation");
				if(bundleName==null)
					bundleName = "OSGI-INF/l10n/bundle";
				String prefix = "/"+bundleName.substring(0, bundleName.lastIndexOf('/'));
				Enumeration<URL> entries = bundle.findEntries(prefix, "*.properties", false);
				List<URL> urls = Collections.emptyList();
				if(entries!=null) {
					urls = new ArrayList<URL>();
					while(entries.hasMoreElements()) {
						URL url = entries.nextElement();
						urls.add(url);
					}
				}
				return new BundleClassloader(prefix,urls);
			}
			
		});
	}
	
	@Override
	public final String loadStringResource(final Class<?> clazz, final String key, Locale locale, final String style, final String variation)
	{
		try
		{
			if(key.startsWith("%"))
			{
				if (locale == null)
				{
					locale = Session.exists() ? Session.get().getLocale() : Locale.getDefault();
				}
				int keyStart = key.indexOf("|");
				int bundleId = Integer.parseInt(key.substring(1,keyStart));
				Bundle bundle = context.getBundle(bundleId);
				String bundleName = bundle.getHeaders().get("Bundle-Localisation");
				if(bundleName==null)
					bundleName = "OSGI-INF/l10n/bundle";
				return ResourceBundle.getBundle(bundleName, locale,cache.get(bundleId)).getString(key.substring(keyStart+1));
			}
			else if(key.startsWith("|"))
			{
				//starts with a marker that this key is to be treated as the value
				return key.substring(1);
			}
			return null;
		}
		catch (MissingResourceException e)
		{
			return null;
		} catch (ExecutionException e) {
			logger.warn("Failed to create classloader for key "+key,e);
			return null;
		}
	}

	@Override
	public final String loadStringResource(final Component component, final String key,
		Locale locale, final String style, final String variation)
	{
		return loadStringResource((Class<?>)null, key, locale, style, variation);
	}
}

class BundleClassloader extends ClassLoader {
	
	private Map<String, URL> resources;
	private int prefixLength;
	
	public BundleClassloader(String prefix, List<URL> urls) {
		
		resources = new HashMap<String, URL>();
		prefixLength = prefix.length();
		for (URL url : urls) {
			String path = url.getPath();
			resources.put(path.substring(path.lastIndexOf('/')+1),url);
		}
	}
	
	@Override
	protected URL findResource(String name) {
		if(name.length()<prefixLength)
			return null;
		return resources.get(name.substring(prefixLength));
	}
	
}
