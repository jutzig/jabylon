package de.jutzig.jabylon.scheduler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.quartz.SchedulerException;

import de.jutzig.jabylon.scheduler.internal.JobRegistry;

public class SchedulerActivator implements BundleActivator, ActionListener {

	private static BundleContext context;
	
	public static final String PLUGIN_ID = "de.jutzig.jabylon.scheduler";

	static BundleContext getContext() {
		return context;
	}

	private JobRegistry jobRegistry;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		SchedulerActivator.context = bundleContext;
		
		Timer timer = new Timer(10*1000, this);
		timer.setRepeats(false);
		timer.start(); //initialize the jobs after a minute pause
//		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
//
//		  Scheduler sched = schedFact.getScheduler();
//
//		  sched.start();
//
//		  // define the job and tie it to our HelloJob class
//		  JobDetail job = JobBuilder.newJob().ofType(JabylonJob.class).build();
////		  JobDetail job = new Job(HelloJob.class)
////		      .withIdentity("myJob", "group1")
////		      .build();
//
//		  // Trigger the job to run now, and then every 40 seconds
//		  CronScheduleBuilder.cronSchedule(cronExpression)
//		  Trigger trigger = TriggerBuilder.newTrigger().forJob(job).withSchedule(schedBuilder)
//		      .withIdentity("myTrigger", "group1")
//		      .startNow()
//		      .withSchedule(simpleSchedule()
//		          .withIntervalInSeconds(40)
//		          .repeatForever())
//		      .build();

		  // Tell quartz to schedule the job using our trigger
//		  sched.scheduleJob(job, trigger);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		SchedulerActivator.context = null;
		if(jobRegistry!=null)
			jobRegistry.shutdown();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			registerJobs();
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private void registerJobs() throws SchedulerException {
		jobRegistry = new JobRegistry();
		jobRegistry.register();
	}

}
