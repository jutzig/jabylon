package org.jabylon.rest.ui.wicket.pages;

/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;
import org.jabylon.rest.ui.util.PageProvider;

@Component(immediate = true)
@Service(PageProvider.class)
public class XliffDownloadPageProvider implements PageProvider {

	@Property(value = "/xliff")
	static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;

	@Override
	public Class<? extends Page> getPageClass() {
		return XliffDownloadPage.class;
	}
}