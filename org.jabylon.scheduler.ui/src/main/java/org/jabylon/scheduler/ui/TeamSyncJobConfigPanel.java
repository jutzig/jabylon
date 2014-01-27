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
package org.jabylon.scheduler.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.model.BooleanPreferencesPropertyModel;
import org.jabylon.rest.ui.model.PreferencesPropertyModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.CronValidator;
import org.jabylon.scheduler.JobExecution;
import org.jabylon.scheduler.ScheduleServiceException;
import org.jabylon.scheduler.SchedulerService;
import org.jabylon.scheduler.internal.jobs.TeamCommitJob;
import org.jabylon.scheduler.internal.jobs.TeamUpdateJob;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class TeamSyncJobConfigPanel extends BasicPanel<ProjectVersion> {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(TeamSyncJobConfigPanel.class);
	
	@Inject
	private SchedulerService scheduler;

	private Preferences root;

	public TeamSyncJobConfigPanel(String id, IModel<ProjectVersion> model, Preferences root) {
		super(id, model);
		this.root = root;
	}
	
	@Override
	protected void construct() {
		super.construct();
		Preferences updateConfig = PreferencesUtil.getNodeForJob(root, TeamUpdateJob.JOB_ID);
		PreferencesPropertyModel updateModel = new PreferencesPropertyModel(updateConfig, JobExecution.PROP_JOB_SCHEDULE, TeamUpdateJob.DEFAULT_SCHEDULE);
		ControlGroup updateCronGroup = new ControlGroup("update-cron-group", nls("update.cron.label"), nls("update.cron.description"));
		TextField<String> updateCron = new TextField<String>("update-cron", updateModel);
		updateCron.add(new CronValidator());
		updateCron.setConvertEmptyInputStringToNull(true);
		updateCronGroup.add(updateCron);
		add(updateCronGroup);

		BooleanPreferencesPropertyModel updateEnabledModel = new BooleanPreferencesPropertyModel(updateConfig, JobExecution.PROP_JOB_ACTIVE, false);
		ControlGroup updateEnabledCronGroup = new ControlGroup("update-cron-enabled-group", Model.of(""),nls("update.cron.enabled.description"));
		CheckBox updateEnabledCron = new CheckBox("update-cron-enabled", updateEnabledModel);
		updateEnabledCronGroup.add(updateEnabledCron);
		add(updateEnabledCronGroup);
		
		Preferences commitConfig = PreferencesUtil.getNodeForJob(root, TeamCommitJob.JOB_ID);
		PreferencesPropertyModel commitModel = new PreferencesPropertyModel(commitConfig, JobExecution.PROP_JOB_SCHEDULE, TeamCommitJob.DEFAULT_SCHEDULE);
		ControlGroup commitCronGroup = new ControlGroup("commit-cron-group", nls("commit.cron.label"), nls("commit.cron.description"));
		TextField<String> commitCron = new TextField<String>("commit-cron", commitModel);
		commitCron.setConvertEmptyInputStringToNull(true);
		commitCron.add(new CronValidator());
		commitCronGroup.add(commitCron);
		add(commitCronGroup);

		BooleanPreferencesPropertyModel commitEnabledModel = new BooleanPreferencesPropertyModel(commitConfig, JobExecution.PROP_JOB_ACTIVE, false);
		ControlGroup commitEnabledCronGroup = new ControlGroup("commit-cron-enabled-group", Model.of(""),nls("commit.cron.enabled.description"));
		CheckBox commitEnabledCron = new CheckBox("commit-cron-enabled", commitEnabledModel);
		commitEnabledCronGroup.add(commitEnabledCron);
		add(commitEnabledCronGroup);
		
		if(scheduler!=null) {
			try {
				Date nextExecution = scheduler.nextExecution(updateConfig);
				if(nextExecution!=null)
				{
					updateCronGroup.setExtraLabel(nls("next.schedule.label",format(nextExecution)));
				}
				nextExecution = scheduler.nextExecution(commitConfig);
				if(nextExecution!=null)
				{
					commitCronGroup.setExtraLabel(nls("next.schedule.label",format(nextExecution)));
				}
			} catch (ScheduleServiceException e) {
				LOG.warn("failed to retrieve next job execution for {}",updateConfig.absolutePath());
			}
			
		}
	}

	protected String format(Date nextExecution) {
		long current = System.currentTimeMillis();
		//if it's less than 15 hours away only show the time
		if(nextExecution.getTime()-current<TimeUnit.HOURS.toMillis(23))
			return SimpleDateFormat.getTimeInstance(DateFormat.SHORT,getLocale()).format(nextExecution);
		return SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT,SimpleDateFormat.SHORT,getLocale()).format(nextExecution);
	}

	public static class TeamSyncJobConfigSection extends AbstractConfigSection<ProjectVersion>{

		private static final long serialVersionUID = 1L;


		@Override
		public WebMarkupContainer doCreateContents(String id, IModel<ProjectVersion> input, Preferences config) {
			return new TeamSyncJobConfigPanel(id, input, config);
		}

		@Override
		public void commit(IModel<ProjectVersion> input, Preferences config) {
			// TODO Auto-generated method stub

		}

		@Override
		public String getRequiredPermission() {
			
			String projectName = null;
			if(getDomainObject()!=null) {
				Project project = getParent(getModel());
				if(project!=null)
				 projectName = project.getName();
			}
			return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_CONFIG);
		}

		private Project getParent(IModel<ProjectVersion> domainObject) {
			if(domainObject.getObject().getParent()!=null)
				return domainObject.getObject().getParent();
			if (domainObject instanceof AttachableModel) {
				AttachableModel<ProjectVersion> model = (AttachableModel<ProjectVersion>) domainObject;
				return (Project) model.getParent().getObject();
			}
			return null;
		}
		
		@Override
		public boolean isVisible(IModel<ProjectVersion> input, Preferences config) {
			String teamProvider = getParent(input).getTeamProvider();
			return super.isVisible(input, config) && teamProvider!=null;
		}
		
	}

}
