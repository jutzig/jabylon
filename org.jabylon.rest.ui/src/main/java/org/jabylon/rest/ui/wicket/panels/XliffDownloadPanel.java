/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.xliff.Language;
import org.jabylon.rest.ui.wicket.xliff.XliffDownloadForm;

/**
 * @author c.samulski (26.01.2016)
 */
public class XliffDownloadPanel extends BasicResolvablePanel<Resolvable<?, ?>> implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public XliffDownloadPanel(String id, IModel<Resolvable<?, ?>> model, PageParameters parameters) {
		super(id, model, parameters);

		List<Language> languages = loadLanguages(model.getObject());
		Form<Void> form = new XliffDownloadForm(languages, model);

		add(form);
	}

	/**
	 * @return a populated list of {@link Language}s for the given {@link ProjectVersion}.<br>
	 */
	private static List<Language> loadLanguages(Resolvable<?, ?> object) {
		List<Language> meta = new ArrayList<Language>();
		List<ProjectLocale> projectLocales = ((ProjectVersion) object).getChildren();

		for (ProjectLocale projectLocale : projectLocales) {
			meta.add(new Language(projectLocale.getLocale()));
		}

		return meta;
	}
}