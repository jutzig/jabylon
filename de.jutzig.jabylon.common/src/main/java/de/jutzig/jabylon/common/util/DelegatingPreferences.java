package de.jutzig.jabylon.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class DelegatingPreferences implements Preferences {

	private Preferences delegate;
	private boolean dirty;
	private List<DelegatingPreferences> children;
	private Map<String, Object> values;

	public DelegatingPreferences(Preferences delegate) {
		super();
		this.delegate = delegate;
		children = new ArrayList<DelegatingPreferences>();
		values = new HashMap<String, Object>();
	}

	public void put(String key, String value) {
		values.put(key, value);
		dirty = true;
	}

	public String get(String key, String def) {
		if(values.containsKey(key))
			return (String) values.get(key);
		return delegate.get(key, def);
	}

	public void remove(String key) {
		values.remove(key);
		dirty = true;
	}

	public void clear() throws BackingStoreException {
		delegate.clear();
		dirty = true;
	}

	public void putInt(String key, int value) {
		delegate.putInt(key, value);
		dirty = true;
	}

	public int getInt(String key, int def) {
		if(values.containsKey(key))
			return (Integer) values.get(key);
		return delegate.getInt(key, def);
	}

	public void putLong(String key, long value) {
		delegate.putLong(key, value);
		dirty = true;
	}

	public long getLong(String key, long def) {
		if(values.containsKey(key))
			return (Long) values.get(key);
		return delegate.getLong(key, def);
	}

	public void putBoolean(String key, boolean value) {
		delegate.putBoolean(key, value);
		dirty = true;
	}

	public boolean getBoolean(String key, boolean def) {
		if(values.containsKey(key))
			return (Boolean) values.get(key);
		return delegate.getBoolean(key, def);
	}

	public void putFloat(String key, float value) {
		delegate.putFloat(key, value);
		dirty = true;
	}

	public float getFloat(String key, float def) {
		if(values.containsKey(key))
			return (Float) values.get(key);
		return delegate.getFloat(key, def);
	}

	public void putDouble(String key, double value) {
		delegate.putDouble(key, value);
		dirty = true;
	}

	public double getDouble(String key, double def) {
		if(values.containsKey(key))
			return (Double) values.get(key);
		return delegate.getDouble(key, def);
	}

	public void putByteArray(String key, byte[] value) {
		
		delegate.putByteArray(key, value);
		dirty = true;
	}

	public byte[] getByteArray(String key, byte[] def) {
		if(values.containsKey(key))
			return (byte[]) values.get(key);
		return delegate.getByteArray(key, def);
	}

	public String[] keys() throws BackingStoreException {
		Set<String> keys = new HashSet<String>(values.keySet());
		keys.addAll(Arrays.asList(delegate.keys()));
		return keys.toArray(new String[keys.size()]);
	}

	public String[] childrenNames() throws BackingStoreException {
		
		return delegate.childrenNames();
	}

	public Preferences parent() {
		return delegate.parent();
	}

	public Preferences node(String pathName) {
		try {
			if (!nodeExists(pathName))
				dirty = true;

		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DelegatingPreferences child = new DelegatingPreferences(delegate.node(pathName));
		children.add(child);
		return child;
	}

	public boolean nodeExists(String pathName) throws BackingStoreException {
		return delegate.nodeExists(pathName);
	}

	public void removeNode() throws BackingStoreException {
		dirty = true;
		delegate.removeNode();
	}

	public String name() {
		return delegate.name();
	}

	public String absolutePath() {
		return delegate.absolutePath();
	}

	public void flush() throws BackingStoreException {
		for (Entry<String, Object> entry : values.entrySet()) {
			setValue(entry.getKey(), entry.getValue());
		}
		for (DelegatingPreferences child : children) {
			child.flush();
		}
		delegate.flush();
		dirty = false;
	}

	public void sync() throws BackingStoreException {
		delegate.sync();
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
			delegate.put(key, (String) value);
		else if (value instanceof Boolean)
			delegate.putBoolean(key, (Boolean) value);
		else if (value instanceof byte[])
			delegate.putByteArray(key, (byte[]) value);
		else if (value instanceof Double)
			delegate.putDouble(key, (Double) value);
		else if (value instanceof Float)
			delegate.putFloat(key, (Float) value);
		else if (value instanceof Integer)
			delegate.putInt(key, (Integer) value);
		else if (value instanceof Long)
			delegate.putLong(key, (Long) value);
		else if(value==null)
			delegate.put(key, null);
		else throw new IllegalArgumentException("Object type "+value+" not supported");
	}

}
