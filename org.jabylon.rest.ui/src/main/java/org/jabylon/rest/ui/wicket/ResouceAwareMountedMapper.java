/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket;

import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.jabylon.rest.ui.wicket.pages.ResourcePage;
import org.jabylon.rest.ui.wicket.pages.StartupPage;

public class ResouceAwareMountedMapper extends MountedMapper {

	private boolean isHomePage;
	private boolean isStartupPage;
	private String mountPath;


	public ResouceAwareMountedMapper(String mountPath, Class<? extends IRequestablePage> pageClass) {
		super(mountPath, pageClass);
		isHomePage = pageClass == ResourcePage.class;
		isStartupPage = pageClass == StartupPage.class;
		this.mountPath = mountPath;
	}

	
	@Override
	protected UrlInfo parseRequest(Request request) {
		if(!isHomePage && !isStartupPage)
			return super.parseRequest(request);
		// get canonical url
		final Url url = request.getUrl().canonical();

		if (url.getSegments().size() != 0)
		{
			// if this is the home page, it must have either no segments, or the first must be workspace
			if(!url.getSegments().get(0).equalsIgnoreCase("workspace"))
				return null;
		}

		return super.parseRequest(request);
	}
	
	public String getMountPath() {
		return mountPath;
	}
	
}
