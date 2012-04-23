package de.jutzig.jabylon.ui.container;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class PreferencesItem implements Item{

	
	private Map<String, PreferencesProperty<?>> properties; 
	
	private Preferences node;
	
	
	
	public PreferencesItem(Preferences node) {
		super();
		this.node = node;
		properties = new LinkedHashMap<String, PreferencesProperty<?>>();
	}

	@Override
	public Property getItemProperty(Object id) {
		return properties.get(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return properties.keySet();
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public <T extends Object> void addProperty(String key, Class<T> type, T defaultValue)
	{
		properties.put(key, new PreferencesProperty<T>(defaultValue, type, node, key));
	}
	

}
