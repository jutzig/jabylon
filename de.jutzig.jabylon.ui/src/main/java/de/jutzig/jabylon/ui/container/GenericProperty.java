/**
 * 
 */
package de.jutzig.jabylon.ui.container;

import com.vaadin.data.util.AbstractProperty;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GenericProperty<T> extends AbstractProperty {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6615275996350392283L;
	private Class<T> type;
	private T value;
	
	
	
	public GenericProperty(Class<T> type, T value) {
		super();
		this.type = type;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Property#getValue()
	 */
	@Override
	public T getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Property#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		throw new UnsupportedOperationException("Read Only");

	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Property#getType()
	 */
	@Override
	public Class<T> getType() {
		return type;
	}

}
