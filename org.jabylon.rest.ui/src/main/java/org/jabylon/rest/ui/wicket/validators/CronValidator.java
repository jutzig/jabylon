/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.validators;

import java.text.ParseException;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.jabylon.scheduler.JobUtil;

/**
 * @author jutzig.dev@googlemail.com
 * 
 */
public class CronValidator implements IValidator<String> {

	private static final long serialVersionUID = 1L;

	public CronValidator() {
		super();
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		String cron = validatable.getValue();
		try {
			if(cron!=null && !cron.isEmpty())
				JobUtil.validateCron(cron);
		} catch (ParseException e) {
			validatable.error(new ValidationError(e.getLocalizedMessage()));
		}
	}
}
