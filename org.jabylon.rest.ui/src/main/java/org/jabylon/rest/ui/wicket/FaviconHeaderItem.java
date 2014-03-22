/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket;

import java.util.Collections;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;
import org.jabylon.rest.ui.util.GlobalResources;

public class FaviconHeaderItem extends HeaderItem {

	private ResourceReference favicon;
	
	
	
	public FaviconHeaderItem(ResourceReference favicon) {
		super();
		this.favicon = favicon;
	}
	
	public FaviconHeaderItem() {
		this(GlobalResources.IMG_JABYLON_FAVICON);
	}

	@Override
	public Iterable<?> getRenderTokens() {
		return Collections.singletonList("<link rel=\"shortcut icon\"");
	}

	@Override
	public void render(Response response) {
		response.write(getFavIconReference(RequestCycle.get().urlFor(favicon, null)));

	}

	private CharSequence getFavIconReference(CharSequence url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<link rel=\"shortcut icon\" href=\"");
		sb.append(url);
		sb.append("\" type=\"image/x-icon\">\n");
		return sb.toString();
	}

}
