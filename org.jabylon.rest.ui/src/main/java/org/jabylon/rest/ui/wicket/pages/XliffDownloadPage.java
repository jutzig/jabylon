package org.jabylon.rest.ui.wicket.pages;

/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.properties.Project;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.wicket.JabylonApplication;
import org.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import org.jabylon.rest.ui.wicket.panels.XliffDownloadPanel;
import org.jabylon.security.CommonPermissions;

/**
 * @author c.samulski (20156-01-26)
 */
public class XliffDownloadPage extends GenericResolvablePage<Resolvable<?, ?>> implements RestrictedComponent {

	private static final long serialVersionUID = 1L;

	public XliffDownloadPage(PageParameters parameters) {
		super(parameters);
		setStatelessHint(true);
	}

	@Override
	protected void construct() {
		addOrReplace(new XliffDownloadPanel("panel-download-xliff", getModel(), getPageParameters()));
		BreadcrumbPanel breadcrumbPanel = new BreadcrumbPanel("breadcrumb-panel", getModel(), getPageParameters(), ResourcePage.class);
		add(breadcrumbPanel);
	}

	@Override
	public String getRequiredPermission() {
        Resolvable<?, ?> object = getModelObject();
        while(object!=null) {
            if (object instanceof Project) {
                return CommonPermissions.constructPermissionName(object, CommonPermissions.ACTION_VIEW);
            }
            object = object.getParent();
        }
        return null;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get()
				.getJavaScriptLibrarySettings().getJQueryReference())));
		super.renderHead(response);
	}
}