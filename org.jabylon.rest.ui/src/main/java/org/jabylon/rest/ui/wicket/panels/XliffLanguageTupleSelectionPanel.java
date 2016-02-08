/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.jabylon.rest.ui.wicket.xliff.Language;

/**
 * @author c.samulski (2016-01-27)
 */
public class XliffLanguageTupleSelectionPanel extends Panel {

	private static final long serialVersionUID = 573629903175612300L;
	private static final String PANEL_ID = "stlsp-panel";
	private static final String LABEL_ID = "stlsp-label";
	private static final String SELECT_ID = "stlsp-select";
	private static final String CHECKBOX_ID = "stlsp-checkbox";

	private Language sourceLanguage = null;
	private final Language targetLanguage;
	private boolean selected = Boolean.FALSE;

	public XliffLanguageTupleSelectionPanel(final List<Language> languages, final Language targetLanguage) {
		super(PANEL_ID);
		this.targetLanguage = targetLanguage;
		/* add UI components */
		add(new Label(LABEL_ID, this.targetLanguage.toString()));
		add(new DropDownChoice<Language>(SELECT_ID, new PropertyModel<Language>(this, "sourceLanguage"), languages));
		add(new CheckBox(CHECKBOX_ID, new PropertyModel<Boolean>(this, "selected")));
	}

	public Language getSourceLanguage() {
		return this.sourceLanguage;
	}

	public Boolean isSelected() {
		return selected;
	}

	/* Called via WICKET on Form Submit. */
	public void setSourceLanguage(Language language) {
		this.sourceLanguage = language;
	}

	/* Called via WICKET on Form Submit. */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Language getTargetLanguage() {
		return targetLanguage;
	}
}