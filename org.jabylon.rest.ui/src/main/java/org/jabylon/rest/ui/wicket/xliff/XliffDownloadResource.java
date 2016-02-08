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
import java.io.OutputStream;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.panels.PropertyListMode;

/**
 * @author c.samulski (2016-02-05)
 */
public final class XliffDownloadResource extends AbstractResource {

	private static final long serialVersionUID = 1L;

	private final Map<Language, Language> languageMap;
	private final IModel<Resolvable<?, ?>> projectVersion;
	private final PropertyListMode filter;

	public XliffDownloadResource(Map<Language, Language> languageMap, IModel<Resolvable<?, ?>> projectVersion,
			PropertyListMode filter) {
		this.languageMap = languageMap;
		this.projectVersion = projectVersion;
		this.filter = filter;
	}

	@Override
	protected ResourceResponse newResourceResponse(Attributes attributes) {
		ResourceResponse resourceResponse = new ResourceResponse();
		resourceResponse.setContentType("application/zip");
		resourceResponse.setTextEncoding("UTF-8");
		resourceResponse.setContentDisposition(ContentDisposition.ATTACHMENT);
		resourceResponse.setFileName(getFileName());
		resourceResponse.setWriteCallback(new WriteCallback() {
			@Override
			public void writeData(Attributes attributes) throws IOException {
				OutputStream outputStream = attributes.getResponse().getOutputStream();
				new XliffDownloadHelper(projectVersion, languageMap, filter, outputStream).handleXliffDownload();
			}
		});
		resourceResponse.disableCaching();
		return resourceResponse;
	}

	private String getFileName() {
		return projectVersion.getObject().getName() + ".zip";
	}
}
