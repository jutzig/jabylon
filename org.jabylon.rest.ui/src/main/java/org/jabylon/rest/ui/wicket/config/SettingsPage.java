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
package org.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.jabylon.rest.ui.model.IEObjectModel;
import org.jabylon.rest.ui.model.WritableEObjectModel;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.pages.GenericResolvablePage;
import org.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SettingsPage extends GenericResolvablePage<CDOObject> implements RestrictedComponent{

    private static final long serialVersionUID = 1L;

    public SettingsPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void construct() {
        super.construct();

        BreadcrumbPanel<CDOObject> breadcrumbPanel = new BreadcrumbPanel<CDOObject>("breadcrumb-panel", getModel(),getPageParameters());
        breadcrumbPanel.setRootLabel(new StringResourceModel("SettingsPage.breadcrumb.root.label",this,null));
        add(breadcrumbPanel);
    }

    @Override
    protected void onBeforeRenderPage() {
        if(getPageParameters().getIndexedCount()>0)
        {
            addOrReplace(new SettingsPanel<CDOObject>("content", getModel(), getPageParameters()));
        }
        else
        {
            addOrReplace(new SettingsOverviewPanel("content"));
        }
        super.onBeforeRenderPage();
    }

    protected CDOObject doLookup(List<String> segments) {

        try {
            CDOObject resolvable = super.doLookup(segments);
            CDOView cdoView = resolvable.cdoView();
            if (cdoView instanceof CDOTransaction) {
                return resolvable;
            }
            return cdoView.getSession().openTransaction().getObject(resolvable);
        } catch (ClassCastException e) {
            /*
             *  TODO this is very much a workaround for
             *  http://github.com/jutzig/jabylon/issues/issue/100
             *  in case our URI does not resolve to a CDOObject, but a list, we take the parent
             *  instead
             */
            getPageParameters().remove(segments.size()-1);
            getPageParameters().add("tab", segments.get(segments.size()-1));
            return doLookup(segments.subList(0, segments.size()-1));
        }
    }


    @Override
    protected IEObjectModel<CDOObject> createModel(CDOObject object) {
        return new WritableEObjectModel<CDOObject>(object);
    }

    @Override
    public String getRequiredPermission() {
          return null;
    }

}
