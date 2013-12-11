/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.updatecenter.repository.ui.UpdatecenterPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class UpdatecenterPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public UpdatecenterPanel(String id, IModel<T> model, PageParameters parameters) {
        super(id, model, parameters);
        add(new BookmarkablePageLink<T>("link", UpdatecenterPage.class));
    }

    public static class UpdatecenterPanelFactory implements PanelFactory<Object>, Serializable
    {

        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new UpdatecenterPanel<Object>(id, input , params);
        }

    }
}
