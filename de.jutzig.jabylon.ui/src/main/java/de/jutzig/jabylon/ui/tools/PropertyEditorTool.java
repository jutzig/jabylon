/**
 * 
 */
package de.jutzig.jabylon.ui.tools;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.ui.Component;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface PropertyEditorTool {
	
	void selectionChanged(PropertyPairContainer currentSelection, Collection<Review> reviews);
	
	void init(PropertyFileDescriptor template, PropertyFileDescriptor translation);
	
	void textSelection(String text, Property owner, EStructuralFeature feature);
	
	Component createComponent();
	
}
