/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections.security;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.util.CommitException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jabylon.cdo.connector.TransactionUtil;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.wicket.components.UserImagePanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.rest.ui.wicket.config.SettingsPanel;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;

public class UsersConfigSection extends GenericPanel<UserManagement> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(UsersConfigSection.class);

    public UsersConfigSection(String id, IModel<UserManagement> model) {
        super(id, model);
        ComplexEObjectListDataProvider<User> provider = new ComplexEObjectListDataProvider<User>(model, UsersPackage.Literals.USER_MANAGEMENT__USERS);
        ListView<User> userList = new ListView<User>("user.row",provider) {


            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<User> item) {

                item.add(new UserImagePanel("username", item.getModel()));
                item.add(new Label("roles",buildRoles(item.getModelObject())));
                PageParameters params = new PageParameters(getPage().getPageParameters());
                params.set(params.getIndexedCount(),"users");
                String name = item.getModelObject().getName();
                //so it doesn't crash on a broken model
                if(name==null)
                	name = "";
                params.set(params.getIndexedCount(),name);
                item.add(new BookmarkablePageLink<Void>("edit",SettingsPage.class, params));
                item.add(new Link<User>("delete",item.getModel()){

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick() {
                        try {
                            TransactionUtil.deleteWithCrossRefs(getModelObject());
                        } catch (CommitException e) {
                            getSession().error(e.getMessage());
                            logger.error("Failed to commit",e);
                        }
                    }

                });
            }
        };
        add(userList);
        add(buildAddNewLink(getModel()));
    }

    protected String buildRoles(User modelObject) {
        StringBuilder builder = new StringBuilder();
        //TODO: shouldn't there be an "allRoles"?
        for (Role role : modelObject.getRoles()) {
            builder.append(role.getName());
            builder.append(", ");
        }
        if(builder.length()>2)
            builder.setLength(builder.length()-2);
        return builder.toString();
    }

    private Component buildAddNewLink(IModel<UserManagement> model) {
        PageParameters params = new PageParameters();
        UserManagement project = model.getObject();
        if (project.cdoState() == CDOState.NEW || project.cdoState() == CDOState.TRANSIENT) {
            // it's a new project, we can't add anything yet
            Button link = new Button("addNew");
            link.setEnabled(false);
            return link;
        }
        params.set(0, "security");
        params.add(SettingsPanel.QUERY_PARAM_CREATE, UsersPackage.Literals.USER.getName());
        params.add(SettingsPanel.QUERY_PARAM_NAMESPACE, UsersPackage.eNS_URI);
        return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, params);
    }

    public static class UsersConfigSectionContributor extends AbstractConfigSection<UserManagement> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<UserManagement> input, Preferences config) {

            return new UsersConfigSection(id, input);
        }

        @Override
        public void commit(IModel<UserManagement> input, Preferences config) {
            // TODO Auto-generated method stub

        }


        @Override
        public String getRequiredPermission() {
            return CommonPermissions.USER_GLOBAL_CONFIG;
        }

    }

}
