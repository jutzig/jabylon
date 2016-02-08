/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.xliff;

import java.util.Locale;

/**
 * @author c.samulski (2016-01-26)
 */
public class Language implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE = "Template";
	private final Locale locale;

	public Language(Locale locale) {
		this.locale = locale;
	}

	public final boolean isTemplate() {
		return locale == null;
	}

	@Override
	public final String toString() {
		if (isTemplate()) {
			return TEMPLATE;
		}
		return locale.getDisplayLanguage();
	}

	public final Locale getLocale() {
		return locale;
	}
}