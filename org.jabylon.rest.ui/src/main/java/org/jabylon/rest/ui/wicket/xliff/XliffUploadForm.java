/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.ZipEntry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.jabylon.properties.Resolvable;

/**
 * Represents the UI form used for uploading XLIFF documents.<br>
 * Calls {@link XliffUploadHelper} to process subsequent upload/import processing via
 * {@link #onSubmit()}.<br>
 * 
 * @author c.samulski (2016-02-08)
 */
public class XliffUploadForm extends StatelessForm<Resolvable<?, ?>> {

	private static final long serialVersionUID = 2016_02_08L;

	private final IModel<Resolvable<?, ?>> projectVersion;
	private static final String LABEL_UPLOAD_XLIFF = "xuf-label-upload";
	private static final String INPUT_UPLOAD_XLIFF = "xuf-input-upload";

	private FileUploadField fileUpload = new FileUploadField(INPUT_UPLOAD_XLIFF);

	public XliffUploadForm(String id, IModel<Resolvable<?, ?>> model) {
		super(id, model);
		add(new Label(LABEL_UPLOAD_XLIFF, new StringResourceModel("label.xliff.upload", this, null)));
		add(fileUpload);
		this.projectVersion = model;
	}

	/**
	 * 1. Triggers import processing via {@link XliffUploadHelper#handleUpload()}.<br>
	 * 2. Triggers the display of success, warning and error messages based on the result of the
	 * aforementioned call.<br>
	 */
	@Override
	protected void onSubmit() {
		final FileUpload zip = fileUpload.getFileUpload();

		if (zip == null) {
			getSession().error(getString("xliff.upload.error.nofile"));
			return;
		}

		try {
			handleNotification(new XliffUploadHelper(projectVersion, zip.getInputStream()).handleUpload());
		} catch (IOException e) {
			getSession().error(getString("xliff.upload.error.unexpected"));
		}
	}

	/**
	 * Handles UI notifications for errors which may have occurred for specific {@link ZipEntry}s
	 * during the import process.<br>
	 */
	private void handleNotification(List<XliffUploadResult> results) {
		for (XliffUploadResult result : results) {
			switch (result.getLevel()) {
			case INFO:
				getSession().success(MessageFormat.format(getString(result.getKey()), result.getParameters()));
				break;
			case WARNING:
				getSession().warn(MessageFormat.format(getString(result.getKey()), result.getParameters()));
				break;
			case ERROR:
				getSession().error(MessageFormat.format(getString(result.getKey()), result.getParameters()));
				break;
			default:
				break;
			}
		}
	}
}
