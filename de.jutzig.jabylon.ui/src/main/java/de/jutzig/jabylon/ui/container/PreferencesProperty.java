package de.jutzig.jabylon.ui.container;

import java.nio.charset.Charset;

import org.osgi.service.prefs.Preferences;

import com.vaadin.data.util.AbstractProperty;

public class PreferencesProperty<T> extends AbstractProperty {

	private T defaultValue;
	private Class<T> type;
	private Preferences node;
	private String key;
	
	
	
	public PreferencesProperty(T defaultValue, Class<T> type,
			Preferences node, String key) {
		super();
		this.defaultValue = defaultValue;
		this.type = type;
		this.node = node;
		this.key = key;
	}

	@Override
	public Object getValue() {
		if(type.isAssignableFrom(String.class))
			return node.get(key, (String) defaultValue);
		if(type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(Boolean.TYPE))
			return node.getBoolean(key, (Boolean)defaultValue);
		if(type.isAssignableFrom(byte[].class))
			return node.getByteArray(key, (byte[])defaultValue);
		if(type.isAssignableFrom(Double.class) || type.isAssignableFrom(Double.TYPE))
			return node.getDouble(key, (Double)defaultValue);
		if(type.isAssignableFrom(Float.class) || type.isAssignableFrom(Float.TYPE))
			return node.getFloat(key, (Float)defaultValue);
		if(type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Integer.TYPE))
			return node.getInt(key, (Integer)defaultValue);
		if(type.isAssignableFrom(Long.class) || type.isAssignableFrom(Long.TYPE))
			return node.getLong(key, (Long)defaultValue);
		throw new UnsupportedOperationException(type+" not supported"); //$NON-NLS-1$
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		if(newValue == null)
			return;
		if(newValue instanceof String)
			setValue((String)newValue);
		else if(type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(Boolean.TYPE))
			node.putBoolean(key, (Boolean)newValue);
		else if(type.isAssignableFrom(byte[].class))
			node.putByteArray(key, (byte[])newValue);
		else if(type.isAssignableFrom(Double.class) || type.isAssignableFrom(Double.TYPE))
			node.putDouble(key, (Double)newValue);
		else if(type.isAssignableFrom(Float.class) || type.isAssignableFrom(Float.TYPE))
			node.putFloat(key, (Float)newValue);
		else if(type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Integer.TYPE))
			node.putInt(key, (Integer)newValue);
		else if(type.isAssignableFrom(Long.class) || type.isAssignableFrom(Long.TYPE))
			node.putLong(key, (Long)newValue);
		else
			throw new UnsupportedOperationException(type+" not supported"); //$NON-NLS-1$

	}

	private void setValue(String newValue) 
	{
		if(type.isAssignableFrom(String.class))
			node.put(key, newValue);
		else if(type.isAssignableFrom(Boolean.class))
			node.putBoolean(key, Boolean.valueOf(newValue));
		else if(type.isAssignableFrom(byte[].class))
			node.putByteArray(key, newValue.getBytes(Charset.forName("UTF-8"))); //$NON-NLS-1$
		else if(type.isAssignableFrom(Double.class))
			node.putDouble(key, Double.valueOf(newValue));
		else if(type.isAssignableFrom(Float.class))
			node.putFloat(key, Float.valueOf(newValue));
		else if(type.isAssignableFrom(Integer.class))
			node.putInt(key, Integer.valueOf(newValue));
		else if(type.isAssignableFrom(Long.class))
			node.putLong(key, Long.valueOf(newValue));
		else
			throw new UnsupportedOperationException(type+" not supported"); //$NON-NLS-1$
	}
	
	
	@Override
	public Class<T> getType() {
		return type;
	}

}
