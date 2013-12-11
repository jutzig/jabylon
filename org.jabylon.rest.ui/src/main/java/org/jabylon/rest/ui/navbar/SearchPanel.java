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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.URI;

import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.rest.ui.wicket.pages.SearchPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SearchPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public SearchPanel(String id, IModel<T> object, PageParameters parameters) {
        super(id, object, parameters);
        add(new SearchForm("form")); //$NON-NLS-1$
        setVersioned(false);
    }



    public static class SearchPanelFactory implements PanelFactory<Object>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new SearchPanel<Object>(id, input, params);

        }

    }

    public class SearchForm extends StatelessForm<Void> {
        private static final long serialVersionUID = 1L;
        private String searchString;

        public SearchForm(String id) {
            super(id);
            TextField<String> field = new TextField<String>("searchfield", new PropertyModel<String>(this, "searchString")); //$NON-NLS-1$ //$NON-NLS-2$
            StringResourceModel placeholder = getPlaceholder(SearchPanel.this.getModel());
            field.add(new AttributeModifier("placeholder", placeholder)); //$NON-NLS-1$
            add(field);

        }
        public void onSubmit() {
            Url url = getRequest().getUrl();
            URI uri = URI.createURI(url.getPath(),false);

            PageParameters params = new PageParameters();
            params.add(SearchPage.SEARCH_TERM, getSearchString());
            String scope = getPageParameters().get(SearchPage.SCOPE).toString(uri.toString()); //if there already is a scope, use it. Otherwise use the current uri
            params.add(SearchPage.SCOPE, scope);
            setResponsePage(SearchPage.class, params);
        }

        public String getSearchString() {
            return searchString;
        }

        public void setSearchString(String searchString) {
            this.searchString = searchString;
        }

        private StringResourceModel getPlaceholder(IModel<T> model) {

            StringResourceModel plainModel = new StringResourceModel("SearchPanel.search.placeholder", SearchPanel.this, null); //$NON-NLS-1$
            if (model == null)
                return plainModel;
            T object = model.getObject();
            if (object instanceof Resolvable<?, ?>) {
                Resolvable<?, ?> r = (Resolvable<?, ?>) object;
                if (r.getName() != null && !r.getName().isEmpty()) {
                    return new StringResourceModel("SearchPanel.scoped.search.placeholder", SearchPanel.this, null,"",r.getName()); //$NON-NLS-1$
                }
            }
            return plainModel;
        }
    }
}
