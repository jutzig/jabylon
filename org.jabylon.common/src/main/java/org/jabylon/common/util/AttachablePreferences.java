/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class AttachablePreferences implements Preferences, Serializable{


    private static final long serialVersionUID = 1L;
    private Map<String, Preferences> children;
    private Map<String, Object> values = new HashMap<String, Object>();
    private Preferences parent;
	private String name;
    
    public AttachablePreferences() {
    	this(null,"attachable");
	}
    
    public AttachablePreferences(Preferences parent, String name) {
    	this.parent = parent;
    	this.name = name;
	}
    
    @Override
    public String absolutePath() {
    	if(parent!=null)
    		return parent.absolutePath() + "/" + name();
        return "/" + name();
    }

    @Override
    public String[] childrenNames() throws BackingStoreException {
        if(children==null)
            return new String[]{};
        Set<String> keys = children.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    public void clear() throws BackingStoreException {
        values.clear();

    }

    @Override
    public void flush() throws BackingStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public String get(String arg0, String arg1) {
        if(values!=null && values.containsKey(arg0))
            return String.valueOf(values.get(arg0));
        return arg1;
    }

    @Override
    public boolean getBoolean(String arg0, boolean arg1) {
        if(values!=null && values.containsKey(arg0))
        {
        	Object value = values.get(arg0);
        	if (value instanceof String) {
        		return Boolean.parseBoolean((String) value);
			}
        	return (Boolean)value;
        }
        return arg1;
    }

    @Override
    public byte[] getByteArray(String arg0, byte[] arg1) {
        if(values!=null && values.containsKey(arg0))
            return (byte[]) values.get(arg0);
        return arg1;
    }

    @Override
    public double getDouble(String arg0, double arg1) {
        if(values!=null && values.containsKey(arg0))
        {
        	Object value = values.get(arg0);
        	if (value instanceof String) {
        		return Double.parseDouble((String) value);
			}
        	return (Double)value;
        }
        return arg1;
    }

    @Override
    public float getFloat(String arg0, float arg1) {
        if(values!=null && values.containsKey(arg0))
        {
        	Object value = values.get(arg0);
        	if (value instanceof String) {
        		return Float.parseFloat((String) value);
			}
        	return (Float)value;
        }
        return arg1;
    }

    @Override
    public int getInt(String arg0, int arg1) {
        if(values!=null && values.containsKey(arg0))
        {
        	Object value = values.get(arg0);
        	if (value instanceof String) {
        		return Integer.parseInt((String) value);
			}
        	return (Integer)value;
        }
        return arg1;
    }

    @Override
    public long getLong(String arg0, long arg1) {
        if(values!=null && values.containsKey(arg0))
        {
        	Object value = values.get(arg0);
        	if (value instanceof String) {
        		return Long.parseLong((String) value);
			}
        	return (Long)value;
        }
        return arg1;
    }

    @Override
    public String[] keys() throws BackingStoreException {
        if(values==null)
            return new String[]{};
        Set<String> keys = values.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Preferences node(String arg0) {
        Map<String, Preferences> children = getOrCreateChildren();
        
        Preferences child = (AttachablePreferences) children.get(arg0);
        if(child==null) {
        	child = new AttachablePreferences();
        	children.put(arg0, child);        	
        }
        return child;
    }

    private Map<String, Preferences> getOrCreateChildren() {
        if(children==null)
            children = new HashMap<String, Preferences>();
        return children;
    }

    @Override
    public boolean nodeExists(String arg0) throws BackingStoreException {
        return getOrCreateChildren().containsKey(arg0);
    }

    @Override
    public Preferences parent() {
        return parent;
    }

    @Override
    public void put(String arg0, String arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putBoolean(String arg0, boolean arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putByteArray(String arg0, byte[] arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putDouble(String arg0, double arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putFloat(String arg0, float arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putInt(String arg0, int arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putLong(String arg0, long arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void remove(String arg0) {
        values.remove(arg0);

    }

    @Override
    public void removeNode() throws BackingStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sync() throws BackingStoreException {
        // TODO Auto-generated method stub

    }
    
    @Override
    public String toString() {
    	return absolutePath();
    }



}
