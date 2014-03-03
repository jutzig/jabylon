/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.util;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class ServiceCache<K, V> implements RemovalListener<ServiceKey<K>, V>{
	
	private Cache<ServiceKey<K>, Collection<ServiceReference<V>>> cache;
	private ConcurrentMap<ServiceKey<K>, Collection<ServiceReference<V>>> refs;
	
	public static <K,V> ServiceCache<K,V> create(CacheLoader<K, V> loader) {
		return null;
	}

	private Bundle bundle;
	private BundleContext context;
	
	public ServiceCache() {
		bundle = FrameworkUtil.getBundle(getClass());
		context = bundle.getBundleContext();
	}

	@Override
	public void onRemoval(RemovalNotification<ServiceKey<K>, V> arg0) {
		ServiceKey<K> key = arg0.getKey();
		Collection<ServiceReference<V>> ref = refs.remove(key);
		if(ref!=null && !ref.isEmpty()) {
			BundleContext context = bundle.getBundleContext();
			for (ServiceReference<V> serviceReference : ref) {
				context.ungetService(serviceReference);							
			}
		}
	}
	
	public V getService(K key){
		return getService(key, null);
	}
	
	public V getService(K key, String filter){
		return null;
	}
	
	public Collection<V> getAllServices(K key){
		return getAllServices(key, null);
	}
	
	public Collection<V> getAllServices(K key, String filter){
		return null;
	}
	
//	class Loader1 implements Callable<V>{
//
//		private String filter;
//		private Class<K> clazz;
//				
//		public Loader(Class<K> clazz, String filter) {
//			super();
//			this.filter = filter;
//			this.clazz = clazz;
//		}
//
//
//
//		@Override
//		public final V call() throws Exception {
//			
//			BundleContext context = bundle.getBundleContext();
//			return load(context, clazz, filter);
//
//		}
//		
//		protected V load(BundleContext context, Class<V> clazz, String filter) {
//
//		}
//		
//	}
//
//	class Loader extends CacheLoader<ServiceKey<K>,Collection<ServiceReference<V>>>{
//
//		@Override
//		public Collection<ServiceReference<V>> load(ServiceKey<K> key) throws Exception {
//			
//			Collection<ServiceReference<V>> references = context.getServiceReferences(clazz, filter);
//			V service = null;
//			for (ServiceReference<V> reference : references) {
//				service = context.getService(reference);
//				if(service==null){
//					
//				}
//			}
//			return con;
//		}
//		
//	}
	
}

class ServiceKey<T> {
	
	private T clazz;
	private String filter;
	public ServiceKey(T clazz, String filter) {
		super();
		this.clazz = clazz;
		this.filter = filter;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		return result;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceKey other = (ServiceKey) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		return true;
	}
}