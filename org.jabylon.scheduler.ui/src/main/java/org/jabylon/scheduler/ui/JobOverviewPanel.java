/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler.ui;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.model.ComputableModel;
import org.jabylon.rest.ui.model.ProgressionModel;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ProgressPanel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.scheduler.JobInstance;
import org.jabylon.scheduler.ScheduleServiceException;
import org.jabylon.scheduler.SchedulerService;
import org.jabylon.security.CommonPermissions;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Function;

public class JobOverviewPanel extends BasicPanel<Workspace> {

	private static final long serialVersionUID = -3170612366896934075L;
//	private static final Logger LOG = LoggerFactory.getLogger(JobOverviewPanel.class);
	
	private IModel<List<JobInstance>> jobsModel;

	public JobOverviewPanel(String id, IModel<Workspace> model) {
		super(id, model);
	}
	
	@Override
	protected void construct() {
		super.construct();
		jobsModel = createJobsModel();
		WebMarkupContainer container = new WebMarkupContainer("container", jobsModel);
		IModel<Integer> sizeModel = new ComputableModel<IModel<List<JobInstance>>, Integer>(new SizeFunction(), jobsModel);
		Label noJobs = new Label("running-jobs", nls("running.jobs.title",sizeModel));
		noJobs.setOutputMarkupId(true);
		container.add(noJobs);
		ListView<JobInstance> list = new ListView<JobInstance>("list",jobsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<JobInstance> item) {
				JobInstance instance = item.getModelObject();
				String argLab = getLabel(instance.getDomainObject());
				
				if(argLab==null)
					item.add(new Label("title", nls(instance.getExecutionObject().getClass(), instance.getDescription())));
				else
					item.add(new Label("title", nls(instance.getExecutionObject().getClass(), instance.getDescription(), argLab)));
				ProgressionModel model = new ProgressionModel(instance.getID());
				ProgressPanel progressPanel = new ProgressPanel("panel", model);
				item.add(progressPanel);
				item.setOutputMarkupId(true);
				progressPanel.start();				
			}

			private String getLabel(Object domainObject) {
				if (domainObject instanceof Workspace) {
					return "Workspace";
					
				}
				if (domainObject instanceof Resolvable) {
					@SuppressWarnings("rawtypes")
					Resolvable r = (Resolvable) domainObject;
					return r.fullPath().toString(); 
				}
				return null;
			}
		};
		list.setOutputMarkupId(true);
		list.setReuseItems(true);
		container.add(list);
		container.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
		add(container);
	}

	private IModel<List<JobInstance>> createJobsModel() {
		return new ComputableModel<String, List<JobInstance>>(new LookupFuncton(), "");
		
	}

	@Override
	protected void detachModel() {
		super.detachModel();
		jobsModel.detach();
	}
	
	private static class LookupFuncton implements Function<String, List<JobInstance>>, Serializable{

		private static final long serialVersionUID = 1409367843199408286L;

		@Override
		public List<JobInstance> apply(String input) {
			try {
				//XXX: this is really bad
				BundleContext context = FrameworkUtil.getBundle(JobOverviewPanel.class).getBundleContext();
				ServiceReference<SchedulerService> reference = context.getServiceReference(SchedulerService.class);
				SchedulerService schedulerService = context.getService(reference);
				List<JobInstance> runningJobs = schedulerService.getRunningJobs();
				context.ungetService(reference);
				return runningJobs;
			} catch (ScheduleServiceException e) {
				
				return Collections.emptyList(); 
			}
		}
		
	}

	public static class JobOverviewConfigSection extends AbstractConfigSection<Workspace>{

		private static final long serialVersionUID = 1L;


		@Override
		public WebMarkupContainer doCreateContents(String id, IModel<Workspace> input, Preferences config) {
			return new JobOverviewPanel(id, input);
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

}

class SizeFunction implements Function<IModel<List<JobInstance>>, Integer>, Serializable{

	private static final long serialVersionUID = -4535953340408752693L;

	@Override
	public Integer apply(IModel<List<JobInstance>> input) {
		return input.getObject().size();
	}
	
}
