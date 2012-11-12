/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import de.jutzig.jabylon.rest.ui.model.PropertyPair;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SinglePropertyEditor extends GenericPanel<PropertyPair> {

	private static final long serialVersionUID = 1L;
	private boolean expanded;

	public SinglePropertyEditor(String id, IModel<PropertyPair> model, boolean expanded) {
		super(id, model);
		this.expanded = expanded;
		setOutputMarkupId(true);
		PropertyPair propertyPair = model.getObject();
		IStatus status = calculateRowStatus(propertyPair);
		String key = propertyPair.getTemplate().getKey();

		final Label icon = new Label("icon");
		icon.add(new AttributeModifier("class", "icon-chevron-right"));
		icon.setOutputMarkupId(true);
		add(icon);

		Label statusIcon = new Label("status-icon");
		add(statusIcon);
		statusIcon.add(new AttributeModifier("class", status.isOK() ? "icon-ok-circle" : "icon-warning-sign"));
		if(status.getSeverity()==IStatus.WARNING)
			add(new AttributeModifier("class", "warning"));
		else if(status.getSeverity()==IStatus.ERROR)
			add(new AttributeModifier("class", "error"));

		final WebMarkupContainer templatePanel = new WebMarkupContainer("template-area");
		templatePanel.setVisible(isExpanded());
		templatePanel.setOutputMarkupId(true);
		add(templatePanel);
		final WebMarkupContainer translationPanel = new WebMarkupContainer("translation-area");
		translationPanel.setVisible(isExpanded());
		translationPanel.setOutputMarkupId(true);
		add(translationPanel);

		final Label translationLabel = new Label("translation-label", new PropertyModel<PropertyPair>(propertyPair, "translated"));
		translationLabel.setVisible(!isExpanded());

		AjaxLink<?> toggleLink = new AjaxLink<Void>("toggle") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				setExpanded(!isExpanded());
				target.add(SinglePropertyEditor.this);
				String iconName = isExpanded() ? "icon-chevron-down" : "icon-chevron-right";
				icon.add(new AttributeModifier("class", iconName));

				translationLabel.setVisible(!isExpanded());
				templatePanel.setVisible(isExpanded());
				translationPanel.setVisible(isExpanded());
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

	private IStatus calculateRowStatus(PropertyPair propertyPair) {
		
		if (propertyPair.getOriginal() == null || propertyPair.getOriginal().isEmpty())
			return new Status(IStatus.ERROR, "de.jutzig.jabylon.rest.ui", "");
		else if (propertyPair.getTranslated() == null || propertyPair.getTranslated().isEmpty())
			return new Status(IStatus.ERROR, "de.jutzig.jabylon.rest.ui", "");
		return Status.OK_STATUS;
	}
	
	
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public boolean isExpanded() {
		return expanded;
	}

}
