/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Url;

public class CustomWebRequest extends ServletWebRequest {

	
	
	private boolean post;

	public CustomWebRequest(HttpServletRequest httpServletRequest, String filterPrefix, Url url) {
		super(wrap(httpServletRequest), filterPrefix, url);
		if(Method.POST.name().equals(httpServletRequest.getMethod()))
			post = true;
	}

	public CustomWebRequest(HttpServletRequest httpServletRequest, String filterPrefix) {
		super(wrap(httpServletRequest), filterPrefix);
		if(Method.POST.name().equals(httpServletRequest.getMethod()))
			post = true;
	}

	private static HttpServletRequest wrap(HttpServletRequest httpServletRequest) {
		
		return new Wrapper(httpServletRequest);
	}
	
	public boolean isPost(){
		return post;
	}

}

class Wrapper extends HttpServletRequestWrapper
{
	public Wrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getContextPath() {
		return "";
		//prevent the wrapper from adding the uri segments to the context
		//return getRequest().getServletContext().getContextPath();
//		return super.getContextPath();
	}
}
