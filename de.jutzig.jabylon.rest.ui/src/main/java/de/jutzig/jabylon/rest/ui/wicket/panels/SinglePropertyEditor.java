/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
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
		setOutputMarkupId(true);
		PropertyPair propertyPair = model.getObject();
		decorateRowStatus(this, propertyPair);
		String key = propertyPair.getTemplate().getKey();

		final Label icon = new Label("icon");
		icon.add(new AttributeModifier("class", "icon-chevron-right"));
		icon.setOutputMarkupId(true);
		add(icon);

		final WebMarkupContainer templatePanel = new WebMarkupContainer("template-area");
		templatePanel.setVisible(false);
		templatePanel.setOutputMarkupId(true);
		add(templatePanel);
		final WebMarkupContainer translationPanel = new WebMarkupContainer("translation-area");
		translationPanel.setVisible(false);
		translationPanel.setOutputMarkupId(true);
		add(translationPanel);

		final Label translationLabel = new Label("translation-label", new PropertyModel<PropertyPair>(propertyPair, "translated"));

		AjaxLink toggleLink = new AjaxLink("toggle") {
			@Override
			public void onClick(AjaxRequestTarget target) {

				target.add(SinglePropertyEditor.this);
				String iconName = translationLabel.isVisible() ? "icon-chevron-down" : "icon-chevron-right";
				icon.add(new AttributeModifier("class", iconName));

				translationLabel.setVisible(!translationLabel.isVisible());
				templatePanel.setVisible(!templatePanel.isVisible());
				translationPanel.setVisible(!translationPanel.isVisible());
			}
		};
		add(toggleLink);
		toggleLink.add(icon);

		add(new Label("key-label", key));
		add(translationLabel);

		TextArea<PropertyPair> textArea = new TextArea<PropertyPair>("template", new PropertyModel<PropertyPair>(propertyPair,
				"original"));
		templatePanel.add(textArea);

		textArea = new TextArea<PropertyPair>("template-comment", new PropertyModel<PropertyPair>(propertyPair, "originalComment"));
		templatePanel.add(textArea);

		textArea = new TextArea<PropertyPair>("translation-comment", new PropertyModel<PropertyPair>(propertyPair,
				"translatedComment"));
		translationPanel.add(textArea);

		textArea = new TextArea<PropertyPair>("translation", new PropertyModel<PropertyPair>(propertyPair, "translated"));
		translationPanel.add(textArea);
	}

	private void decorateRowStatus(Component component, PropertyPair propertyPair) {
		if (propertyPair.getOriginal() == null || propertyPair.getOriginal().isEmpty())
			component.add(new AttributeModifier("class", "error"));
		else if (propertyPair.getTranslated() == null || propertyPair.getTranslated().isEmpty())
			component.add(new AttributeModifier("class", "error"));
	}

}
