/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.validators;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.eclipse.emf.common.util.URI;

/**
 * @author jutzig.dev@googlemail.com
 * 
 */
public class UriValidator implements IValidator<URI> {

	private static final long serialVersionUID = 1L;

	public UriValidator() {
		super();
	}

	@Override
	public void validate(IValidatable<URI> validatable) {
		URI uri = validatable.getValue();
		String key = null;
		if(uri.isRelative())
			key = UriValidator.class.getSimpleName()+".not.absolute";
		if(uri.path()==null && "https".equals(uri.scheme()) && "http".equals(uri.scheme()))
			key = UriValidator.class.getSimpleName()+".no.path";
		if(key!=null) {
			ValidationError error = new ValidationError();
			error.addKey(key);
			validatable.error(error);			
		}

	}
}
