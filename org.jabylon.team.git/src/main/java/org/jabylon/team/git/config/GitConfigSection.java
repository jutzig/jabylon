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
package org.jabylon.team.git.config;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import org.jabylon.properties.Project;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GitConfigSection extends AbstractConfigSection<Project>{

    private static final long serialVersionUID = 1L;

    private boolean gitSelected(IModel<Project> model) {
        return "Git".equals(model.getObject().getTeamProvider());
    }


    @Override
    public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences config) {
        GitConfigPanel panel = new GitConfigPanel(id, input, config);
        panel.setVisible(gitSelected(input));
        return panel;
    }

    @Override
    public boolean isVisible(IModel<Project> input, Preferences config) {
        return gitSelected(input) && super.isVisible(input, config);
    }


    @Override
    public void commit(IModel<Project> input, Preferences config) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getRequiredPermission() {
        String projectName = null;
        if(getDomainObject()!=null)
            projectName = getDomainObject().getName();
        return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_EDIT);
    }

}
