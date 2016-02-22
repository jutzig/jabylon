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
import java.util.Map;
import java.util.zip.ZipEntry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.xliff.XliffUploadResult.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOG = LoggerFactory.getLogger(XliffUploadForm.class);

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
			handleNotifications(new XliffUploadHelper(projectVersion, zip.getInputStream()).handleUpload());
		} catch (IOException e) {
			getSession().error(getString("xliff.upload.error.unexpected"));
		}
	}
	
	/**
	 * Handles UI notifications for errors which may have occurred for specific {@link ZipEntry}s
	 * during the import process.<br>
	 */
	private void handleNotifications(Map<Level, List<XliffUploadResult>> results) {
		for (Map.Entry<Level, List<XliffUploadResult>> result : results.entrySet()) {
			String message = buildMessageForLevel(result.getValue());

			if (message.isEmpty()) {
				continue;
			}

			switch (result.getKey()) {
			case INFO: // don't spam info UI notifications. create an aggregated message.
				getSession().success(createSuccessMessage(result.getValue()));
				break;
			case WARNING:
				getSession().warn(message);
				break;
			case ERROR:
				getSession().error(message);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Creates one aggregated info message for all files that were uploaded successfully.<br>
	 */
	private String createSuccessMessage(List<XliffUploadResult> value) {
		return MessageFormat.format(getString("xliff.upload.success.aggregated"), value.size());
	}

	private static final int MAX_NOTIFICATIONS_PER_LEVEL = 2;

	/**
	 * Constructs an aggregated warning/error/info message from the given {@link List} of
	 * {@link XliffUploadResult}s, consuming at most {@link #MAX_NOTIFICATIONS_PER_LEVEL} results (to not spam the
	 * screen too much).<br>
	 */
	private String buildMessageForLevel(List<XliffUploadResult> results) {
		StringBuilder message = new StringBuilder("");

		int count = 0;
		for (XliffUploadResult result : results) {
			String singleMessage = MessageFormat.format(getString(result.getKey()), result.getParameters());
			if (count < MAX_NOTIFICATIONS_PER_LEVEL) {
				message.append(singleMessage);
				if (++count < MAX_NOTIFICATIONS_PER_LEVEL) {
					message.append('\n');
				}
			}
			/*
			 * Even though we don't include every result for UI notifications (simply too much), we
			 * do log them all.<br>
			 */
			logResult(result.getLevel(), singleMessage);
		}
		return message.toString();
	}

	/**
	 * Log a single (non-aggregated) upload result.<br>
	 */
	private void logResult(Level level, String message) {
		switch (level) {
		case INFO:
			LOG.info(message);
			break;
		case WARNING:
			LOG.warn(message);
			break;
		case ERROR:
			LOG.error(message);
			break;
		default:
			break;
		}
	}
}
