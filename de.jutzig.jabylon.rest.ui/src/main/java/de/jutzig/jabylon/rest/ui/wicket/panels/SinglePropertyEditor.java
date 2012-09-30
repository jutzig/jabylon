/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.jutzig.jabylon.rest.ui.model.PropertyPair;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SinglePropertyEditor extends GenericPanel<PropertyPair> {

	public SinglePropertyEditor(String id, IModel<PropertyPair> model) {
		super(id, model);
		PropertyPair pair = model.getObject();
		add(new Label("key",pair.getTemplate().getKey()));
		TextArea<PropertyPair> textArea = new TextArea<PropertyPair>("template", new PropertyModel<PropertyPair>(model, "original"));
		add(textArea);
		
		textArea = new TextArea<PropertyPair>("template-comment", new PropertyModel<PropertyPair>(model, "originalComment"));
		add(textArea);
		
		textArea = new TextArea<PropertyPair>("translation-comment", new PropertyModel<PropertyPair>(model, "translatedComment"));
		add(textArea);
		
		textArea = new TextArea<PropertyPair>("translation", new PropertyModel<PropertyPair>(model, "translated"));
		add(textArea);
	}

}
