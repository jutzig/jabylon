/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket;

import java.util.List;

import org.apache.wicket.DefaultExceptionMapper;
import org.apache.wicket.Session;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.Url.QueryParameter;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptionMapper extends DefaultExceptionMapper {

	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionMapper.class);
	
	@Override
	public IRequestHandler map(Exception e) {
		if (e instanceof ObjectNotFoundException) {
			//see https://github.com/jutzig/jabylon/issues/175
			Request request = RequestCycle.get().getRequest();
			Url url = request.getUrl();
			List<QueryParameter> parameters = url.getQueryParameters();
			boolean redirected = false;
			for (QueryParameter queryParameter : parameters) {
				if(queryParameter.getValue().isEmpty() && queryParameter.getName().matches("\\d+")) {
					url.removeQueryParameters(queryParameter.getName());
					LOG.error("Detected request to expired CDO ID. Attempting redirect to "+url.toString(),e);
					Session.get().error("Sorry, this page content has expired. Please try again");
					redirected = true;
					break;
				}
			}			
			if(redirected)
				return new RedirectRequestHandler("/"+url.toString());
		}
		return super.map(e);
	}
	
}
