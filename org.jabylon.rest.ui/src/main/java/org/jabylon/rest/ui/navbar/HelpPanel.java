/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class HelpPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public HelpPanel(String id, IModel<T> model, PageParameters parameters) {
        super(id, model, parameters);
        add(new ExternalLink("link","http://jabylon.org")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static class HelpPanelFactory implements PanelFactory<Object>, Serializable
    {

        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new HelpPanel<Object>(id, input , params);
        }

    }
}
