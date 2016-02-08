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
package org.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.wicket.BasicPanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BreadcrumbPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;
    private IModel<String> rootLabel = Model.of("Home");
    private final Class<? extends Page> pageClass;

    public BreadcrumbPanel(String id, IModel<T> object, PageParameters parameters) {
        super(id, object, parameters);
        pageClass = null;
    }

    /**
     * Allow the base {@link Page} {@link Class} (and hence link root) to be overridden.<br>
     */
    public BreadcrumbPanel(String id, IModel<T> object, PageParameters parameters, Class<? extends Page> pageClass) {
        super(id, object, parameters);
        this.pageClass = pageClass;
    }

    @Override
    protected void onBeforeRenderPanel() {
        populateBreadcrumbs();
    }

    private void populateBreadcrumbs() {
        final List<String> segments = computeSegments(getPageParameters());
        final PageParameters crumbParams = new PageParameters();
        ListView<String> listView = new ListView<String>("crumbs",segments) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<String> item) {
                BookmarkablePageLink<String> link;
                if(item.getModelObject()==null || item.getModelObject().isEmpty()) {
                    link = new BookmarkablePageLink<String>("link", getPageClass());
                     link.setBody(rootLabel);
                }
                else
                {
                    crumbParams.set(item.getIndex()-1, item.getModelObject());
                    link = new BookmarkablePageLink<String>("link", getPageClass(), new PageParameters(crumbParams));
                    link.setBody(item.getModel());
                }
                if(item.getIndex()==segments.size()-1)
                    link.add(new AttributeModifier("class", "active"));
                item.add(link);
            }
        };
        addOrReplace(listView);

    }

    private Class<? extends Page> getPageClass() {
        return pageClass != null ? pageClass : getPage().getClass();
    }

    private List<String> computeSegments(PageParameters pageParameters) {
        if (pageParameters == null)
            return Collections.emptyList();
        int size = pageParameters.getIndexedCount();
        List<String> segments = new ArrayList<String>(size+1);
        //add a null to go to the root
        segments.add("");
        for(int i=0;i<size;i++) {
            segments.add(pageParameters.get(i).toString());
        }
        return segments;
    }

    public void setRootLabel(IModel<String> rootLabel) {
        this.rootLabel = rootLabel;
    }

    public void setRootLabel(String rootLabel) {
        setRootLabel(Model.of(rootLabel));
    }

}
