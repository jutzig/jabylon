/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.model.IModel;

public class BootstrapTabbedPanel<T extends ITab> extends TabbedPanel<T> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BootstrapTabbedPanel(String id, List<T> tabs) {
        super(id, tabs);

        commonInit();
    }

    public BootstrapTabbedPanel(String id, List<T> tabs, IModel<Integer> model) {
        super(id, tabs, model);

        commonInit();
    }

    private void commonInit() {
//        add(new BootstrapBaseBehavior(),
//            new CssClassNameAppender("tabbable"),
//            new AssertTagNameBehavior("div"));
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "active"; //$NON-NLS-1$
    }

    @Override
    protected String getLastTabCssClass() {
        return ""; //$NON-NLS-1$
    }
}
