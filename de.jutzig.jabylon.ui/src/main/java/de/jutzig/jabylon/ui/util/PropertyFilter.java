/**
 * 
 */
package de.jutzig.jabylon.ui.util;

import com.vaadin.data.Item;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;

import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;

/**
 * @author joe
 *
 */
public class PropertyFilter implements Filter {

	
	private String filter;
	
	
	
	
	public PropertyFilter(String filter) {
		super();
		this.filter = filter;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container.Filter#passesFilter(java.lang.Object, com.vaadin.data.Item)
	 */
	@Override
	public boolean passesFilter(Object itemId, Item item)
			throws UnsupportedOperationException {
		PropertyPairItem pair = (PropertyPairItem)item;
		return matches(pair.getSourceProperty()) || matches(pair.getTargetProperty());
	}

	private boolean matches(de.jutzig.jabylon.properties.Property property) {
		String key = property.getKey();
		if(key != null && key.contains(filter))
			return true;
		
		String comment = property.getComment();
		if(comment != null && comment.contains(filter))
			return true;
		String value = property.getValue();
		if(value != null && value.contains(filter))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container.Filter#appliesToProperty(java.lang.Object)
	 */
	@Override
	public boolean appliesToProperty(Object propertyId) {
		return true;
	}

}
