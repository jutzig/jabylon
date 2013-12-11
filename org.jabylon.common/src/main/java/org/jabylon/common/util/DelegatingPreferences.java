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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class DelegatingPreferences implements Preferences, Serializable {

    private static final long serialVersionUID = -2100265681534715500L;
    private transient Preferences delegate;
    private String absolutePath;
    private boolean dirty;
    private List<DelegatingPreferences> children;
    private Map<String, Object> values;

    public DelegatingPreferences(Preferences delegate) {
        super();
        this.delegate = delegate;
        absolutePath = delegate.absolutePath();
        children = new ArrayList<DelegatingPreferences>();
        values = new HashMap<String, Object>();
    }

    public void put(String key, String value) {
        values.put(key, value);
        dirty = true;
    }

    public String get(String key, String def) {
        if(values.containsKey(key))
            return String.valueOf(values.get(key));
        return getDelegate().get(key, def);
    }

    public void remove(String key) {
        values.remove(key);
        dirty = true;
    }

    public void clear() throws BackingStoreException {
        getDelegate().clear();
        dirty = true;
    }

    public void putInt(String key, int value) {
        getDelegate().putInt(key, value);
        dirty = true;
    }

    public int getInt(String key, int def) {
        if(values.containsKey(key))
            return (Integer) values.get(key);
        return getDelegate().getInt(key, def);
    }

    public void putLong(String key, long value) {
        getDelegate().putLong(key, value);
        dirty = true;
    }

    public long getLong(String key, long def) {
        if(values.containsKey(key))
            return (Long) values.get(key);
        return getDelegate().getLong(key, def);
    }

    public void putBoolean(String key, boolean value) {
        getDelegate().putBoolean(key, value);
        dirty = true;
    }

    public boolean getBoolean(String key, boolean def) {
        if(values.containsKey(key))
            return (Boolean) values.get(key);
        return getDelegate().getBoolean(key, def);
    }

    public void putFloat(String key, float value) {
        getDelegate().putFloat(key, value);
        dirty = true;
    }

    public float getFloat(String key, float def) {
        if(values.containsKey(key))
            return (Float) values.get(key);
        return getDelegate().getFloat(key, def);
    }

    public void putDouble(String key, double value) {
        getDelegate().putDouble(key, value);
        dirty = true;
    }

    public double getDouble(String key, double def) {
        if(values.containsKey(key))
            return (Double) values.get(key);
        return getDelegate().getDouble(key, def);
    }

    public void putByteArray(String key, byte[] value) {

        getDelegate().putByteArray(key, value);
        dirty = true;
    }

    public byte[] getByteArray(String key, byte[] def) {
        if(values.containsKey(key))
            return (byte[]) values.get(key);
        return getDelegate().getByteArray(key, def);
    }

    public String[] keys() throws BackingStoreException {
        Set<String> keys = new HashSet<String>(values.keySet());
        keys.addAll(Arrays.asList(getDelegate().keys()));
        return keys.toArray(new String[keys.size()]);
    }

    public String[] childrenNames() throws BackingStoreException {

        return getDelegate().childrenNames();
    }

    public Preferences parent() {
        return getDelegate().parent();
    }

    public Preferences node(String pathName) {
        try {
            if (!nodeExists(pathName))
                dirty = true;

        } catch (BackingStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DelegatingPreferences child = new DelegatingPreferences(getDelegate().node(pathName));
        children.add(child);
        return child;
    }

    public boolean nodeExists(String pathName) throws BackingStoreException {
        return getDelegate().nodeExists(pathName);
    }

    public void removeNode() throws BackingStoreException {
        dirty = true;
        getDelegate().removeNode();
    }

    public String name() {
        return getDelegate().name();
    }

    public String absolutePath() {
        return getDelegate().absolutePath();
    }

    public void flush() throws BackingStoreException {
        for (Entry<String, Object> entry : values.entrySet()) {
            setValue(entry.getKey(), entry.getValue());
        }
        for (DelegatingPreferences child : children) {
            child.flush();
        }
        getDelegate().flush();
        dirty = false;
    }

    public void sync() throws BackingStoreException {
        getDelegate().sync();
    }

    public boolean isDirty() {
        if (dirty)
            return true;
        for (DelegatingPreferences child : children) {
            if (child.isDirty())
                return true;
        }
        return false;

    }

    protected void setValue(String key, Object value) {
        if (value instanceof String)
            getDelegate().put(key, (String) value);
        else if (value instanceof Boolean)
            getDelegate().putBoolean(key, (Boolean) value);
        else if (value instanceof byte[])
            getDelegate().putByteArray(key, (byte[]) value);
        else if (value instanceof Double)
            getDelegate().putDouble(key, (Double) value);
        else if (value instanceof Float)
            getDelegate().putFloat(key, (Float) value);
        else if (value instanceof Integer)
            getDelegate().putInt(key, (Integer) value);
        else if (value instanceof Long)
            getDelegate().putLong(key, (Long) value);
        else if(value==null)
            getDelegate().remove(key);
        else throw new IllegalArgumentException("Object type "+value+" not supported");
    }

    private Preferences getDelegate() {
        if(delegate==null)
        {
            delegate = InstanceScope.INSTANCE.getNode(absolutePath);
        }
        return delegate;
    }

}
