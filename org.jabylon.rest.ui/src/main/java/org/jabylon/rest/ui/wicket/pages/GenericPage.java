/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.pages;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jabylon.rest.ui.navbar.NavbarPanel;
import org.jabylon.rest.ui.util.WebContextUrlResourceReference;
import org.jabylon.rest.ui.wicket.JabylonApplication;
import org.jabylon.rest.ui.wicket.components.CustomFeedbackPanel;
import org.jabylon.rest.ui.wicket.components.IAjaxFeedbackPage;

public abstract class GenericPage<T> extends WebPage implements IAjaxFeedbackPage {

    private static final long serialVersionUID = 1L;

    private boolean constructed;
    private IModel<T> model;
    private CustomFeedbackPanel feedbackPanel;

    public GenericPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {

        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
        response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("bootstrap/js/bootstrap.min.js"))));
        response.render(CssHeaderItem.forReference(new WebContextUrlResourceReference("css/main.css")));
        super.renderHead(response);
    }

    @Override
    protected final void onBeforeRender() {

        setStatelessHint(true);
        internalConstruct();
        onBeforeRenderPage();
        super.onBeforeRender();
    }

    protected void onBeforeRenderPage() {

    }

    private final void internalConstruct() {
        if (!constructed) {
            feedbackPanel = new CustomFeedbackPanel("feedbackPanel");
            feedbackPanel.setOutputMarkupId(true);
            add(feedbackPanel);

            setModel(createModel(getPageParameters()));
            add(new NavbarPanel<T>("navbar", getModel(), getPageParameters()));

            preConstruct();
            construct();
            constructed = true;

        }
    }

    protected void preConstruct() {
        // subclasses may override

    }

    protected void construct() {

        // subclasses may override
    }

    protected abstract IModel<T> createModel(PageParameters params);

    public void setModel(IModel<T> model) {
        this.model = model;
    }

    @SuppressWarnings("rawtypes")
    protected IModel<T> createModel(T object) {
        return new Model((Serializable) object);
    }

    public IModel<T> getModel() {
        return model;
    }

    public T getModelObject() {
        if(model==null)
            return null;
        return model.getObject();
    }

    @Override
    public void detachModels() {
        super.detachModels();
        if(getModel()!=null)
            getModel().detach();
    }

    @Override
    public void showFeedback(AjaxRequestTarget target) {
        target.add(feedbackPanel);
        target.appendJavaScript("$('#" + feedbackPanel.getMarkupId() + "').addClass('ajax')" +
                ".clearQueue().show().slideDown().delay(5000).slideUp().end().hide();");
    }

}
