/**
 * 
 */
package de.jutzig.jabylon.scheduler.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.spi.MutableTrigger;

import de.jutzig.jabylon.scheduler.SchedulerActivator;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class JobRegistry {

	private Map<IConfigurationElement, JabylonJob> jobMap;
	private Scheduler scheduler;
	private static final String KEY_ACTIVE = "active";
	private static final String KEY_SCHEDULE = "cron";

	public JobRegistry() {

	}

	public void register() throws SchedulerException {

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		scheduler = schedFact.getScheduler();
		scheduler.start();

		IEclipsePreferences root = InstanceScope.INSTANCE.getNode("jobs");
		jobMap = new HashMap<IConfigurationElement, JabylonJob>();
		IConfigurationElement[] iConfigurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				SchedulerActivator.PLUGIN_ID, "jobs");
		for (IConfigurationElement iConfigurationElement : iConfigurationElements) {
			Preferences node = root.node(iConfigurationElement.getAttribute("id"));
			JobDetail detail = createJobDetails(iConfigurationElement);
			String activeByDefault = iConfigurationElement.getAttribute("activeByDefault");
			boolean defaultActive = "true".equals(activeByDefault);
			boolean active = node.getBoolean(KEY_ACTIVE, defaultActive);
			if (active) {
				CronTrigger trigger = createSchedule(detail, node, iConfigurationElement);
				try {
					trigger.getJobDataMap().put(JabylonJob.RUNNABLE_KEY, iConfigurationElement.createExecutableExtension("class"));
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				scheduler.addJob(detail, true);
				scheduler.scheduleJob(trigger);
			}
		}
	}

	private CronTrigger createSchedule(JobDetail detail, Preferences prefs, IConfigurationElement element) {
		String cron = prefs.get(KEY_SCHEDULE, element.getAttribute("defaultSchedule"));
		return TriggerBuilder.newTrigger().forJob(detail).startNow().withIdentity("trigger"+element.getAttribute("id")).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

	}

	private JobDetail createJobDetails(IConfigurationElement element) {
		return JobBuilder.newJob(JabylonJob.class).withIdentity(element.getAttribute("id"))
				.withDescription(element.getAttribute("description")).build();
	}

	public void shutdown() throws SchedulerException {
		scheduler.shutdown(true);
	}

}
