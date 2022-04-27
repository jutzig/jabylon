/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jabylon.cdo.server.ServerConstants;
import org.jabylon.common.progress.RunnableWithProgress;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.index.properties.IndexActivator;
import org.jabylon.index.properties.QueryService;
import org.jabylon.index.properties.jobs.impl.ReorgIndexJob;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.CustomStringResourceModel;
import org.jabylon.rest.ui.model.PreferencesPropertyModel;
import org.jabylon.rest.ui.model.ProgressionModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ControlGroup;
import org.jabylon.rest.ui.wicket.components.ProgressPanel;
import org.jabylon.rest.ui.wicket.components.ProgressShowingAjaxButton;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.validators.CronValidator;
import org.jabylon.scheduler.JobExecution;
import org.jabylon.scheduler.ScheduleServiceException;
import org.jabylon.scheduler.SchedulerService;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexingConfigSection extends BasicPanel<Workspace> {

    private static final long serialVersionUID = 1L;

    @Inject
    private QueryService queryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexingConfigSection.class);

	private ProgressionModel progressModel;

	@Inject
	private SchedulerService scheduler;

	private Preferences config;

    public IndexingConfigSection(String id, IModel<Workspace> model, Preferences config) {
        super(id, model);
        this.config = config;
    }

    @Override
    protected void construct() {
    	super.construct();
        setOutputMarkupId(true);
        progressModel = new ProgressionModel("");
        add(new Label("summary", new CustomStringResourceModel("index.size.summary", this, null, getIndexSize())));
        final ProgressPanel progressPanel = new ProgressPanel("progress", progressModel);
        add(progressPanel);
        add(createUpdateIndexAction(progressPanel));

		Preferences indexJobConfig = PreferencesUtil.getNodeForJob(config, ReorgIndexJob.JOB_ID);
		PreferencesPropertyModel updateModel = new PreferencesPropertyModel(indexJobConfig, JobExecution.PROP_JOB_SCHEDULE, ReorgIndexJob.DEFAULT_SCHEDULE);
		ControlGroup indexCronGroup = new ControlGroup("index-cron-group", nls("index.cron.label"), nls("index.cron.description"));
		TextField<String> indexCron = new TextField<String>("index-cron", updateModel) {

			private static final long serialVersionUID = 1572798560921411829L;

			@Override
			public void convertInput() {
				super.convertInput();
				String[] value = getInputAsArray();
				String tmp = value != null && value.length > 0 ? value[0] : null;
				if(tmp==null)
					setConvertedInput("");
			}
		};
		indexCron.add(new CronValidator());
		indexCron.setConvertEmptyInputStringToNull(false);
		indexCronGroup.add(indexCron);
		add(indexCronGroup);

		if(scheduler!=null) {
			try {
				Date nextExecution = scheduler.nextExecution(indexJobConfig);
				if(nextExecution!=null)
				{
					indexCronGroup.setExtraLabel(nls("next.schedule.label",format(nextExecution)));
				}
			} catch (ScheduleServiceException e) {
				LOGGER.warn("failed to retrieve next job execution for {}",indexJobConfig.absolutePath());
			}
		}
		Form<Void> uploadForm = new FileUploadForm("tmx-upload-form");
		add(uploadForm);
    }

	protected String format(Date nextExecution) {
		long current = System.currentTimeMillis();
		//if it's less than 15 hours away only show the time
		if(nextExecution.getTime()-current<TimeUnit.HOURS.toMillis(23))
			return SimpleDateFormat.getTimeInstance(DateFormat.SHORT,getLocale()).format(nextExecution);
		return SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT,SimpleDateFormat.SHORT,getLocale()).format(nextExecution);
	}

	private long getIndexSize() {
		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
		long size = 0;
		try {
			if (directory != null) {
				String[] files;
				files = directory.listAll();
				for (String file : files) {
					size += directory.fileLength(file);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Failed to compute index size",e);
		}
		return size / 1024;
	}


	protected Component createUpdateIndexAction(ProgressPanel progressPanel) {
		RunnableWithProgress runnable = new RunnableWithProgress() {

			private static final long serialVersionUID = 1L;

			@Override
			public IStatus run(IProgressMonitor monitor) {
				Activator.getDefault().getRepositoryConnector();
				try {
					queryService.rebuildIndex(monitor);
				} catch (CorruptIndexException e) {
					return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
				} catch (IOException e) {
					return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
				}
				return Status.OK_STATUS;

			}
		};
		return new ProgressShowingAjaxButton("update-index", progressPanel, runnable, nls("update.index.job.label"));
	}

    public static class IndexingConfig extends AbstractConfigSection<Workspace> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Workspace> input, Preferences prefs) {
            return new IndexingConfigSection(id, input, prefs);
        }

        @Override
        public void commit(IModel<Workspace> input, Preferences config) {
            // nothing to do

        }


        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if(getDomainObject()!=null)
                projectName = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.WORKSPACE,projectName,CommonPermissions.ACTION_CONFIG);
        }
    }

    private class FileUploadForm extends Form<Void>
    {

		private static final long serialVersionUID = -3653084217384164795L;

		private final ArrayList<FileUpload> uploads;

		public FileUploadForm(String id) {
			super(id);
	           // set this form to multipart mode (always needed for uploads!)
            setMultiPart(true);
            uploads = new ArrayList<FileUpload>();

            // Add one multi-file upload field
            IModel<ArrayList<FileUpload>> model = new Model<ArrayList<FileUpload>>(uploads);
            add(new MultiFileUploadField("tmx-upload",model));

            // Set maximum size to 100K for demo purposes
//            setMaxSize(Bytes.kilobytes(100));
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();
			File home = new File(ServerConstants.WORKING_DIR);
			File tmx = new File(home,"tmx");
			tmx.mkdirs();
			boolean didSomething = false;
			for (FileUpload fileUpload : uploads) {
				File target = new File(tmx,fileUpload.getClientFileName());
				try {
					target.createNewFile();
					fileUpload.writeTo(target);
					didSomething = true;
				} catch (Exception e) {
					LOGGER.error("Failed to upload TMX file "+target,e);
				}
			}
			if(didSomething)
			{
				RunnableWithProgress runnable = new RunnableWithProgress() {

					private static final long serialVersionUID = 1L;

					@Override
					public IStatus run(IProgressMonitor monitor) {
						Activator.getDefault().getRepositoryConnector();
						try {
							queryService.rebuildIndex(monitor);
						} catch (CorruptIndexException e) {
							return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
						} catch (IOException e) {
							return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
						}
						return Status.OK_STATUS;

					}
				};
				Activator.getDefault().getProgressService().schedule(runnable, nls("update.index.job.label").getString());
			}
		}

    }
}
