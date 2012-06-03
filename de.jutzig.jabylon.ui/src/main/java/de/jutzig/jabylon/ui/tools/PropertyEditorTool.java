/**
 * 
 */
package de.jutzig.jabylon.ui.tools;

import java.util.Collection;

import com.vaadin.ui.Component;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface PropertyEditorTool {
	
	void selectionChanged(PropertyPairItem currentSelection, Collection<Review> reviews, SuggestionAcceptor acceptor);
	
	void init(PropertyFileDescriptor template, PropertyFileDescriptor translation);
	
	Component createComponent();
	
}
