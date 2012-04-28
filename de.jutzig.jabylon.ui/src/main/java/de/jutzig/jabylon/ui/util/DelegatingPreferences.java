/**
 * 
 */
package de.jutzig.jabylon.ui.util;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class DelegatingPreferences implements Preferences {

	private Preferences delegate;
	private boolean dirty;

	public DelegatingPreferences(Preferences delegate) {
		super();
		this.delegate = delegate;
	}

	public void put(String key, String value) {
		delegate.put(key, value);
		dirty = true;
	}

	public String get(String key, String def) {
		return delegate.get(key, def);
	}

	public void remove(String key) {
		delegate.remove(key);
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
		return delegate.getInt(key, def);
	}

	public void putLong(String key, long value) {
		delegate.putLong(key, value);
		dirty = true;
	}

	public long getLong(String key, long def) {
		return delegate.getLong(key, def);
	}

	public void putBoolean(String key, boolean value) {
		delegate.putBoolean(key, value);
		dirty = true;
	}

	public boolean getBoolean(String key, boolean def) {
		return delegate.getBoolean(key, def);
	}

	public void putFloat(String key, float value) {
		delegate.putFloat(key, value);
		dirty = true;
	}

	public float getFloat(String key, float def) {
		return delegate.getFloat(key, def);
	}

	public void putDouble(String key, double value) {
		delegate.putDouble(key, value);
		dirty = true;
	}

	public double getDouble(String key, double def) {
		return delegate.getDouble(key, def);
	}

	public void putByteArray(String key, byte[] value) {
		delegate.putByteArray(key, value);
		dirty = true;
	}

	public byte[] getByteArray(String key, byte[] def) {
		return delegate.getByteArray(key, def);
	}

	public String[] keys() throws BackingStoreException {
		return delegate.keys();
	}

	public String[] childrenNames() throws BackingStoreException {
		return delegate.childrenNames();
	}

	public Preferences parent() {
		return delegate.parent();
	}

	public Preferences node(String pathName) {
		try {
			if(!nodeExists(pathName))
				dirty = true;
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new DelegatingPreferences(delegate.node(pathName));
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
		delegate.flush();
	}

	public void sync() throws BackingStoreException {
		delegate.sync();
	}


}
