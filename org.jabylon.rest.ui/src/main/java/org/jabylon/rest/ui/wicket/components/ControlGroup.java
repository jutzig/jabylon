/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;

public class ControlGroup extends Border {

	private static final long serialVersionUID = 7523670767478562406L;
	private Label label;
	private Label help;
	private FeedbackMessage feedback;
	private Label feedbackLabel;
	private Label extra;

	public ControlGroup(final String id) {
		this(id, Model.of(""), Model.of(""));
	}

	public ControlGroup(final String id, final IModel<String> label) {
		this(id, label, Model.of(""));
	}

	public ControlGroup(final String id, final IModel<String> label, final IModel<String> help) {
		super(id, Model.of(""));

		this.label = new Label("label", label);
		this.help = new Label("help", help);
		this.extra = new Label("extra-label", "");
		extra.setVisible(false);
		this.feedbackLabel = new Label("feedback", Model.of(""));

		addToBorder(this.label, this.help, this.feedbackLabel, this.extra);
	}
	
	public void setExtraLabel(IModel<String> model) {
		extra.setVisible(model.getObject()!=null && !model.getObject().isEmpty());
		extra.setDefaultModel(model);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final List<FormComponent<?>> formComponents = collectFormComponents();
		for (FormComponent<?> formComponent : formComponents) {
			formComponent.setOutputMarkupId(true);
		}
		final int size = formComponents.size();

		if (size > 0) {
			FormComponent<?> formComponent = formComponents.get(size - 1);
			label.add(new AttributeModifier("for", formComponent.getMarkupId()));
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<FormComponent<?>> collectFormComponents() {
		ComponentHierarchyIterator visitor = getBodyContainer().visitChildren(FormComponent.class);
		List components = new ArrayList();
		while (visitor.hasNext()) {
			Component next = visitor.next();
			components.add(next);
		}
		return components;
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		feedback = findWorstMessage();
		if(feedback!=null)
		{
			feedbackLabel.setDefaultModel(Model.of(feedback.getMessage()));
		}
		else
		{
			feedbackLabel.setDefaultModel(Model.of(""));
		}
		hideIfEmpty(help);
		hideIfEmpty(feedbackLabel);
	}
	
	private void hideIfEmpty(Label component) {
		Object content = component.getDefaultModelObject();
		component.setVisible(content!=null && !content.toString().isEmpty());
		
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		String stateClass = getStateClass(feedback);
		tag.put("class", "control-group" + stateClass);

	}

	protected FeedbackMessage findWorstMessage() {
		List<FormComponent<?>> components = collectFormComponents();
		FeedbackMessage worst = null;
		int worstLevel = 0;
		for (FormComponent<?> formComponent : components) {
			if(!formComponent.hasFeedbackMessage())
				continue;
			FeedbackMessages messages = formComponent.getFeedbackMessages();
			if(messages!=null)
			{
				for (FeedbackMessage message : messages) {
					if(message.getLevel()>worstLevel)
					{
						worstLevel = message.getLevel();
						worst = message;
					}
				}
			}
		}
		return worst;
	}
	
	protected String getStateClass(FeedbackMessage message) {

		if(message==null)
			return "";
		
		switch (message.getLevel()) {
		case FeedbackMessage.INFO:
			return " info";
		case FeedbackMessage.SUCCESS:
			return " success";	
		case FeedbackMessage.WARNING:
			return " warning";
		case FeedbackMessage.ERROR:
			return " error";
		default:
			break;
		}
		return "";
	}
	
}