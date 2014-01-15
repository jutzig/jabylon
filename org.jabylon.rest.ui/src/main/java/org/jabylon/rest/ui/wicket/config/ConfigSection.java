/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.osgi.service.prefs.Preferences;

public interface ConfigSection<T> extends Serializable, RestrictedComponent{

    WebMarkupContainer createContents(String id, IModel<T> input, Preferences config);

    void apply(Preferences config);

    void commit(IModel<T> input, Preferences config);

    boolean isVisible(IModel<T> input, Preferences config);

}
