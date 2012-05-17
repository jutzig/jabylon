/**
 * 
 */
package de.jutzig.jabylon.ui.tools.internal;

import java.util.Collection;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.tools.PropertyEditorTool;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ReviewTool implements PropertyEditorTool {

	
	private VerticalLayout layout;
	
	/**
	 * 
	 */
	public ReviewTool() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#selectionChanged(de.jutzig.jabylon.ui.container.PropertyPairContainer, java.util.List)
	 */
	@Override
	public void selectionChanged(PropertyPairItem currentSelection, Collection<Review> reviews) {
		layout.removeAllComponents();
		for (Review review : reviews) {
			Label label = new Label();
			label.setCaption(review.getMessage());
			label.setIcon(ImageConstants.IMAGE_ERROR);
			label.setContentMode(Label.CONTENT_TEXT);
			layout.addComponent(label);
			layout.setExpandRatio(label, 2);
			
//			TODO: wrap doesn't work
		}

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#init(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.PropertyFileDescriptor)
	 */
	@Override
	public void init(PropertyFileDescriptor template, PropertyFileDescriptor translation) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#createComponent()
	 */
	@Override
	public Component createComponent() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		return layout;
	}

}
