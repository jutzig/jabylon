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
package org.jabylon.scheduler.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.util.ApplicationConstants;
import org.jabylon.common.util.AttachablePreferences;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.scheduler.JobExecution;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@Component(immediate = true, enabled = true)
public class JobRegistry implements INodeChangeListener, IPreferenceChangeListener {

	private Scheduler scheduler;
	
	public static final String PLUGIN_ID = "org.jabylon.scheduler";
	private static final Logger logger = LoggerFactory.getLogger(JobRegistry.class);

	@Reference
	private RepositoryConnector repositoryConnector;
	@Reference
	private URIResolver uriResolver;
	
	@Reference(referenceInterface=JobExecution.class,cardinality=ReferenceCardinality.OPTIONAL_MULTIPLE,policy=ReferencePolicy.DYNAMIC,bind="bindJob",unbind="unbindJob")
	private Map<String,JobExecution> jobDefinitions = new ConcurrentHashMap<String,JobExecution>();
	private Map<String, Preferences> jobInstances = new ConcurrentHashMap<String, Preferences>();

	public JobRegistry() {

	}
	
	public void bindUriResolver(URIResolver resolver) {
		this.uriResolver = resolver;
	}

	public void unbindUriResolver(URIResolver resolver) {
		this.uriResolver = resolver;
	}
	
	
	
	public void bindRepositoryConnector(RepositoryConnector connector) {
		this.repositoryConnector = connector;
	}

	public void unbindRepositoryConnector(RepositoryConnector connector) {
		this.repositoryConnector = null;
	}

	public void bindJob(JobExecution execution, Map<String,Object> properties) {
		AttachablePreferences prefs = new AttachablePreferences(PreferencesUtil.workspaceScope().node(ApplicationConstants.JOBS_NODE_NAME),execution.getID());
		jobDefinitions.put(execution.getID(),execution);
		Set<Entry<String, Object>> entrySet = properties.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			prefs.put(entry.getKey(), entry.getValue().toString());
		}
		try {
			updateJob(prefs);
		} catch (SchedulerException e) {
			logger.error("Failed to schedule job "+execution,e);
		}
	}

	public void unbindJob(JobExecution execution) {
		Iterator<Entry<String, Preferences>> iterator = jobInstances.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Preferences> entry = (Map.Entry<String, Preferences>) iterator.next();
			Preferences value = entry.getValue();
			if(execution.getID().equals(value.name()))
			{
				iterator.remove();
				removeJob(value.absolutePath());
			}
			
		}
		AttachablePreferences prefs = new AttachablePreferences(PreferencesUtil.workspaceScope().node(ApplicationConstants.JOBS_NODE_NAME),execution.getID());
		jobDefinitions.remove(execution.getID());
		removeJob(prefs.absolutePath());
		
		
	}	

	@Activate
	public void activate() throws SchedulerException {

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		scheduler = schedFact.getScheduler();
		scheduler.start();
		absorbNode(PreferencesUtil.rootScope());
		for (Preferences jobDefinition : jobInstances.values()) {
			updateJob(jobDefinition);
		}
	}

	/**
	 * updates or creates a job according to the given preferences
	 * 
	 * @param node
	 * @param jobID
	 * @throws SchedulerException
	 */
	public void updateJob(Preferences node) throws SchedulerException {

		jobInstances.put(node.absolutePath(), node);
		if(scheduler==null)
			//not yet activated
			return;
		String jobID = node.absolutePath();
		boolean active = node.getBoolean(JobExecution.PROP_JOB_ACTIVE, false);
		CronTrigger trigger = null;
		try {
			trigger = createSchedule(jobID, node);
		} catch (Exception e) {
			logger.error("Invalid cron expression for job "+node,e);
		}
		removeJob(jobID);
		if(trigger!=null && active)
		{
			scheduler.scheduleJob(createJobDetails(node, jobID), trigger);
		}
	}

	public void runNow(String jobID) throws SchedulerException {
		scheduler.triggerJob(new JobKey(jobID));
	}

	private CronTrigger createSchedule(String jobID, Preferences prefs) {
		String cron = prefs.get(JobExecution.PROP_JOB_SCHEDULE, null);
		if (cron == null || cron.trim().isEmpty())
			return null;
		return TriggerBuilder.newTrigger().forJob(prefs.absolutePath()).withIdentity(prefs.absolutePath()).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

	}

	private JobDetail createJobDetails(Preferences element, String jobID) {
		JobBuilder builder = JobBuilder.newJob(JabylonJob.class).withIdentity(element.absolutePath()).withDescription(element.get(JobExecution.PROP_JOB_DESCRIPTION,null))
				.storeDurably(true);
		try {
			String[] keys = element.keys();
			for (String string : keys) {
				builder.usingJobData(string, element.get(string, null));
			}
		} catch (BackingStoreException e) {
			logger.error("Failed to retrieve properties of node "+element,e);
		}
		JobDataMap extras = new JobDataMap();
		extras.put(JabylonJob.CONNECTOR_KEY,repositoryConnector);
		extras.put(JabylonJob.EXECUTION_KEY, jobDefinitions.get(element.name()));
		extras.put(JabylonJob.DOMAIN_OBJECT_KEY, getDomainObject(element));
		builder.usingJobData(extras);
		return builder.build();
	}

	private Object getDomainObject(Preferences jobConfig) {
		//up one node, and one more to leave the /jobs node
		Preferences domainPrefs = jobConfig.parent().parent();
		String domainPath = domainPrefs.absolutePath();
		String prefix = InstanceScope.INSTANCE.getNode(ApplicationConstants.CONFIG_NODE).absolutePath();
		String path = domainPath.substring(prefix.length(),domainPath.length());
		return uriResolver.resolve(path);
	}

	@Deactivate
	public void deactivate() throws SchedulerException {
		scheduler.shutdown(true);
		jobDefinitions.clear();
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		if (hasChange(event)) {
			if (event.getKey().equals(JobExecution.PROP_JOB_ACTIVE)) {
				if (Boolean.FALSE.equals(event.getNewValue()))
					removeJob(event.getNode().absolutePath());
			}
			try {
				updateJob(event.getNode());
			} catch (SchedulerException e) {
				logger.error("Failed to update job "+event.getNode().absolutePath(),e);
			}
		}
	}

	private boolean hasChange(PreferenceChangeEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (oldValue != null)
			return !oldValue.equals(newValue);
		return oldValue != newValue;
	}

	@Override
	public void added(NodeChangeEvent event) {
		Preferences child = event.getChild();
		try {
			absorbNode(child);
		} catch (SchedulerException e) {
			logger.error("Failed to absorb node "+child,e);
		}
	}

	/**
	 * attaches listeners to the node if necessary and schedules a job if this
	 * node (or a child) contains jobs
	 * 
	 * @param prefs
	 * @throws SchedulerException 
	 */
	protected void absorbNode(Preferences prefs) throws SchedulerException {
		IEclipsePreferences node = toEclipsePreferences(prefs);
		if (node.parent().name().equals(ApplicationConstants.JOBS_NODE_NAME)) {
			updateJob(node);
			node.addPreferenceChangeListener(this);
		} else {
			node.addNodeChangeListener(this);
			String[] children;
			try {
				children = node.childrenNames();
				for (String child : children) {
					absorbNode(node.node(child));					
				}
			} catch (BackingStoreException e) {
				logger.error("Failed to absorb node "+node,e);
			}
		}

	}

	@Override
	public void removed(NodeChangeEvent event) {
		Preferences child = event.getChild();
		String jobID = child.name();
		removeJob(jobID);
	}

	protected void removeJob(String jobID) {
		if(scheduler==null)
			return;
		JobKey triggerKey = new JobKey(jobID);
		try {
			if (scheduler.isStarted() && !scheduler.isShutdown() && scheduler.checkExists(triggerKey)) {
				scheduler.deleteJob(triggerKey);
			}
		} catch (SchedulerException e) {
			logger.error("Failed to delete job " + jobID, e);
		}

	}

	protected IEclipsePreferences toEclipsePreferences(Preferences node) {

		if (node instanceof IEclipsePreferences) {
			IEclipsePreferences pref = (IEclipsePreferences) node;
			return pref;
		}
		return null;
	}

}
