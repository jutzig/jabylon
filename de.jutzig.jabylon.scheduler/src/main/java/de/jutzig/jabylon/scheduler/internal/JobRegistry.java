/**
 * 
 */
package de.jutzig.jabylon.scheduler.internal;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.osgi.service.prefs.Preferences;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.common.util.PreferencesUtil;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@Component(immediate=true,enabled=true)
public class JobRegistry {

	private Scheduler scheduler;
	public static final String KEY_ACTIVE = "active";
	public static final String KEY_SCHEDULE = "cron";
	public static final String KEY_JOBS = "jobs";

	public static final String PLUGIN_ID = "de.jutzig.jabylon.scheduler";
	private static final Logger logger = LoggerFactory.getLogger(JobRegistry.class);
	
	@Reference
	private RepositoryConnector repositoryConnector;
	
	public JobRegistry() {

	}
	
	public void bindRepositoryConnector(RepositoryConnector connector)
	{
		this.repositoryConnector = connector;
	}
	
	public void unbindRepositoryConnector(RepositoryConnector connector)
	{
		this.repositoryConnector = null;
	}

	@Activate
	public void activate() throws SchedulerException {

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		scheduler = schedFact.getScheduler();
		scheduler.start();
		
		Preferences root = PreferencesUtil.workspaceScope().node("jobs");
		IConfigurationElement[] iConfigurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(PLUGIN_ID, KEY_JOBS);
		for (IConfigurationElement iConfigurationElement : iConfigurationElements) {
			String jobID = iConfigurationElement.getAttribute("id");
			Preferences node = root.node(jobID);
			JobDetail detail = createJobDetails(iConfigurationElement);
				
			detail.getJobDataMap().put(JabylonJob.ELEMENT_KEY, iConfigurationElement);
			detail.getJobDataMap().put(JabylonJob.CONNECTOR_KEY, repositoryConnector);
			try {
				scheduler.addJob(detail, true);
			} catch (SchedulerException e) {
				logger.error("Failed to add job "+detail,e);
				continue;
			}
			
			String activeByDefault = iConfigurationElement.getAttribute("activeByDefault");
			boolean defaultActive = Boolean.valueOf(activeByDefault);
			boolean active = node.getBoolean(KEY_ACTIVE, defaultActive);
			CronTrigger trigger = createSchedule(jobID, node, iConfigurationElement);
			if (active) {
				try {
					scheduler.scheduleJob(trigger);
				} catch (SchedulerException e) {
					logger.error("Failed to schedule job "+trigger,e);
					continue;
				}
			}
		}
	}
	
	public void updateJobs() throws SchedulerException{
		Preferences root = PreferencesUtil.workspaceScope().node(KEY_JOBS);
		IConfigurationElement[] iConfigurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(PLUGIN_ID, "jobs");
		for (IConfigurationElement iConfigurationElement : iConfigurationElements) {
			String jobID = iConfigurationElement.getAttribute("id");
			Preferences node = root.node(jobID);
			String activeByDefault = iConfigurationElement.getAttribute("activeByDefault");
			boolean defaultActive = "true".equals(activeByDefault);
			boolean active = node.getBoolean(KEY_ACTIVE, defaultActive);
			CronTrigger trigger = createSchedule(jobID, node, iConfigurationElement);
			TriggerKey triggerKey = new TriggerKey(jobID);
			if (active) {
				if(scheduler.checkExists(triggerKey))
					scheduler.rescheduleJob(triggerKey,trigger);
				else
					scheduler.scheduleJob(trigger);
			}
			else
			{
				if(scheduler.checkExists(triggerKey))
					scheduler.unscheduleJob(triggerKey);
			}
		}
	}
	
	public void runNow(String jobID) throws SchedulerException
	{
		scheduler.triggerJob(new JobKey(jobID));
	}

	private CronTrigger createSchedule(String jobName, Preferences prefs, IConfigurationElement element) {
		String cron = prefs.get(KEY_SCHEDULE, element.getAttribute("defaultSchedule"));
		if(cron==null || cron.trim().isEmpty())
			cron = "0 0 0 * * ?";
		return TriggerBuilder.newTrigger().forJob(jobName).startNow().withIdentity(jobName).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

	}

	private JobDetail createJobDetails(IConfigurationElement element) {
		return JobBuilder.newJob(JabylonJob.class).withIdentity(element.getAttribute("id"))
				.withDescription(element.getAttribute("description")).storeDurably(true).build();
	}

	@Deactivate
	public void deactivate() throws SchedulerException {
		scheduler.shutdown(true);
	}

	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
}
