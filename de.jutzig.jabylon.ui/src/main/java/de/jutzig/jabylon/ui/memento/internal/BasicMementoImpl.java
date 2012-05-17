/**
 * 
 */
package de.jutzig.jabylon.ui.memento.internal;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

import de.jutzig.jabylon.ui.memento.Memento;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BasicMementoImpl implements Memento{

	private Map<String, String> values;
	private Map<String, Memento> children;
	
	public BasicMementoImpl() {
		values = new HashMap<String, String>();
		children = new MapMaker().concurrencyLevel(1).makeComputingMap(new Function<String, Memento>() {

			@Override
			public Memento apply(String from) {
				return new BasicMementoImpl();
			}
				
		});
	}
	
	@Override
	public void put(String key, String value) {
		values.put(key, value);
		
	}

	@Override
	public String get(String key) {
		return values.get(key);
	}

	@Override
	public Memento getChild(String path) {
		return children.get(path);
	}
	
}
