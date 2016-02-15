/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.xliff.XliffUploadForm;

/**
 * @author c.samulski (2016-02-08)
 */
public class XliffUploadPanel extends BasicResolvablePanel<Resolvable<?, ?>> implements java.io.Serializable {

	private static final long serialVersionUID = 2016_02_08L;
	private static final String FORM_UPLOAD_XLIFF = "form-upload-xliff";

	public XliffUploadPanel(String id, IModel<Resolvable<?, ?>> model, PageParameters parameters) {
		super(id, model, parameters);
		add(new XliffUploadForm(FORM_UPLOAD_XLIFF, getModel()));
	}
}
