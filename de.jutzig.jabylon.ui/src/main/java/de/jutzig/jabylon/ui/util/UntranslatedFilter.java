package de.jutzig.jabylon.ui.util;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;

@SuppressWarnings("serial")
public class UntranslatedFilter implements Filter {

	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		if(item instanceof PropertyPairItem) {
			PropertyPairItem pairItem = (PropertyPairItem)item;
			String val = pairItem.getTargetProperty().getValue();
			if(val==null || val.trim().equals(""))
				return true;
		}
		return false;
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return true;
	}

}
