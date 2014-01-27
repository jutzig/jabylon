/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.rest.ui.model.BooleanPreferencesPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;

public class TranslationChecksConfigSection extends BasicPanel<Project> {

    private static final long serialVersionUID = 1L;

    @Inject
    private List<ReviewParticipant> participants;

    public TranslationChecksConfigSection(String id, IModel<Project> model, Preferences prefs) {
        super(id, model);
        RepeatingView repeater = new RepeatingView("checks");
        if(participants!=null)
        {
            for (ReviewParticipant participant : participants) {
                WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
                repeater.add(container);

                BooleanPreferencesPropertyModel propertyModel = new BooleanPreferencesPropertyModel(prefs, participant.getID(), false);
                CheckBox checkBox = new CheckBox("check",propertyModel);
                container.add(new Label("check-label",nls(participant.getClass(),participant.getName())));
                container.add(checkBox);
                container.add(new Label("description",nls(participant.getClass(),participant.getDescription())));
            }
        }
        add(repeater);
    }


    public static class TranslationChecksConfig extends AbstractConfigSection<Project> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences prefs) {
            return new TranslationChecksConfigSection(id, input, prefs.node(PreferencesUtil.NODE_CHECKS));
        }

        @Override
        public void commit(IModel<Project> input, Preferences config) {
            // TODO Auto-generated method stub
            // TODO rename on filesystem

        }


        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if(getDomainObject()!=null)
                projectName = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_EDIT);
        }

    }

}
