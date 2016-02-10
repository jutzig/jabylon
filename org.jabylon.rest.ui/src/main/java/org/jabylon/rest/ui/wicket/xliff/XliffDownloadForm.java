/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.resource.IResource;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.panels.PropertyListMode;
import org.jabylon.rest.ui.wicket.panels.XliffLanguageTupleSelectionPanel;

/**
 * @author c.samulski (2016-01-28)
 */
public class XliffDownloadForm extends StatelessForm<Void> {

	private static final long serialVersionUID = -7727556253068015509L;
	private static final String FORM_ID = "mdf-languageselect";
	private static final String COMPONENT_LIST_ID = "mdf-components";
	private static final String LABEL_TARGETLANG_ID = "mdf-label-targetlang";
	private static final String LABEL_SOURCELANG_ID = "mdf-label-sourcelang";
	private static final String SELECT_FILTER_ID = "mdf-select-filter";

	private final IModel<Resolvable<?, ?>> projectVersion;
	private PropertyListMode filter = PropertyListMode.ALL;

	public XliffDownloadForm(List<Language> languages, IModel<Resolvable<?, ?>> model) {
		super(FORM_ID);
		this.projectVersion = model;
		buildForm(languages);
	}

	/**
	 * Constructs *this* form. <br>
	 *
	 * @param languages
	 *            Languages to serve as basis for {@link Component} data.<br>
	 */
	private void buildForm(List<Language> languages) {
		/* Retrieve language selection panel form elements. */
		List<XliffLanguageTupleSelectionPanel> languageSelectionTupels = getLanguageSelectionPanels(languages);

		add(new Label(LABEL_TARGETLANG_ID, new StringResourceModel("label.source.language",this,null)));
		add(new Label(LABEL_SOURCELANG_ID, new StringResourceModel("label.target.language",this,null)));
		add(new DropDownChoice<PropertyListMode>(SELECT_FILTER_ID, //
				new PropertyModel<PropertyListMode>(this, "filter"), //
				Arrays.asList(PropertyListMode.values()), //
				new EnumChoiceRenderer<PropertyListMode>(this)));

		add(new ListView<XliffLanguageTupleSelectionPanel>(COMPONENT_LIST_ID, languageSelectionTupels) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<XliffLanguageTupleSelectionPanel> item) {
				item.add(item.getModelObject());
			}
		});
	}

	/**
	 * Construct a {@link XliffLanguageTupleSelectionPanel} for each language, filter out the template as
	 * we don't want to offer this as a selection choice in our form.<br>
	 *
	 * @return {@link List} of populated {@link XliffLanguageTupleSelectionPanel}s, for each language
	 *         passed.<br>
	 */
	private static List<XliffLanguageTupleSelectionPanel> getLanguageSelectionPanels(List<Language> languages) {
		List<XliffLanguageTupleSelectionPanel> languageSelectionTupels = new ArrayList<XliffLanguageTupleSelectionPanel>();

		for (Iterator<Language> languageIterator = languages.iterator(); languageIterator.hasNext();) {
			Language language = languageIterator.next();
			/* Don't offer template as target. */
			if (language.isTemplate()) {
				continue;
			}
			languageSelectionTupels.add(new XliffLanguageTupleSelectionPanel(languages, language));
		}
		return languageSelectionTupels;
	}

	/**
	 * Called by WICKET on form submit. Triggers subsequent processing, i.e. the conversion/download
	 * of XLIFF files.
	 * <p>
	 * 1. Retrieve form result as {@link XliffLanguageTupleSelectionPanel} objects.<br>
	 * 2. Convert to Map holding Language tuples.<br>
	 * 3. Call XLIFF conversion/export processing.<br>
	 */
	@Override
	protected void onSubmit() {
		List<XliffLanguageTupleSelectionPanel> result = getSelection();
		final Map<Language, Language> resultAsMap = processSelection(result);

		getRequestCycle().scheduleRequestHandlerAfterCurrent(new IRequestHandler() {

			@Override
			public void respond(IRequestCycle requestCycle) {
				XliffDownloadResource downloadResource = new XliffDownloadResource(resultAsMap, projectVersion, filter);
				downloadResource.respond(new IResource.Attributes(requestCycle.getRequest(), requestCycle.getResponse()));
			}

			@Override
			public void detach(IRequestCycle requestCycle) {
				// nothing to do.
			}
		});
	}

	/**
	 * Presumably *not* the "WICKET way" of doing things, as we're abusing a {@link Panel} object as
	 * a storage POJO, but it works fine for now, is clear enough and hassle-free. ;-)<br>
	 *
	 * Iterates *this* forms children, retrieving selected child {@link Panel}s.<br>
	 *
	 * @return a {@link List} of {@link XliffLanguageTupleSelectionPanel}s, which represent
	 *         {@link Language} tuples (source and target).<br>
	 */
	private List<XliffLanguageTupleSelectionPanel> getSelection() {
		final List<XliffLanguageTupleSelectionPanel> result = new ArrayList<XliffLanguageTupleSelectionPanel>();
		for (Component languageTupel : this.visitChildren(XliffLanguageTupleSelectionPanel.class)) {
			XliffLanguageTupleSelectionPanel asLanguageTupel = (XliffLanguageTupleSelectionPanel) languageTupel;
			if (asLanguageTupel.isSelected()) {
				result.add(asLanguageTupel);
			}
		}
		return result;
	}

	/**
	 * Convert the retrieved {@link XliffLanguageTupleSelectionPanel} objects to a {@link Map}.
	 * K=TargetLanguage, V=SourceLanguage.<br>
	 */
	private static Map<Language, Language> processSelection(List<XliffLanguageTupleSelectionPanel> selectedItems) {
		Map<Language, Language> languageTupels = new HashMap<Language, Language>();

		for (XliffLanguageTupleSelectionPanel languageTupel : selectedItems) {
			languageTupels.put(languageTupel.getTargetLanguage(), languageTupel.getSourceLanguage());
		}
		return languageTupels;
	}
}