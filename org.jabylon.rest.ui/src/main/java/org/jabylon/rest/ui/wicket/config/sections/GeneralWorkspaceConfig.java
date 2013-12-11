/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;

public class GeneralWorkspaceConfig extends AbstractConfigSection<Workspace>{

    private static final long serialVersionUID = 1L;

    @Override
    public WebMarkupContainer doCreateContents(String id, IModel<Workspace> input, Preferences prefs) {
        return new WorkspaceConfigSection(id, input, prefs);
    }

    @Override
    public void commit(IModel<Workspace> input, Preferences config) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getRequiredPermission() {
        return CommonPermissions.WORKSPACE_CONFIG;
    }

}
