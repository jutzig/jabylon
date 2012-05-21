package de.jutzig.jabylon.scheduler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.quartz.SchedulerException;

import de.jutzig.jabylon.scheduler.internal.JobRegistry;

public class SchedulerActivator implements BundleActivator {

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
		
		registerJobs();
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

	private void registerJobs() throws SchedulerException {
		jobRegistry = new JobRegistry();
		jobRegistry.register();
	}

}
